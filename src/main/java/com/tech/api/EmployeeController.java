package com.tech.api;

import com.tech.domain.Employee;
import com.tech.exception.EmployeeNotFoundException;
import com.tech.service.IEmployeeService;
import com.tech.utils.GlobalPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(GlobalPath.VERSION_API)
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    IEmployeeService employeeService;

    @PostMapping(GlobalPath.EMPLOYEE_PATH)
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        try {
            Employee savedEmployee = employeeService.addEmployee(employee);
            return new ResponseEntity<>(savedEmployee, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("invalid data");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(GlobalPath.EMPLOYEE_PATH)
    public ResponseEntity<List<Employee>> getAllEmployees() {

        try {
            List<Employee> employeeList = employeeService.getAllEmployees();
            return new ResponseEntity<>(employeeList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(GlobalPath.EMPLOYEE_BY_ID_PATH)
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("employeeId") Long employeeId) {

        try {
            Employee employee = employeeService.getEmployeeById(employeeId);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(GlobalPath.UPDATED_EMPLOYEE_PATH)
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee body, @PathVariable("employeeId") String employeeId) {
        try {
            Employee result = employeeService.updateEmployee(body);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(GlobalPath.EMPLOYEE_BY_ID_PATH)
    public void deleteEmployee(@PathVariable("employeeId") Long employeeId ){
        employeeService.deteleEmployeeById(employeeId);
    }
}
