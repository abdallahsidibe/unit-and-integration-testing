package com.tech.service.impl;

import com.tech.domain.Employee;
import com.tech.exception.EmployeeNotFoundException;
import com.tech.repository.EmployeeRepository;
import com.tech.service.IEmployeeService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements IEmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;


    /**
     * @param employee
     * @return saved employee
     */
    @Override
    public Employee addEmployee(Employee employee) throws EmployeeNotFoundException {
        logger.info("saving new employee");
        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (savedEmployee.isPresent()){
            try {
                throw new EmployeeNotFoundException("Employee already exist with given email:  "+ employee.getEmail());
            } catch (EmployeeNotFoundException e) {
                throw new EmployeeNotFoundException("Employee not exist with given email");
            }
        }
        return employeeRepository.save(employee);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Employee getEmployeeById(Long id) throws EmployeeNotFoundException {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found !"));
    }

    /**
     * @return employee List
     */
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * @param body
     * @return updated Employee
     */
    @Override
    public Employee updateEmployee(Employee body, Long id) {


        Optional<Employee> employee = employeeRepository.findById(id);

        Employee updatedEmployee = new Employee();

        if (employee.isPresent()) {
            Employee result = employee.get();
            result.setFirstName(body.getFirstName());
            result.setLastName(body.getLastName());
            result.setEmail(body.getEmail());

            updatedEmployee = employeeRepository.save(result);
        }

        return updatedEmployee;
    }

    /**
     * @param id
     */
    @Override
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }
}
