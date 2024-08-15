package main.java.com.jayati.EmployeeManagementSystem.repository;


import main.java.com.jayati.EmployeeManagementSystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
