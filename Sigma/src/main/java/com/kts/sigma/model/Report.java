package com.kts.sigma.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class Report {

    private LocalDateTime startMonth;

    private LocalDateTime endMonth;

    private List<BigDecimal> salesPerMonth;

    private List<BigDecimal> expensesPerMonth;

    public LocalDateTime getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(LocalDateTime startMonth) {
        this.startMonth = startMonth;
    }

    public LocalDateTime getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(LocalDateTime endMonth) {
        this.endMonth = endMonth;
    }

    public List<BigDecimal> getSalesPerMonth() {
        return salesPerMonth;
    }

    public void setSalesPerMonth(List<BigDecimal> salesPerMonth) {
        this.salesPerMonth = salesPerMonth;
    }

    public List<BigDecimal> getExpensesPerMonth() {
        return expensesPerMonth;
    }

    public void setExpensesPerMonth(List<BigDecimal> expensesPerMonth) {
        this.expensesPerMonth = expensesPerMonth;
    }
}
