package com.kts.sigma;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.kts.sigma.controller.ItemInMenuControllerIntegrationTest;
import com.kts.sigma.controller.ItemInOrderControllerIntegrationTest;
import com.kts.sigma.controller.JwtControllerIntegrationTest;
import com.kts.sigma.controller.MenuControllerIntegrationTest;
import com.kts.sigma.controller.OrderControllerIntegrationtest;
import com.kts.sigma.controller.ReportsControllerIntegrationTest;
import com.kts.sigma.controller.TableControllerIntegrationTest;
import com.kts.sigma.controller.UserControllerIntegrationTest;
import com.kts.sigma.controller.ZoneControllerIntegrationTest;
import com.kts.sigma.repository.EmployeeRepositoryIntegrationTest;
import com.kts.sigma.repository.ItemInMenuRepositoryIntegrationTest;
import com.kts.sigma.repository.ItemRepositoryIntegrationTest;
import com.kts.sigma.repository.MenuRepositoryIntegrationTest;
import com.kts.sigma.repository.OrderRepositoryIntegrationTest;
import com.kts.sigma.repository.PaymentRepositoryIntegrationTest;
import com.kts.sigma.repository.TableRepositoryIntegrationTest;
import com.kts.sigma.service.ItemInMenuServiceUnitTest;
import com.kts.sigma.service.ItemInOrderServiceUnitTest;
import com.kts.sigma.service.ItemServiceUnitTest;
import com.kts.sigma.service.JwtUserDetailsServiceUnitTest;
import com.kts.sigma.service.MenuServiceIntegrationTest;
import com.kts.sigma.service.MenuServiceUnitTest;
import com.kts.sigma.service.OrderServiceIntegrationTest;
import com.kts.sigma.service.OrderServiceUnitTest;
import com.kts.sigma.service.ReportsServiceUnitTest;
import com.kts.sigma.service.TableServiceIntegrationTest;
import com.kts.sigma.service.TableServiceUnitTest;
import com.kts.sigma.service.UserServiceIntegrationTest;
import com.kts.sigma.service.UserServiceUnitTest;
import com.kts.sigma.service.ZoneServiceIntegrationTest;
import com.kts.sigma.service.ZoneServiceUnitTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	//Repository tests
	EmployeeRepositoryIntegrationTest.class,
	ItemInMenuRepositoryIntegrationTest.class,
	ItemRepositoryIntegrationTest.class,
	MenuRepositoryIntegrationTest.class,
	OrderRepositoryIntegrationTest.class,
	PaymentRepositoryIntegrationTest.class,
	TableRepositoryIntegrationTest.class,
	
	//Service unit tests
	ItemInMenuServiceUnitTest.class,
	ItemInOrderServiceUnitTest.class,
	ItemServiceUnitTest.class,
	JwtUserDetailsServiceUnitTest.class,
	MenuServiceUnitTest.class,
	OrderServiceUnitTest.class,
	ReportsServiceUnitTest.class,
	TableServiceUnitTest.class,
	UserServiceUnitTest.class,
	ZoneServiceUnitTest.class,
	
	//service integration tests
	MenuServiceIntegrationTest.class,
	OrderServiceIntegrationTest.class,
	TableServiceIntegrationTest.class,
	UserServiceIntegrationTest.class,
	ZoneServiceIntegrationTest.class,
	
	//controller tests
	ItemInMenuControllerIntegrationTest.class,
	ItemInOrderControllerIntegrationTest.class,
	JwtControllerIntegrationTest.class,
	MenuControllerIntegrationTest.class,
	OrderControllerIntegrationtest.class,
	ReportsControllerIntegrationTest.class,
	UserControllerIntegrationTest.class,
	ZoneControllerIntegrationTest.class,
	TableControllerIntegrationTest.class
})
public class TestSuite {

}
