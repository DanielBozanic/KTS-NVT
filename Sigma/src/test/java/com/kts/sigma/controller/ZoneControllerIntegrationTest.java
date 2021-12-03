package com.kts.sigma.controller;

import static org.junit.Assert.assertEquals;

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

import com.kts.sigma.constants.TableConstants;
import com.kts.sigma.constants.ZoneConstants;
import com.kts.sigma.dto.TableDTO;
import com.kts.sigma.dto.ZoneDTO;
import com.kts.sigma.model.TableState;
import com.kts.sigma.service.ZoneService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ZoneControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ZoneService zoneService;
	
	@Test
	public void getAll_ValidState_ReturnsAllZones() {
		ResponseEntity<ZoneDTO[]> responseEntity = restTemplate
				.getForEntity("/zones/getAll", ZoneDTO[].class);

		ZoneDTO[] zones = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(ZoneConstants.DB_TOTAL_ZONES.intValue(), zones.length);
	}
	
	@Test
	public void createNewZone_NameExists_ReturnsBadRequest() {
		ZoneDTO zoneDto = new ZoneDTO();
		zoneDto.setName(ZoneConstants.DB_ZONE_ID_1_NAME);
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(
				"/zones/createNewZone", zoneDto, String.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void createNewZone_ValidName_ReturnsCreatedZone() {
		Integer beforeAdd = zoneService.getAll().size();
		
		ZoneDTO zoneDto = new ZoneDTO();
		zoneDto.setName(ZoneConstants.NEW_ZONE_NAME);
		
		ResponseEntity<ZoneDTO> responseEntity = restTemplate.postForEntity(
				"/zones/createNewZone", zoneDto, ZoneDTO.class);
		
		ZoneDTO created = responseEntity.getBody();
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(ZoneConstants.NEW_ZONE_NAME, created.getName());
		assertEquals(beforeAdd + 1, zoneService.getAll().size());
		
		zoneService.deleteById(created.getId());
	}
	
	@Test
	public void findById_InvalidId_ReturnsNotFound() {
		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity("/zones/" + ZoneConstants.INVALID_ID, String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void findById_ValidId_ReturnsZone() {
		ResponseEntity<ZoneDTO> responseEntity = restTemplate
				.getForEntity("/zones/" + ZoneConstants.DB_ZONE_ID_1, ZoneDTO.class);
		
		ZoneDTO zoneDto = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(ZoneConstants.DB_ZONE_ID_1_NAME, zoneDto.getName());
		assertEquals(ZoneConstants.DB_ZONE_ID_1, zoneDto.getId());
	}
	
	@Test
	public void removeTableFromZone_InvalidTableId_ReturnsNotFound() {
		TableDTO tableForRemoval = new TableDTO();
		tableForRemoval.setId(TableConstants.INVALID_TABLE_ID);
		tableForRemoval.setState(TableState.FREE);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"/zones/removeTableFromZone", HttpMethod.DELETE, 
				new HttpEntity<TableDTO>(tableForRemoval), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void removeTableFromZone_TableStateNotFree_ReturnsBadRequest() {
		TableDTO tableForRemoval = new TableDTO();
		tableForRemoval.setId(TableConstants.DB_TABLE_ID_2);
		tableForRemoval.setState(TableState.IN_PROGRESS);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"/zones/removeTableFromZone", HttpMethod.DELETE, 
				new HttpEntity<TableDTO>(tableForRemoval), String.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
	public void removeTableFromZone_ValidState_ReturnsListOfTablesForZone() {
		Integer beforeRemove = zoneService.getTables(ZoneConstants.DB_ZONE_ID_2).size();
		
		TableDTO tableForRemoval = new TableDTO();
		tableForRemoval.setId(TableConstants.DB_TABLE_ID_6);
		tableForRemoval.setState(TableState.FREE);
		
		ResponseEntity<TableDTO[]> responseEntity = restTemplate.exchange(
				"/zones/removeTableFromZone", HttpMethod.DELETE, 
				new HttpEntity<TableDTO>(tableForRemoval), TableDTO[].class);
		
		TableDTO[] tables = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(beforeRemove - 1, tables.length);
	}
	
	@Test
	public void updateNumberChairs_InvalidTableId_ReturnsNotFound() {
		TableDTO tableForUpdate = new TableDTO();
		tableForUpdate.setId(TableConstants.INVALID_TABLE_ID);
		tableForUpdate.setNumberOfChairs(5);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"/zones/updateNumberChairs", HttpMethod.PUT, 
				new HttpEntity<TableDTO>(tableForUpdate), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void updateNumberChairs_ValidTableId_ReturnsUpdatedTable() {
		TableDTO tableForUpdate = new TableDTO();
		tableForUpdate.setId(TableConstants.DB_TABLE_ID_1);
		tableForUpdate.setNumberOfChairs(5);
		
		ResponseEntity<TableDTO> responseEntity = restTemplate.exchange(
				"/zones/updateNumberChairs", HttpMethod.PUT, 
				new HttpEntity<TableDTO>(tableForUpdate), TableDTO.class);
		
		TableDTO updatedTable = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(TableConstants.DB_TABLE_ID_1, updatedTable.getId());
		assertEquals(5, updatedTable.getNumberOfChairs().intValue());
	}
	
	@Test
	public void getTables_ValidState_ReturnsListOfTablesForZone() {
		ResponseEntity<TableDTO[]> responseEntity = restTemplate
				.getForEntity("/zones/tables/" + ZoneConstants.DB_ZONE_ID_1, TableDTO[].class);
		
		TableDTO[] found = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(2, found.length);
	}
}
