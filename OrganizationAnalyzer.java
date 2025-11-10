package com.company.service;

import com.company.model.AnalysisResult;
import com.company.model.Employee;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrganizationAnalyzer {
    private static final BigDecimal MIN_MANAGER_MULTIPLIER = new BigDecimal("1.20");
    private static final BigDecimal MAX_MANAGER_MULTIPLIER = new BigDecimal("1.50");
    private static final int MAX_REPORTING_LEVELS = 4;

    public AnalysisResult analyze(List<Employee> employees) {
        Map<Integer, Employee> employeeMap = buildEmployeeMap(employees);
        buildHierarchy(employees, employeeMap);
        
        AnalysisResult result = new AnalysisResult();
        
        for (Employee employee : employees) {
            if (employee.isManager()) {
                analyzeSalary(employee, result);
            }
            analyzeReportingLine(employee, result);
        }
        
        return result;
    }
    
    private Map<Integer, Employee> buildEmployeeMap(List<Employee> employees) {
        Map<Integer, Employee> map = new HashMap<>();
        for (Employee employee : employees) {
            map.put(employee.getId(), employee);
        }
        return map;
    }
    
    private void buildHierarchy(List<Employee> employees, Map<Integer, Employee> employeeMap) {
        for (Employee employee : employees) {
            if (employee.getManagerId() != null) {
                Employee manager = employeeMap.get(employee.getManagerId());
                if (manager != null) {
                    employee.setManager(manager);
                    manager.addSubordinate(employee);
                }
            }
        }
    }
    
    private void analyzeSalary(Employee manager, AnalysisResult result) {
        BigDecimal avgSubordinateSalary = calculateAverageSubordinateSalary(manager);
        BigDecimal minExpectedSalary = avgSubordinateSalary.multiply(MIN_MANAGER_MULTIPLIER);
        BigDecimal maxExpectedSalary = avgSubordinateSalary.multiply(MAX_MANAGER_MULTIPLIER);
        
        if (manager.getSalary().compareTo(minExpectedSalary) < 0) {
            BigDecimal shortfall = minExpectedSalary.subtract(manager.getSalary());
            result.addUnderpaidManager(manager, shortfall);
        } else if (manager.getSalary().compareTo(maxExpectedSalary) > 0) {
            BigDecimal excess = manager.getSalary().subtract(maxExpectedSalary);
            result.addOverpaidManager(manager, excess);
        }
    }
    
    private BigDecimal calculateAverageSubordinateSalary(Employee manager) {
        List<Employee> subordinates = manager.getSubordinates();
        if (subordinates.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal total = subordinates.stream()
            .map(Employee::getSalary)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        return total.divide(new BigDecimal(subordinates.size()), 2, RoundingMode.HALF_UP);
    }
    
    private void analyzeReportingLine(Employee employee, AnalysisResult result) {
        int levels = countManagerLevels(employee);
        if (levels > MAX_REPORTING_LEVELS) {
            result.addLongReportingLine(employee, levels - MAX_REPORTING_LEVELS);
        }
    }
    
    private int countManagerLevels(Employee employee) {
        int count = 0;
        Employee current = employee.getManager();
        while (current != null) {
            count++;
            current = current.getManager();
        }
        return count;
    }
}
