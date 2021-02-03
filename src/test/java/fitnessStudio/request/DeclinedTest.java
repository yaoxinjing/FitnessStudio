package fitnessStudio.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fitnessStudio.request.RequestState.RequestStateType;

public class DeclinedTest {
	
	@Autowired
	RequestState requestState;
	
	@Test
	void testConstructor() {
		Declined declined = new Declined("test");
		assertEquals(declined.getReason(), "test");
		assertEquals(declined.getStateType(), RequestStateType.ABGELEHNT);
	}
	
	@Test
	void testSetReason() {
		Declined declined = new Declined("test");
		declined.setReason("otherTest");
		assertEquals(declined.getReason(), "otherTest");
	}
}
