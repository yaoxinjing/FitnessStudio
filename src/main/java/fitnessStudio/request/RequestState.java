package fitnessStudio.request;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** 
 * Superclass for request states, representing if a request has been 
 * accepted, declined or is in process. It is a class rather rather 
 * then an attribute to be able to dynamically assign a reason for declining a request.
 */
@Entity
public class RequestState {

	private @Id @GeneratedValue long id;

	public static enum RequestStateType {ABGELEHNT, EINGEREICHT, AKZEPTIERT};
	protected RequestStateType stateType;

	public RequestStateType getStateType() {
		return stateType;
	}

	public void setState(RequestStateType stateType) {
		this.stateType = stateType;
	}

	public String toString() {
		return stateType.name();
	}
}
