package fitnessStudio.user;

import java.util.Optional;

import static org.salespointframework.core.Currencies.*;
import javax.validation.Valid;

import org.javamoney.moneta.Money;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.ArrayList;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.salespointframework.useraccount.Role;

import fitnessStudio.FitnessStudio;
import fitnessStudio.FitnessStudioManagement;
import fitnessStudio.request.RequestManagement;

import java.time.*;

@Controller
class StudioUserController {

	private final StudioUserManagement studiouserManagement;
	private final FitnessStudio fitnessStudio;
	private final RequestManagement requestManagement;
	private final StudioUserRepository studioUserRepository;
	private final FitnessStudioManagement fitnessStudioManagement;

	StudioUserController(StudioUserManagement studiouserManagement, FitnessStudio fitnessStudio,
			RequestManagement requestManagement, StudioUserRepository studioUserRepository,
			FitnessStudioManagement fitnessStudioManagement) {

		Assert.notNull(studiouserManagement, "StudioUserManagement must not be null!");
		Assert.notNull(requestManagement, "RequestManagement must not be null!");
		Assert.notNull(studioUserRepository, "StudioUserRepository must not be null!");
		Assert.notNull(fitnessStudioManagement, "FitnessStudioManagement must not be null!");

		this.studiouserManagement = studiouserManagement;
		this.fitnessStudio = fitnessStudio;
		this.requestManagement = requestManagement;
		this.studioUserRepository = studioUserRepository;
		this.fitnessStudioManagement = fitnessStudioManagement;
	}

	@PostMapping("/register")
	String registerNew(@Valid RegistrationForm form, Model model, Errors result) {

		if (result.hasErrors()) {
			return "register";
		}

		// Falls alles in Ordnung ist legen wir einen StudioUser an
		if (studiouserManagement.createUser(form) != null) {
			return "redirect:/index";
		} else {
			model.addAttribute("showerror", "true");
			return register(form, model);
		}
	}

