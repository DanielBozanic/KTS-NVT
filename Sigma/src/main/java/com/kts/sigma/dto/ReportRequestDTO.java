package com.kts.sigma.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ReportRequestDTO {

    public LocalDateTime startMonth;

    public LocalDateTime endMonth;

    private List<BigDecimal> salesPerMonth;

    private List<BigDecimal> expensesPerMonth;

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
}
