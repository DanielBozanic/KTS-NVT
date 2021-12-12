package com.kts.sigma.controller;

import com.kts.sigma.constants.ZoneConstants;
import com.kts.sigma.dto.ZoneDTO;
import com.kts.sigma.model.JwtResponse;
import com.kts.sigma.security.JwtTokenUtil;
import com.kts.sigma.service.serviceImpl.JwtUserDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class JwtControllerIntegrationTest {
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createAuthenticationToken_ValidState_ReturnsOK() {
//        ResponseEntity<JwtResponse> responseEntity = restTemplate
//                .getForEntity("/authenticate", JwtResponse.class);
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
