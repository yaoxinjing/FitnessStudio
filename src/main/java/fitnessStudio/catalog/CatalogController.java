package fitnessStudio.catalog;

import org.javamoney.moneta.Money;
import static org.salespointframework.core.Currencies.*;

import java.math.BigDecimal;
import java.time.LocalDate;


import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fitnessStudio.catalog.Commodity.CommodityType;

@Controller
@PreAuthorize("isAuthenticated()")
public class CatalogController {
	private static final Logger LOG = LoggerFactory.getLogger(CatalogController.class);
	
	private final CommodityCatalog catalog;
	private final UniqueInventory<UniqueInventoryItem> inventory;
	
	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	CatalogController(CommodityCatalog commodityCatalog, UniqueInventory<UniqueInventoryItem> inventory) {
		Assert.notNull(commodityCatalog, "CommodityCatalog must not be null!");
		Assert.notNull(inventory, "Inventory must not be null!");
		this.catalog = commodityCatalog;
		this.inventory = inventory;
	}
	
	
	/* ==== Mappings ==== */ 
	/* ==GetMappings== ≧◉◡◉≦ */
	//Show Commodity
	@GetMapping("/catalog/commodity/{id}")
	String ShopCommodity(Model model, @PathVariable Commodity id) {
		Assert.notNull(model, "Model must not be null!");
		if(id == null ) {
			return "redirect:../../manageShop" ;
		}
		if(!catalog.existsById(id.getId())){
			return "redirect:../../manageShop" ; 
		}
		model.addAttribute("commodity", id);
		if(id!= null && id.getType() == CommodityType.SUPPLEMENT) {
				model.addAttribute("mhb", ((Supplement)id).getExpirationdate());			
		}
		
		return "commodity";
	}
	
	//Remove Commodity from Catalog (ɔ◔‿◔)ɔ ♥
	@GetMapping("/catalog/removeCommodity/{id}")
	String RemoveCommodity(Model model, @PathVariable ProductIdentifier id) {
		Assert.notNull(model, "Model must not be null!");
		if(catalog.existsById(id)) {
			if(inventory.findByProduct(catalog.findById(id).get()).isPresent()) {
				inventory.delete(inventory.findByProduct(catalog.findById(id).get()).get());
				LOG.info("Removed Item from Inventory");
			}
			LOG.info("Deleted Product: "+catalog.findById(id).get().getName());
			catalog.delete(catalog.findById(id).get());
			
			//catalog.deleteById(id);
			//catalog.deleteAll();
			LOG.debug("Removed"+id+"from Catalog");
		}
			
		return "redirect:../../manageShop";
	}
	
	@GetMapping("/catalog/addCommodity")
	String addcommodity() {
		return "addCommodity";
	}
	
	
	/* ==PostMappings== (👍≖‿‿≖)👍 */
	//Edit existing Commodity
	@PostMapping("catalog/editCommodity")
	String ShopEditCommodity(@RequestParam Commodity commodity, @RequestParam String name,
			@RequestParam String discount, @RequestParam String price, @RequestParam String description,
			@RequestParam Integer minAmount, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate mhb) {
		Assert.notNull(commodity,  "Commodity must not be null!");
		commodity.setDiscount(discount);
		commodity.setName(name);
		commodity.setPrice(Money.of(BigDecimal.valueOf(Double.valueOf(price.replaceAll("EUR ", ""))), EURO));
		commodity.setDescription(description);
		commodity.setMinCount(minAmount);
		if(commodity.getType() == CommodityType.SUPPLEMENT) {
			((Supplement) commodity).setExpirationdate(mhb);
		}
		catalog.save(commodity);
		return "redirect:../../manageShop";
	}
	
	@SuppressWarnings("deprecation")
	@PostMapping("catalog/addCommodity")
	String CatalogAddCommodity(@RequestParam String name, @RequestParam String discount, @RequestParam String price,
				@RequestParam String description, @RequestParam Integer minAmount, @RequestParam Integer stock,
				@RequestParam Integer type, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate mhb) {
		Money money;
		if(name != null && catalog.findByName(name).isEmpty() && type != null) {
			try {
				money = Money.of(BigDecimal.valueOf(Double.valueOf(price.replaceAll("EUR ", ""))), EURO);
			} catch (Exception e) {
				money =  Money.of(BigDecimal.ROUND_CEILING, EURO);
				LOG.warn("Create Commodity invalid Price");
			}
			if(stock == null) {
				stock = 0;
			}
			Commodity add;
			try {
				switch (type) {
				case 0:
					add = new Drink(name, money, description, minAmount);
					break;
				case 1:
					add = new Equipment(name, money, description, minAmount);
					break;
				case 2:
					if(mhb == null) {
						add = new Supplement(name, money, description, minAmount, LocalDate.MAX);
					}else {
						add = new Supplement(name, money, description, minAmount, mhb);
					} break;
				default:
					LOG.warn("Create Commodity invalid Type");
					return "redirect:../../manageShop";
				}
			} catch (Exception e) {
				return "redirect:/catalog/addCommodity/";
			}
			
			catalog.save(add);
			inventory.save(new UniqueInventoryItem(add, Quantity.of(stock)));
		}
		
		return "redirect:../../manageShop";
	}
	
	
}
