package com.company.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AnalysisResult {
    private final List<SalaryIssue> underpaidManagers = new ArrayList<>();
    private final List<SalaryIssue> overpaidManagers = new ArrayList<>();
    private final List<ReportingLineIssue> longReportingLines = new ArrayList<>();

    public void addUnderpaidManager(Employee manager, BigDecimal shortfall) {
        underpaidManagers.add(new SalaryIssue(manager, shortfall));
    }

    public void addOverpaidManager(Employee manager, BigDecimal excess) {
        overpaidManagers.add(new SalaryIssue(manager, excess));
    }

    public void addLongReportingLine(Employee employee, int excess) {
        longReportingLines.add(new ReportingLineIssue(employee, excess));
    }

    public List<SalaryIssue> getUnderpaidManagers() { return underpaidManagers; }
    public List<SalaryIssue> getOverpaidManagers() { return overpaidManagers; }
    public List<ReportingLineIssue> getLongReportingLines() { return longReportingLines; }

    public static class SalaryIssue {
        private final Employee employee;
        private final BigDecimal amount;

        public SalaryIssue(Employee employee, BigDecimal amount) {
            this.employee = employee;
            this.amount = amount;
        }

        public Employee getEmployee() { return employee; }
        public BigDecimal getAmount() { return amount; }
    }

    public static class ReportingLineIssue {
        private final Employee employee;
        private final int excessLevels;

        public ReportingLineIssue(Employee employee, int excessLevels) {
            this.employee = employee;
            this.excessLevels = excessLevels;
        }

        public Employee getEmployee() { return employee; }
        public int getExcessLevels() { return excessLevels; }
    }
}
