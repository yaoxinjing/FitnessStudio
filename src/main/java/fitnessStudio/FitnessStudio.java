package fitnessStudio;

import static org.salespointframework.core.Currencies.*;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;
import org.salespointframework.EnableSalespoint;
import org.salespointframework.SalespointSecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableSalespoint
@Component
public class FitnessStudio {
	private static final String LOGIN_ROUTE = "/login";
	private Integer contractDuration = 6;
	private MonetaryAmount membershipFee = Money.of(20, EURO);
	private MonetaryAmount affiliateBonus = Money.of(10, EURO);
	private MonetaryAmount monthlyPowerCosts = Money.of(1000, EURO);
	private MonetaryAmount monthlyWaterCosts = Money.of(500, EURO);
	private MonetaryAmount monthlyRentalCosts = Money.of(5000, EURO);
	
	/* ===Main=== ʕ•́ᴥ•̀ʔ */
	public static void main(String[] args) {
		SpringApplication.run(FitnessStudio.class, args);
	}
	
	/** @author Oliver Gierke */
	@Configuration
	static class FitnessStudioWebConfiguration implements WebMvcConfigurer {
		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addViewController(LOGIN_ROUTE).setViewName("login");
			registry.addViewController("/").setViewName("index");
		}
	}
	
	/** @author Oliver Gierke */
	@Configuration
	static class WebSecurityConfiguration extends SalespointSecurityConfiguration {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable();
			http.authorizeRequests().antMatchers("/**").permitAll().and().//
					formLogin().loginPage(LOGIN_ROUTE).loginProcessingUrl(LOGIN_ROUTE).failureUrl("/loginError").and(). //
					logout().logoutUrl("/logout").logoutSuccessUrl("/");
		}
	}
	
	
	/* ===Getter && Setter=== ʕ•́ᴥ•̀ʔ */
	public MonetaryAmount getAffiliateBonus() {
		return affiliateBonus;
	}

	public void setAffiliateBonus(MonetaryAmount affiliateBonus) {
		this.affiliateBonus = affiliateBonus;
	}
	
	public void setContractDuration(Integer duration) {
		this.contractDuration = duration;
	}
	
	public Integer getContractDuration() {
		return this.contractDuration;
	}
	
	public void setMembershipFee(MonetaryAmount membershipFee) {
		this.membershipFee = membershipFee;
	}

	public MonetaryAmount getMembershipFee() {
		return membershipFee;
	}
	
	public void setMonthlyPowerCosts(MonetaryAmount monthlyPowerCosts) {
		this.monthlyPowerCosts = monthlyPowerCosts;
	}

	public MonetaryAmount getMonthlyPowerCosts() {
		return monthlyPowerCosts;
	}
	
	public void setMonthlyWaterCosts(MonetaryAmount monthlyWaterCosts) {
		this.monthlyWaterCosts = monthlyWaterCosts;
	}

	public MonetaryAmount getMonthlyWaterCosts() {
		return monthlyWaterCosts;
	}
	
	public void setMonthlyRentalCosts(Money monthlyRentalCosts) {
		this.monthlyRentalCosts = monthlyRentalCosts;
	}

	public MonetaryAmount getMonthlyRentalCosts() {
		return monthlyRentalCosts;
	}
	

}
