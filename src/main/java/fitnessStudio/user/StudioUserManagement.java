package fitnessStudio.user;
import fitnessStudio.*;
import fitnessStudio.request.Request;
import fitnessStudio.request.RequestRepository;

import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.Password.UnencryptedPassword;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.money.MonetaryAmount;
import java.util.ArrayList;
import java.util.List;
import java.time.*;

@Service
@Transactional
public class StudioUserManagement {

	public static final Role CUSTOMER_ROLE = Role.of("CUSTOMER");

	private final StudioUserRepository users;
	private final UserAccountManagement userAccounts;
	private final StudioUserFactory factory = new StudioUserFactory();
	private final FitnessStudio fitnessStudio;
	private final RequestRepository requestRepository;

	/**
	 * Creates a new {@link StudioUserManagement} with the given {@link StudioUserRepository} and
	 * {@link UserAccountManagement}.
	 *
	 * @param users must not be {@literal null}.
	 * @param userAccounts must not be {@literal null}.
	 */
	StudioUserManagement(StudioUserRepository users, UserAccountManagement userAccounts, 
				FitnessStudio fitnessStudio, RequestRepository requestRepository) {

		Assert.notNull(users, "StudioUserRepository must not be null!");
		Assert.notNull(userAccounts, "UserAccountManagement must not be null!");

		this.users = users;
		this.userAccounts = userAccounts;
		this.fitnessStudio = fitnessStudio;
		this.requestRepository = requestRepository;
	}

	public StudioUserRepository getUserRepository() {
		return users;
	}

	private UserAccount createUserAccount(RegistrationForm form) {
		var password = UnencryptedPassword.of(form.getPassword());
		if (!(isUsernameTaken(form.getUsername()))) {
			var userAccount = userAccounts.create(form.getUsername(), password, CUSTOMER_ROLE);
			userAccount.setFirstname(form.getFirstname());
			userAccount.setLastname(form.getLastname());
			return userAccount;
		} else {
			return null;
		}
	}

	
	/**
	 * Creates a new {@link StudioUser} using the information given in the {@link RegistrationForm}.
	 *
	 * @param form must not be {@literal null}.
	 * @return the new {@link StudioUser} instance.
	 */
	public StudioUser createUser(RegistrationForm form) {

		Contract contract =  (new Contract("Mitgliedsvertrag", this.fitnessStudio.getMembershipFee(), 
			LocalDate.now(), LocalDate.now().plusMonths(fitnessStudio.getContractDuration())));
		Assert.notNull(form, "Registration form must not be null!");
		UserAccount userAccount = createUserAccount(form);
		if (userAccount != null) {
			return users.save(factory.createUser(userAccount, form, contract));
		} else {
			return null;
		}
	}
	
	
	@SuppressWarnings("unused")
	public Employee createEmployee(EmployeeRegistrationForm form, ArrayList<String> task) {
		
		Assert.notNull(form, "Registration form must not be null!");
		
		UserAccount userAccount = createUserAccount(form);
		userAccount.add(Role.of("EMPLOYEE"));
		
		
		Contract contract =  (new Contract("Mitarbeitervertrag", this.fitnessStudio.getMembershipFee(),
			LocalDate.now(),LocalDate.now().plusMonths(fitnessStudio.getContractDuration())));
		
		if (userAccount != null) {
			return users.save(factory.createEmployee(userAccount, form, task, contract));
		}
		return null;
	}
	
	public Manager createManager(RegistrationForm form, MonetaryAmount salary, ArrayList<String> task) {
		
		Assert.notNull(form, "Registration form must not be null!");
		
		UserAccount userAccount = createUserAccount(form);
		userAccount.add(Role.of("BOSS"));
		
		
		var userAddress = new Address(form.getStreet(), form.getHousenumber(), form.getAddressaddition(), 
			form.getCity(), form.getPostalcode());
				
		return users.save(factory.createManager(userAccount, form, task, salary));
	}
	
	public void	changePassword(UserAccount userAccount, String password) {
		userAccounts.changePassword(userAccount, UnencryptedPassword.of(password));
	}
	
	public StudioUser findCorrespondingUser(UserAccount acc) {
		Streamable<StudioUser> temp = users.findAll();
		for (StudioUser user : temp) {
			if (user.getUserAccount() == acc) {
				return user;
			}
		}
		return null;
	}
	
	
	//output all users with StudioUser class
	public Iterable<StudioUser> findAllUsers() {
		Streamable<StudioUser> temp = users.findAll();
		ArrayList<StudioUser> out = new ArrayList<StudioUser>();
		for (StudioUser user : temp) {
			if ((user.getClass().equals(StudioUser.class))) {
				out.add(user);
			}
			
		}
		return out;
	}
	//same with Employee class
	public Iterable<StudioUser> findAllEmployees() {
		Streamable<StudioUser> temp = users.findAll();
		ArrayList<StudioUser> out = new ArrayList<StudioUser>();
		for (StudioUser user : temp) {
			if ((user.getClass().equals(Employee.class))) {
				out.add(user);
			}
			
		}
		return out;
	}
	//same with Manager class
	public Iterable<StudioUser> findAllManagers() {
		Streamable<StudioUser> temp = users.findAll();
		ArrayList<StudioUser> out = new ArrayList<StudioUser>();
		for (StudioUser user : temp) {
			if ((user.getClass().equals(Manager.class))) {
				out.add(user);
			}
			
		}
		return out;	
	}
		
		
		public Streamable<StudioUser> findAll() {
			return users.findAll();
		}
		
		public StudioUser findById(long id) {
			//add exception handling...
			return users.findById(id).get();
		}
		
		public void deleteById(long id) {
			//add exception handling...
			//set user attribute of every correspending transaction to null
			//otherwise the site may crash because transactions reference non-existing users
			//Remove Request from requestRepo
//			StudioUser stud = users.findById(id).get();
//			List<Request> list = stud.getContract().getRequests();
//			stud.setContract(null);
//			users.save(stud);
//			requestRepository.deleteAll(list);
			
//			stud.getContract().getRequests().clear();
//			users.save(stud);
			users.deleteById(id);
		}
		
		public StudioUser findByUsername(String username ) {
			Streamable<StudioUser> temp = users.findAll();
			StudioUser out = null;
			for (StudioUser user : temp) {
				if ((user.getUsername().equals(username))) {
					out = user;
				}
			}
			return out;
		}
		
		public StudioUser findByFullname(String fullname) {
			Streamable<StudioUser> temp = users.findAll();
			StudioUser out = null;
			for (StudioUser user : temp) {
				if ((user.getFullname().equals(fullname))) {
					out = user;
				}
			}
			return out;
		}
		
		private boolean isUsernameTaken(String name) {
			Streamable<StudioUser> temp = users.findAll();
			for (StudioUser user : temp) {
				if ((user.getUsername().equals(name))) {
					return true;
				}
			}
			return false;
		}

	}
	