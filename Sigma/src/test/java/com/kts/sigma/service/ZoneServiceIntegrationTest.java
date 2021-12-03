package com.kts.sigma.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.kts.sigma.Exception.ItemExistsException;
import com.kts.sigma.Exception.ItemInUseException;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.constants.TableConstants;
import com.kts.sigma.constants.ZoneConstants;
import com.kts.sigma.dto.TableDTO;
import com.kts.sigma.dto.ZoneDTO;
import com.kts.sigma.model.TableState;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ZoneServiceIntegrationTest {

	@Autowired
	private ZoneService zoneService;
	
	@Test
	public void getAll_ValidState_ReturnsAllZones() {
		List<ZoneDTO> found = zoneService.getAll();
		
		assertEquals(ZoneConstants.DB_TOTAL_ZONES.intValue(), found.size());
	}
	
	@Test(expected = ItemExistsException.class)
	public void createNewZone_NameExists_ThrowsException() {
		ZoneDTO zoneDto = new ZoneDTO();
		zoneDto.setName(ZoneConstants.DB_ZONE_ID_1_NAME);
		
		zoneService.createNewZone(zoneDto);
	}
	
	@Test
	public void createNewZone_ValidName_ReturnsCreatedZone() {
		ZoneDTO zoneDto = new ZoneDTO();
		zoneDto.setName(ZoneConstants.NEW_ZONE_NAME);
		
		Integer beforeAdd = zoneService.getAll().size();
		
		ZoneDTO created = zoneService.createNewZone(zoneDto);
		
		assertEquals(ZoneConstants.NEW_ZONE_NAME, created.getName());
		assertEquals(beforeAdd + 1, zoneService.getAll().size());
		
		zoneService.deleteById(created.getId());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void findById_InvalidId_ThrowsException() {
		zoneService.findById(ZoneConstants.INVALID_ID);
	}
	
	@Test
	public void findById_ValidId_ReturnsZone() {
		ZoneDTO zoneDto = zoneService.findById(ZoneConstants.DB_ZONE_ID_1);
		
		assertEquals(ZoneConstants.DB_ZONE_ID_1_NAME, zoneDto.getName());
		assertEquals(ZoneConstants.DB_ZONE_ID_1, zoneDto.getId());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void removeTableFromZone_InvalidTableId_ThrowsException() {
		TableDTO tableForRemoval = new TableDTO();
		tableForRemoval.setId(TableConstants.INVALID_TABLE_ID);
		tableForRemoval.setState(TableState.FREE);
		
		zoneService.removeTableFromZone(tableForRemoval);
	}
	
	@Test(expected = ItemInUseException.class)
	public void removeTableFromZone_TableStateNotFree_ThrowsException() {
		TableDTO tableForRemoval = new TableDTO();
		tableForRemoval.setId(TableConstants.DB_TABLE_ID_2);
		tableForRemoval.setState(TableState.IN_PROGRESS);
	
		zoneService.removeTableFromZone(tableForRemoval);
	}
	
	@Test
	public void removeTableFromZone_ValidState_ReturnsListOfTablesForZone() {
		Integer beforeRemove = zoneService.getTables(ZoneConstants.DB_ZONE_ID_2).size();
		
		TableDTO tableForRemoval = new TableDTO();
		tableForRemoval.setId(TableConstants.DB_TABLE_ID_6);
		tableForRemoval.setState(TableState.FREE);
		
		ArrayList<TableDTO> tables = zoneService.removeTableFromZone(tableForRemoval);
		
		assertEquals(beforeRemove - 1, tables.size());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void updateNumberChairs_InvalidTableId_ThrowsException() {
		TableDTO tableForUpdate = new TableDTO();
		tableForUpdate.setId(TableConstants.INVALID_TABLE_ID);
		tableForUpdate.setNumberOfChairs(5);
		
		zoneService.updateNumberChairs(tableForUpdate);
	}
	
	@Test
	public void updateNumberChairs_ValidTableId_ReturnsUpdatedTable() {
		TableDTO tableForUpdate = new TableDTO();
		tableForUpdate.setId(TableConstants.DB_TABLE_ID_1);
		tableForUpdate.setNumberOfChairs(5);
		
		TableDTO updatedTable = zoneService.updateNumberChairs(tableForUpdate);
		
		assertEquals(TableConstants.DB_TABLE_ID_1, updatedTable.getId());
		assertEquals(5, updatedTable.getNumberOfChairs().intValue());
	}
	
	@Test
	public void getTables_ValidState_ReturnsListOfTablesForZone() {
		List<TableDTO> found = zoneService.getTables(ZoneConstants.DB_ZONE_ID_1);
		assertEquals(TableConstants.DB_TOTAL_TABLES_IN_ZONE_1.intValue(), found.size());
	}
}
