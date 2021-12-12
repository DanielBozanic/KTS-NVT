package com.kts.sigma.service;

import com.kts.sigma.service.serviceImpl.JwtUserDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class JwtUserDetailsServiceUnitTest {
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Test(expected = UsernameNotFoundException.class)
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        jwtUserDetailsService.loadUserByUsername("usernameWrong");
    }

    @Test
    public void shouldGenerateAuthToken() throws Exception {
        jwtUserDetailsService.loadUserByUsername("admin");
    }

}
