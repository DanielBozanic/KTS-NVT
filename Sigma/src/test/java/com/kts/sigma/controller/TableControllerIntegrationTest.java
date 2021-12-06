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
import com.kts.sigma.constants.UserContants;
import com.kts.sigma.constants.ZoneConstants;
import com.kts.sigma.dto.EmployeeDTO;
import com.kts.sigma.dto.TableDTO;
import com.kts.sigma.dto.ZoneDTO;
import com.kts.sigma.model.RestaurantTable;
import com.kts.sigma.model.TableState;
import com.kts.sigma.repository.TableRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class TableControllerIntegrationTest {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private TableRepository tableRepository;
	
	@Test
	public void getAll_ValidState_ReturnsAllTables() {
		ResponseEntity<TableDTO[]> responseEntity = restTemplate
				.getForEntity("/tables", TableDTO[].class);

		TableDTO[] tables = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(TableConstants.DB_MAX_TABLE_NUMBER.intValue(), tables.length);
	}
	
	@Test
	public void findById_InvalidId_ReturnsNotFound() {
		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity("/tables/" + TableConstants.INVALID_TABLE_ID, String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void findById_ValidId_ReturnsTable() {
		ResponseEntity<TableDTO> responseEntity = restTemplate
				.getForEntity("/tables/" + TableConstants.DB_TABLE_ID_1, TableDTO.class);
		
		TableDTO table = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(TableConstants.DB_TABLE_ID_1, table.getId());
	}
	
	@Test
	public void deleteById_InvalidId_ReturnsNotFound() {
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/tables/" + TableConstants.INVALID_TABLE_ID, HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void deleteById_ValidId_ReturnsNotFound() {
		RestaurantTable table = new RestaurantTable();
		table = tableRepository.save(table);
		
		assertEquals(tableRepository.findAll().size(), TableConstants.DB_MAX_TABLE_NUMBER+1);
		
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/tables/" + table.getId(), HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(tableRepository.findAll().size(), TableConstants.DB_MAX_TABLE_NUMBER.intValue());
	}
	
	@Test
	public void changeState_InvalidId_ReturnsNotFound() {
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/tables/" + TableConstants.INVALID_TABLE_ID + "/IN_PROGRESS/" + UserContants.DB_EMPLOYEE_ID_1_CODE, 
						HttpMethod.PUT, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void changeState_InvalidCode_ReturnsForbidden() {
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/tables/" + TableConstants.DB_TABLE_ID_1 + "/IN_PROGRESS/" + UserContants.INVALID_EMPLOYEE_CODE, 
						HttpMethod.PUT, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}
	
	@Test
	public void changeState_ValidIdValidCode_ReturnsNothing() {
		RestaurantTable table = new RestaurantTable();
		table = tableRepository.save(table);
		
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/tables/" + table.getId() + "/IN_PROGRESS/" + UserContants.DB_EMPLOYEE_ID_1_CODE, 
						HttpMethod.PUT, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(TableState.IN_PROGRESS, tableRepository.findById(TableConstants.DB_MAX_TABLE_NUMBER + 1).get().getState());
		
		tableRepository.deleteById(table.getId());
	}
	
	@Test
	public void addTable_ValidState_ReturnsTable() {
		TableDTO dto = new TableDTO();
		dto.setState(TableState.FREE);
		dto.setNumberOfChairs(4);
		dto.setZoneId(1);
		
		ResponseEntity<TableDTO> responseEntity = restTemplate.postForEntity(
				"/tables/addTable", dto,
				TableDTO.class);
		
		TableDTO table = responseEntity.getBody();
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(TableConstants.DB_MAX_TABLE_NUMBER.intValue() + 1, table.getTableNumber().intValue());
	}
}
