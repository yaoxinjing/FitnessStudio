package fitnessStudio.request;

import javax.validation.constraints.NotEmpty;

/** 
 * Checks inputs while editing or creating a vacation request for errors 
 */
class VacationRequestForm {

	@NotEmpty(message = "{VacationRequestForm.beginning.NotEmpty}")
	private final String beginning;

	@NotEmpty(message = "{VacationRequestForm.end.NotEmpty}")
	private final String end;
	
	@NotEmpty(message = "{VacationRequestForm.reason.NotEmpty}")
	private final String reason;

	public VacationRequestForm(String beginning, String end, String reason) {
		
		this.beginning = beginning;
		this.end = end;
		this.reason = reason;
	}

	public String getBeginning() {
		return beginning;
	}
	
	public String getEnd() {
		return end;
	}

	public String getReason() {
		return reason;
	}
}


