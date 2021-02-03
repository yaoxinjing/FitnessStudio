package fitnessStudio.request;

import javax.validation.constraints.NotEmpty;

/** 
 * Checks inputs while editing or creating a pause request for errors 
 */
class PauseRequestForm {

	@NotEmpty(message = "{PauseRequestForm.month.NotEmpty}") //
	private final String month;

	@NotEmpty(message = "{PauseRequestForm.reason.NotEmpty}") //
	private final String reason;

	public PauseRequestForm(String month, String reason) {

		this.month = month;
		this.reason = reason;
	}

	public String getMonth() {
		return month;
	}

	public String getReason() {
		return reason;
	}
}

