package fitnessStudio.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RegistrationForm {

	@NotEmpty(message = "{RegistrationForm.username.NotEmpty}") //
	private final String username;

	@NotEmpty(message = "{RegistrationForm.firstname.NotEmpty}") //
	private final String firstname;

	@NotEmpty(message = "{RegistrationForm.lastname.NotEmpty}") //
	private final String lastname;

	@NotEmpty(message = "{RegistrationForm.street.NotEmpty}") //
	private final String street;

	@NotNull(message = "{RegistrationForm.housenumber.NotEmpty}") //
	private final Integer housenumber;

	@NotEmpty(message = "{RegistrationForm.addressaddition.NotEmpty}") //
	private final String addressaddition;

	@NotEmpty(message = "{RegistrationForm.city.NotEmpty}") //
	private final String city;

	@NotEmpty(message = "{RegistrationForm.postalcode.NotEmpty}") //
	private final String postalcode;


	@NotEmpty(message = "{RegistrationForm.email.NotEmpty}") //
	@Email(message = "{RegistrationForm.email.ProperEmail}")
	private final String email;

	@NotEmpty(message = "{RegistrationForm.phonenumber.NotEmpty}") //
	private final String phonenumber;

	@NotEmpty(message = "{RegistrationForm.password.NotEmpty}") //
	private final String password;

	public RegistrationForm(String username, String firstname, String lastname, String street, 
			Integer housenumber, String addressaddition, String city, String postalcode, String email, 
			String phonenumber, String password) {

		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.street = street;
		this.email = email;
		this.phonenumber = phonenumber;
		this.password = password;

		this.housenumber = housenumber;
		this.addressaddition = addressaddition;
		this.city = city;
		this.postalcode = postalcode;
	}

	public String getUsername() {
		return username;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}
	
	public String getStreet() {
		return street;
	}

	public Integer getHousenumber() {
		return housenumber;
	}

	public String getAddressaddition() {
		return addressaddition;
	}

	public String getCity() {
		return city;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public String getEmail() {
		return email;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public String getPassword() {
		return password;
	}

	public String toString() {
		return this.username + " " + this.firstname + " " + this.lastname;
	}
}
