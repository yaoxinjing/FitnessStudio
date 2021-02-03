package fitnessStudio.catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.DurationStyle;

import org.javamoney.moneta.Money;
import static org.salespointframework.core.Currencies.*;


public class CommodityTest {
 
    @Test
    void testSetDiscount() {
        Drink com = new Drink("produkt", Money.of(20, EURO), "tolles produkt", 2);
        com.setDiscount("10");
        assertEquals("10", com.getDiscount());
        com.setDiscount("10%");
        assertEquals("10%", com.getDiscount());
        com.setDiscount("");
        assertEquals(null, com.getDiscount());
        com.setDiscount(null);
        assertEquals(null, com.getDiscount());
        
        com = new Drink("produkt", Money.of(20, EURO), "tolles produkt", 2);
        com.setDiscount("0");
        assertEquals(null, com.getDiscount());

        com = new Drink("produkt", Money.of(20, EURO), "tolles produkt", 2);
        com.setDiscount("1-23.-");
        assertEquals(null, com.getDiscount());

    }

    @Test
    void testGetPrice() {
        Drink com = new Drink("produkt", Money.of(20, EURO), "tolles produkt", 2);
        assertEquals(Money.of(20, EURO), com.getPrice());        
        com.setDiscount("10");
        assertEquals(Money.of(10, EURO), com.getPrice());
        assertEquals(Money.of(20, EURO), com.getGrossPrice());
        com.setDiscount("10%");
        assertEquals(Money.of(18, EURO), com.getPrice());
        com.setDiscount(null);
        assertEquals(Money.of(20, EURO), com.getPrice());
        
    }

    @Test
    void testMinCount() {
        Drink com = new Drink("produkt", Money.of(20, EURO), "tolles produkt", 2);
        assertEquals(Money.of(20, EURO), com.getPrice());
        assertEquals(2, com.getMinCount());        
        com.setMinCount(4);
        assertEquals(4, com.getMinCount());        
    }

    @Test
    void testGetGrossPrice() {
        Drink com = new Drink("produkt", Money.of(20, EURO), "tolles produkt", 2);
        assertEquals("€ 20", com.getGrossPriceUnitless());
        com.setDiscount("10");
        assertEquals("€ 10", com.getPriceUnitless());
    }

}
