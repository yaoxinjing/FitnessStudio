package fitnessStudio.user;


import javax.money.MonetaryAmount;
import javax.persistence.*;

import fitnessStudio.Pair;
import org.salespointframework.useraccount.UserAccount;
import static org.salespointframework.core.Currencies.*;
import org.javamoney.moneta.Money;

import java.util.ArrayList;
import java.util.Date;

@Entity
public class StudioUser {

	private @Id @GeneratedValue long id;

	private String email;
	private String phonenumber;
	private boolean isPresent;
	@OneToOne(targetEntity=fitnessStudio.user.Address.class, cascade = CascadeType.ALL)
	private Address address;
	@OneToOne(cascade = {CascadeType.ALL})
	private Contract contract;

	@OneToOne
	private UserAccount userAccount;

	@Column(name="loginLogoutTimestamp") @Lob 
	private ArrayList<Pair<Date, Date>> loginLogoutTimestamp;
	private MonetaryAmount balance;

	private boolean setAffiliateBonus; // // check if login user already give the user name who invite him

	private boolean userNameFalsh; // check if login user give the falsh user name

	@SuppressWarnings("unused")
	public StudioUser() {
		this.contract = null;
		isPresent = false;
		this.loginLogoutTimestamp = new ArrayList<>();
		setAffiliateBonus = false;
		userNameFalsh=false;
	}


	public StudioUser(UserAccount userAccount, Address address, String email, String phonenumber, Contract contract) {
		this.userAccount = userAccount;
		this.email = email;
		this.phonenumber = phonenumber;
		this.address = address;
		this.contract = contract;
		this.contract.setPartner(this);
		this.balance = Money.of(0, EURO);
		this.loginLogoutTimestamp = new ArrayList<>();
		setAffiliateBonus = false;
		userNameFalsh=false;
	}

	public void updateData(Address address, String email, String phonenumber) {
		this.email = email;
		this.phonenumber = phonenumber;
		this.address = address;
		this.isPresent = false;
	}

	public boolean isUserNameFalsh() {
		return userNameFalsh;
	}

	public void setUserNameFalsh(boolean userNameFalsh) {
		this.userNameFalsh = userNameFalsh;
	}

	public boolean isSetAffiliateBonus() {
		return setAffiliateBonus;
	}

	public void setSetAffiliateBonus(boolean setAffiliateBonus) {
		this.setAffiliateBonus = setAffiliateBonus;
	}

	public MonetaryAmount getBalance() {
		return balance;
	}

	public void setBalance(MonetaryAmount balance) {
		this.balance = balance;
	}
	
	public MonetaryAmount addBalance(MonetaryAmount balanceAdded) {
		this.balance = this.balance.add(balanceAdded);
		return this.balance;
	}
	
	public MonetaryAmount subtractBalance(MonetaryAmount subBalance) {
		this.balance = balance.subtract(subBalance);
		return this.balance;
	}

	public ArrayList<Pair<java.util.Date, java.util.Date>> getLoginLogoutTimestamp() {
		return loginLogoutTimestamp;
	}

	public void setLoginLogoutTimestamp(ArrayList<Pair<java.util.Date, java.util.Date>> loginLogoutTimestamp) {
		this.loginLogoutTimestamp = loginLogoutTimestamp;
	}
	
	public boolean getIsPresent() {
		return isPresent;
	}

	public StudioUser setIsPresent(boolean present) {
		this.isPresent = present;
		return this;
	}

	public long getId() {
		return id;
	}

	public Address getAddress() {
		return address;
	}

	public Contract getContract() {
		return contract;
	}
	
	public StudioUser setContract(Contract con) {
		this.contract = con;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public String getUsername() {
		return userAccount.getUsername();
	} 

	public String getFirstname() {
		return userAccount.getFirstname();
	} 
	public String getLastname() {
		return userAccount.getLastname();
	} 
	public String getFullname() {
		return userAccount.getFirstname() + " " + userAccount.getLastname();
	}
}
