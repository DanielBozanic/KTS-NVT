package com.kts.sigma.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.kts.sigma.repository.OrderRepository;
import com.kts.sigma.repository.TableRepository;
import com.kts.sigma.repository.ZoneRepository;
import com.kts.sigma.Exception.ItemExistsException;
import com.kts.sigma.Exception.ItemInUseException;
import com.kts.sigma.Exception.ItemNotFoundException;
import com.kts.sigma.constants.ZoneConstants;
import com.kts.sigma.constants.TableConstants;
import com.kts.sigma.dto.TableDTO;
import com.kts.sigma.dto.ZoneDTO;
import com.kts.sigma.model.OrderState;
import com.kts.sigma.model.RestaurantOrder;
import com.kts.sigma.model.RestaurantTable;
import com.kts.sigma.model.TableState;
import com.kts.sigma.model.Zone;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ZoneServiceUnitTest {

	@Autowired
	private ZoneService zoneService;
	
	@MockBean
	private ZoneRepository zoneRepositoryMock;
	
	@MockBean
	private TableRepository tableRepositoryMock;
	
	@MockBean
	private OrderRepository orderRepositoryMock;
	
	@Test
	public void getAll_ValidState_ReturnsAllZones() {
		List<Zone> zones = new ArrayList<Zone>();
		
		Zone zone1 = new Zone();
		zone1.setId(ZoneConstants.DB_ZONE_ID_1);
		
		Zone zone2 = new Zone();
		zone2.setId(ZoneConstants.DB_ZONE_ID_2);
		
		zones.add(zone1);
		zones.add(zone2);
		
		given(zoneRepositoryMock.findAll()).willReturn(zones);
		
		List<ZoneDTO> found = zoneService.getAll();
		
		verify(zoneRepositoryMock, times(1)).findAll();
		
		assertEquals(ZoneConstants.DB_TOTAL_ZONES.intValue(), found.size());
	}
	
	@Test(expected = ItemExistsException.class)
	public void createNewZone_NameExists_ThrowsException() {
		Zone zone = new Zone();
		zone.setId(ZoneConstants.DB_ZONE_ID_1);
		zone.setName(ZoneConstants.DB_ZONE_ID_1_NAME);
		
		given(zoneRepositoryMock.findByName(ZoneConstants.DB_ZONE_ID_1_NAME)).willReturn(zone);
		
		ZoneDTO zoneDto = new ZoneDTO();
		zoneDto.setName(ZoneConstants.DB_ZONE_ID_1_NAME);
		
		zoneService.createNewZone(zoneDto);
		
		verify(zoneRepositoryMock, times(1)).findByName(ZoneConstants.DB_ZONE_ID_1_NAME);
	}
	
	@Test
	public void createNewZone_ValidName_ReturnsCreatedZone() {
		Zone savedZone = new Zone();
		savedZone.setId(ZoneConstants.NEW_ZONE_ID);
		savedZone.setName(ZoneConstants.NEW_ZONE_NAME);
		
		given(zoneRepositoryMock.findByName(ZoneConstants.NEW_ZONE_NAME)).willReturn(null);
		given(zoneRepositoryMock.save(any(Zone.class))).willReturn(savedZone);
		
		ZoneDTO zoneDto = new ZoneDTO();
		zoneDto.setName(ZoneConstants.NEW_ZONE_NAME);
		
		ZoneDTO created = zoneService.createNewZone(zoneDto);
		
		verify(zoneRepositoryMock, times(1)).findByName(ZoneConstants.NEW_ZONE_NAME);
		verify(zoneRepositoryMock, times(1)).save(any(Zone.class));
		
		assertEquals(ZoneConstants.NEW_ZONE_NAME, created.getName());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void findById_InvalidId_ThrowsException() {
		given(zoneRepositoryMock.findById(ZoneConstants.INVALID_ID)).willReturn(Optional.ofNullable(null));
		
		zoneService.findById(ZoneConstants.INVALID_ID);
		
		verify(zoneRepositoryMock, times(1)).findById(ZoneConstants.INVALID_ID);
	}
	
	@Test
	public void findById_ValidId_ReturnsZone() {
		Zone zone = new Zone();
		zone.setId(ZoneConstants.DB_ZONE_ID_1);
		zone.setName(ZoneConstants.DB_ZONE_ID_1_NAME);
		
		given(zoneRepositoryMock.findById(ZoneConstants.DB_ZONE_ID_1)).willReturn(Optional.of(zone));
		
		ZoneDTO zoneDto = zoneService.findById(ZoneConstants.DB_ZONE_ID_1);
		
		verify(zoneRepositoryMock, times(1)).findById(ZoneConstants.DB_ZONE_ID_1);
		
		assertEquals(ZoneConstants.DB_ZONE_ID_1, zoneDto.getId());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void removeTableFromZone_InvalidTableId_ThrowsException() {
		given(tableRepositoryMock.getTableByIdAndState(TableConstants.INVALID_TABLE_ID, TableState.FREE)).willReturn(null);
		
		TableDTO tableForRemoval = new TableDTO();
		tableForRemoval.setId(TableConstants.INVALID_TABLE_ID);
		tableForRemoval.setState(TableState.FREE);
		
		zoneService.removeTableFromZone(tableForRemoval);
		
		verify(tableRepositoryMock, times(1)).getTableByIdAndState(TableConstants.INVALID_TABLE_ID, TableState.FREE);
	}
	
	@Test(expected = ItemInUseException.class)
	public void removeTableFromZone_TableStateNotFree_ThrowsException() {
		Zone zone = new Zone();
		zone.setId(ZoneConstants.DB_ZONE_ID_1);
		
		RestaurantTable table = new RestaurantTable();
		table.setId(TableConstants.DB_TABLE_ID_1);
		table.setState(TableState.IN_PROGRESS);
		table.setZone(zone);
		
		given(tableRepositoryMock.getTableByIdAndState(TableConstants.DB_TABLE_ID_1, TableState.IN_PROGRESS)).willReturn(table);
		
		TableDTO tableForRemoval = new TableDTO();
		tableForRemoval.setId(TableConstants.DB_TABLE_ID_1);
		tableForRemoval.setState(TableState.IN_PROGRESS);

		zoneService.removeTableFromZone(tableForRemoval);
		
		verify(tableRepositoryMock, times(1)).getTableByIdAndState(TableConstants.DB_TABLE_ID_1, TableState.IN_PROGRESS);
	}
	
	@Test
	public void removeTableFromZone_ValidState_ReturnsListOfTablesForZone() {
		Zone zone = new Zone();
		zone.setId(ZoneConstants.DB_ZONE_ID_1);
		
		ArrayList<RestaurantTable> tablesForZone1 = new ArrayList<RestaurantTable>();
		
		RestaurantTable table2 = new RestaurantTable();
		table2.setId(TableConstants.DB_TABLE_ID_2);
		table2.setState(TableState.FREE);
		table2.setZone(zone);
		
		tablesForZone1.add(table2);
		
		RestaurantTable table = new RestaurantTable();
		table.setId(TableConstants.DB_TABLE_ID_1);
		table.setState(TableState.FREE);
		table.setZone(zone);
		
		RestaurantTable savedTable = new RestaurantTable();
		savedTable.setId(TableConstants.DB_TABLE_ID_1);
		savedTable.setState(TableState.FREE);
		savedTable.setZone(null);
		
		given(tableRepositoryMock.getTableByIdAndState(TableConstants.DB_TABLE_ID_1, TableState.FREE)).willReturn(table);
		given(tableRepositoryMock.save(any(RestaurantTable.class))).willReturn(savedTable);
		given(tableRepositoryMock.findByZoneId(ZoneConstants.DB_ZONE_ID_1)).willReturn(tablesForZone1);
		
		TableDTO tableForRemoval = new TableDTO();
		tableForRemoval.setId(TableConstants.DB_TABLE_ID_1);
		tableForRemoval.setState(TableState.FREE);
		
		ArrayList<TableDTO> tables = zoneService.removeTableFromZone(tableForRemoval);
		
		verify(tableRepositoryMock, times(1)).getTableByIdAndState(TableConstants.DB_TABLE_ID_1, TableState.FREE);
		verify(tableRepositoryMock, times(1)).save(any(RestaurantTable.class));
		verify(tableRepositoryMock, times(1)).findByZoneId(ZoneConstants.DB_ZONE_ID_1);
		
		assertEquals(tablesForZone1.size(), tables.size());
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void updateNumberChairs_InvalidTableId_ThrowsException() {
		given(tableRepositoryMock.findById(TableConstants.INVALID_TABLE_ID)).willReturn(Optional.ofNullable(null));
		
		TableDTO tableForUpdate = new TableDTO();
		tableForUpdate.setId(TableConstants.INVALID_TABLE_ID);
		tableForUpdate.setNumberOfChairs(5);
		
		zoneService.updateNumberChairs(tableForUpdate);
		
		verify(tableRepositoryMock, times(1)).findById(TableConstants.INVALID_TABLE_ID);
	}
	
	@Test
	public void updateNumberChairs_ValidTableId_ReturnsUpdatedTable() {
		RestaurantTable table = new RestaurantTable();
		table.setId(TableConstants.DB_TABLE_ID_1);
		table.setNumberOfChairs(4);
		
		RestaurantTable savedTable = new RestaurantTable();
		savedTable.setId(TableConstants.DB_TABLE_ID_1);
		savedTable.setNumberOfChairs(5);
		
		given(tableRepositoryMock.findById(TableConstants.DB_TABLE_ID_1)).willReturn(Optional.of(table));
		given(tableRepositoryMock.save(any(RestaurantTable.class))).willReturn(savedTable);
		
		TableDTO tableForUpdate = new TableDTO();
		tableForUpdate.setId(TableConstants.DB_TABLE_ID_1);
		tableForUpdate.setNumberOfChairs(5);
		
		TableDTO updatedTable = zoneService.updateNumberChairs(tableForUpdate);
		
		verify(tableRepositoryMock, times(1)).findById(TableConstants.DB_TABLE_ID_1);
		verify(tableRepositoryMock, times(1)).save(any(RestaurantTable.class));
		
		assertEquals(5, updatedTable.getNumberOfChairs().intValue());
	}
	
	@Test
	public void getTables_FreeReservedAndChargedTables_ReturnsListOfTablesForZone() {
		Zone zone = new Zone();
		zone.setId(ZoneConstants.DB_ZONE_ID_1);
		
		ArrayList<RestaurantTable> tablesForZone1 = new ArrayList<RestaurantTable>();
		
		RestaurantTable table1 = new RestaurantTable();
		table1.setId(TableConstants.DB_TABLE_ID_1);
		table1.setState(TableState.FREE);
		table1.setZone(zone);
		
		RestaurantTable table2 = new RestaurantTable();
		table2.setId(TableConstants.DB_TABLE_ID_2);
		table2.setState(TableState.FREE);
		table2.setZone(zone);
		
		tablesForZone1.add(table1);
		tablesForZone1.add(table2);
		
		given(tableRepositoryMock.findByZoneId(ZoneConstants.DB_ZONE_ID_1)).willReturn(tablesForZone1);
		
		List<TableDTO> found = zoneService.getTables(ZoneConstants.DB_ZONE_ID_1);
		
		verify(tableRepositoryMock, times(1)).findByZoneId(ZoneConstants.DB_ZONE_ID_1);
		
		assertEquals(tablesForZone1.size(), found.size());
	}
	
	@Test
	public void getTables_InProgressToDeliverAndDoneTables_ReturnsListOfTablesForZone() {
		Zone zone = new Zone();
		zone.setId(ZoneConstants.DB_ZONE_ID_1);
		
		ArrayList<RestaurantTable> tablesForZone1 = new ArrayList<RestaurantTable>();
		
		RestaurantTable table1 = new RestaurantTable();
		table1.setId(TableConstants.DB_TABLE_ID_1);
		table1.setState(TableState.IN_PROGRESS);
		table1.setZone(zone);
		
		RestaurantTable table2 = new RestaurantTable();
		table2.setId(TableConstants.DB_TABLE_ID_2);
		table2.setState(TableState.DONE);
		table2.setZone(zone);
		
		tablesForZone1.add(table1);
		tablesForZone1.add(table2);
		
		List<RestaurantOrder> orders1 = new ArrayList<RestaurantOrder>();
		List<RestaurantOrder> orders2 = new ArrayList<RestaurantOrder>();
		
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
		
		
		orders1.add(order1);
		orders1.add(order2);
		orders2.add(order3);
		orders2.add(order4);

		given(tableRepositoryMock.findByZoneId(ZoneConstants.DB_ZONE_ID_1)).willReturn(tablesForZone1);
		given(orderRepositoryMock.findByTableId(TableConstants.DB_TABLE_ID_1)).willReturn(orders1);
		given(orderRepositoryMock.findByTableId(TableConstants.DB_TABLE_ID_2)).willReturn(orders2);
		
		List<TableDTO> found = zoneService.getTables(ZoneConstants.DB_ZONE_ID_1);
		
		verify(tableRepositoryMock, times(1)).findByZoneId(ZoneConstants.DB_ZONE_ID_1);
		verify(orderRepositoryMock, times(1)).findByTableId(TableConstants.DB_TABLE_ID_1);
		verify(orderRepositoryMock, times(1)).findByTableId(TableConstants.DB_TABLE_ID_2);
		
		assertEquals(tablesForZone1.size(), found.size());
	}
}
