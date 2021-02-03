package fitnessStudio.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;

import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import fitnessStudio.request.Request.RequestType;
import fitnessStudio.request.RequestState.RequestStateType;
import fitnessStudio.user.Contract;
import fitnessStudio.user.StudioUser;
import fitnessStudio.user.StudioUserManagement;

/** 
 * Provides all functionality related to requests
 */
@Component
public class RequestManagement {
	
	private final RequestRepository requests;
	private final StudioUserManagement studioUserManagement;
	
	public RequestManagement(RequestRepository requests, StudioUserManagement studioUserManagement) {
		
		Assert.notNull(requests, "Requests must not be null!");
		Assert.notNull(studioUserManagement, "StudioUserManagement must not be null!");
		
		this.requests = requests;
		this.studioUserManagement = studioUserManagement;
	}
	
	/**
	 * Method for creating new pause request
	 */
	public PauseRequest submitPauseRequest(PauseRequestForm form, Contract contract) {
		PauseRequest newRequest = new PauseRequest(YearMonth.parse(form.getMonth()), form.getReason());
		newRequest.setContract(contract);
		return requests.save(newRequest);
	}

	public VacationRequest submitVacationRequest(VacationRequestForm form, Contract contract) {
		VacationRequest newRequest = new VacationRequest
				(LocalDate.parse(form.getBeginning()), LocalDate.parse(form.getEnd()), form.getReason());
		newRequest.setContract(contract);
		return requests.save(newRequest);
	}
	
	public TrainingRequest submitTrainingRequest(TrainingRequestForm form, Contract contract) {
		TrainingRequest newRequest = new TrainingRequest(LocalDate.parse(form.getDate()),
				LocalTime.parse(form.getTime()), form.getEmployee());
		newRequest.setContract(contract);
		return requests.save(newRequest);
	}
	
	public void acceptRequest(long id, StudioUser employee) {
		Request request = requests.findById(id).get();
		request.setState(new Accepted());
		request.setApplicationProcessor(employee);
		requests.save(request);
	}
	
	public void declineRequest(long id, String reason, StudioUser employee) {
		Request request = requests.findById(id).get();
		request.setState(new Declined(reason));
		request.setApplicationProcessor(employee);
		requests.save(request);
	}
	
	public RequestRepository getRequests() {
		return requests;
	}
	
	public Request findById(long id) {
		return requests.findById(id).get();
	}
	
	public Iterable<Request> findByContract(Contract contract) {
		Streamable<Request> temp = requests.findAll();
		ArrayList<Request> out = new ArrayList<Request>();
		for (Request request : temp) {
			if (request.getContract() == contract) {
				out.add(request);
			}
		}
		return out;
	}
	
	public Iterable<Request> findByStateAndType(RequestStateType requestState, RequestType requestType) {
		Streamable<Request> temp = requests.findAll();
		ArrayList<Request> out = new ArrayList<Request>();
		for (Request request : temp) {
			if (request.getType() == requestType && request.getState() == requestState) {
				out.add(request);
			}
		}
		return out;
	}
	
	/**
	 * Method that returns all already processed requests
	 */
	public Iterable<Request> getProcessedRequests() {
		Streamable<Request> temp = requests.findAll();
		ArrayList<Request> out = new ArrayList<Request>();
		for (Request request : temp) {
			if (request.getState() == RequestStateType.AKZEPTIERT || request.getState() == RequestStateType.ABGELEHNT) {
				out.add(request);
			}
		}
		return out;
	}
}
