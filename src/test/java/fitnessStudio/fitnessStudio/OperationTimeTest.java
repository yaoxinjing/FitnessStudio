package fitnessStudio.fitnessStudio;

import fitnessStudio.*;
import fitnessStudio.user.RegistrationForm;
import fitnessStudio.user.StudioUser;
import fitnessStudio.user.StudioUserManagement;
import fitnessStudio.user.StudioUserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Streamable;

import java.util.List;
@SpringBootTest
public class OperationTimeTest {
	@Autowired
	private OperationTimeRepositiory operationTimeRepositiory;

	@Autowired
	FitnessStudioManagement fitnessStudioManagement;

	@Autowired
	StudioUserRepository studioUserRepository;
	
	@Autowired
	FitnessStudio fitnessStudio;
	
	@Autowired
	StudioUserManagement studioUserManagement;

	StudioUser user1,user2,user3;

	@Autowired
	OrderManagement<Order> orderManagement;

	@BeforeEach
	void before(){
		studioUserManagement.createUser(new RegistrationForm("user1", "Hans", "Wurst", "strasse", 23, "c", "stadt", "PLZ", "test@test.de",  "110", "123"));
		studioUserManagement.createUser(new RegistrationForm("user2", "Paul", "Sanger", "strasse", 23, "c", "stadt", "PLZ", "test@test.de",  "110", "123"));
		studioUserManagement.createUser(new RegistrationForm("user3", "Tanja", "Faber", "strasse", 23, "c", "stadt", "PLZ", "test@test.de",  "110", "123"));
		Streamable<StudioUser> users = studioUserRepository.findAll();
		List<StudioUser> userList = users.toList();
		user1 = userList.get(0);
		user2 = userList.get(1);
		user3 = userList.get(2);
		fitnessStudioManagement = new FitnessStudioManagement(studioUserRepository,orderManagement,operationTimeRepositiory, fitnessStudio);
	}

	@Test
	public void OperationTimeTest(){
//		OperationTime operationTime = new OperationTime();

//		operationTime.setName("default");
//
//		operationTime.setMonday(new Workday("05:00","18:00"));
//		operationTime.setThursday(new Workday("06:00","18:00"));
//		operationTime.setTuesday(new Workday("06:00","18:00"));
//		operationTime.setWednesday(new Workday("06:00","18:00"));
//		operationTime.setFriday(new Workday("06:00","18:00"));
//		operationTime.setSaturday(new Workday("06:00","18:00"));
//		operationTime.setSunday(new Workday("06:00","18:00"));
//		operationTimeRepositiory.save(operationTime);


		Workday day = new Workday("1:00","17:00");
		OperationForm newForm = new OperationForm(day,day,day,day,day,day,day);

		fitnessStudioManagement.changeOprationTime(newForm);

		OperationTime operationTime1 = operationTimeRepositiory.findByName("default");

		Assert.assertEquals(operationTime1.getMonday(),day);
		Assert.assertEquals(operationTime1.getTuesday(),day);
		Assert.assertEquals(operationTime1.getWednesday(),day);
		Assert.assertEquals(operationTime1.getFriday(),day);
		Assert.assertEquals(operationTime1.getSaturday(),day);
		Assert.assertEquals(operationTime1.getSunday(),day);

	}
}
