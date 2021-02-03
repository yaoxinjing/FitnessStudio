package fitnessStudio.request;

import fitnessStudio.user.StudioUserManagement;
import fitnessStudio.user.StudioUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class RequestControllerTests {
	
	@Autowired
	MockMvc mvc;
	@Autowired
	RequestController requestController;
	@Autowired
	RequestManagement requestManagement;
	@Autowired
	StudioUserManagement studioUserManagement;
	@Autowired
	StudioUserRepository users;
	
	@BeforeEach
	void before(){

		/*LocalDate date = LocalDate.parse("2021-01-01");

		Contract standardvertrag    =  (new Contract("Kundenvertrag standard",Money.of(10, EURO),date, date.plusMonths(6)));
		Contract mitarbeitervertrag =  (new Contract("Mitarbeitervertrag standard",Money.of(3000, EURO),LocalDate.parse("2021-01-01"),LocalDate.parse("2023-01-01")));

		studioUserManagement.createUser(new RegistrationForm("user1", "Hans", "Wurst", "strasse", 23, "c", "stadt", "PLZ", "test@test.de",  "110", "123", 2));	
		studioUserManagement.createUser(new RegistrationForm("user2", "Paul", "Sanger", "strasse", 23, "c", "stadt", "PLZ", "test@test.de",  "110", "123", 4));

		ArrayList<String> tasks = new ArrayList<String>();
		tasks.add("TRAINEE");

		
		
		
		StudioUser user1 = studioUserManagement.findAllUsers().iterator().next();
		Contract contract1 = user1.getContract();
		
		contract1.addRequest(requestManagement.submitPauseRequest(YearMonth.parse("2021-08"), "WM!!!", contract1));
		contract1.addRequest(requestManagement.submitPauseRequest(YearMonth.parse("2022-05"), "Wahlkampf", contract1));
		contract1.addRequest(requestManagement.submitTrainingRequest(LocalDate.parse("2022-05-17"), LocalTime.parse("11:16:32"), contract1));
		
		StudioUser user2 = studioUserManagement.findAllUsers().iterator().next();
		Contract contract2 = user2.getContract();
		
		contract2.addRequest(requestManagement.submitVacationRequest
				(LocalDate.parse("2021-02-22"), LocalDate.parse("2021-02-27"), "keine Angabe",contract2));
		contract2.addRequest(requestManagement.submitVacationRequest
				(LocalDate.parse("2021-04-11"), LocalDate.parse("2021-04-14"), "Konzert",contract2));*/	
	}

//	@Test
//	@WithMockUser(roles = {"BOSS"})
//	void accessRequests() throws Exception {
//		mvc.perform(get("/manageRequest")) //
//				.andExpect(status().isOk())
//				.andExpect(view().name("manageRequest"));
//	}
	
//	@Test
//	void acceptRequest() throws Exception {
//		mvc.perform(get("/manageRequest/acceptRequest/{id}")) //
//				.andExpect(status().is3xxRedirection())
//				.andExpect(content().string(containsString("manageRequest")));
//	}
//	
//	@Test
//	void declineRequest() throws Exception {
//		mvc.perform(get("/declineRequest/acceptRequest/{id}")) //
//				.andExpect(status().is3xxRedirection())
//				.andExpect(content().string(containsString("manageRequest")));
//	
//	}
}