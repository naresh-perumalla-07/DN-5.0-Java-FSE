package com.employee.controller;

import com.employee.entity.Employee;
import com.employee.projection.EmployeeProjection;
import com.employee.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Exercise 4: CRUD - Read all
    @GetMapping
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    // Exercise 4: CRUD - Read by ID
    @GetMapping("/{id}")
    public Employee getById(@PathVariable Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + id));
    }

    // Exercise 4: CRUD - Create
    @PostMapping
    public Employee create(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    // Exercise 4: CRUD - Update
    @PutMapping("/{id}")
    public Employee update(@PathVariable Long id, @RequestBody Employee details) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + id));
        emp.setName(details.getName());
        emp.setEmail(details.getEmail());
        emp.setDepartment(details.getDepartment());
        return employeeRepository.save(emp);
    }

    // Exercise 4: CRUD - Delete
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        employeeRepository.deleteById(id);
        return "Deleted employee: " + id;
    }

    // Exercise 5: Search by department name
    @GetMapping("/search")
    public List<Employee> searchByDepartment(@RequestParam String department) {
        return employeeRepository.findByDepartmentName(department);
    }

    // Exercise 6: Pagination and sorting
    @GetMapping("/page")
    public Page<Employee> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "name") String sortBy) {
        return employeeRepository.findAll(
                PageRequest.of(page, size, Sort.by(sortBy)));
    }

    // Exercise 8: Projections
    @GetMapping("/projections")
    public List<EmployeeProjection> getProjections() {
        return employeeRepository.findAllProjectedBy();
    }
}
