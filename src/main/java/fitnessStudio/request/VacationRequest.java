package fitnessStudio.request;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;

/** 
 * Request subclass for vacation requests 
 */
@Entity	
public class VacationRequest extends Request {
	
	private LocalDate beginning;
	private LocalDate end;
	private String reason;
	
	public VacationRequest () {}

	public VacationRequest(LocalDate beginning, LocalDate end, String reason) {
		super();
		this.beginning = beginning;
		this.end = end;
		this.reason = reason;
		this.type = RequestType.URLAUBSANTRAG;
	}
	
	/**
	 * Method for updating arguments
	 */ 
	public void updateData(LocalDate beginning, LocalDate end, String reason) {
		this.beginning = beginning;
		this.end = end;
		this.reason = reason;
	}
	
	public LocalDate getBeginning() {
		return beginning;
	}
	
	/**
	 * Method for converting LocalDate to a normal String
	 */
		public String getBeginningString() {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	    	String beginningString = beginning.format(formatter);
			return beginningString;
		}
	
	public LocalDate getEnd() {
		return end;
	}
	
	public String getEndString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    	String endString = end.format(formatter);
		return endString;
	}
	
	public String getReason() {
		return reason;
	}
	
}
