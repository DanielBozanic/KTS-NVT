package com.kts.sigma.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.kts.sigma.constants.UserContants;
import com.kts.sigma.model.Payment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class PaymentRepositoryIntegrationTest {

	@Autowired
	private PaymentRepository paymentRepository;
	
	@Test
	public void findActivePaymentByEmployeeId_InvalidEmployeeId_ReturnsNull() {
		Payment payment = paymentRepository.findActivePaymentByEmployeeId(UserContants.INVALID_USER_ID);
		assertNull(payment);
	}
	
	@Test
	public void findActivePaymentByEmployeeId_ValidEmployeeId_ReturnsPayment() {
		Payment payment = paymentRepository.findActivePaymentByEmployeeId(UserContants.DB_EMPLOYEE_ID_1);
		
		assertEquals(null, payment.getDateEnd());
		assertEquals(UserContants.DB_EMPLOYEE_ID_1, payment.getEmployee().getId());
	}
}
