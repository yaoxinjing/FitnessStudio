package fitnessStudio.request;

import java.util.Iterator;

import org.salespointframework.core.DataInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import fitnessStudio.user.Contract;
import fitnessStudio.user.StudioUser;
import fitnessStudio.user.StudioUserManagement;

/** 
 * Initializes test requests
 */
@Component
@Order(20)
public class RequestDataInitializer implements DataInitializer {
	
	private static final Logger LOG = LoggerFactory.getLogger(RequestDataInitializer.class);

	private final RequestManagement requestManagement;
	private final StudioUserManagement studioUserManagement;
	private final RequestRepository requestRepository;

	RequestDataInitializer(RequestManagement requestManagement, StudioUserManagement studioUserManagement, RequestRepository requestRepository) {

		Assert.notNull(requestManagement, "RequestManagement must not be null!");
		Assert.notNull(studioUserManagement, "StudioUserManagement must not be null!");
		Assert.notNull(requestRepository, "StudioUserManagement must not be null!");

		this.requestManagement = requestManagement;
		this.studioUserManagement = studioUserManagement;
		this.requestRepository = requestRepository;
	}

	@Override
	public void initialize() {
		
		if (requestRepository.findAll().iterator().hasNext()) {
			return;
		}
		
		LOG.info("Creating example requests.");
		
		Iterator<StudioUser> userIterator = studioUserManagement.findAllUsers().iterator();
		StudioUser user1 = userIterator.next();
		Contract userContract1 = user1.getContract();
		userIterator.remove();
		StudioUser user2 = userIterator.next();
		Contract userContract2 = user2.getContract();
		userIterator.remove();
		StudioUser user3 = userIterator.next();
		Contract userContract3 = user3.getContract();
		
		Iterator<StudioUser> employeeIterator = studioUserManagement.findAllEmployees().iterator();
		StudioUser employee1 = employeeIterator.next();
		Contract employeeContract1 = employee1.getContract();
		employeeIterator.remove();
		StudioUser employee2 = employeeIterator.next();
		Contract employeeContract2 = employee2.getContract();
		
		userContract1.addRequest(requestManagement.submitPauseRequest
				(new PauseRequestForm("2021-08", "WM!!!"), userContract1));
		userContract2.addRequest(requestManagement.submitPauseRequest
				(new PauseRequestForm("2021-10", "keine Angabe"), userContract2));
		
		userContract1.addRequest(requestManagement.submitTrainingRequest
				(new TrainingRequestForm("2022-05-17", "11:15", employee1.getFullname()), userContract1));
		userContract3.addRequest(requestManagement.submitTrainingRequest
				(new TrainingRequestForm("2022-07-03", "13:00", employee2.getFullname()), userContract3));
		
		employeeContract1.addRequest(requestManagement.submitVacationRequest
				(new VacationRequestForm("2021-02-22", "2021-02-27", "Skiurlaub"), employeeContract1));
		employeeContract2.addRequest(requestManagement.submitVacationRequest
				(new VacationRequestForm("2021-04-11", "2021-04-14", "Konzert"), employeeContract2));
	}
}
