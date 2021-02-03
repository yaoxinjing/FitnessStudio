package fitnessStudio.request;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fitnessStudio.request.Request.RequestType;
import fitnessStudio.request.RequestState.RequestStateType;
import fitnessStudio.user.RegistrationForm;
import fitnessStudio.user.StudioUser;
import fitnessStudio.user.StudioUserManagement;

@SpringBootTest
//@ExtendWith(SpringExtension.class)
class RequestTests {
	
	@Autowired
	StudioUserManagement studioUserManagement;
	
	@Test
	void testSetter() {		
		Request request = new VacationRequest(LocalDate.parse("2021-02-22"), LocalDate.parse("2021-02-27"), "keine Angabe");
		ArrayList<String> tasks = new ArrayList<String>();
		tasks.add("TRAINEE");		
		StudioUser applicationProcessor = studioUserManagement.createUser(new RegistrationForm("user1", "Hans", "Wurst", "strasse", 23, "c", "stadt", "PLZ", "test@test.de",  "110", "123"));		
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		
		request.setState(new Accepted());
		assertEquals(request.getState(), RequestStateType.AKZEPTIERT);
		request.setApplicationProcessor(applicationProcessor);
		assertEquals(applicationProcessor, request.getApplicationProcessor());
	}
	
	@Test
	void testGetters() {
		Request request = new VacationRequest(LocalDate.parse("2021-02-22"), LocalDate.parse("2021-02-27"), "keine Angabe");
		ArrayList<String> tasks = new ArrayList<String>();
		tasks.add("TRAINEE");
		StudioUser applicationProcessor = studioUserManagement.createUser(new RegistrationForm("user1", "Hans", "Wurst", "strasse", 23, "c", "stadt", "PLZ", "test@test.de",  "110", "123"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		
		assertEquals(RequestType.URLAUBSANTRAG, request.getType());
		assertEquals(LocalDateTime.now().format(formatter), request.getFilingDate());
		//assertEquals(LocalDateTime.now().format(formatter), request.getProcessingDate());
		assertEquals(RequestStateType.EINGEREICHT, request.getState());
		request.setApplicationProcessor(applicationProcessor);
		assertEquals(applicationProcessor, request.getApplicationProcessor());
	}

}
