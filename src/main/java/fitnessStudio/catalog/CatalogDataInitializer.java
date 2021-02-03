package fitnessStudio.catalog;

import static org.salespointframework.core.Currencies.*;

import java.time.LocalDate;


import org.javamoney.moneta.Money;
import org.salespointframework.core.DataInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Order(20) // Startup Order
class CatalogDataInitializer implements DataInitializer{
	private static final Logger LOG = LoggerFactory.getLogger(CatalogDataInitializer.class);

	private final CommodityCatalog commodityCatalog;
	
	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	CatalogDataInitializer(CommodityCatalog commodityCatalog) {

		Assert.notNull(commodityCatalog, "CommodityCatalog must not be null!");

		this.commodityCatalog = commodityCatalog;
	}

	//Initialize Default Data
	@Override
	public void initialize() {
		if (commodityCatalog.findAll().iterator().hasNext()) {
			return;
		}
		commodityCatalog.save((Commodity)new Drink("Martini", Money.of(4, EURO), "For our special Customers", 5));
		commodityCatalog.save((Commodity)new Drink("Cuba", Money.of(2, EURO), "For our special Customers", 5));
		commodityCatalog.save((Commodity)new Drink("Orcablut", Money.of(3, EURO), "For our special Customers", 5));
		commodityCatalog.save((Commodity)new Drink("Level Down", Money.of(2, EURO), "For our special Customers", 5));
		commodityCatalog.save((Commodity)new Drink("Rum", Money.of(5, EURO), "For our special Customers", 5));
		commodityCatalog.save((Commodity)new Drink("Wodka", Money.of(5, EURO), "For our special Customers", 5));
		commodityCatalog.save((Commodity)new Drink("Grasshopper", Money.of(4, EURO), "For our special Customers", 5));
		commodityCatalog.save((Commodity)new Supplement("Protein Riegel", Money.of(0.6, EURO), "Krasses Zeug", 5,
				LocalDate.parse("2021-01-27")));
		commodityCatalog.save((Commodity)new Supplement("Eier", Money.of(1.40, EURO), "Das Gelbe vom Ei", 5,
				LocalDate.parse("2021-01-27")));
		commodityCatalog.save((Commodity)new Supplement("Pumpernickel", Money.of(1, EURO),
				"Ein Kaninchen im FitnessStudio", 5, LocalDate.parse("2021-02-27")));
		commodityCatalog.save((Commodity)new Equipment("DIE Hantel", Money.of(70, EURO),
				"For our special Customers", 5));
		commodityCatalog.save((Commodity)new Equipment("Der Lauchzerstörer", Money.of(120, EURO),
				"For our special Customers", 5));
		commodityCatalog.save((Commodity)new Equipment("Der Spargelschäler", Money.of(90, EURO),
				"For our special Customers", 5));
		commodityCatalog.save((Commodity)new Equipment("Der Zweigbrecher", Money.of(50, EURO),
				"For our special Customers", 5));
		LOG.info("Created default Catalog Data");
		
	}
}
