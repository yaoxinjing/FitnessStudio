package fitnessStudio.catalog;

import javax.persistence.Entity;
import org.javamoney.moneta.Money;

@Entity
public class Drink extends Commodity{
	
	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	private Drink() {
		super(CommodityType.DRINK);
	}
	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	public Drink(String name, Money price, String description, int minCount) {
		super(name, price, description, minCount, CommodityType.DRINK);
	}

}
