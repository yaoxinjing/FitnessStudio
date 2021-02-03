package fitnessStudio.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/** 
 * Checks inputs while editing or creating a training request for errors 
 */
class TrainingRequestForm {

	@NotEmpty(message = "{TrainingRequestForm.date.NotEmpty}") //
	private final String date;
	
	@NotEmpty(message = "{TrainingRequestForm.time.NotEmpty}") //
	private final String time;
	
	@NotNull(message = "{TrainingRequestForm.employee.NotEmpty}") //
	private final String employee;

	public TrainingRequestForm(String date, String time, String employee) {

		this.date = date;
		this.time = time;
		this.employee = employee;
	}

	public String getDate() {
		return date;
	}
	
	public String getTime() {
		return time;
	}
	
	public String getEmployee() {
		return employee;
	}
}

