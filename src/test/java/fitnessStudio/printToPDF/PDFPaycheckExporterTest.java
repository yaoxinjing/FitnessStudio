package fitnessStudio.printToPDF;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class PDFPaycheckExporterTest{
	
	List<String> tasks = new LinkedList<String>();
	List<String> task = new LinkedList<String>();
	PDFPaycheckExporter pdfPaycheckExporter = new PDFPaycheckExporter();
	

	@BeforeEach
    public void setUp() throws Exception {
        tasks = Arrays.asList("Trainer", "Verkauf", "Putzen");
		task.add("Trainer");

    }
	
	
	@Test
	void printTasksToStringListTest() {
		assertThat("Trainer, Verkauf, Putzen").isEqualTo(pdfPaycheckExporter.printTasksToString(tasks));
	}
	
	@Test
	void printTasksToStringSingleWordTest() {
		assertThat("Trainer").isEqualTo(pdfPaycheckExporter.printTasksToString(task));		
	}
	
	@Test
	void printTasksToStringEmptyListTest() {
		assertThat("").isEqualTo(pdfPaycheckExporter.printTasksToString(new LinkedList<String>()));		
	}
	
	@Test
	void printTasksToStringNullArgumentTest() {
		try {
			String result = pdfPaycheckExporter.printTasksToString(null);
		} catch (IllegalArgumentException e) {
			assertTrue("java.lang.IllegalArgumentException".equals(e.toString()));
			return;
		} 
		assertTrue(false);		
	}
	
	@Test
	void PDFPaycheckExporterKonstruktorTest() {
		try {
			new PDFPaycheckExporter(null,null);
		} catch (IllegalArgumentException e) {
			assertTrue("java.lang.IllegalArgumentException".equals(e.toString()));
			return;
		} 
		assertTrue(false);		
	}
	
	@Test
	void exportNullArgumentTest() {
		try {
			pdfPaycheckExporter.export(null);
		} catch (IllegalArgumentException e) {
			assertTrue("java.lang.IllegalArgumentException".equals(e.toString()));
			return;
		} 
		assertTrue(false);		
	}
}
