package com.tech.service;

import com.tech.domain.Employee;
import com.tech.exception.EmployeeNotFoundException;
import com.tech.repository.EmployeeRepository;
import com.tech.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    // Mock  va permettre de simuler le EmployeeRepository en reproduisant son comportement,
    //    il est possible aussi de le faire manuellement de cette façon
    //    EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class)
    @Mock
    private EmployeeRepository employeeRepository;

    //  InjectMocks va créer l’objet EmployeeServiceImpl et non une simulation, utile si l’on teste cette instance de classe
    //  ou que le corps d’une méthode de cette classe doit être exécuté.
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup() {
//        employeeRepository = Mockito.mock(EmployeeRepository.class);
//        employeeService = new EmployeeServiceImpl(employeeRepository);

        employee = Employee.builder()
                .firstName("Dupont")
                .lastName("Pedro")
                .email("pedro@gmail.com")
                .build();

//        employee.setId(1L);
//        employee.setFirstName("Dupont");
//        employee.setLastName("Pedro");
//        employee.setEmail("pedro@gmail.com");
    }

    @DisplayName("JUnit test for addEmployee method")
    @Test
    public void givenEmployeeObject_whenAddEmployee_thenReturnEmployeeObject() {

        //Given
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());

        given(employeeRepository.save(employee)).willReturn(employee);

        //Given
//        lenient().when(employeeRepository.findByEmail(employee.getEmail()))
//                .thenReturn(Optional.empty());
//
//        lenient().when(employeeRepository.save(employee)).thenReturn(employee);
//
//        System.out.println(employeeRepository);
//        System.out.println(employeeService);

        // When
        Employee savedEmployee = employeeService.addEmployee(employee);
        System.out.println(savedEmployee);

        // Then
        assertThat(savedEmployee).isNotNull();
    }

}
