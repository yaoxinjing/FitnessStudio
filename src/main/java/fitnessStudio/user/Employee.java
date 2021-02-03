package fitnessStudio.user;

import java.util.ArrayList;

import javax.money.MonetaryAmount;

import org.salespointframework.useraccount.UserAccount;

import fitnessStudio.request.RequestState;

import javax.persistence.*;


@Entity
public class Employee extends StudioUser {
	
	private MonetaryAmount salary;
	private ArrayList<String> tasks;
	
	@SuppressWarnings("unused")
	public Employee(){
		super();
	}
	
	public Employee(MonetaryAmount salary, ArrayList<String> tasks, UserAccount userAccount, 
		Address address, String email, String phonenumber, Contract contract) {

		super(userAccount, address, email, phonenumber, contract);
		this.salary = salary;
		this.tasks = tasks;
	}

	
	public MonetaryAmount getSalary() {
		return this.salary;
	}

	public ArrayList<String> getTasks() {
		return this.tasks;
	}	
	
	public RequestState vacationStatus() {
		return null;
		
	}
	
	}
