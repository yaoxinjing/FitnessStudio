package fitnessStudio.inventory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.stereotype.Service;

import fitnessStudio.catalog.Commodity;
import fitnessStudio.catalog.Commodity.CommodityType;
import fitnessStudio.catalog.CommodityCatalog;
import fitnessStudio.catalog.Supplement;

@Service
public class InventoryManager {
	private final UniqueInventory<UniqueInventoryItem> inventory;
	private final CommodityCatalog commodityCatalog;
	
	
	//Constructor
	public InventoryManager(UniqueInventory<UniqueInventoryItem> inventory, CommodityCatalog commodityCatalog) {
		super();
		this.inventory = inventory;
		this.commodityCatalog = commodityCatalog;
	}



	public Map<Commodity, Quantity> reOrderList() {
		HashMap<Commodity, Quantity> reorderList = new HashMap<Commodity, Quantity>();
		for (UniqueInventoryItem item : inventory.findAll()) {
			//for each in case multiple products with same name
			for (Commodity commodity : commodityCatalog.findByName(item.getProduct().getName()).toList()) {
				if(item.getQuantity().isLessThan(Quantity.of(commodity.getMinCount()))) {
					reorderList.put(commodity, Quantity.of(commodity.getMinCount()).subtract(item.getQuantity()));
				}
			}
		}
		return reorderList;
	}
	
	public List<Commodity> OverMHB() {
		ArrayList<Commodity> supp = new ArrayList<Commodity>();
		for (Commodity supplement : commodityCatalog.findByType(CommodityType.SUPPLEMENT)) {
			if(((Supplement)supplement).getExpirationdate().isBefore(LocalDate.now())) {
				supp.add(supplement);
			}
		}
		return supp;
	}
}
