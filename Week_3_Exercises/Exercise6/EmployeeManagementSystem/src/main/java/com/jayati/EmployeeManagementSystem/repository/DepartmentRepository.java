package main.java.com.jayati.EmployeeManagementSystem.repository;

import main.java.com.jayati.EmployeeManagementSystem.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
