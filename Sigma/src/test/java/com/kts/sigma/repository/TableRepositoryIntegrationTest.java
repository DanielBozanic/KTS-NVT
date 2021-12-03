package com.kts.sigma.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.kts.sigma.constants.TableConstants;
import com.kts.sigma.constants.ZoneConstants;
import com.kts.sigma.model.RestaurantTable;
import com.kts.sigma.model.TableState;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class TableRepositoryIntegrationTest {

	@Autowired
	private TableRepository tableRepository;
	
	@Test
	public void findByZoneId_InvalidZoneId_Returns_EmptyList() {
		ArrayList<RestaurantTable> tables = tableRepository.findByZoneId(ZoneConstants.INVALID_ID);
		assertEquals(0, tables.size());
	}
	
	@Test
	public void findByZoneId_ValidZoneId_ReturnListOfTables() {
		ArrayList<RestaurantTable> tables = tableRepository.findByZoneId(ZoneConstants.DB_ZONE_ID_1);
		assertEquals(TableConstants.DB_TOTAL_TABLES_IN_ZONE_1.intValue(), tables.size());
	}
	
	@Test
	public void getTableByIdAndState_InvalidTableId_ReturnsNull() {
		RestaurantTable table = tableRepository.getTableByIdAndState(TableConstants.INVALID_TABLE_ID, TableState.FREE);
		assertNull(table);
	}
	
	@Test
	public void getTableByIdAndState_ValidTableId_ReturnsTable() {
		RestaurantTable table = tableRepository.getTableByIdAndState(TableConstants.DB_TABLE_ID_1, TableState.FREE);
		
		assertEquals(TableConstants.DB_TABLE_ID_1, table.getId());
		assertEquals(TableState.FREE, table.getState());
	}
	
	@Test
	public void findByTableNumber_InvalidTableNumber_ReturnsNull() {
		RestaurantTable table = tableRepository.findByTableNumber(-100);
		assertNull(table);
	}
	
	@Test
	public void findByTableNumber_ValidTableNumber_ReturnsTable() {
		RestaurantTable table = tableRepository.findByTableNumber(1);
		assertEquals(1, table.getTableNumber().intValue());
	}
	
	@Test
	public void findMaxTableNumber_TablesPresentInDatabase_ReturnsMaxTableNumber() {
		Integer maxNumber = tableRepository.findMaxTableNumber();
		assertEquals(TableConstants.DB_MAX_TABLE_NUMBER, maxNumber);
	}
	
}
