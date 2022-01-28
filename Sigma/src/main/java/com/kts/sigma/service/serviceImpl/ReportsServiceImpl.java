package com.kts.sigma.service.serviceImpl;

import com.kts.sigma.Exception.DateNotValidException;
import com.kts.sigma.dto.EmployeeDTO;
import com.kts.sigma.dto.ItemInOrderDTO;
import com.kts.sigma.dto.OrderDTO;
import com.kts.sigma.dto.ReportRequestDTO;
import com.kts.sigma.model.ItemInOrder;
import com.kts.sigma.model.OrderState;
import com.kts.sigma.model.Report;
import com.kts.sigma.repository.ItemInOrderRepository;
import com.kts.sigma.service.OrderService;
import com.kts.sigma.service.ReportsService;
import com.kts.sigma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class ReportsServiceImpl implements ReportsService {
    private UserService userService; //check the pays
    private OrderService orderService; //check the payments.. tj total.. gledam itemInOrder, pa item in meenu za selling price
    
    @Autowired
    private ItemInOrderRepository itemRepository;

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
        long monthsBetween = ChronoUnit.MONTHS.between(startDate, endDate) + 1;

        if(startMonth == -1){
            throw new DateNotValidException(request.startMonth);
        }else if(endMonth == -1) {
            throw new DateNotValidException(request.startMonth);
        }

        Report report = new Report();
        List<BigDecimal> expenses = new ArrayList<>();
        List<BigDecimal> sales = new ArrayList<>();

        //for expenses
        LocalDateTime currentDate = startDate;
        for(int i = 1; i <= monthsBetween; i++){
            BigDecimal totalPayEmployees = BigDecimal.ZERO; //total for one month
            for (EmployeeDTO employee : allUsers){
                if(employee.isActive() && employee.getDateOfEmployment().isBefore(currentDate)){
                    totalPayEmployees = totalPayEmployees.add(employee.getPaymentBigDecimal());
                }
            }
            expenses.add(totalPayEmployees);
            sales.add(BigDecimal.ZERO);
            currentDate = currentDate.plusMonths(1);
        }

        for (OrderDTO order: restaurantOrders){
            if(!isWithinRange(startDate,endDate,order.getOrderDateTime())){
                continue;
            }
            if(order.getState() != OrderState.CHARGED){
                continue;
            }
            int index = Math.toIntExact(ChronoUnit.MONTHS.between(startDate, order.getOrderDateTime()));
            BigDecimal value = sales.get(index);
            sales.set(index, value.add(order.getTotalPrice()));
            
            value = expenses.get(index);
            for (ItemInOrderDTO i : order.getItems()) {
				ItemInOrder item = itemRepository.findById(i.getId()).orElse(null);
				
				if(item != null) {
					value = value.add(item.getItem().getItem().getBuyingPrice());
				}
			}
            expenses.set(index, value);
        }

        report.setExpensesPerMonth(expenses);
        report.setSalesPerMonth(sales);
        report.setStartMonth(startDate);
        report.setEndMonth(endDate);
        return report;
    }
}
