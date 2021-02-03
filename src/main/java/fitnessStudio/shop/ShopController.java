package fitnessStudio.shop;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.order.Cart;
import org.salespointframework.order.CartItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.payment.*;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fitnessStudio.Pair;
import fitnessStudio.catalog.Commodity;
import fitnessStudio.catalog.CommodityCatalog;
import fitnessStudio.catalog.Commodity.CommodityType;
import fitnessStudio.user.StudioUser;
import fitnessStudio.user.StudioUserRepository;


@Controller
@PreAuthorize("hasAnyRole('EMPLOYEE','BOSS')")
@SessionAttributes({"cart", "selectedUser"})
class ShopController {
	private static final Logger LOG = LoggerFactory.getLogger(ShopController.class);
	
	@SuppressWarnings("unused")
	private static final Quantity NONE = Quantity.of(0);
	
	private final CommodityCatalog catalog;
	private final StudioUserRepository studioUserRepository;
	private final UniqueInventory<UniqueInventoryItem> inventory;
	private final OrderManagement<Order> orderManagement;
	private final StudioUserRepository userRepository;

	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	ShopController(CommodityCatalog catalog, StudioUserRepository studioUserRepository,
			UniqueInventory<UniqueInventoryItem> inventory, OrderManagement<Order> orderManagement,
			StudioUserRepository userRepository) {
		//Asserts (｡◕‿◕｡)
		Assert.notNull(catalog, 					"CommodityCatalogue must not be null!");
		Assert.notNull(studioUserRepository, 		"StudioUserRepositoryCatalogue must not be null!");
		this.catalog 				= catalog;
		this.studioUserRepository 	= studioUserRepository;
		this.inventory = inventory;
		this.orderManagement = orderManagement;
		this.userRepository = userRepository;
	}

	/* === Session Attributes === */
	//Default value for Cart that is stored in session
	@ModelAttribute("cart")
	Cart initializeCart() {
		return new Cart();
	}
	//Default value for sessionUser (selected User in Shop)
	@ModelAttribute("selectedUser")
	public StudioUser initializeSessionUser() {
	    return null;
	}
	
	
	/* ==== Mappings ==== */ 
	/* ==GetMappings== ≧◉◡◉≦ */
	//Shop Mapping, most important Mapping
	@GetMapping("/shop")
	String commodityCatalog(Model model, @ModelAttribute("cart") Cart cart,
			@ModelAttribute("selectedUser") StudioUser selectedUser, @LoggedIn Optional<UserAccount> userAccount) {
		Assert.notNull(model, "Model must not be null!");
		Assert.notNull(cart, "Cart 	 must not be null!");
		model.addAttribute("Drinks", 		catalog.findByType(CommodityType.DRINK));
		model.addAttribute("Equipment", 	catalog.findByType(CommodityType.EQUIPMENT));
		model.addAttribute("Supplements", 	catalog.findByType(CommodityType.SUPPLEMENT));
		model.addAttribute("User", 			studioUserRepository.findByIsPresent(true));
		model.addAttribute("Cart", 			cart.iterator());
		model.addAttribute("SelectedUser", 	selectedUser);
		if(userAccount.isPresent()
				&&userRepository.findByUserAccount(userAccount.get()) != null
				&&!userRepository.findByUserAccount(userAccount.get()).getIsPresent()) {
					StudioUser loginUser = userRepository.findByUserAccount(userAccount.get());
					loginUser.setIsPresent(true);
					ArrayList<Pair<Date, Date>> loginLogoutTimestamp = loginUser.getLoginLogoutTimestamp();
					Pair<java.util.Date, java.util.Date> login=new Pair<>(new java.util.Date(System.currentTimeMillis()),
																		new java.util.Date(System.currentTimeMillis()));
					loginLogoutTimestamp.add(login);
					loginUser.setLoginLogoutTimestamp(loginLogoutTimestamp);
					userRepository.save(loginUser);
					LOG.info(userAccount.get().getFirstname()+" "+userAccount.get().getLastname()+" entered the FitnessStudio");


		}
		return "shop";
	}
	
	//add Commodity to Cart
	@GetMapping("/cart/addCommodity/{id}")
	String addCommodity(@PathVariable Commodity id, @ModelAttribute Cart cart) {
//		Assert.notNull(cart, "Cart 		must not be null!");
//		Assert.notNull(id,   "Commodity must not be null!");
		if(id != null && cart != null) {
			// if enough commodity's in storage
			if(!cart.addOrUpdateItem(id, 
					Quantity.of(0)).getQuantity().isGreaterThanOrEqualTo(inventory.findByProduct(id).get().getQuantity())) {
				cart.addOrUpdateItem(id, Quantity.of(1));
				LOG.debug("Added "+id.getName()+" to cart");
			}
		}else {
			LOG.debug("commodity or cart is null");
		}
		
		return "redirect:../../shop";
	}
	
	//remove commodity from Cart
	@GetMapping("/cart/removeCommodity/{id}")
	String removeCommodity(@PathVariable String id, @ModelAttribute Cart cart) {
		Assert.notNull(cart,  "Cart must not be null!");
		cart.removeItem(id);
		LOG.debug("Removed "+id+" from cart");
		return "redirect:../../shop";
	}
	
	/* ==PostMappings== (👍≖‿‿≖)👍 */
	//Mapping for Shop User Selection
	@PostMapping("/shop/selectUser")
	String selectUser(@ModelAttribute("selectedUser") StudioUser selectedUser,
			RedirectAttributes attributes, @RequestParam String sessionUser) {
		try {
			selectedUser = studioUserRepository.findById(Long.parseLong(sessionUser)).get(); //update selected User
		}catch(NumberFormatException stringFormat) {
			LOG.warn("Invalid Value for sessionUser Id: "+sessionUser);
		}
		attributes.addFlashAttribute("selectedUser", selectedUser);	//keep data if a new webpage is loaded
		return "redirect:../shop";
	}
	
	//Pay for the Items in Cart
	@PostMapping("/shop/pay")
	String Pay(@ModelAttribute Cart cart, @ModelAttribute("selectedUser") StudioUser selectedUser,
			@ModelAttribute("payment") String payment) {
		StudioUser studioUser = studioUserRepository.findById(selectedUser.getId()).get();
		if(selectedUser != null && cart != null && studioUser != null) {
			//check again if enough in stock
			boolean outOfStock = false;
			for (CartItem cartItem : cart) {
				if(!inventory.findByProduct(cartItem.getProduct()).get().getQuantity().
						isGreaterThanOrEqualTo(cartItem.getQuantity())) {
					cart.removeItem(cartItem.getId());	//remove item from cart and go back to shop to check again and then pay
					cart.addOrUpdateItem(cartItem.getProduct(), 0);
					outOfStock = true;
					break;
				}
			}
			if(!outOfStock) {
				Order order = new Order(studioUser.getUserAccount(), Balance.BALANCE);
				if("cash".equals(payment)) {
					order.setPaymentMethod(Cash.CASH);
				}
				//pay
				cart.addItemsTo(order);
				if(order.getPaymentMethod().equals(Balance.BALANCE)) {
					//substract from user account
					studioUser.subtractBalance(order.getTotal());
					studioUserRepository.save(studioUser);
				}
				orderManagement.payOrder(order);
				orderManagement.completeOrder(order);
			}
			LOG.info("Transaction from "+studioUser.getFullname()+" with worth of "+cart.getPrice()+" was successfull");
			cart.clear();
		}
		return "redirect:../shop";
	}
}

