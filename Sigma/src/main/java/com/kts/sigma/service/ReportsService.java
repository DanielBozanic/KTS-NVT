package com.kts.sigma.service;

import com.kts.sigma.dto.ReportRequestDTO;
import com.kts.sigma.model.Report;


public interface ReportsService {
    public Report getReports(ReportRequestDTO request);
}