	@PostMapping("/registerEmployee")
	@PreAuthorize("hasRole('BOSS')")
	String registerNewEmployee(@Valid EmployeeRegistrationForm form, Model model, Errors result) {

		if (result.hasErrors()) {
			return "registerEmployee";
		}

		// Falles alles in Ordnung ist legen wir einen Employee an
		ArrayList<String> tasks = new ArrayList<String>();
		tasks.add("TRAINEE");

		if (studiouserManagement.createEmployee(form, tasks) != null) {
			return "redirect:/manageEmployee";
		} else {
			model.addAttribute("showerror", "true");
			return registerEmployee(form, model);
		}

	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/manageProfile")
	public String manageProfile(@LoggedIn Optional<UserAccount> userAccount, Model model) {

		return userAccount.map(account -> {

			// make name field not editable for regular users for their own safety
			if (!(account.hasRole(Role.of("EMPLOYEE")))) {
				// http://forum.thymeleaf.org/disabled-fieldsest-td4031330.html
				model.addAttribute("fieldDisabled", "disabled");
			}

			
			StudioUser user = studiouserManagement.findCorrespondingUser(account);
			Address add = user.getAddress();
			// check how to implement password change
			RegistrationForm form2 = new RegistrationForm(user.getUsername(), user.getFirstname(), user.getLastname(),
					add.getStreet(), add.getHouseNumber(), add.getAdditionalAddress(), add.getCity(), add.getZipcode(),
					user.getEmail(), user.getPhonenumber(), "");
			ContractForm form3 = new ContractForm(user.getContract().getDescription(), user.getContract().getPrice()
					.getNumber().doubleValue(), user.getContract().startTime().toString(), user.getContract()
					.endTime().toString());
			model.addAttribute("contractForm",form3);
			model.addAttribute("form", form2);

			if ((account.hasRole(Role.of("BOSS")))) {
				model.addAttribute("showContract", true);
			}

			return("/manageProfile");
		}).orElse("/manageProfile");
	}

	@GetMapping("/manageProfile/{id}")	
	@PreAuthorize("hasAnyRole('EMPLOYEE','BOSS')")
	public String manageProfileWithID(@LoggedIn Optional<UserAccount> userAccount, @PathVariable long id, Model model) {

		return userAccount.map(account -> {

			StudioUser user = studiouserManagement.findById(id);
			Address add = user.getAddress();
			RegistrationForm form = new RegistrationForm(user.getUsername(), user.getFirstname(), user.getLastname(),
					add.getStreet(), add.getHouseNumber(), add.getAdditionalAddress(), add.getCity(), add.getZipcode(),
					user.getPhonenumber(), user.getEmail(), "");
			model.addAttribute("form", form);
			ContractForm form2 = new ContractForm(user.getContract().getDescription(), user.getContract().getPrice()
					.getNumber().doubleValue(), user.getContract().startTime().toString(), user.getContract()
					.endTime().toString());
			model.addAttribute("contractForm",form2);
			model.addAttribute("requestList", requestManagement.findByContract(user.getContract()));

			if ((account.hasRole(Role.of("BOSS")))) {
				model.addAttribute("showContract", true);
			}

			return("/manageProfile");
		}).orElse("/manageProfile");
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/manageProfile")
	public String manageProfile(@LoggedIn Optional<UserAccount> userAccount, RegistrationForm form, Model model) {

		return userAccount.map(account -> {
			
			StudioUser user = studiouserManagement.findCorrespondingUser(account);
			user.updateData(user.getAddress().changeAddress(form.getStreet(), form.getHousenumber(),
					form.getAddressaddition(), form.getCity(), form.getPostalcode()), form.getPhonenumber(),
					form.getEmail());

			// i had to add this line because java
			// when its not there, the values wont get saved
			studiouserManagement.findCorrespondingUser(account).getAddress().getHouseNumber();
			
			if (form.getPassword() != "") {
				studiouserManagement.changePassword(account, form.getPassword());
			}
			return("redirect:/manageProfile");
		}).orElse("redirect:/manageProfile");
	}

	@PreAuthorize("hasAnyRole('EMPLOYEE','BOSS')")
	@PostMapping("/manageContract")
	public String manageProfile(@LoggedIn Optional<UserAccount> userAccount, ContractForm form, Model model) {

		return userAccount.map(account -> {
			StudioUser user = studiouserManagement.findCorrespondingUser(account);
			user.getContract().updateData(form.getDescription(), Money.of(form.getPrice(), EURO),
					LocalDate.parse(form.getStartTime()), LocalDate.parse(form.getEndTime()));

			// i had to add this line because java
			// when its not there, the values wont get saved
			studiouserManagement.findCorrespondingUser(account).getAddress().getHouseNumber();
			
			return("redirect:/manageProfile");
		}).orElse("redirect:/manageProfile");
	}


	@GetMapping("/register")
	String register(RegistrationForm form, Model model) {
		model.addAttribute("form", form);
		return "register";
	}

	@PreAuthorize("hasRole('BOSS')")
	@GetMapping("/registerEmployee")
	String registerEmployee(EmployeeRegistrationForm form, Model model) {
		model.addAttribute("form", form);
		return "addEmployee";
	}

	
	@GetMapping("/manageMember")
	@PreAuthorize("hasAnyRole('EMPLOYEE','BOSS')")
	String manageMember(Model model) {
		model.addAttribute("userList", studiouserManagement.findAllUsers());
		studiouserManagement.findAllUsers();

		return "manageMember";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/manageMember")
	public String changeUserBalance(@RequestParam(name = "balance") Double balance,
			@RequestParam(name = "username") String username){
		StudioUser user = studiouserManagement.findByUsername(username);
		
		if (user == null) {
			return "redirect:/manageMember";
		}
		
		fitnessStudioManagement.changeBalance(user, balance);

		return "redirect:/manageMember";
	}

	@GetMapping("/manageEmployee")
	@PreAuthorize("hasRole('BOSS')")
	String manageEmployee(Model model) {
		model.addAttribute("userList", studiouserManagement.findAllEmployees());
		studiouserManagement.findAllUsers();

		return "manageEmployee";
	}


	@GetMapping("/user/remove/{id}")	
	@PreAuthorize("hasAnyRole('EMPLOYEE','BOSS')")
	public String removeUser(@PathVariable long id, Model model) {

		studiouserManagement.findAllUsers();
		//add exception handling for findById...
		String ret;
		if (studiouserManagement.findById(id).getClass().equals(Employee.class)) {
			ret = "redirect:/manageEmployee";
			} else {
				ret = "redirect:/manageMember";
				}
		studiouserManagement.deleteById(id);

		return ret;

	}
}
