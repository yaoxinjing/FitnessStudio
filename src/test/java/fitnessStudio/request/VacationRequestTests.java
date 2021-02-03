package fitnessStudio.request;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
class VacationRequestTests {
	
	@Autowired
	StudioUserManagement studioUserManagement;
	
	@Test
	void testSetter() {		
		VacationRequest request = new VacationRequest(LocalDate.parse("2022-05-20"), LocalDate.parse("2022-05-25"), "keine Angabe");
		
		request.updateData(LocalDate.parse("2022-05-17"), LocalDate.parse("2022-05-28"), "Wahlkampf");
		assertEquals(LocalDate.parse("2022-05-17"), request.getBeginning());
		assertEquals(LocalDate.parse("2022-05-28"), request.getEnd());
		assertEquals("Wahlkampf", request.getReason());
		
	}
	
	@Test
	void testGetters() {
		VacationRequest request = new VacationRequest(LocalDate.parse("2022-05-20"), LocalDate.parse("2022-05-25"), "keine Angabe");
		
		assertEquals(LocalDate.parse("2022-05-20"), request.getBeginning());
		assertEquals("20.05.2022", request.getBeginningString());
		assertEquals(LocalDate.parse("2022-05-25"), request.getEnd());
		assertEquals("25.05.2022", request.getEndString());
		assertEquals("keine Angabe", request.getReason());
	}

}
