package fitnessStudio.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ContractFormTest {
	
	@Autowired
	ContractForm contractForm = new ContractForm("test", 20, "01.01.2021", "31.12.2021");
	
	
	@Test
	void testGetDescription(){
		assertEquals(contractForm.getDescription(), "test");
	}
	
	@Test
	void testGetPrice(){
		assertEquals(contractForm.getPrice(), 20);
	}
	
	@Test
	void testGetStratTime(){
		assertEquals(contractForm.getStartTime(), "01.01.2021");
	}
	
	@Test
	void testGetEndTime(){
		assertEquals(contractForm.getEndTime(), "31.12.2021");
	}
}
