package fitnessStudio.catalog;

import java.time.LocalDate;

import javax.persistence.Entity;

import org.javamoney.moneta.Money;

@Entity
public class Supplement extends Commodity{
	private LocalDate expirationdate;
	
	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	private Supplement() {
		super(CommodityType.SUPPLEMENT);
	}
	
	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	public Supplement(String name, Money price, String description, int minCount, LocalDate expirationdate) {
		super(name, price, description, minCount, CommodityType.SUPPLEMENT);
		this.expirationdate = expirationdate;
	}

	
	/* ===Methods=== ʕ•́ᴥ•̀ʔ */
	public LocalDate getExpirationdate() {
		return expirationdate;
	}

	public void setExpirationdate(LocalDate expirationdate) {
		this.expirationdate = expirationdate;
	}
	
	
	
}
