package fitnessStudio.inventory;

import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import fitnessStudio.catalog.CommodityCatalog;


@Component
@Order(20)	//Priority
class InventoryInitializer implements DataInitializer {
	private static final Logger LOG = LoggerFactory.getLogger(InventoryController.class);
	private final UniqueInventory<UniqueInventoryItem> inventory;
	private final CommodityCatalog commodityCatalog;

	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	InventoryInitializer(UniqueInventory<UniqueInventoryItem> inventory, CommodityCatalog commodityCatalog) {
		Assert.notNull(inventory, "Inventory must not be null!");
		Assert.notNull(commodityCatalog, "CommodityCatalog must not be null!");
		this.inventory = inventory;
		this.commodityCatalog = commodityCatalog;
	}

	//initialize default data
	@Override
	public void initialize() {
		commodityCatalog.findAll().forEach(commodity -> {
			if (inventory.findByProduct(commodity).isEmpty()) {
				LOG.info("Creating default Amount for: "+commodity.getName());
				inventory.save(new UniqueInventoryItem(commodity, Quantity.of(10)));
			}
		});
	}
}
