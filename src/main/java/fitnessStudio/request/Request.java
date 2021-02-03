package fitnessStudio.request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import fitnessStudio.request.RequestState.RequestStateType;
import fitnessStudio.user.Contract;
import fitnessStudio.user.StudioUser;

/** 
 * Superclass for requests which provides all general attributes 
 */
@Entity
public abstract class Request {

	private @Id @GeneratedValue long id;
	private LocalDateTime filingDate;
	private LocalDateTime processingDate;
	@ManyToOne(cascade = {CascadeType.ALL})
	public StudioUser applicationProcessor;
	@OneToOne(cascade=CascadeType.ALL)
	public RequestState state;
	@ManyToOne(cascade = {CascadeType.ALL})
	private Contract contract;
	public static enum RequestType {PAUSIERUNGSANTRAG, URLAUBSANTRAG, TRAININGSANTRAG};
	protected RequestType type;

	public Request() {
		this.filingDate = LocalDateTime.now();
		this.processingDate = null;
		this.applicationProcessor = null;
		this.state = new InProcess();
	}
	
	/**
	 * Method for converting all-uppercase Strings from Enumerations to normal case Strings
	 */
	public String convertString(String string) {
		String out = "";
		for (int i = 0; i < string.length(); i++) {
			if (i == 0) {
				out += (String.valueOf(Character.toUpperCase(string.charAt(i))));
			} else {
				out += (String.valueOf(Character.toLowerCase(string.charAt(i))));
			}
		}
		return out;
	}
	
	public RequestType getType() {
		return type;
	}
	
	public String getTypeString() {
		return convertString(type.toString());
	}
		
	public long getId() {
		return this.id;
	}

	public StudioUser getApplicant() {
		return this.contract.getPartner();
	}

	public void setState(RequestState state){
		this.processingDate = LocalDateTime.now();
		this.state = state;
	}
	
	public RequestStateType getState() {
		return state.stateType;
	}
	
	public String getStateString() {
		return convertString(state.stateType.toString());
	}
	
	/**
	 * Method with formatter to convert LocalDateTime to normal String
	 */
	public String getFilingDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    	String filingDateString = filingDate.format(formatter);
    	return filingDateString;
	}

	public String getProcessingDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    	String processingDateString = processingDate.format(formatter);
    	return processingDateString;
	}

	public void setApplicationProcessor(StudioUser applicationProcessor) {
		this.applicationProcessor = applicationProcessor;
	}

	public StudioUser getApplicationProcessor() {
		return applicationProcessor;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}
	
	public Contract getContract() {
		return this.contract;
	}
}
