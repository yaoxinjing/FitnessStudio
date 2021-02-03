package fitnessStudio;

import fitnessStudio.user.StudioUser;

import org.javamoney.moneta.Money;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import fitnessStudio.catalog.CatalogController;
import fitnessStudio.catalog.Commodity;
import fitnessStudio.catalog.CommodityCatalog;
import fitnessStudio.inventory.InventoryManager;
import fitnessStudio.user.ManagerForm;
import fitnessStudio.user.StudioUserRepository;
import org.springframework.web.bind.annotation.RequestParam;

import static org.salespointframework.core.Currencies.EURO;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
public class FitnessStudioController {

	private static final Logger LOG = LoggerFactory.getLogger(CatalogController.class);
	
	@Autowired @NotNull
	private CommodityCatalog catalog;
	@Autowired @NotNull
	private StudioUserRepository userRepository;
	@Autowired @NotNull
	private FitnessStudioManagement fitnessStudioManagement;
	@Autowired @NotNull
	private InventoryManager inventoryManager;
	@Autowired @NotNull
	private OperationTimeRepositiory operationTimeRepositiory;
	@Autowired @NotNull
	private OrderManagement<Order> orderManagement;
	@Autowired @NotNull
	private FitnessStudio fitnessStudio;
	@Autowired @NotNull
	private UniqueInventory<UniqueInventoryItem> inventory;

	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	public FitnessStudioController() {
		//This is empty, because we have now used Autowired
	}

	/* ==== Mappings ==== */
	/* ==GetMappings== ≧◉◡◉≦ */
	//MainPage redirect to index.html
	/* ==== Mappings ==== */
	/* ==GetMappings== ≧◉◡◉≦ */
	//MainPage redirect to index.html
	@GetMapping("/")
	public String startpage(@LoggedIn Optional<UserAccount> userAccount) {
		return "redirect:/index";
	}

	//set logged out state for User
	@GetMapping("/logoutState")
	public String getlogoutMap(@LoggedIn Optional<UserAccount> userAccount) {
		if(userAccount.isPresent()
				&&userRepository.findByUserAccount(userAccount.get()) != null) {
				StudioUser loginUser = userRepository.findByUserAccount(userAccount.get());
				loginUser.setIsPresent(false);
				ArrayList<Pair<Date, Date>> loginLogoutTimestamp = loginUser.getLoginLogoutTimestamp();
				Date loginTimestamp=null;
				if(loginLogoutTimestamp.size()>0){
					loginTimestamp=loginLogoutTimestamp.get(loginLogoutTimestamp.size()-1).getFirst();
					loginLogoutTimestamp.remove(loginLogoutTimestamp.size()-1);
				}

				Pair<Date,Date> pair = new Pair<>(loginTimestamp,new Date(System.currentTimeMillis()));
				loginLogoutTimestamp.add(pair);

				loginUser.setLoginLogoutTimestamp(loginLogoutTimestamp);
				userRepository.save(loginUser);
				LOG.info(userAccount.get().getFirstname()+" "+userAccount.get().getLastname()+" left the FitnessStudio");

		}
		return "redirect:/logout";
	}

	//Default Page
	@GetMapping("/index")
	public String getHomepage(@LoggedIn Optional<UserAccount> userAccount) {
		//set logged in state for user
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
		return "index";
	}

	//Default Page
	@GetMapping("/loginError")
	public String getLoginError(Model model) {
		model.addAttribute("error", "Username or Password wrong");
		return "login";
	}


	//redirect to bilder.html
	@GetMapping("/bilder")
	String infos() {
		return "newPictures";
	}

	//redirect to the settings page
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/verwaltung")
	public String verwaltung() {
		return "verwaltung";
	}


