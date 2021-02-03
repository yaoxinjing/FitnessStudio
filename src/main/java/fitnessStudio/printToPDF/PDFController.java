package fitnessStudio.printToPDF;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import fitnessStudio.catalog.Commodity;
import fitnessStudio.inventory.InventoryManager;
import fitnessStudio.user.Employee;
import fitnessStudio.user.StudioUserRepository;


@Controller
public class PDFController {
	
	// Datum formatioeren fuer PDFs
	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
	String currentDateTime = dateFormatter.format(new Date());
	// Repositorys importieren für Transaktionsabruf
	private final OrderManagement<Order> orderManagement;
	private final StudioUserRepository studioUserRepository;
	private final InventoryManager inventoryManager;
	
	// Konstruktor
	public PDFController(OrderManagement<Order> orderManagement,
			StudioUserRepository studioUserRepository, InventoryManager inventoryManager) {
		if ((orderManagement == null) || (studioUserRepository == null)) {
			throw new IllegalArgumentException();
		}
		this.orderManagement = orderManagement;
		this.studioUserRepository = studioUserRepository;
		this.inventoryManager = inventoryManager;
	}
	
		
	// transaction
	@PostMapping("/downloadTA")
	//@PreAuthorize("hasAnyRole('EMPLOYEE','USER')")
	void downloadGC(HttpServletResponse response, @LoggedIn Optional<UserAccount> userAccount) {	
		if (response == null) {
			throw new IllegalArgumentException();
		}
		
		// import content
		List<Order> pdfContent = orderManagement.findBy(userAccount.get()).toList();
		// filename
		String headerVal = "attachment; filename=Transaktionen_" + currentDateTime + ".pdf";
		// pdf erstellen
		exportTransactionsToPDF(response, pdfContent, headerVal, userAccount);
		}
	
	// create PDF
	public void exportTransactionsToPDF(HttpServletResponse response, 
			List<Order> pdfContent, String headerVal, Optional<UserAccount> userAccount) {	
		if ((response == null) || (headerVal == null) || (pdfContent == null)) {
			throw new IllegalArgumentException();
		}
		
		String headerKey = "Content-Disposition";
		String headerValue = headerVal;
		// filename
		response.setHeader(headerKey, headerValue);
		// create transactionsExporter object and call it using fields
		PDFTransactionsExporter exporter = new PDFTransactionsExporter(pdfContent, userAccount, studioUserRepository);
		exporter.export(response);
		}	
	

	// Create Gehaltscheck PDF
	@PostMapping("/downloadGC")
	@PreAuthorize("hasAnyRole('EMPLOYEE','BOSS')")
	void downloadTA(HttpServletResponse response, @LoggedIn Optional<UserAccount> userAccount) {			
		if (response == null) {
			throw new IllegalArgumentException();
		}
		
		Employee employee = (Employee) studioUserRepository.findByUserAccount(userAccount.get());
					
		// filename
		String headerVal = "attachment; filename=Gehaltscheck_" + currentDateTime + ".pdf";
		exportPaycheckToPDF(response, headerVal, userAccount, employee);
	}
	
	//create pdf
	public void exportPaycheckToPDF(HttpServletResponse response, String headerVal, 
			Optional<UserAccount> userAccount, Employee employee) {	
		
		if ((response == null) || (headerVal == null)) {
			throw new IllegalArgumentException();
		}
		
		String headerKey = "Content-Disposition";
		String headerValue = headerVal;
		// filename
		response.setHeader(headerKey, headerValue);		
		PDFPaycheckExporter exporter = new PDFPaycheckExporter(userAccount, employee);
		exporter.export(response);
	}
	
	// Transaction PDF
	@PostMapping("/downloadNBL")
	@PreAuthorize("hasAnyRole('EMPLOYEE','BOSS')")
	void downloadNBL(HttpServletResponse response) {
		if (response == null) {
			throw new IllegalArgumentException();
		}
		
		// import content
		List<String> pdfContent = new LinkedList<String>(); 
		List<Commodity> overMHBContent = inventoryManager.OverMHB();
		Iterator<?> iterator = inventoryManager.reOrderList().entrySet().iterator();
		// filename
		String headerVal = "attachment; filename=Nachbestelliste" + currentDateTime + ".pdf";
		// pdf erstellen
		exportNachbestellungToPDF(response, pdfContent, headerVal, iterator, overMHBContent);
	}
	
	// create PDF
	public void exportNachbestellungToPDF(HttpServletResponse response, List<String> pdfContent, 
			String headerVal, Iterator<?> iterator, List<Commodity> overMHBContent) {	
		if ((response == null) || (iterator == null) || (headerVal == null) || (pdfContent == null)) {
			throw new IllegalArgumentException();
		}
		
		String headerKey = "Content-Disposition";
		String headerValue = headerVal;
		// filename
		response.setHeader(headerKey, headerValue);
		// export
		PDFNachbestellungsExporter exporter = new PDFNachbestellungsExporter(pdfContent, iterator, overMHBContent);
		exporter.export(response);
	}		
}