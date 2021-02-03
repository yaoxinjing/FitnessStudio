package fitnessStudio.inventory;

import org.salespointframework.inventory.InventoryItemIdentifier;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
class InventoryController {

	private final UniqueInventory<UniqueInventoryItem> inventory;

	InventoryController(UniqueInventory<UniqueInventoryItem> inventory) {
		this.inventory = inventory;
	}
	
	@GetMapping("/stock")
	@PreAuthorize("hasAnyRole('BOSS', 'EMPLOYEE')" )
	String stock(Model model) {
		Assert.notNull(model, "Model must not be null!");
		model.addAttribute("Stock", inventory.findAll());

		return "stock";
	}
	
	@PostMapping("/stock/change")
	@PreAuthorize("hasAnyRole('BOSS', 'EMPLOYEE')")
	String changeStock(@ModelAttribute("InventoryId") InventoryItemIdentifier inventoyId,
			@ModelAttribute("amount") float amount) {
		inventory.save(inventory.findById(inventoyId).get().decreaseQuantity(inventory.findById(inventoyId).get()
				.getQuantity()).increaseQuantity(Quantity.of(amount)));
		return "redirect:../stock";
	}
}
