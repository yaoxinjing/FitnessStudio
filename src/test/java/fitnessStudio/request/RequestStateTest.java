package fitnessStudio.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fitnessStudio.request.RequestState.RequestStateType;

public class RequestStateTest {
	
	@Autowired
	RequestState requestState;
	
	@Test
	void testSetState() {
		RequestState requestState = new RequestState();
		requestState.setState(RequestStateType.AKZEPTIERT);
		assertEquals(requestState.getStateType(), RequestStateType.AKZEPTIERT);
	}
}
