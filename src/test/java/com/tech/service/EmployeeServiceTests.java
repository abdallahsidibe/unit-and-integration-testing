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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


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
                .id(1L)
                .firstName("Dupont")
                .lastName("Pedro")
                .email("pedro@gmail.com")
                .build();

    }

    @DisplayName("JUnit test for addEmployee method")
    @Test
    public void givenEmployeeObject_whenAddEmployee_thenReturnEmployeeObject() throws EmployeeNotFoundException {

        //Given
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        //Given
//        lenient().when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.empty());
//        lenient().when(employeeRepository.save(employee)).thenReturn(employee);

//        System.out.println(employeeRepository);
//        System.out.println(employeeService);

//        Mockito.when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.empty());
//        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);

        // When
        Employee savedEmployee = employeeService.addEmployee(employee);
        System.out.println(savedEmployee);

        // Then
        assertThat(savedEmployee).isNotNull();
    }

    @DisplayName("JUnit test addEmployee method which throw exception ")
    @Test
    public void givenExistingEmail_whenAddEmployee_thenThrowException() {
        // Given
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        // When
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.addEmployee(employee);
        });

        // Then
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @DisplayName("JUnit test getAllEmployees method  ")
    @Test
    public void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeeList() throws EmployeeNotFoundException {
        // Given
        Employee employee1 = Employee.builder()
                .firstName("Alexandre")
                .lastName("Dumas")
                .email("dumas@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        // When
        List<Employee> employeeList = employeeService.getAllEmployees();

        // Then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getAllEmployees method (negative scenario)")
    @Test
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {
        // Given
        Employee employee1 = Employee.builder()
                .firstName("alpha")
                .lastName("Stark")
                .email("alpha@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for getEmployeeById method")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws EmployeeNotFoundException {
        // given
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        // when
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId());

        // then
        assertThat(savedEmployee).isNotNull();

    }


    @DisplayName("JUnit test for updateEmployee method")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        // given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);

        employee.setEmail("alves@gmail.com");
        employee.setFirstName("Ali");
        // when -  action or the behaviour that we are going test
        Employee updatedEmployee = employeeService.updateEmployee(employee, 1L);

        // then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("alves@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Ali");
    }


    @DisplayName("JUnit test for deleteEmployee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing(){
        // given - precondition or setup
        long employeeId = 1L;

        willDoNothing().given(employeeRepository).deleteById(employeeId);

        // when -  action or the behaviour that we are going test
        employeeService.deleteEmployeeById(employeeId);

        // then - verify the output
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }


}
