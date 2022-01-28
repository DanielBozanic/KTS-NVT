package com.kts.sigma.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.kts.sigma.Exception.AccessForbiddenException;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.constants.TableConstants;
import com.kts.sigma.constants.UserContants;
import com.kts.sigma.constants.ZoneConstants;
import com.kts.sigma.dto.TableDTO;
import com.kts.sigma.model.OrderState;
import com.kts.sigma.model.RestaurantOrder;
import com.kts.sigma.model.RestaurantTable;
import com.kts.sigma.model.TableState;
import com.kts.sigma.model.Waiter;
import com.kts.sigma.model.Zone;
import com.kts.sigma.repository.EmployeeRepository;
import com.kts.sigma.repository.OrderRepository;
import com.kts.sigma.repository.TableRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TableServiceUnitTest {

	@Autowired
	TableService tableService;
	
	@MockBean
	TableRepository tableRepositoryMock;
	
	@MockBean
	EmployeeRepository employeeRepositoryMock;
	
	@MockBean
	OrderRepository orderRepositoryMock;
	
	@Test
	public void getAll_ValidState_ReturnsAll() {
		ArrayList<RestaurantTable> tables = new ArrayList<RestaurantTable>();
		
		RestaurantTable table1 = new RestaurantTable();
		table1.setId(TableConstants.DB_TABLE_ID_1);
		tables.add(table1);
		
		RestaurantTable table2 = new RestaurantTable();
		table2.setId(TableConstants.DB_TABLE_ID_2);
		tables.add(table2);
		
		RestaurantTable table3 = new RestaurantTable();
		table3.setId(TableConstants.DB_TABLE_ID_3);
		tables.add(table3);
		
		RestaurantTable table4 = new RestaurantTable();
		table4.setId(TableConstants.DB_TABLE_ID_4);
		tables.add(table4);
		
		RestaurantTable table5 = new RestaurantTable();
		table5.setId(TableConstants.DB_TABLE_ID_5);
		tables.add(table5);
		
		RestaurantTable table6 = new RestaurantTable();
		table6.setId(TableConstants.DB_TABLE_ID_6);
		tables.add(table6);
		
		given(tableRepositoryMock.findAll()).willReturn(tables);
		
		List<TableDTO> found = (List<TableDTO>) tableService.getAll();
		
		verify(tableRepositoryMock, times(1)).findAll();
		
		assertEquals(TableConstants.DB_MAX_TABLE_NUMBER.intValue(), found.size());
	}
	
	@Test
	public void finById_ValidId_ReturnsTable() {
		RestaurantTable table = new RestaurantTable();
		table.setId(TableConstants.DB_TABLE_ID_1);
		
		given(tableRepositoryMock.findById(TableConstants.DB_TABLE_ID_1)).willReturn(Optional.of(table));
		
		TableDTO found = tableService.findById(TableConstants.DB_TABLE_ID_1);
		
		verify(tableRepositoryMock, times(1)).findById(TableConstants.DB_TABLE_ID_1);
		
		assertNotEquals(null, found);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void finById_InValidId_ThrowsException() {
		given(tableRepositoryMock.findById(TableConstants.DB_TABLE_ID_1)).willReturn(Optional.empty());
		
		TableDTO found = tableService.findById(TableConstants.DB_TABLE_ID_1);
	}
	
	@Test
	public void deleteById_ValidId_ReturnsNothing() {
		RestaurantTable table = new RestaurantTable();
		table.setId(TableConstants.DB_TABLE_ID_1);
		
		given(tableRepositoryMock.findById(TableConstants.DB_TABLE_ID_1)).willReturn(Optional.of(table));
		
		tableService.deleteById(TableConstants.DB_TABLE_ID_1);
		
		verify(tableRepositoryMock, times(1)).findById(TableConstants.DB_TABLE_ID_1);
		verify(tableRepositoryMock, times(1)).deleteById(TableConstants.DB_TABLE_ID_1);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void deleteById_InValidId_ThrowsException() {
		given(tableRepositoryMock.findById(TableConstants.DB_TABLE_ID_1)).willReturn(Optional.empty());
		
		tableService.deleteById(TableConstants.DB_TABLE_ID_1);
	}
	
	@Test
	public void changeState_ValidIdValidCode_ReturnsNothing() {
		RestaurantTable table = new RestaurantTable();
		table.setId(TableConstants.DB_TABLE_ID_1);
		
		Waiter waiter = new Waiter();
		waiter.setId(UserContants.DB_EMPLOYEE_ID_1);
		waiter.setCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		given(tableRepositoryMock.findById(TableConstants.DB_TABLE_ID_1)).willReturn(Optional.of(table));
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(waiter);
		
		tableService.changeState(TableConstants.DB_TABLE_ID_1, TableState.FREE, UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		verify(tableRepositoryMock, times(1)).findById(TableConstants.DB_TABLE_ID_1);
		verify(employeeRepositoryMock, times(1)).findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void changeState_InValidId_ThrowsException() {
		Waiter waiter = new Waiter();
		waiter.setId(UserContants.DB_EMPLOYEE_ID_1);
		waiter.setCode(UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		given(tableRepositoryMock.findById(TableConstants.DB_TABLE_ID_1)).willReturn(Optional.empty());
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(waiter);
		
		tableService.changeState(TableConstants.DB_TABLE_ID_1, TableState.FREE, UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	
	@Test(expected = AccessForbiddenException.class)
	public void changeState_InValidCode_ReturnsNothing() {
		RestaurantTable table = new RestaurantTable();
		table.setId(TableConstants.DB_TABLE_ID_1);
		
		given(tableRepositoryMock.findById(TableConstants.DB_TABLE_ID_1)).willReturn(Optional.of(table));
		given(employeeRepositoryMock.findByCode(UserContants.DB_EMPLOYEE_ID_1_CODE)).willReturn(null);
		
		tableService.changeState(TableConstants.DB_TABLE_ID_1, TableState.FREE, UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	
	@Test
	public void addTable_ValidState_ReturnsTable() {
		RestaurantTable table = new RestaurantTable();
		table.setId(TableConstants.DB_TABLE_ID_1);
		
		TableDTO dto = new TableDTO();
		dto.setNumberOfChairs(4);
		
		given(tableRepositoryMock.findMaxTableNumber()).willReturn(TableConstants.DB_MAX_TABLE_NUMBER);
		given(tableRepositoryMock.save(Mockito.any())).willReturn(table);
		
		TableDTO found = tableService.addTable(dto);
		
		verify(tableRepositoryMock, times(1)).findMaxTableNumber();
		verify(tableRepositoryMock, times(1)).save(Mockito.any());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void updateTablePosition_InvalidTableId_ThrowsException() {
		given(tableRepositoryMock.findById(-100)).willReturn(Optional.ofNullable(null));
		
		TableDTO tableDto = new TableDTO();
		tableDto.setId(-100);
		
		tableService.updateTablePosition(tableDto);
		
		verify(tableRepositoryMock, times(1)).findById(-100);
	}
	
	@Test
	public void updateTablePosition_FreeReservedAndChargedTables_ReturnsUpdatedTable() {
		RestaurantTable table1 = new RestaurantTable();
		table1.setId(TableConstants.DB_TABLE_ID_1);
		table1.setState(TableState.FREE);
		table1.setX(50);
		table1.setY(50);
		
		RestaurantTable savedTable = new RestaurantTable();
		savedTable.setId(TableConstants.DB_TABLE_ID_1);
		savedTable.setState(TableState.FREE);
		savedTable.setX(100);
		savedTable.setY(300);

		given(tableRepositoryMock.findById(TableConstants.DB_TABLE_ID_1)).willReturn(Optional.of(table1));
		given(tableRepositoryMock.save(any(RestaurantTable.class))).willReturn(savedTable);
		
		TableDTO tableDto = new TableDTO();
		tableDto.setId(TableConstants.DB_TABLE_ID_1);
		tableDto.setX(100);
		tableDto.setY(300);

		TableDTO updatedTable = tableService.updateTablePosition(tableDto);
		
		verify(tableRepositoryMock, times(1)).findById(ZoneConstants.DB_ZONE_ID_1);
		verify(tableRepositoryMock, times(1)).save(table1);
		
		assertEquals(100, updatedTable.getX());
		assertEquals(300, updatedTable.getY());
	}
	
	@Test
	public void updateTablePosition_InProgressToDeliverAndDoneTables_ReturnsUpdatedTable() {
		List<RestaurantOrder> orders = new ArrayList<RestaurantOrder>();
		RestaurantOrder order1 = new RestaurantOrder();
		order1.setId(1);
		order1.setState(OrderState.CHARGED);
		
		RestaurantOrder order2 = new RestaurantOrder();
		order2.setId(2);
		order2.setState(OrderState.IN_PROGRESS);
		
		RestaurantOrder order3 = new RestaurantOrder();
		order3.setId(3);
		order3.setState(OrderState.IN_PROGRESS);
		
		RestaurantOrder order4 = new RestaurantOrder();
		order4.setId(4);
		order4.setState(OrderState.CHARGED);
		
		orders.add(order1);
		orders.add(order2);
		orders.add(order3);
		orders.add(order4);
		
		RestaurantTable table1 = new RestaurantTable();
		table1.setId(TableConstants.DB_TABLE_ID_1);
		table1.setState(TableState.IN_PROGRESS);
		table1.setX(50);
		table1.setY(50);
		
		RestaurantTable savedTable = new RestaurantTable();
		savedTable.setId(TableConstants.DB_TABLE_ID_1);
		savedTable.setState(TableState.IN_PROGRESS);
		savedTable.setX(100);
		savedTable.setY(300);
		
		given(tableRepositoryMock.findById(TableConstants.DB_TABLE_ID_1)).willReturn(Optional.of(table1));
		given(tableRepositoryMock.save(any(RestaurantTable.class))).willReturn(savedTable);
		given(orderRepositoryMock.findByTableId(TableConstants.DB_TABLE_ID_1)).willReturn(orders);
		
		TableDTO tableDto = new TableDTO();
		tableDto.setId(TableConstants.DB_TABLE_ID_1);
		tableDto.setX(100);
		tableDto.setY(300);

		TableDTO updatedTable = tableService.updateTablePosition(tableDto);
		
		verify(tableRepositoryMock, times(1)).findById(ZoneConstants.DB_ZONE_ID_1);
		verify(tableRepositoryMock, times(1)).save(table1);
		verify(orderRepositoryMock, times(1)).findByTableId(TableConstants.DB_TABLE_ID_1);
		
		assertEquals(100, updatedTable.getX());
		assertEquals(300, updatedTable.getY());
	}

}
