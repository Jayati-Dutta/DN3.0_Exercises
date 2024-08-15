package com.jayati.EmployeeManagementSystem.repository;

import com.jayati.employeemanagementsystem.entity.Employee;

import main.java.com.jayati.EmployeeManagementSystem.projection.EmployeeProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByName(String name);

    @Query("SELECT e FROM Employee e WHERE e.email = :email")
    Employee findByEmail(@Param("email") String email);
}
