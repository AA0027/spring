package org.example.com.repo;

import org.example.com.domain.Employee;
import org.example.com.type.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findEmployeeByUsername(String username);
    @Query(value = """
    select e from Employee  e where e.username in :ids
""")
    Optional<List<Employee>> findEmpByList(@Param("ids") List<String> usernames);

    Optional<List<Employee>> findEmployeeByDept(Dept dept);

    @Query(value = """
        select e from Employee e order by e.dept
    """)
    Optional<List<Employee>> findEmployees();

    Optional<List<Employee>> findEmployeeByNameContainsIgnoreCase(String name);



}
