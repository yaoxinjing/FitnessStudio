package fitnessStudio.request;

/** 
 * Request state subclass, representing an declined request state
 */
public class Declined extends RequestState {
	
	private String reason;
	
	public Declined(String reason) {
		this.reason = reason;
		this.stateType = RequestStateType.ABGELEHNT;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
