package com.employee.repository;

import com.employee.entity.Employee;
import com.employee.projection.EmployeeProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Exercise 3: Derived query methods
    List<Employee> findByName(String name);
    List<Employee> findByDepartmentId(Long departmentId);
    List<Employee> findByEmailContaining(String keyword);

    // Exercise 5: Custom query with @Query
    @Query("SELECT e FROM Employee e WHERE e.department.name = :deptName")
    List<Employee> findByDepartmentName(@Param("deptName") String departmentName);

    // Exercise 5: Named query (defined on Entity)
    List<Employee> findByDepartmentName(String deptName, Pageable pageable);

    // Exercise 6: Pagination
    Page<Employee> findByNameContaining(String name, Pageable pageable);

    // Exercise 8: Interface-based projection
    @Query("SELECT e.name AS name, e.email AS email FROM Employee e")
    List<EmployeeProjection> findAllProjectedBy();
}
