package fitnessStudio.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PauseRequestFormTests {
	
	@Autowired 
	PauseRequestForm pauseRequestForm;
	
	@Test
	void testMonth() {
		PauseRequestForm form = new PauseRequestForm("03-2021", "keine Angabe!");
		String result = form.getMonth();
		assertEquals("03-2021", result);
	}
	
	@Test
	void testReason() {
		PauseRequestForm form = new PauseRequestForm("03-2021", "keine Angabe!");
		String result = form.getReason();
		assertEquals("keine Angabe!", result);
	}

}
