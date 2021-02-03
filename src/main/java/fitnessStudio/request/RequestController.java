package fitnessStudio.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Optional;

import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import fitnessStudio.request.Request.RequestType;
import fitnessStudio.request.RequestState.RequestStateType;
import fitnessStudio.user.StudioUser;
import fitnessStudio.user.StudioUserManagement;

/** 
 * Spring controller class to recognize a link being opened related to requests
 */
@Controller
public class RequestController {
	
	private final RequestManagement requestManagement;
	private final StudioUserManagement studioUserManagement;
	private final RequestRepository requests;

	public RequestController(RequestManagement requestManagement, 
			StudioUserManagement studioUserManagement, RequestRepository requests) {
		
		Assert.notNull(requestManagement, "RequestManagement must not be null!");
		Assert.notNull(studioUserManagement, "StudioUserManagement must not be null!");
		Assert.notNull(requests, "Requests must not be null!");
		
		this.requestManagement = requestManagement;
		this.studioUserManagement = studioUserManagement;
		this.requests = requests;
	}
	
	@PreAuthorize("hasAnyRole('CUSTOMER','EMPLOYEE','BOSS')")
	@GetMapping("/manageRequest")
	public String requestList(@LoggedIn Optional<UserAccount> userAccount, Model model) {
		
		model.addAttribute("currentUserRequests", requestManagement.findByContract
				(studioUserManagement.findCorrespondingUser(userAccount.get()).getContract()));
		model.addAttribute("pauseRequests", requestManagement
				.findByStateAndType(RequestStateType.EINGEREICHT, RequestType.PAUSIERUNGSANTRAG));
		model.addAttribute("trainingRequests", requestManagement
				.findByStateAndType(RequestStateType.EINGEREICHT, RequestType.TRAININGSANTRAG));
		model.addAttribute("vacationRequests", requestManagement
				.findByStateAndType(RequestStateType.EINGEREICHT, RequestType.URLAUBSANTRAG));
		model.addAttribute("processedRequests", requestManagement.getProcessedRequests());

		return "manageRequest";
	}
	
	@PreAuthorize("hasAnyRole('EMPLOYEE','BOSS')")
	@GetMapping("/manageRequest/acceptRequest/{id}")
    String acceptRequest(@LoggedIn Optional<UserAccount> userAccount, Model model, @PathVariable long id) {
        requestManagement.acceptRequest(id, studioUserManagement.findCorrespondingUser(userAccount.get()));
        
        return "redirect:../../manageRequest";
    }
	
	@PreAuthorize("hasAnyRole('EMPLOYEE','BOSS')")
	@GetMapping("/manageRequest/declineRequest/{id}")
    String declineRequest(@LoggedIn Optional<UserAccount> userAccount, Model model, @PathVariable long id) {
		requestManagement.declineRequest(id, "keine Angabe!", studioUserManagement.findCorrespondingUser(userAccount.get()));
        return "redirect:../../manageRequest";
    }
	
	@PreAuthorize("hasAnyRole('CUSTOMER','EMPLOYEE','BOSS')")
	@GetMapping("/manageRequest/addPauseRequest")
	String addPauseRequest(PauseRequestForm form, Model model) {
		model.addAttribute("form", form);
		
		return "addPauseRequest";
	}
	
	@PostMapping("/manageRequest/addPauseRequest")
	public String addPauseRequest
	(@LoggedIn Optional<UserAccount> userAccount, PauseRequestForm form, Errors result, Model model) {
		
		if (result.hasErrors()) {
			return "addPauseRequest";
		}
		
		if(YearMonth.parse(form.getMonth()).isBefore(YearMonth.now()))  {
			model.addAttribute("showPlausError", "true");
			return addPauseRequest(form, model);
		}

		StudioUser user = studioUserManagement.findCorrespondingUser(userAccount.get());
		requestManagement.submitPauseRequest(form, user.getContract());
		
		return "redirect:../manageRequest";
	}
	
	@PreAuthorize("hasAnyRole('EMPLOYEE','BOSS')")
	@GetMapping("/manageRequest/addVacationRequest")
	String addVacationRequest(VacationRequestForm form, Model model) {
		model.addAttribute("form", form);
		return "addVacationRequest";
	}
	
	@PostMapping("/manageRequest/addVacationRequest")
	public String addVacationRequest(@LoggedIn Optional<UserAccount> userAccount, 
			VacationRequestForm form, Errors result, Model model) {
		
		if (result.hasErrors()) {
			return "addVacationRequest";
		}
		
		if((LocalDate.parse(form.getBeginning()).isBefore(LocalDate.now())) || (!LocalDate.parse(form.getBeginning())
				.isBefore(LocalDate.parse(form.getEnd())))) {
			model.addAttribute("showPlausError", "true");
			return addVacationRequest(form, model);
		}

		StudioUser user = studioUserManagement.findCorrespondingUser(userAccount.get());
		requestManagement.submitVacationRequest
		(form, user.getContract());
		
		return "redirect:../manageRequest";
	}
	
