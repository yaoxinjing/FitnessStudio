package fitnessStudio.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ManagerFormTest {
	
	@Autowired
	ManagerForm managerForm;
	
	@Test
	void testAffiliate() {
		ManagerForm form = new ManagerForm(20, 20, 20, 1500, 500, 5000);
		ManagerForm form1 = new ManagerForm(null, null, null, null, null, null);
		assertEquals(form.getAffiliate(), 20);
	}
	
	@Test
	void testDuration() {
		ManagerForm form = new ManagerForm(20, 20, 20, 1500, 500, 5000);
		ManagerForm form1 = new ManagerForm(null, null, null, null, null, null);
		assertEquals(form.getDuration(), 20);
	}
}
