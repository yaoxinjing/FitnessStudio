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
class TrainingRequestTests {
	
	@Autowired
	StudioUserManagement studioUserManagement;
	
	@Test
	void testSetter() {		
		TrainingRequest request = new TrainingRequest(LocalDate.parse("2022-05-20"), LocalTime.parse("11:45"), "user1");
		
		request.updateData(LocalDate.parse("2022-05-17"), LocalTime.parse("11:15"), "user2");
		assertEquals(LocalDate.parse("2022-05-17"), request.getDate());
		assertEquals(LocalTime.parse("11:15"), request.getTime());
		assertEquals("user2", request.getEmployee());
		
	}
	
	@Test
	void testGetters() {
		TrainingRequest request = new TrainingRequest(LocalDate.parse("2022-05-17"), LocalTime.parse("11:15"), "user1");
		
		assertEquals(LocalDate.parse("2022-05-17"), request.getDate());
		assertEquals("17.05.2022", request.getDateString());
		assertEquals(LocalTime.parse("11:15"), request.getTime());
		assertEquals("11:15", request.getTimeString());
		assertEquals("user1", request.getEmployee());
	}

}
