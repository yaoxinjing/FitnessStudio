package fitnessStudio.printToPDF;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.salespointframework.inventory.UniqueInventory;
import org.salespointframework.inventory.UniqueInventoryItem;
import org.salespointframework.order.Cart;
import org.salespointframework.order.CartItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lowagie.text.pdf.PdfPTable;

import fitnessStudio.catalog.CommodityCatalog;
import fitnessStudio.user.StudioUser;
import fitnessStudio.user.StudioUserManagement;


@SpringBootTest
//@RunWith( SpringJUnit4ClassRunner.class )
public class PDFTransactionExporterTest {

	PDFTransactionsExporter pdfTransactionsExporter = new PDFTransactionsExporter();
	
	@Autowired
	private StudioUserManagement studioUserManagement;
	
	@Autowired
	private OrderManagement<Order> orderManagement;
	
	@Autowired
	private CommodityCatalog commodityCatalog;
	
	@Autowired
	private UniqueInventory<UniqueInventoryItem> inventory;
	
	
	@Test
	public void writeTableDataTest() {
		Order order = null;	
		
		StudioUser user = null;
		for (StudioUser studioUser : studioUserManagement.findAllUsers()) {
			if(studioUser.getUsername().equals("Gast")) {
				user = studioUser;
			}
		}
		if(user != null) {
			Random rand;
			for (int i = 0; i < 15; i++) {
				rand = new Random();
				order = new Order(user.getUserAccount(), Cash.CASH);
				Cart cart = new Cart();
				for (int j = 0; j < rand.nextInt(Integer.MAX_VALUE)%commodityCatalog.count() +1; j++) {
					cart.addOrUpdateItem(commodityCatalog.findAll().toList().get((int)(rand.nextInt(Integer.MAX_VALUE)%commodityCatalog.count())),Quantity.of((int)(rand.nextInt(Integer.MAX_VALUE)%5 +1)));
				}
				cart.addItemsTo(order);
				for (CartItem cartItem : cart) {
					inventory.save(inventory.findByProduct(cartItem.getProduct()).get().increaseQuantity(cartItem.getQuantity()));
				}
				orderManagement.payOrder(order);
				orderManagement.completeOrder(order);
				cart.clear();
			}
		}
		
	
		
		PdfPTable table = new PdfPTable(4);	
		
		
		pdfTransactionsExporter.writeTableData(table, order);
		
		assertTrue(true);	
	}
	
	
	
	@Test
	void PDFTransactionsExporterKonstruktorNullArgumentTest() {
		try {
			new PDFController(null,null,null);
		} catch (IllegalArgumentException e) {
			assertTrue("java.lang.IllegalArgumentException".equals(e.toString()));
			return;
		} 
			
	}
	
	
	@Test
	void writeTableHeaderNullArgumentTest() {
		try {
			pdfTransactionsExporter.writeTableHeader(null,null);
		} catch (IllegalArgumentException e) {
			assertTrue("java.lang.IllegalArgumentException".equals(e.toString()));
			return;
		} 
		assertTrue(false);		
	}
	
	@Test
	void writeTableDataNullArgumentTest() {
		try {
			pdfTransactionsExporter.writeTableData(null, null);
		} catch (IllegalArgumentException e) {
			assertTrue("java.lang.IllegalArgumentException".equals(e.toString()));
			return;
		} 
		assertTrue(false);		
	}
	
	@Test
	void exportNullArgumentTest() {
		try {
			pdfTransactionsExporter.export(null);
		} catch (IllegalArgumentException e) {
			assertTrue("java.lang.IllegalArgumentException".equals(e.toString()));
			return;
		} 
		assertTrue(false);		
	}
	
}
