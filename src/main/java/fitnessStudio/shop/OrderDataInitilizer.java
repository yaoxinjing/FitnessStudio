package fitnessStudio.shop;

import java.util.Random;

import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.order.Cart;
import org.salespointframework.order.CartItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fitnessStudio.catalog.CommodityCatalog;
import fitnessStudio.user.StudioUser;
import fitnessStudio.user.StudioUserManagement;

@Component
//@Order(22)	//Priority
public class OrderDataInitilizer implements DataInitializer{
	private static final Logger LOG = LoggerFactory.getLogger(OrderDataInitilizer.class);
	private final CommodityCatalog commodityCatalog;
	private final StudioUserManagement studioUserManagement;
	private final OrderManagement<Order> orderManagement;
	private final UniqueInventory<UniqueInventoryItem> inventory;
	
	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	public OrderDataInitilizer(CommodityCatalog commodityCatalog, StudioUserManagement studioUserManagement,
			OrderManagement<Order> orderManagement, UniqueInventory<UniqueInventoryItem> inventory) {
		this.commodityCatalog = commodityCatalog;
		this.studioUserManagement = studioUserManagement;
		this.orderManagement = orderManagement;
		this.inventory = inventory;
	}



	@Override
	public void initialize() {
		if(orderManagement.findBy(OrderStatus.COMPLETED).isEmpty()) {
			StudioUser user = null;
			for (StudioUser studioUser : studioUserManagement.findAllUsers()) {
				if(studioUser.getUsername().equals("Gast")) {
					user = studioUser;
				}
			}
			if(user != null) {
				Random rand;
				for (int i = 0; i < 15; i++) {
					rand = new Random();
					Order order = new Order(user.getUserAccount(), Cash.CASH);
					Cart cart = new Cart();
					for (int j = 0; j < rand.nextInt(Integer.MAX_VALUE)%commodityCatalog.count() +1; j++) {
						cart.addOrUpdateItem(commodityCatalog.findAll().toList()
								.get((int) (rand.nextInt(Integer.MAX_VALUE)%commodityCatalog.count())),
								Quantity.of((int)(rand.nextInt(Integer.MAX_VALUE)%5 +1)));
					}
					cart.addItemsTo(order);
					for (CartItem cartItem : cart) {
						inventory.save(inventory.findByProduct(cartItem.getProduct()).get().increaseQuantity(cartItem.getQuantity()));
					}
					orderManagement.payOrder(order);
					orderManagement.completeOrder(order);
					cart.clear();
					//LOG.info("Test");
					
				}
			}
		}
		
		
	}

	

	

	
}
