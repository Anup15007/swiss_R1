package com.company;

import com.company.model.AnalysisResult;
import com.company.model.Employee;
import com.company.service.EmployeeCSVParser;
import com.company.service.OrganizationAnalyzer;
import java.io.IOException;
import java.util.List;

public class OrganizationalAnalyzerApp {
    
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java -jar organizational-analyzer.jar <csv-file-path>");
            System.exit(1);
        }
        
        String filePath = args[0];
        
        try {
            EmployeeCSVParser parser = new EmployeeCSVParser();
            OrganizationAnalyzer analyzer = new OrganizationAnalyzer();
            
            List<Employee> employees = parser.parseEmployees(filePath);
            AnalysisResult result = analyzer.analyze(employees);
            
            printResults(result);
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }
    }
    
    private static void printResults(AnalysisResult result) {
        System.out.println("=== ORGANIZATIONAL ANALYSIS RESULTS ===\n");
        
        System.out.println("MANAGERS EARNING LESS THAN THEY SHOULD:");
        if (result.getUnderpaidManagers().isEmpty()) {
            System.out.println("None found.\n");
        } else {
            for (AnalysisResult.SalaryIssue issue : result.getUnderpaidManagers()) {
                System.out.printf("- %s should earn $%.2f more%n", 
                    issue.getEmployee(), issue.getAmount());
            }
            System.out.println();
        }
        
        System.out.println("MANAGERS EARNING MORE THAN THEY SHOULD:");
        if (result.getOverpaidManagers().isEmpty()) {
            System.out.println("None found.\n");
        } else {
            for (AnalysisResult.SalaryIssue issue : result.getOverpaidManagers()) {
                System.out.printf("- %s earns $%.2f too much%n", 
                    issue.getEmployee(), issue.getAmount());
            }
            System.out.println();
        }
        
        System.out.println("EMPLOYEES WITH TOO LONG REPORTING LINES:");
        if (result.getLongReportingLines().isEmpty()) {
            System.out.println("None found.\n");
        } else {
            for (AnalysisResult.ReportingLineIssue issue : result.getLongReportingLines()) {
                System.out.printf("- %s has %d manager level(s) too many%n", 
                    issue.getEmployee(), issue.getExcessLevels());
            }
            System.out.println();
        }
    }
}
