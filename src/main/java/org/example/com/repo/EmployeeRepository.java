package org.example.com.repo;

import org.example.com.domain.Employee;
import org.example.com.type.Dept;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findEmployeeByUsername(String username);

    Optional<List<Employee>> findEmployeeByDept(Dept dept);



}
