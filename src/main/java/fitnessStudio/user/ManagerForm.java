package fitnessStudio.user;

import javax.validation.constraints.NotEmpty;

public class ManagerForm {
		
		@NotEmpty(message = "{ManagerForm.membershipFee.NotEmpty}") //
		private final Integer membershipFee;
	
		@NotEmpty(message = "{ManagerForm.affiliate.NotEmpty}") //
		private final Integer affiliate;

		@NotEmpty(message = "{ManagerForm.duration.NotEmpty}") //
		private final Integer duration;
		
		@NotEmpty(message = "{ManagerForm.monthlyPowerCosts.NotEmpty}") //
		private final Integer monthlyPowerCosts;
		
		@NotEmpty(message = "{ManagerForm.monthlyWaterCosts.NotEmpty}") //
		private final Integer monthlyWaterCosts;
		
		@NotEmpty(message = "{ManagerForm.monthlyRentalCosts.NotEmpty}") //
		private final Integer monthlyRentalCosts;

		public ManagerForm(Integer duration, Integer membershipFee, Integer affiliate, Integer monthlyPowerCosts,
				Integer monthlyWaterCosts, Integer monthlyRentalCosts) {
			
			this.membershipFee = membershipFee;
			this.affiliate = affiliate;
			this.duration = duration;
			this.monthlyPowerCosts = monthlyPowerCosts;
			this.monthlyWaterCosts = monthlyWaterCosts;
			this.monthlyRentalCosts = monthlyRentalCosts;

		}
		
		public Integer getMembershipFee() {
			return membershipFee;
		}
		
		public Integer getAffiliate() {
			return affiliate;
		}

		public Integer getDuration() {
			return duration;
		}
		
		public Integer getMonthlyPowerCosts() {
			return monthlyPowerCosts;
		}
		
		public Integer getMonthlyWaterCosts() {
			return monthlyWaterCosts;
		}
		
		public Integer getMonthlyRentalCosts() {
			return monthlyRentalCosts;
		}
}
