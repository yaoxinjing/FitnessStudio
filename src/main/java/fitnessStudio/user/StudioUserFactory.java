package fitnessStudio.user;

import org.salespointframework.useraccount.UserAccount;

import javax.money.MonetaryAmount;
import static org.salespointframework.core.Currencies.*;
import org.javamoney.moneta.Money;

import java.util.ArrayList;

public class StudioUserFactory {
    StudioUserFactory() {}

    StudioUser createUser(UserAccount user, RegistrationForm form, Contract contract) {
        var userAddress = new Address(form.getStreet(), form.getHousenumber(), form.getAddressaddition(), 
            form.getCity(), form.getPostalcode());
        return new StudioUser(user, userAddress, form.getEmail(), form.getPhonenumber(), contract);
    }

    Employee createEmployee(UserAccount user, EmployeeRegistrationForm form, ArrayList<String> task, 
        Contract contract) {
        var userAddress = new Address(form.getStreet(), form.getHousenumber(), form.getAddressaddition(), 
            form.getCity(), form.getPostalcode());
        return new Employee(Money.of(form.getSalary(), EURO), task, user, userAddress, form.getEmail(), 
            form.getPhonenumber(), contract);
    }

    Manager createManager(UserAccount user, RegistrationForm form, ArrayList<String> task , MonetaryAmount salary) {
        var userAddress = new Address(form.getStreet(), form.getHousenumber(), form.getAddressaddition(), 
            form.getCity(), form.getPostalcode());
        return Manager.getManager(salary, task, user, userAddress, form.getEmail(), form.getPhonenumber());
    }
}