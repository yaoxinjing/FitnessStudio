package fitnessStudio.printToPDF;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fitnessStudio.inventory.InventoryManager;
import fitnessStudio.user.StudioUserRepository;




@SpringBootTest
public class PDFControllerTest {
	
	@Autowired OrderManagement<Order> orderManagement;
	@Autowired StudioUserRepository studioUserRepository;
	@Autowired InventoryManager inventoryManager;
	
	public PDFController pdfController;
	
	@BeforeEach
	void setUp() {
		pdfController = new PDFController(orderManagement, 
				studioUserRepository, inventoryManager);
	}
	
	
	@Test
	void PDFControllerKonstruktorNullArgumentTest() {
		try {
			new PDFController(null,null,null);
		} catch (IllegalArgumentException e) {
			assertTrue("java.lang.IllegalArgumentException".equals(e.toString()));
			return;
		} 
		assertTrue(false);		
	}
	
	@Test
	void PDFControllerDownloadGCNullArgumentTest() {
		try {
			pdfController.downloadGC(null, null);
		} catch (IllegalArgumentException e) {
			assertTrue("java.lang.IllegalArgumentException".equals(e.toString()));
			return;
		} 
		assertTrue(false);		
	}
	
	@Test
	void exportTransactionsToPDFNullArgumentTest() {
		try {
			pdfController.exportTransactionsToPDF(null, null, null, null);
		} catch (IllegalArgumentException e) {
			assertTrue("java.lang.IllegalArgumentException".equals(e.toString()));
			return;
		} 
		assertTrue(false);		
	}
	
	@Test
	void downloadTANullArgumentTest() {
		try {
			pdfController.downloadTA(null, null);
		} catch (IllegalArgumentException e) {
			assertTrue("java.lang.IllegalArgumentException".equals(e.toString()));
			return;
		} 
		assertTrue(false);		
	}
	
	
	@Test
	void exportPaycheckToPDFNullArgumentTest() {
		try {
			pdfController.exportPaycheckToPDF(null, null, null, null);
		} catch (IllegalArgumentException e) {
			assertTrue("java.lang.IllegalArgumentException".equals(e.toString()));
			return;
		} 
		assertTrue(false);		
	}
	
	@Test
	void downloadNBLNullArgumentTest() {
		try {
			pdfController.downloadNBL(null);
		} catch (IllegalArgumentException e) {
			assertTrue("java.lang.IllegalArgumentException".equals(e.toString()));
			return;
		} 
		assertTrue(false);		
	}
	
	@Test
	void exportNachbestellungToPDFNullArgumentTest() {
		try {
			pdfController.exportNachbestellungToPDF(null, null, null, null,null);
		} catch (IllegalArgumentException e) {
			assertTrue("java.lang.IllegalArgumentException".equals(e.toString()));
			return;
		} 
		assertTrue(false);		
	}	
}