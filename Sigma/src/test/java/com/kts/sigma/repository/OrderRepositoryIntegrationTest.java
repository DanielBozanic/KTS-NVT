package com.kts.sigma.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.kts.sigma.constants.TableConstants;
import com.kts.sigma.model.RestaurantOrder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderRepositoryIntegrationTest {

	@Autowired
	OrderRepository orderRepository;
	
	@Test
	public void findById_ValidId_ReturnsOrders() {
		List<RestaurantOrder> orders = orderRepository.findByTableId(TableConstants.DB_TABLE_ID_1);
		assertEquals(1, orders.size());
	}
	
	@Test
	public void findById_InValidId_ReturnsOrders() {
		List<RestaurantOrder> orders = orderRepository.findByTableId(TableConstants.INVALID_TABLE_ID);
		assertEquals(0, orders.size());
	}
}
