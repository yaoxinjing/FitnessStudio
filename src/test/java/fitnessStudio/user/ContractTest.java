package fitnessStudio.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.javamoney.moneta.Money;
import static org.salespointframework.core.Currencies.*;
import java.time.LocalDate;


//not testing Partner attribute
public class ContractTest {
    @Autowired
	Contract contract = new Contract("Mustervertrag", Money.of(20, EURO), LocalDate.parse("2021-01-01"), LocalDate.parse("2021-10-01"));
 
    @Test 
    void testDescription() {
        assertEquals("Mustervertrag", contract.getDescription());
    }

    @Test 
    void testPrice() {
        assertEquals(Money.of(20, EURO), contract.getPrice());
    }

    @Test
    void testStartTime() {
        assertEquals(LocalDate.parse("2021-01-01"), contract.startTime());
    }

    @Test
    void testEndTime() {
        assertEquals(LocalDate.parse("2021-10-01"), contract.endTime());
   
    }

    @Test
    void testToString() {
        assertEquals("Mustervertrag", contract.toString());
    }

    @Test
    void testUpdateData() {
        Contract contract2 = new Contract("Mustervertrag", Money.of(20, EURO), LocalDate.parse("2021-01-01"), LocalDate.parse("2021-10-01"));
        assertEquals(contract2.getDescription(), contract.getDescription());
        assertEquals(contract2.getPrice(), contract.getPrice());
        assertEquals(contract2.startTime(), contract.startTime());
        assertEquals(contract2.endTime(), contract.endTime());
        contract2.updateData("Neuer Mustervertrag", Money.of(10, EURO), LocalDate.parse("2022-01-01"), LocalDate.parse("2022-10-01"));
        assertNotEquals(contract2.getDescription(), contract.getDescription());
        assertNotEquals(contract2.getPrice(), contract.getPrice());
        assertNotEquals(contract2.startTime(), contract.startTime());
        assertNotEquals(contract2.endTime(), contract.endTime());

    }
}
