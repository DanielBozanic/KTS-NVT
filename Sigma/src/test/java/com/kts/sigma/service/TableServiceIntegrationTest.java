package com.kts.sigma.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.kts.sigma.Exception.AccessForbiddenException;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.constants.TableConstants;
import com.kts.sigma.constants.UserContants;
import com.kts.sigma.constants.ZoneConstants;
import com.kts.sigma.dto.TableDTO;
import com.kts.sigma.model.TableState;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class TableServiceIntegrationTest {

	@Autowired
	TableService tableService;
	
	@Test
	public void getAll_ValidState_ReturnsAllTables() {
		List<TableDTO> found = (List<TableDTO>) tableService.getAll();
		
		assertEquals(TableConstants.DB_MAX_TABLE_NUMBER.intValue(), found.size());
	}
	
	@Test
	public void findById_ValidId_ReturnsTable() {
		TableDTO found = tableService.findById(TableConstants.DB_TABLE_ID_1);
		
		assertNotNull(found);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void findById_InValidId_ThrowsException() {
		tableService.findById(TableConstants.INVALID_TABLE_ID);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void deleteById_ValidId_ReturnsNothing() {
		int found = ((List<TableDTO>) tableService.getAll()).size();
		
		tableService.deleteById(TableConstants.DB_TABLE_ID_4);
		
		int found2 = ((List<TableDTO>) tableService.getAll()).size();
		
		assertEquals(found - 1, found2);
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void deleteById_InValidId_ThrowsException() {
		tableService.deleteById(TableConstants.INVALID_TABLE_ID);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void addTable_ValidState_ReturnsNothing() {
		int found = ((List<TableDTO>) tableService.getAll()).size();
		
		TableDTO table = new TableDTO();
		table.setNumberOfChairs(4);
		table.setState(TableState.FREE);
		table.setZoneId(ZoneConstants.DB_ZONE_ID_1);
		
		tableService.addTable(table);
		
		int found2 = ((List<TableDTO>) tableService.getAll()).size();
		
		assertEquals(found + 1, found2);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void changeState_ValidIdValidCode_ReturnsNothing() {
		tableService.changeState(TableConstants.DB_TABLE_ID_1, TableState.IN_PROGRESS, UserContants.DB_EMPLOYEE_ID_1_CODE);
		
		TableState found = tableService.findById(TableConstants.DB_TABLE_ID_1).getState();
		
		assertEquals(found, TableState.IN_PROGRESS);
	}

	@Test(expected = ItemNotFoundException.class)
	public void changeState_InValidId_ThrowsException() {
		tableService.changeState(TableConstants.INVALID_TABLE_ID, TableState.IN_PROGRESS, UserContants.DB_EMPLOYEE_ID_1_CODE);
	}
	
	@Test(expected = AccessForbiddenException.class)
	public void changeState_InValidCode_ThrowsException() {
		tableService.changeState(TableConstants.DB_TABLE_ID_1, TableState.IN_PROGRESS, UserContants.INVALID_EMPLOYEE_CODE);
	}
}
