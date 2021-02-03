package fitnessStudio.request;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
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
class PauseRequestTests {
	
	@Autowired
	StudioUserManagement studioUserManagement;
	
	@Test
	void testSetter() {		
		PauseRequest request = new PauseRequest(YearMonth.parse("2021-10"), "keine Angabe");
		
		request.updateData(YearMonth.parse("2021-11"), "Wahlkampf");
		assertEquals(YearMonth.parse("2021-11"), request.getMonth());
		assertEquals("Wahlkampf", request.getReason());
		
	}
	
	@Test
	void testGetters() {
		PauseRequest request = new PauseRequest(YearMonth.parse("2021-10"), "keine Angabe");
		
		assertEquals(YearMonth.parse("2021-10"), request.getMonth());
		assertEquals("10.2021", request.getMonthString());
		assertEquals("keine Angabe", request.getReason());
	}

}
