package fitnessStudio.shop;

import org.salespointframework.payment.PaymentMethod;



public class Balance extends PaymentMethod {

	private static final long serialVersionUID = 5087632169177352654L;

	public static final Balance BALANCE = new Balance();

	private Balance() {
		super("Balance");
	}
}