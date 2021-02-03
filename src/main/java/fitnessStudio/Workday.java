package fitnessStudio;

import com.mysema.commons.lang.Assert;
import java.io.Serializable;

public class Workday implements  Serializable {
	private static final long serialVersionUID = 6263813104267427472L;

	private String openingTime;
	private String closeTime;

	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	public  Workday(){}
	
	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	public Workday(String openingTime, String closeTime) {
		Assert.notNull(openingTime, "openingTime can not be NULL!");
		Assert.notNull(closeTime, "closeTime can not be NULL!");
		
		this.openingTime = openingTime;
		this.closeTime = closeTime;
	}
	
	/* ===Methods=== ʕ•́ᴥ•̀ʔ */
	//Display Data
	@Override
	public String toString() {
		return "Workday{" +
				", openingTime='" + openingTime + '\'' +
				", closeTime='" + closeTime + '\'' +
				'}';
	}

	/* ===Getters && Setters=== ʕ•́ᴥ•̀ʔ */
	
	public String getOpeningTime() {
		return openingTime;
	}

	public String getCloseTime() {
		return closeTime;
	}



	public void setOpeningTime(String openingTime) {
		this.openingTime = openingTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Workday)) {
			return false;
		}
		Workday day = (Workday) o;
		return this.openingTime.equals(day.getOpeningTime()) &&
				this.closeTime.equals(day.getCloseTime());
	}

}
