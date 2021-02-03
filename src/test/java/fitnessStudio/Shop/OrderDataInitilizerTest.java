package fitnessStudio.Shop;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration
public class OrderDataInitilizerTest {

	@Autowired
	OrderManagement<Order> orderManagement;
	
	@Test
	public void OrderInitTest() {
		Assertions.assertFalse(orderManagement.findBy(OrderStatus.COMPLETED).isEmpty());
	}
	
}
