package fitnessStudio.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RegistrationFormTest {
	
	@Autowired
	RegistrationForm registrationForm;
	
	@Test
	void testUsername() {
		RegistrationForm form = new RegistrationForm("peterlustig","peter","lustig","straße",12," ",
			 "Musterhausen", "12345", "test@mail.com","110","123");
		assertEquals(form.getUsername(), "peterlustig");
	}	

	@Test
	void testFirstName() {
		RegistrationForm form = new RegistrationForm("peterlustig","peter","lustig","straße",12," ",
			 "Musterhausen", "12345", "test@mail.com","110","123");
		assertEquals(form.getFirstname(), "peter");
	}	
	
	@Test
	void testLastName() {
		RegistrationForm form = new RegistrationForm("peterlustig","peter","lustig","straße",12," ",
			 "Musterhausen", "12345", "test@mail.com","110","123");
		assertEquals(form.getLastname(), "lustig");
	}	

	@Test
	void testToString() {
		RegistrationForm form = new RegistrationForm("peterlustig","peter","lustig","straße",12," ",
			 "Musterhausen", "12345", "test@mail.com","110","123");
		assertEquals(form.toString(), "peterlustig peter lustig");
	}	

	@Test
	void testStreet() {
		RegistrationForm form = new RegistrationForm("peterlustig","peter","lustig","straße",12," ",
			 "Musterhausen", "12345", "test@mail.com","110","123");
		assertEquals(form.getStreet(), "straße");
	}	

	@Test
	void testHousenumber() {
		RegistrationForm form = new RegistrationForm("peterlustig","peter","lustig","straße",12," ",
			 "Musterhausen", "12345", "test@mail.com","110","123");
		assertEquals(form.getHousenumber(), 12);
	}	

	@Test
	void testAddressaddition() {
		RegistrationForm form = new RegistrationForm("peterlustig","peter","lustig","straße",12," ",
			 "Musterhausen", "12345", "test@mail.com","110","123");
		assertEquals(form.getAddressaddition(), " ");
	}	

	@Test
	void testCity() {
		RegistrationForm form = new RegistrationForm("peterlustig","peter","lustig","straße",12," ",
			 "Musterhausen", "12345", "test@mail.com","110","123");
		assertEquals(form.getCity(), "Musterhausen");
	}	

	@Test
	void testPostalcode() {
		RegistrationForm form = new RegistrationForm("peterlustig","peter","lustig","straße",12," ",
			 "Musterhausen", "12345", "test@mail.com","110","123");
		assertEquals(form.getPostalcode(), "12345");
	}	

	@Test
	void getEmail() {
		RegistrationForm form = new RegistrationForm("peterlustig","peter","lustig","straße",12," ",
			 "Musterhausen", "12345", "test@mail.com","110","123");
		assertEquals(form.getEmail(), "test@mail.com");
	}	

	@Test
	void getPhonenumber() {
		RegistrationForm form = new RegistrationForm("peterlustig","peter","lustig","straße",12," ",
			 "Musterhausen", "12345", "test@mail.com","110","123");
		assertEquals(form.getPhonenumber(), "110");
	}	
}