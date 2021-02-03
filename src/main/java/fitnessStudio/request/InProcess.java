package fitnessStudio.request;

/** 
 * Request state subclass, representing an in process request state
 */
public class InProcess extends RequestState {

	public InProcess() {
		this.stateType = RequestStateType.EINGEREICHT; 
	}
	
}
