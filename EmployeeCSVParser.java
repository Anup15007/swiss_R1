package com.company.service;

import com.company.model.Employee;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EmployeeCSVParser {
    
    public List<Employee> parseEmployees(String filePath) throws IOException {
        List<Employee> employees = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                Employee employee = parseEmployeeLine(line);
                if (employee != null) {
                    employees.add(employee);
                }
            }
        }
        
        return employees;
    }
    
    private Employee parseEmployeeLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != 5) {
            return null;
        }
        
        try {
            int id = Integer.parseInt(parts[0].trim());
            String firstName = parts[1].trim();
            String lastName = parts[2].trim();
            BigDecimal salary = new BigDecimal(parts[3].trim());
            Integer managerId = parts[4].trim().isEmpty() ? null : Integer.parseInt(parts[4].trim());
            
            return new Employee(id, firstName, lastName, salary, managerId);
        } catch (NumberFormatException e) {
            System.err.println("Invalid line format: " + line);
            return null;
        }
    }
}
