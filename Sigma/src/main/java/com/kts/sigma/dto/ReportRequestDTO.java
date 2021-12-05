package com.kts.sigma.dto;

import java.time.LocalDateTime;

public class ReportRequestDTO {

    public LocalDateTime startMonth;

    public LocalDateTime endMonth;

    public ReportRequestDTO(LocalDateTime startMonth, LocalDateTime endMonth) {
        this.startMonth = startMonth;
        this.endMonth = endMonth;
    }
}
