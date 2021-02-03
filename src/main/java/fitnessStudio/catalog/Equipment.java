package fitnessStudio.catalog;

import javax.persistence.Entity;

import org.javamoney.moneta.Money;

@Entity
public class Equipment extends Commodity{
	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	private Equipment() {
		super(CommodityType.EQUIPMENT);
	}
	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	public Equipment(String name, Money price, String description, int minCount) {
		super(name, price, description, minCount, CommodityType.EQUIPMENT);
	}
}
