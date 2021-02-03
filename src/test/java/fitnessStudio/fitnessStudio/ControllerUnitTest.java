package fitnessStudio.fitnessStudio;

import fitnessStudio.FitnessStudio;
import fitnessStudio.FitnessStudioController;
import fitnessStudio.FitnessStudioManagement;
import fitnessStudio.OperationTimeRepositiory;
import fitnessStudio.user.RegistrationForm;
import fitnessStudio.user.StudioUser;
import fitnessStudio.user.StudioUserManagement;
import fitnessStudio.user.StudioUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Streamable;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerUnitTest {
	@Autowired
	FitnessStudioController fitnessStudioController;
	@Autowired
	MockMvc mvc;

	@Autowired
	FitnessStudioManagement fitnessStudioManagement;

	@Autowired
	StudioUserRepository studioUserRepository;



	@Autowired
	OperationTimeRepositiory operationTimeRepositiory;

	@Autowired
	FitnessStudio fitnessStudio;

	@Autowired
	StudioUserManagement studioUserManagement;


	@Autowired
	OrderManagement<Order> orderManagement;

	Optional<UserAccount> userAccount;

	StudioUser user1,user2,user3;

	@BeforeEach
	void before(){
		studioUserManagement.createUser(new RegistrationForm("user1",
				"Hans", "Wurst", "strasse", 23,
				"c", "stadt", "PLZ", "test@test.de",
				"110", "123"));
		studioUserManagement.createUser(new RegistrationForm("user2",
				"Paul", "Sanger", "strasse", 23,
				"c", "stadt", "PLZ", "test@test.de",
				"110", "123"));
		studioUserManagement.createUser(new RegistrationForm("user3",
				"Tanja", "Faber", "strasse", 23,
				"c", "stadt", "PLZ", "test@test.de",
				"110", "123"));
		Streamable<StudioUser> users = studioUserRepository.findAll();
		List<StudioUser> userList = users.toList();
		user1 = userList.get(0);
		userAccount = Optional.of(user1.getUserAccount());
		user2 = userList.get(1);
		user3 = userList.get(2);
		fitnessStudioManagement = new FitnessStudioManagement(studioUserRepository,
				orderManagement,operationTimeRepositiory, fitnessStudio);
	}

//	@Test
//	void acceseHomepage() throws Exception {
//		mvc.perform(get("/")) //
//				.andExpect(status().is3xxRedirection())
//				.andExpect(content().string(containsString("")));
//	}

//	@Test
//	void acceseLogeout() throws Exception {
//		mvc.perform(get("/logoutState")) //
//				.andExpect(status().is3xxRedirection())
//				.andExpect(content().string(containsString("")));
//	}


//	@Test
//	void acceseIndexpage() throws Exception {
//		mvc.perform(get("/index")) //
//				.andExpect(status().isOk())
//				.andExpect(view().name("index"));
//	}
//
//
//	@Test
//	void acceseloginError() throws Exception {
//		mvc.perform(get("/loginError")) //
//				.andExpect(status().isOk())
//				.andExpect(view().name("login"));
//		ExtendedModelMap model = new ExtendedModelMap();
//		fitnessStudioController.getLoginError(model);
//
//		assertThat(model.get("error").equals("Username or Password wrong"));
//
//	}
//
//
//	@Test
//	void acceseBildPage() throws Exception {
//		mvc.perform(get("/bilder")) //
//				.andExpect(status().isOk())
//				.andExpect(view().name("bilder"));
//
//
//	}
//	@Test
//	@WithMockUser(roles = {"USER"})
//	void acceseVerwaltung() throws Exception {
//		mvc.perform(get("/verwaltung")) //
//				.andExpect(status().isOk())
//				.andExpect(view().name("verwaltung"));
//	}
//
//	@Test
//	@WithMockUser(roles = {"USER"})
//	void changeUserBalance() throws Exception {
//		Double balance = 10.0;
//		fitnessStudioController.changeUserBalance(balance,userAccount);
//		assertEquals(studioUserRepository.findByUserAccount(user1.getUserAccount()).getBalance(),
//				Money.of(10, EURO));
//
//	}
//
//	@Test
//	@WithMockUser(roles = {"USER"})
//	void addAffiliateBonus()throws Exception{
//		fitnessStudioController.addAffiliateBonus(user2.getFullname(),userAccount);
//		assertEquals(studioUserRepository.findByUserAccount(user2.getUserAccount()).getBalance(),
//				Money.of(10, EURO));
//	}

}
