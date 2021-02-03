package fitnessStudio.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class AddressTest {
 
    @Autowired
	Address address = new Address("Musterstrasse", 12, "b", "Dresden", "12345");
	
	@Test
	void testToString() {
		assertEquals(address.toString(), "Musterstrasse 12 b, 12345 Dresden");
    }	

	@Test
	void testStreet() {
		assertEquals(address.getStreet(), "Musterstrasse");
    }	
    
    @Test
	void testHousenumber() {
		assertEquals(address.getHouseNumber(), 12);
    }	
    
    @Test
	void testAdditionalAddress() {
		assertEquals(address.getAdditionalAddress(), "b");
    }	
    
    @Test
	void testCity() {
		assertEquals(address.getCity(), "Dresden");
    }	

    @Test
	void testZipcode() {
		assertEquals(address.getZipcode(), "12345");
    }	

    @Test
    void testAddressChange() {
        Address address = new Address("Musterstrasse", 12, "b", "Dresden", "12345");
        assertEquals(address.toString(), "Musterstrasse 12 b, 12345 Dresden");
        address.changeAddress("Neustrasse", 21, "a", "Musterhausen", "11111");
		assertEquals(address.toString(), "Neustrasse 21 a, 11111 Musterhausen");
    }

}