package com.kts.sigma.controller;

import com.kts.sigma.dto.ReportRequestDTO;
import com.kts.sigma.model.Report;
import com.kts.sigma.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportsController {
    @Autowired
    private ReportsService reportsService;

    @PostMapping("")
    ResponseEntity<Report> post(@RequestBody ReportRequestDTO newEntity) {
        return new ResponseEntity<>(reportsService.getReports(newEntity), HttpStatus.OK);
    }

}
