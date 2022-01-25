package com.kts.sigma.service.serviceImpl;

import com.kts.sigma.Exception.DateNotValidException;
import com.kts.sigma.dto.EmployeeDTO;
import com.kts.sigma.dto.OrderDTO;
import com.kts.sigma.dto.ReportRequestDTO;
import com.kts.sigma.model.Report;
import com.kts.sigma.service.OrderService;
import com.kts.sigma.service.ReportsService;
import com.kts.sigma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class ReportsServiceImpl implements ReportsService {
    private UserService userService; //check the pays
    private OrderService orderService; //check the payments.. tj total.. gledam itemInOrder, pa item in meenu za selling price

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    Date localDateTimeToDate(LocalDateTime temp){
        ZonedDateTime zdt = temp.atZone(ZoneId.systemDefault());
        Date output = Date.from(zdt.toInstant());
        return output;
    }

    boolean isWithinRange(LocalDateTime startDate, LocalDateTime endDate, LocalDateTime testDate) {
        Date start = localDateTimeToDate(startDate);
        Date end = localDateTimeToDate(endDate);
        Date test = localDateTimeToDate(testDate);
        return !(test.before(start) || test.after(end));
    }

    LocalDateTime getCorrectDate(LocalDateTime temp){
        int hour = temp.getHour();
        return temp.plusHours(24 - hour);
    }

    @Override
    public Report getReports(ReportRequestDTO request) {
        List<EmployeeDTO> allUsers = (List<EmployeeDTO>) userService.getAllEmployees();
        List<OrderDTO> restaurantOrders = (List<OrderDTO>) orderService.getAll();


        LocalDateTime startDate = getCorrectDate(request.startMonth);
        LocalDateTime endDate = getCorrectDate(request.endMonth);

        if(startDate == null){
            throw new DateNotValidException(request.startMonth);
        }else if(endDate == null) {
            throw new DateNotValidException(request.startMonth);
        }

        Integer startMonth = startDate.getMonthValue();
        Integer endMonth = endDate.getMonthValue();

        //check year
        if(startMonth == -1){
            throw new DateNotValidException(request.startMonth);
        }else if(endMonth == -1) {
            throw new DateNotValidException(request.startMonth);
        }

        Report report = new Report();
        List<BigDecimal> expenses = new ArrayList<>();
        List<BigDecimal> sales = new ArrayList<>();

        //add the check if they're not suspended during that period or not hired
        BigDecimal totalPayEmployees = BigDecimal.ZERO; //total for one month
        for (EmployeeDTO employee : allUsers){
            if(employee.isActive()){
                totalPayEmployees = totalPayEmployees.add(employee.getPaymentBigDecimal());
            }
        }

        for(int i = startMonth.intValue(); i <= endMonth.intValue(); i++){
            expenses.add(totalPayEmployees); //added pay expenses
            sales.add(BigDecimal.ZERO);
        }

        for (OrderDTO order: restaurantOrders){
            Integer month = order.getOrderDateTime().getMonthValue();
            if(!isWithinRange(startDate,endDate,order.getOrderDateTime())){
                continue;
            }
            int index = month - startMonth;
            BigDecimal value = sales.get(index);
            sales.set(index, value.add(order.getTotalPrice()));
            // add prices of item to expenses
        }

        report.setExpensesPerMonth(expenses);
        report.setSalesPerMonth(sales);
        report.setStartMonth(startDate);
        report.setEndMonth(endDate);
        return report;
    }
}
