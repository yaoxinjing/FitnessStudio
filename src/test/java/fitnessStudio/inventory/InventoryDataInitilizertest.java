package fitnessStudio.inventory;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration
public class InventoryDataInitilizertest {
	
	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventory;
	
	@Test
	public void InventoryNotEmpty() {
		Assertions.assertTrue(inventory.count() > 0);
	}
	
	@Test
	public void InventoryAmountTest() {
		for (UniqueInventoryItem item : inventory.findAll()) {
			Assertions.assertTrue(item.getQuantity().isGreaterThan(Quantity.NONE));
			Assertions.assertNotNull(item.getProduct());
		}
	}

}
