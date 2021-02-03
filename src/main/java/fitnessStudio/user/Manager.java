package fitnessStudio.user;

import java.time.LocalDate;
import java.util.ArrayList;
import static org.salespointframework.core.Currencies.*;
import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.UserAccount;


import javax.persistence.*;


@Entity
public class Manager extends Employee{
	
	private static Manager manager;
	
	
	@SuppressWarnings("unused")
	public Manager(){
		super();
	}
	
	private Manager(MonetaryAmount salary, ArrayList<String> task, UserAccount userAccount,
			Address address, String email, String phonenumber) {
		super(salary, task, userAccount, address, email, phonenumber,
				(new Contract("Bossvertrag", Money.of(0, EURO),
						LocalDate.parse("2021-01-01"),LocalDate.parse("2023-01-01"))));
	}
	
	public static Manager getManager(MonetaryAmount salary, ArrayList<String> task,
			UserAccount userAccount, Address address, String email, String phonenumber) {
		if(manager == null) {
			manager = new Manager(salary, task, userAccount, address, email, phonenumber);
		}
		return manager;	
	}

	public static Manager getManager() {
		if(manager == null) {
			manager = new Manager(Money.of(0, EURO), null, null, null, "itseme@test", "1234");		
			}
		return manager;
	}
	
	
}