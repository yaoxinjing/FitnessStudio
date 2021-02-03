package fitnessStudio.fitnessStudio;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.constraints.NotNull;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import fitnessStudio.FitnessStudioController;


@SpringBootTest
public class checkFitnessStudioController{
	
	@NotNull
	private FitnessStudioController fitnessStudioController = new FitnessStudioController();
	
	@Test
	public void StartpageTest() {
		Assert.assertEquals("redirect:/index", fitnessStudioController.startpage(null));
	}
	
	@Test
	public void VerwaltungTest() {
		Assert.assertTrue("verwaltung".equals(fitnessStudioController.verwaltung()));
	}
	
	
}
