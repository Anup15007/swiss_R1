package com.company.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Employee {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final BigDecimal salary;
    private final Integer managerId;
    private final List<Employee> subordinates;
    private Employee manager;

    public Employee(int id, String firstName, String lastName, BigDecimal salary, Integer managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
        this.subordinates = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public BigDecimal getSalary() { return salary; }
    public Integer getManagerId() { return managerId; }
    public List<Employee> getSubordinates() { return subordinates; }
    public Employee getManager() { return manager; }
    
    public void setManager(Employee manager) { this.manager = manager; }
    public void addSubordinate(Employee subordinate) { this.subordinates.add(subordinate); }
    
    public boolean isManager() { return !subordinates.isEmpty(); }
    public boolean isCEO() { return managerId == null; }
    
    public String getFullName() { return firstName + " " + lastName; }
    
    @Override
    public String toString() {
        return String.format("%s (%d)", getFullName(), id);
    }
}
