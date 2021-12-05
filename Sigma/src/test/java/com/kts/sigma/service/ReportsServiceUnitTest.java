package com.kts.sigma.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import com.kts.sigma.dto.*;
import com.kts.sigma.model.*;
import com.kts.sigma.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.kts.sigma.Exception.AccessForbiddenException;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.Exception.ItemNotValidException;
import com.kts.sigma.constants.ItemConstants;
import com.kts.sigma.constants.ItemInMenuConstants;
import com.kts.sigma.constants.ItemInOrderConstants;
import com.kts.sigma.constants.MenuConstants;
import com.kts.sigma.constants.OrderConstants;
import com.kts.sigma.constants.TableConstants;
import com.kts.sigma.constants.UserContants;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportsServiceUnitTest {
    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    ReportsService reportsService;

    @MockBean
    OrderRepository orderRepositoryMock;

    @MockBean
    UserRepository userRepositoryMock;

    @Test
    public void getReports_ValidState_ReturnsReports() {
        String str = "12-02-2021 12:30";
        String str2 = "12-08-2021 12:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime startDate = LocalDateTime.parse(str, formatter);
        LocalDateTime endDate = LocalDateTime.parse(str2, formatter);
        Report report = new Report();
    }

    @Test(expected = ItemNotFoundException.class)
    public void getReports_InvalidDateThrowsException() {
        String str = "12-02-2021 12:30";
        String str2 = "12-08-2021 12:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime startDate = LocalDateTime.parse(str, formatter);
        LocalDateTime endDate = LocalDateTime.parse(str2, formatter);
        given(orderRepositoryMock.findById(OrderConstants.INVALID_ORDER_ID)).willReturn(Optional.empty());
        orderService.findById(OrderConstants.INVALID_ORDER_ID);
        verify(orderRepositoryMock, times(0)).findById(OrderConstants.INVALID_ORDER_ID);
    }




}

