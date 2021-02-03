package fitnessStudio;


public class OperationForm {

	private Workday monday;
	private Workday tuesday;
	private Workday wednesday;
	private Workday thursday;
	private Workday friday;
	private Workday saturday;
	private Workday sunday;

	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	public OperationForm() {}
	
	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	public OperationForm( Workday monday,
						  Workday tuesday,
						  Workday wednesday,
						  Workday thursday,
						  Workday friday,
						  Workday saturday,
						  Workday sunday) {
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;
	}

	/* ===Getter && Setter=== ʕ•́ᴥ•̀ʔ */
	//Getter
	public Workday getMonday() {
		return monday;
	}

	public Workday getTuesday() {
		return tuesday;
	}

	public Workday getWednesday() {
		return wednesday;
	}

	public Workday getThursday() {
		return thursday;
	}

	public Workday getFriday() {
		return friday;
	}

	public Workday getSaturday() {
		return saturday;
	}

	public Workday getSunday() {
		return sunday;
	}

	
	//Setter
	public void setMonday(Workday monday) {
		this.monday = monday;
	}

	public void setTuesday(Workday tuesday) {
		this.tuesday = tuesday;
	}

	public void setWednesday(Workday wednesday) {
		this.wednesday = wednesday;
	}

	public void setThursday(Workday thursday) {
		this.thursday = thursday;
	}

	public void setFriday(Workday friday) {
		this.friday = friday;
	}

	public void setSaturday(Workday saturday) {
		this.saturday = saturday;
	}

	public void setSunday(Workday sunday) {
		this.sunday = sunday;
	}
	
}
