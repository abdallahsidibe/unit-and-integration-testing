package com.tech.service;

import com.tech.domain.Employee;
import com.tech.exception.EmployeeNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    Employee addEmployee(Employee employee);

    Employee getEmployeeById(Long id) throws EmployeeNotFoundException;

    List<Employee> getAllEmployees();

    Employee updateEmployee(Employee employee);

    void deteleEmployeeById(Long id);

}
