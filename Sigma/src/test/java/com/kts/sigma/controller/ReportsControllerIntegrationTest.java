package com.kts.sigma.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import com.kts.sigma.dto.ReportRequestDTO;
import com.kts.sigma.model.Report;
import com.kts.sigma.service.ReportsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.kts.sigma.constants.ItemConstants;
import com.kts.sigma.constants.ItemInMenuConstants;
import com.kts.sigma.constants.MenuConstants;
import com.kts.sigma.dto.ItemDTO;
import com.kts.sigma.dto.MenuDTO;
import com.kts.sigma.service.MenuService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ReportsControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ReportsService menuService;

    @Test
    public void post_ValidState_ReturnsReport() {
        ResponseEntity<Report> responseEntity = restTemplate
                .getForEntity("/reports", Report.class);

        Report report = responseEntity.getBody();

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, responseEntity.getStatusCode());
    }

}
