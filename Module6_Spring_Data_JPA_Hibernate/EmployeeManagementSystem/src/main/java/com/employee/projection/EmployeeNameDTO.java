package com.employee.projection;

// Exercise 8: Class-based projection (DTO)
public class EmployeeNameDTO {

    private final String name;
    private final String email;

    public EmployeeNameDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
}
