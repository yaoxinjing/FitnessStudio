package fitnessStudio.user;

import javax.validation.constraints.NotNull;

public class EmployeeRegistrationForm extends RegistrationForm{

	@NotNull(message = "{EmployeeRegistrationForm.salary.NotEmpty}") //
	private final Integer salary;

	public EmployeeRegistrationForm(String username, String firstname, String lastname, String street, 
		Integer housenumber, String addressaddition, String city, String postalcode, String email, 
		String phonenumber, String password, Integer salary) {

		super(username, firstname, lastname, street, housenumber, addressaddition, city, postalcode, 
			email, phonenumber, password);
		this.salary = salary;
	}

	public Integer getSalary() {
		return salary;
	}
}
