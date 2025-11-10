package com.company.service;

import com.company.model.AnalysisResult;
import com.company.model.Employee;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class OrganizationAnalyzerTest {
    
    @Test
    public void testUnderpaidManager() {
        Employee ceo = new Employee(1, "CEO", "Boss", new BigDecimal("100000"), null);
        Employee manager = new Employee(2, "Manager", "Underpaid", new BigDecimal("50000"), 1);
        Employee emp1 = new Employee(3, "Employee", "One", new BigDecimal("45000"), 2);
        Employee emp2 = new Employee(4, "Employee", "Two", new BigDecimal("47000"), 2);
        
        List<Employee> employees = Arrays.asList(ceo, manager, emp1, emp2);
        
        OrganizationAnalyzer analyzer = new OrganizationAnalyzer();
        AnalysisResult result = analyzer.analyze(employees);
        
        assertEquals(1, result.getUnderpaidManagers().size());
        assertEquals("Manager", result.getUnderpaidManagers().get(0).getEmployee().getFirstName());
    }
    
    @Test
    public void testOverpaidManager() {
        Employee ceo = new Employee(1, "CEO", "Boss", new BigDecimal("100000"), null);
        Employee manager = new Employee(2, "Manager", "Overpaid", new BigDecimal("80000"), 1);
        Employee emp1 = new Employee(3, "Employee", "One", new BigDecimal("40000"), 2);
        Employee emp2 = new Employee(4, "Employee", "Two", new BigDecimal("42000"), 2);
        
        List<Employee> employees = Arrays.asList(ceo, manager, emp1, emp2);
        
        OrganizationAnalyzer analyzer = new OrganizationAnalyzer();
        AnalysisResult result = analyzer.analyze(employees);
        
        assertEquals(1, result.getOverpaidManagers().size());
        assertEquals("Manager", result.getOverpaidManagers().get(0).getEmployee().getFirstName());
    }
    
    @Test
    public void testLongReportingLine() {
        Employee ceo = new Employee(1, "CEO", "Boss", new BigDecimal("100000"), null);
        Employee mgr1 = new Employee(2, "Manager", "One", new BigDecimal("80000"), 1);
        Employee mgr2 = new Employee(3, "Manager", "Two", new BigDecimal("70000"), 2);
        Employee mgr3 = new Employee(4, "Manager", "Three", new BigDecimal("60000"), 3);
        Employee mgr4 = new Employee(5, "Manager", "Four", new BigDecimal("55000"), 4);
        Employee emp = new Employee(6, "Employee", "Deep", new BigDecimal("45000"), 5);
        
        List<Employee> employees = Arrays.asList(ceo, mgr1, mgr2, mgr3, mgr4, emp);
        
        OrganizationAnalyzer analyzer = new OrganizationAnalyzer();
        AnalysisResult result = analyzer.analyze(employees);
        
        assertEquals(1, result.getLongReportingLines().size());
        assertEquals("Employee", result.getLongReportingLines().get(0).getEmployee().getFirstName());
        assertEquals(1, result.getLongReportingLines().get(0).getExcessLevels());
    }
}
