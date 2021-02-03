package fitnessStudio.catalog;

import javax.annotation.Nonnull;
import static org.salespointframework.core.Currencies.*;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.persistence.Entity;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
public abstract class Commodity extends Product{
	private static final Logger LOG = LoggerFactory.getLogger(CatalogController.class);
	public static enum CommodityType {
		DRINK, SUPPLEMENT, EQUIPMENT;
	}
	
	private final CommodityType type;
	private String discount, description;
	private int minCount;
	
	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	public Commodity(CommodityType type) {
		super("Default", Money.of(0, EURO));
		this.type = type;
	}
	
	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	public Commodity(String name, MonetaryAmount price, String description, int minCount, CommodityType type) {
		super(name, price);
		this.description = description;
		this.minCount = minCount;
		this.type = type;
	}
	
	/* ==== Getter && Setter ==== */ 
	
	public CommodityType getType() {
		return type;
	}

	public int getMinCount() {
		return minCount;
	}

	public void setMinCount(int minCount) {
		this.minCount = minCount;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		//No Discount
		if(discount == null || discount.equals("")) {
			this.discount = null;
			return;
		}	
		String tmp = new String(discount);
		if(discount.contains("%")) { //remove percent Symbol
			tmp = tmp.replace("%", "");
		//check if input can be converted to a number
		}
		try {
			if(0 == (int)Double.parseDouble(tmp)) { // Check for 0 and if that String is-
													// convertible to a number 
				discount = null; //No Discount
			}
		}catch (NumberFormatException nfe) {
			LOG.debug("String cannot be converted into Double");
	        return;
	    }
		this.discount = discount;
	}
	
	@Override
	public @Nonnull MonetaryAmount getPrice() {
		MonetaryAmount Money = super.getPrice();
		if(discount == null) {
			return Money;
		//Percentage Discount
		}
		if(discount.contains("%")) {
			return  super.getPrice().multiply(1.0-Double.parseDouble(discount.replaceAll("%", ""))/100);
		}
		//Fixed Discount
		return Money.subtract(Monetary.getDefaultAmountFactory().setCurrency("EUR")
				.setNumber(Double.parseDouble(discount)).create());
	}
	
	//Create Euro Symbol in Price
	public String getPriceUnitless() {
		return getPrice().toString().replace("EUR", "€");
	}
	
	//get Price with Symbol, pre Discount
	public String getGrossPriceUnitless() {
		return getGrossPrice().toString().replace("EUR", "€");
	}
	
	
	//Get Price pre Discount
	@Nonnull
	public MonetaryAmount getGrossPrice() {
		return super.getPrice();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static String MoneySymbol(String string) {
		if(string == null) {
			return string;
		}
		if(string.contains("EUR")) {
			return string.replace("EUR", "") + "€";
		}
		return string;
	}
	
	public static String MoneySymbol(MonetaryAmount money) {
		if(money == null) {
			return "";
		}
		if(money.toString().contains("EUR")) {
			return money.toString().replace("EUR", "") + "€";
		}
		return money.toString();
	}
	
	public String getTypeString() {
		return type.name();
	}
	
	
	
	
}
