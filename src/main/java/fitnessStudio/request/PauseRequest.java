package fitnessStudio.request;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;

/** 
 * Request subclass for pause requests 
 */
@Entity
public class PauseRequest extends Request {

	private String reason;
	private YearMonth month;

	public PauseRequest() {}

	public PauseRequest(YearMonth month, String reason) {
		super();
		this.month = month;
		this.reason = reason;
		this.type = RequestType.PAUSIERUNGSANTRAG;
	}
	
	/**
	 * Method for updating arguments
	 */
	public void updateData(YearMonth month, String reason) {
		this.month = month;
		this.reason = reason;
	}
	
	public String getReason() {
		return reason;
	}

	public YearMonth getMonth() {
		return month;
	}
	
	/**
	 *  Method for converting YearMonth to a normal String
	 */
	public String getMonthString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.yyyy");
		String monthString = month.format(formatter);
		return monthString;
	}

}
