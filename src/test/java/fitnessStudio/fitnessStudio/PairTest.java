package fitnessStudio.fitnessStudio;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import fitnessStudio.Pair;
import org.junit.jupiter.api.Test;
@SpringBootTest
public class PairTest {

	@Test
	void testPair(){
		Pair<String,String> a = new Pair<>("1","2");

		Pair<String,String> b = new Pair<>("1","2");

		Pair<String,String> c = new Pair<>("1","1");

		Assertions.assertTrue(a.equals(b));

		Assertions.assertFalse(a.equals(c));

	}
}
