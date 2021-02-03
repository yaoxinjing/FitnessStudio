package fitnessStudio.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import fitnessStudio.user.StudioUser;

/** 
 * Request subclass for training requests 
 */
@Entity
public class TrainingRequest extends Request {

	private LocalDate date;
	private LocalTime time;
	private String employee;

	public TrainingRequest() {}

	public TrainingRequest(LocalDate date, LocalTime time, String employee) {
		super();
		this.date = date;
		this.time = time;
		this.employee = employee;
		this.type = RequestType.TRAININGSANTRAG;
	}
	
	/**
	 * Method for updating arguments
	 */
	public void updateData(LocalDate date, LocalTime time, String employee) {
		this.date = date;
		this.time = time;
		this.employee = employee;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	// Method for converting LocalDate to a normal String
	public String getDateString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	    String dateString = date.format(formatter);
		return dateString;
	}
	
	public LocalTime getTime() {
		return time;
	}
	
	public String getTimeString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    	String timeString = time.format(formatter);
		return timeString;
	}
	
	public String getEmployee() {
		return employee;
	}

}
