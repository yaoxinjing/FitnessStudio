package fitnessStudio.catalog;

import java.math.BigDecimal;

import static org.salespointframework.core.Currencies.*;
import org.javamoney.moneta.Money;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration


public class CommodityCatalogTest {
	
	@Autowired
	private CommodityCatalog commodityCatalog;
	
	
	@Test
	public void addToCatalog() {
		Commodity test = new Drink("test", Money.of(BigDecimal.TEN, EURO), "Dies ist ein Test", 0);
		commodityCatalog.save(test);
		Assertions.assertTrue(commodityCatalog.findById(test.getId()).get().equals(test));
		commodityCatalog.delete(test);
	}
}
