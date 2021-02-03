package fitnessStudio.printToPDF;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PDFNachbestellungsExporterTest {
	
	PDFNachbestellungsExporter pdfNachbestellungsExporter = new PDFNachbestellungsExporter();

	@Test
	void PDFNachbestellungsExporterKonstruktorNullArgumentTest() {
		try {
			new PDFNachbestellungsExporter(null,null,null);
		} catch (IllegalArgumentException e) {
			assertTrue("java.lang.IllegalArgumentException".equals(e.toString()));
			return;
		} 
		assertTrue(false);		
	}
	
	
	@Test
	void writeTableHeaderNullArgumentTest() {
		try {
			pdfNachbestellungsExporter.writeTableHeader(null, null);
		} catch (IllegalArgumentException e) {
			assertTrue("java.lang.IllegalArgumentException".equals(e.toString()));
			return;
		} 
		assertTrue(false);		
	}
	
	
	@Test
	void writeTableDataNullArgumentTest() {
		try {
			pdfNachbestellungsExporter.writeTableData(null, null, null);
		} catch (IllegalArgumentException e) {
			assertTrue("java.lang.IllegalArgumentException".equals(e.toString()));
			return;
		} 
		assertTrue(false);		
	}
	
	@Test
	void exportNullArgumentTest() {
		try {
			pdfNachbestellungsExporter.export(null);
		} catch (IllegalArgumentException e) {
			assertTrue("java.lang.IllegalArgumentException".equals(e.toString()));
			return;
		} 
		assertTrue(false);		
	}
}
