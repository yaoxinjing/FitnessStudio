package fitnessStudio.user;

import javax.validation.constraints.NotEmpty;

class ContractForm {

	@NotEmpty(message = "{ContractForm.description.NotEmpty}") //
	private final String description;

	@NotEmpty(message = "{ContractForm.price.NotEmpty}") //
	private final double price;

	@NotEmpty(message = "{ContractForm.startTime.NotEmpty}") //
	private final String startTime;

	@NotEmpty(message = "{ContractForm.endTime.NotEmpty}") //
    private final String endTime;
    

	public ContractForm(String description, double price, String startTime, String endTime) {

        this.description = description;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;

    }

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public String getStartTime() {
		return startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}

	public String toString() {
		return this.description;
	}
}
