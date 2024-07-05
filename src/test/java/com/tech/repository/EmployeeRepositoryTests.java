package com.tech.repository;

import com.tech.domain.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .firstName("messi")
                .lastName("andre")
                .email("messi@gmail.com")
                .build();
    }

    // JUnit test for save employee operation
    @DisplayName("JUnit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

        // Given - precondition or setup
        employee = Employee.builder()
                .firstName("messi")
                .lastName("andre")
                .email("messi@gmail.com")
                .build();

        // When - action or the behaviour we are going to test
        Employee savedEmployee = employeeRepository.save(employee);

        // Then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit test for get all employee operation")
    @Test
    public void givenEmployeesList_whenFindAll_thenEmployeeList() {

        // Given - precondition or setup
        Employee employee1 = Employee.builder()
                .firstName("aziz")
                .lastName("dupont")
                .email("dupont@gmail.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        // When - action or the behaviour we are going to test
        List<Employee> employeeList = employeeRepository.findAll();

        // Then - verify output
        assertThat(employeeList).isNotEmpty();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @DisplayName("Junit test for find employee operation")
    @Test
    public void givenEmployeeObject_whenFindById_returnEmployeeObject() {

        // Given - precondition or setup
        Employee savedEmployee = employeeRepository.save(employee);

        // When - action or the behaviour we are going to test
        //Optional<Employee> employee1 = employeeRepository.findById(savedEmployee.getId());
        Employee employee1 = employeeRepository.findById(savedEmployee.getId()).get();

        // Then - verify output
        assertThat(employee1).isNotNull();
    }

    @DisplayName("Junit test for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_returnUpdateEmployee() {

        // Given - precondition or setup
        Employee savedEmployee = employeeRepository.save(employee);
        Employee employee1 = employeeRepository.findById(savedEmployee.getId()).get();


        // When - action or the behaviour we are going to test
        employee1.setFirstName("messi1");
        employee1.setLastName("andre1");
        employee1.setEmail("messi1@gmail.com");
        Employee updatedEmployee = employeeRepository.save(employee1);

        // Then - verify output
        assertThat(updatedEmployee.getFirstName()).isEqualTo("messi1");
        assertThat(updatedEmployee.getLastName()).isEqualTo("andre1");
        assertThat(updatedEmployee.getEmail()).isEqualTo("messi1@gmail.com");
    }

    @DisplayName("Junit test for delete employee operation")
    @Test
    public void givenEmployeeObject_whenDeleteEmployee_returnRemoveEmployee() {

        // Given - precondition or setup
        Employee savedEmployee = employeeRepository.save(employee);

        // When - action or the behaviour we are going to test
        employeeRepository.deleteById(savedEmployee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(savedEmployee.getId());

        // Then - verify output
        assertThat(employeeOptional).isEmpty();
    }
}
