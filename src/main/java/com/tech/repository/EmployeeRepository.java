package com.tech.repository;

import com.tech.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(" select u from Employee u where u.email = :email")
    Optional<Employee> findByEmail(@Param("email") String email);

}
