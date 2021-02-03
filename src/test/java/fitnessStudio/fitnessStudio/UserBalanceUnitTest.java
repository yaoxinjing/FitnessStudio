package fitnessStudio.fitnessStudio;

import fitnessStudio.FitnessStudio;
import fitnessStudio.FitnessStudioManagement;
import fitnessStudio.OperationTimeRepositiory;
import fitnessStudio.catalog.CommodityCatalog;

import fitnessStudio.user.RegistrationForm;
import fitnessStudio.user.StudioUser;
import fitnessStudio.user.StudioUserManagement;
import fitnessStudio.user.StudioUserRepository;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.*;

import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Streamable;

import static org.salespointframework.core.Currencies.EURO;

import java.util.List;
import java.util.Optional;


//更改RegistrationForm class to public
//change all class in Fitnesstudio manager to public
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserBalanceUnitTest {
	@Autowired
	FitnessStudioManagement fitnessStudioManagement;

	@Autowired
	StudioUserRepository studioUserRepository;

	@Autowired
	CommodityCatalog commodityCatalog;

	@Autowired
	OperationTimeRepositiory operationTimeRepositiory;

	@Autowired
	FitnessStudio fitnessStudio;

	@Autowired
	StudioUserManagement studioUserManagement;

	RegistrationForm registrationForm;


	Optional<UserAccount> userAccount;
	@Autowired
	OrderManagement<Order> orderManagement;

	StudioUser user1,user2,user3;

	@BeforeAll
	void before(){
		studioUserManagement.createUser(new RegistrationForm("user1", "Hans", "Wurst", "strasse", 23, "c", "stadt", "PLZ", "test@test.de",  "110", "123"));
		studioUserManagement.createUser(new RegistrationForm("user2", "Paul", "Sanger", "strasse", 23, "c", "stadt", "PLZ", "test@test.de",  "110", "123"));
		studioUserManagement.createUser(new RegistrationForm("user3", "Tanja", "Faber", "strasse", 23, "c", "stadt", "PLZ", "test@test.de",  "110", "123"));
		Streamable<StudioUser> users = studioUserRepository.findAll();
		List<StudioUser> userList = users.toList();
		user1 = userList.get(0);
		user2 = userList.get(1);
		user3 = userList.get(2);
		fitnessStudioManagement = new FitnessStudioManagement(studioUserRepository,orderManagement,operationTimeRepositiory, fitnessStudio);
	}

	@Test
	void changeUserBalance(){
		user1.setBalance(Money.of(0, EURO));
		user2.setBalance(Money.of(0, EURO));
		user3.setBalance(Money.of(0, EURO));
		studioUserRepository.save(user1);
		studioUserRepository.save(user2);
		studioUserRepository.save(user3);
		fitnessStudioManagement.changeBalance(user1,100);
		fitnessStudioManagement.changeBalance(user2,20);
		fitnessStudioManagement.changeBalance(user3,500);

		Assertions.assertTrue(user1.getBalance().equals(Money.of(100, EURO)));
		Assertions.assertTrue(user2.getBalance().equals(Money.of(20, EURO)));
		Assertions.assertTrue(user3.getBalance().equals(Money.of(500, EURO)));


	}



}
