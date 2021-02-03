package fitnessStudio;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OperationTime {

	private @Id
	@GeneratedValue
	long id;
	private String name;

	private Workday monday;
	private Workday tuesday;
	private Workday wednesday;
	private Workday thursday;
	private Workday friday;
	private Workday saturday;
	private Workday sunday;
	
	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	public OperationTime() {}

	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	public OperationTime(long id,
						 String name,
						 Workday monday,
						 Workday tuesday,
						 Workday wednesday,
						 Workday thursday,
						 Workday friday,
						 Workday saturday,
						 Workday sunday) {
		this.id = id;
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;
		this.name=name;
	}
	
	
	/* ===Getter && Setter=== ʕ•́ᴥ•̀ʔ */
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name=name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Workday getMonday() {
		return monday;
	}

	public void setMonday(Workday monday) {
		this.monday = monday;
	}

	public Workday getTuesday() {
		return tuesday;
	}

	public void setTuesday(Workday tuesday) {
		this.tuesday = tuesday;
	}

	public Workday getWednesday() {
		return wednesday;
	}

	public void setWednesday(Workday wednesday) {
		this.wednesday = wednesday;
	}

	public Workday getThursday() {
		return thursday;
	}

	public void setThursday(Workday thursday) {
		this.thursday = thursday;
	}

	public Workday getFriday() {
		return friday;
	}

	public void setFriday(Workday friday) {
		this.friday = friday;
	}

	public Workday getSaturday() {
		return saturday;
	}

	public void setSaturday(Workday saturday) {
		this.saturday = saturday;
	}

	public Workday getSunday() {
		return sunday;
	}

	public void setSunday(Workday sunday) {
		this.sunday = sunday;
	}

	/* ===Methods=== ʕ•́ᴥ•̀ʔ */
	
	@Override
	public String toString() {
		return "OperationTime{" +
				"monday=" + monday +
				", tuesday=" + tuesday +
				", wednesday=" + wednesday +
				", thursday=" + thursday +
				", friday=" + friday +
				", saturday=" + saturday +
				", sunday=" + sunday +
				'}';
	}
}
