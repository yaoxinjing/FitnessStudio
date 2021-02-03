package fitnessStudio.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.salespointframework.core.Currencies.EURO;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

public class ManagerTest {

	@Autowired
	 private static Manager manager = null;
	

	@Test
	void testSingletonEmpty() {
	manager = Manager.getManager();
	assertEquals(Manager.getManager(), manager);
	Manager manager2 = Manager.getManager();
	assertEquals(manager2, manager);
	}
	
	@DirtiesContext
	@Test
	void testSingleton() {
	manager = null;
	manager = Manager.getManager(Money.of(0, EURO), null, null, null, "itseme@test", "1234");
	assertEquals(Manager.getManager(), manager);
	Manager manager2 = Manager.getManager(Money.of(0, EURO), null, null, null, "itseme@test", "1234");
	assertEquals(manager2, manager);
	}
}
