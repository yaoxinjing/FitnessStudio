package fitnessStudio.fitnessStudio;

import fitnessStudio.FitnessStudio;
import fitnessStudio.FitnessStudioManagement;
import fitnessStudio.OperationTimeRepositiory;
import fitnessStudio.catalog.CommodityCatalog;
import fitnessStudio.user.RegistrationForm;
import fitnessStudio.user.StudioUser;
import fitnessStudio.user.StudioUserManagement;
import fitnessStudio.user.StudioUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Streamable;

import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Optional;
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class addAffiliateBonusUnitTest {
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

	StudioUser user1,user2,user3;

	@Autowired
	OrderManagement<Order> orderManagement;

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
	void addAffiliateBonus(){
		//input name is as same as login user name :false
		boolean checkValue = fitnessStudioManagement.addAffiliateBonus(user1.getFullname(),user1);
		Assertions.assertFalse(checkValue);

		//input name not found
		boolean checkValue1 = fitnessStudioManagement.addAffiliateBonus("123241",user1);
		Assertions.assertFalse(checkValue1);

		//input name is valid
		boolean checkValue2 = fitnessStudioManagement.addAffiliateBonus(user2.getUsername(),user1);
		Assertions.assertTrue(checkValue2);
		Assertions.assertTrue(user1.isSetAffiliateBonus());
	}

}
