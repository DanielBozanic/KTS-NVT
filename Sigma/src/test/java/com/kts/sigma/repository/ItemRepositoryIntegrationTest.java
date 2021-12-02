package com.kts.sigma.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.kts.sigma.model.Item;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemRepositoryIntegrationTest {

	@Autowired
	private ItemRepository itemRepository;
	
	@Test
	public void search_ValidSearchTerm_ReturnsItem() {
		ArrayList<Item> items = itemRepository.search("hett");
		assertEquals("Spaghetti", items.get(0).getName());
	}
}
