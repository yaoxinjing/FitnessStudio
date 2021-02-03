package fitnessStudio.user;


import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.UserAccountManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;


@Component
@Order(10)
class StudioUserDataInitializer implements DataInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(StudioUserDataInitializer.class);

	private final UserAccountManagement userAccountManagement;
	private final StudioUserManagement studiouserManagement;
	private final StudioUserRepository users;

	/**
	 * Creates a new {@link StudioUserDataInitializer} with the given {@link UserAccountManagement} and
	 * {@link StudioUserRepository}.
	 *
	 * @param userAccountManagement must not be {@literal null}.
	 * @param customerManagement must not be {@literal null}.
	 */
	StudioUserDataInitializer(UserAccountManagement userAccountManagement, StudioUserManagement customerManagement,
			StudioUserRepository users) {

		Assert.notNull(userAccountManagement, "UserAccountManagement must not be null!");
		Assert.notNull(customerManagement, "StudioUserRepository must not be null!");

		this.userAccountManagement = userAccountManagement;
		this.studiouserManagement = customerManagement;
		this.users = users;
	}

	@Override
	public void initialize() {

		// Skip creation if database was already populated
		if (userAccountManagement.findByUsername("boss").isPresent()) {
			return;
		}

		LOG.info("Creating default users and customers.");

		var password = "123";

		studiouserManagement.createUser(new RegistrationForm("user1", "Hans", "Wurst", "strasse", 23, "c", "stadt",
				"PLZ", "test@test.de",  "110", "123"));	
		studiouserManagement.createUser(new RegistrationForm("user2", "Paul", "Sanger", "strasse", 23, "c", "stadt",
				"PLZ", "test@test.de",  "110", "123"));
		studiouserManagement.createUser(new RegistrationForm("user3", "Tanja", "Faber", "strasse", 23, "c", "stadt",
				"PLZ", "test@test.de",  "110", "123"));
		users.save(studiouserManagement.createUser(new RegistrationForm("Gast", "Gast", "", "Teststraße", 0, "c",
				"TestStadt", "PLZ", "test@test.de",  "", "123456789")).setIsPresent(true));

		ArrayList<String> tasks = new ArrayList<String>();
		tasks.add("TRAINEE");

		studiouserManagement.createEmployee(new EmployeeRegistrationForm("employee1", 	"Peter", 	"Mueller",
		 		"straße",  23, "c", "stadt", "PLZ", "test2@test.de",  "110", password, 3000), tasks);
		studiouserManagement.createEmployee(new EmployeeRegistrationForm("employee2", 	"Kerstin", 	"Hertzog",
		 		"straße",  23, "c", "stadt", "PLZ", "test2@test.de",  "110", password, 3000), tasks);
		studiouserManagement.createManager(new RegistrationForm("boss", 				"Silke", 	"Fleischer",
			"straße", 23, "c", "stadt", "PLZ", "test3@test.de",  "112", password), null, tasks);
	}
}