	//see Account details
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/accountOverview")
	String accountOverview(Model model,@LoggedIn Optional<UserAccount> userAccount) {
		StudioUser loginUser = userRepository.findByUserAccount(userAccount.get());
		//fitnessStudioManagement.currentMonthOrders(loginUser).get(0).getOrderLines().toList().get(0).getQuantity();
		model.addAttribute("orderListMonth", fitnessStudioManagement.currentMonthOrders(loginUser));
		model.addAttribute("totalTrainingTime",fitnessStudioManagement.getTotalVisitingTime(loginUser));
		model.addAttribute("getVisitingCount",fitnessStudioManagement.getVisitingCount(loginUser));
		model.addAttribute("moneySpendInThisMonth",fitnessStudioManagement.moneySpendThisMonth(loginUser));
		// check if login user already give the user name who invite him
		model.addAttribute("BonusIsSet",Boolean.toString(loginUser.isSetAffiliateBonus())); 
		model.addAttribute("userNameFalsh",Boolean.toString(loginUser.isUserNameFalsh()));
		model.addAttribute("contract", userRepository.findByUserAccount(userAccount.get()).getContract());
		model.addAttribute("balance", userRepository.findByUserAccount(userAccount.get()).getBalance());
		return "accountOverview";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/accountOverview/affiliateBonus")
	public String addAffiliateBonus(@RequestParam(name = "userName") String userName,
			@LoggedIn Optional<UserAccount> userAccount){
		StudioUser loginUser = userRepository.findByUserAccount(userAccount.get());
		fitnessStudioManagement.addAffiliateBonus(userName,loginUser);
		//System.out.println(value);
		return "redirect:/accountOverview";
	}


	//Shop management page (create/change products)
	@PreAuthorize("hasAnyRole('EMPLOYEE','BOSS')")
	@GetMapping("/manageShop")
	public String getManageShop(Model model) {
		ArrayList<Commodity> discountedCommoditys = new ArrayList<Commodity>();
		ArrayList<Commodity> undiscountedCommoditys = new ArrayList<Commodity>();
		//seperate products by discount
		for (Commodity commodity : catalog.findAll()) {
			if(commodity.getDiscount() == null || commodity.getDiscount() == ""){
				undiscountedCommoditys.add(commodity);
			} else{
				discountedCommoditys.add(commodity);
			}
		}
		//add discounted and undiscounted Commoditys to model
		model.addAttribute("undiscountedCommoditys", undiscountedCommoditys);
		model.addAttribute("discountedCommoditys", discountedCommoditys);
		model.addAttribute("inventory", inventory);
		return "manageShop";
	}


	//set Studios settings
	@GetMapping("/manageStudio")
	@PreAuthorize("hasRole('BOSS')")
	String manageStudio(Model model, OperationForm operationForm) {
		OperationTime defaultOperationTime = operationTimeRepositiory.findByName("default");
		model.addAttribute("form", operationForm);
		model.addAttribute("dates", defaultOperationTime);
		ManagerForm settingsForm = new ManagerForm(fitnessStudio.getContractDuration(), 
				fitnessStudio.getMembershipFee().getNumber().intValue(), 
				fitnessStudio.getAffiliateBonus().getNumber().intValue(),
				fitnessStudio.getMonthlyPowerCosts().getNumber().intValue(), 
				fitnessStudio.getMonthlyWaterCosts().getNumber().intValue(),
				fitnessStudio.getMonthlyRentalCosts().getNumber().intValue());
		model.addAttribute("settingsForm", settingsForm);
		return "manageStudio";
	}
	
	@PostMapping("/editStudioSettings")
	@PreAuthorize("hasRole('BOSS')")
	public String editStudioSettings(ManagerForm form) {
		fitnessStudio.setContractDuration(form.getDuration());
		fitnessStudio.setMembershipFee(Money.of(form.getMembershipFee(), EURO));
		fitnessStudio.setAffiliateBonus(Money.of(form.getAffiliate(), EURO));
		fitnessStudio.setMonthlyPowerCosts(Money.of(form.getMonthlyPowerCosts(), EURO));
		fitnessStudio.setMonthlyWaterCosts(Money.of(form.getMonthlyWaterCosts(), EURO));
		fitnessStudio.setMonthlyRentalCosts(Money.of(form.getMonthlyRentalCosts(), EURO));
		return "redirect:/manageStudio";
	}

	//display general information about FitnessStudio
	@GetMapping("/infos")
	public String getInfo(Model model, OperationForm form) {
		OperationTime defautOperationTime = operationTimeRepositiory.findByName("default");
		model.addAttribute("form", form);
		model.addAttribute("dates", defautOperationTime);
		return "infos";
	}


	/* ==PostMappings== (👍≖‿‿≖)👍 */
	//set operation times
	@PostMapping("/infos")
	@PreAuthorize("hasRole('BOSS')")
	String changeOpeningTimes(@Valid @ModelAttribute OperationForm form, ManagerForm settingsForm, 
		Model model, Errors result) {

		if (result.hasErrors()) {
			return "infos";
		}
		if(!checkOperationTimesPlausibility(form)) {
			model.addAttribute("showoptimeerror", "true");
			OperationTime defautOperationTime = operationTimeRepositiory.findByName("default");
			model.addAttribute("form", form);
			model.addAttribute("dates", defautOperationTime);
			model.addAttribute("settingsForm", settingsForm);
			return "manageStudio";
		}
		fitnessStudioManagement.changeOprationTime(form);
		return "redirect:/manageStudio";
	}
	
	boolean checkOperationTimesPlausibility(OperationForm form) {
		boolean b1 = checkWorkdayTimePlausibility(form.getMonday())
			&& checkWorkdayTimePlausibility(form.getTuesday())
			&& checkWorkdayTimePlausibility(form.getWednesday());
		boolean b2 = checkWorkdayTimePlausibility(form.getThursday())
			&& checkWorkdayTimePlausibility(form.getFriday());
		boolean b3 = checkWorkdayTimePlausibility(form.getSaturday())
			&& checkWorkdayTimePlausibility(form.getSunday());
	
		return (b1 && b2 && b3);
	}

boolean checkWorkdayTimePlausibility(Workday day) {
	return LocalTime.parse(day.getOpeningTime()).isBefore(LocalTime.parse(day.getCloseTime()));
}

}