	@PreAuthorize("hasAnyRole('CUSTOMER','EMPLOYEE','BOSS')")
	@GetMapping("/manageRequest/addTrainingRequest")
	String addTrainingRequest(TrainingRequestForm form, Model model) {
		model.addAttribute("form", form);
		
		Iterable<StudioUser> temp = studioUserManagement.findAllEmployees();
		ArrayList<String> out = new ArrayList<String>();
		for (StudioUser user : temp) {
			out.add(user.getFullname());	
		}
		
		model.addAttribute("employees", out);
		return "addTrainingRequest";
	}
	
	@PostMapping("/manageRequest/addTrainingRequest")
	public String addTrainingRequest(@LoggedIn Optional<UserAccount> userAccount, 
			TrainingRequestForm form, Errors result, Model model) {
		
		if (result.hasErrors()) {
			return "addTrainingRequest";
		}
		
		if(LocalDate.parse(form.getDate()).isBefore(LocalDate.now()) || 
				(LocalDate.parse(form.getDate()).isEqual(LocalDate.now()) && 
						LocalTime.parse(form.getTime()).isBefore(LocalTime.now())))  {
			model.addAttribute("showPlausError", "true");
			return addTrainingRequest(form, model);
		}
		
		StudioUser user = studioUserManagement.findCorrespondingUser(userAccount.get());
		requestManagement.submitTrainingRequest
		(form, user.getContract());
		
		return "redirect:../manageRequest";
	}
	
	@PreAuthorize("hasAnyRole('EMPLOYEE','BOSS')")
	@GetMapping("/manageRequest/editPauseRequest/{id}")
	public String editPauseRequest(@PathVariable long id, Model model) {
		PauseRequest request = (PauseRequest)requestManagement.findById(id);
		PauseRequestForm form = new PauseRequestForm(request.getMonth().toString(), request.getReason());
		model.addAttribute("form", form);
		model.addAttribute("id", id);
		
		return"editPauseRequest";
	}
	
	@PostMapping("/manageRequest/editPauseRequest/{id}")
	public String editPauseRequest(@PathVariable long id, PauseRequestForm form, Errors result, Model model) {
		
		if (result.hasErrors()) {
			return "editPauseRequest";
		}
		
		PauseRequest request = (PauseRequest)requestManagement.findById(id);
		request.updateData(YearMonth.parse(form.getMonth()), form.getReason());
		requests.save(request);

		return"redirect:../../manageRequest";
	}
	
	@PreAuthorize("hasAnyRole('EMPLOYEE','BOSS')")
	@GetMapping("/manageRequest/editVacationRequest/{id}")
	public String editVacationRequest(@PathVariable long id, Model model) {
		VacationRequest request = (VacationRequest)requestManagement.findById(id);
		VacationRequestForm form = new VacationRequestForm
				(request.getBeginning().toString(), request.getEnd().toString(), request.getReason());
		model.addAttribute("form", form);
		model.addAttribute("id", id);
		
		return"editVacationRequest";
	}

	@PostMapping("/manageRequest/editVacationRequest/{id}")
	public String editVacationRequest(@PathVariable long id, VacationRequestForm form, Errors result, Model model) {
		
		if (result.hasErrors()) {
			return "editVacationRequest";
		}
		
		VacationRequest request = (VacationRequest)requestManagement.findById(id);
		request.updateData(LocalDate.parse(form.getBeginning()), LocalDate.parse(form.getEnd()), form.getReason());
		requests.save(request);

		return"redirect:../../manageRequest";
	}
	
	@PreAuthorize("hasAnyRole('CUSTOMER','EMPLOYEE','BOSS')")
	@GetMapping("/manageRequest/editTrainingRequest/{id}")
	public String editTrainingRequest(@PathVariable long id, Model model) {
		TrainingRequest request = (TrainingRequest)requestManagement.findById(id);
		TrainingRequestForm form = new TrainingRequestForm
				(request.getDate().toString(), request.getTime().toString(), request.getEmployee());
		
		Iterable<StudioUser> temp = studioUserManagement.findAllEmployees();
		ArrayList<String> out = new ArrayList<String>();
		for (StudioUser user : temp) {
			out.add(user.getFullname());	
		}
		
		model.addAttribute("employees", out);
		
		model.addAttribute("form", form);
		model.addAttribute("id", id);
		
		return"editTrainingRequest";
	}

	@PostMapping("/manageRequest/editTrainingRequest/{id}")
	public String editTrainingRequest(@PathVariable long id, TrainingRequestForm form, Errors result, Model model) {
		
		if (result.hasErrors()) {
			return "editTrainingRequest";
		}
		
		TrainingRequest request = (TrainingRequest)requestManagement.findById(id);
		request.updateData(LocalDate.parse(form.getDate()), LocalTime.parse(form.getTime())
				, form.getEmployee());
		requests.save(request);

		return"redirect:../../manageRequest";
	}
}
