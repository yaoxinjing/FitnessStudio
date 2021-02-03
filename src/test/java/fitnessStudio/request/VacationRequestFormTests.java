package fitnessStudio.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class VacationRequestFormTests {
	
	@Autowired 
	VacationRequestForm vacationRequestForm;
	
	@Test
	void testBeginning() {
		VacationRequestForm form = new VacationRequestForm("22-02-2021", "24-02-2021", "keine Angabe!");
		String result = form.getBeginning();
		assertEquals("22-02-2021", result);
	}
	
	@Test
	void testEnd() {
		VacationRequestForm form = new VacationRequestForm("22-02-2021", "24-02-2021", "keine Angabe!");
		String result = form.getEnd();
		assertEquals("24-02-2021", result);
	}
	
	@Test
	void testReason() {
		VacationRequestForm form = new VacationRequestForm("22-02-2021", "24-02-2021", "keine Angabe!");
		String result = form.getReason();
		assertEquals("keine Angabe!", result);
	}

}
