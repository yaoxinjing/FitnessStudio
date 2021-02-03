package fitnessStudio.printToPDF;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;

import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
import org.salespointframework.useraccount.UserAccount;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import fitnessStudio.catalog.Commodity;
import fitnessStudio.user.StudioUserRepository;


public class PDFTransactionsExporter extends PDFDocument {

	// PDF Content list
	private List<Order> pdfContent;
	private UserAccount userAccount;
	private final StudioUserRepository studioUserRepository;
	
	public PDFTransactionsExporter() {
		super();
		this.studioUserRepository = null;
	}
	
	// Konstruktor
	public PDFTransactionsExporter(List<Order> pdfContent, 
			Optional<UserAccount> userAccount, StudioUserRepository studioUserRepository) {
		super();
		this.pdfContent = pdfContent;
		this.userAccount = userAccount.get(); 
		this.studioUserRepository = studioUserRepository;
	}
	
	
	//paste content to Table

	public void writeTableData(PdfPTable table, Order order) {
		if ((table == null) || (order == null)) {
			throw new IllegalArgumentException();
		}
		
		
		table.addCell(new Phrase(order.getDateCreated().toLocalDate().toString()));
		table.addCell(new Phrase(Commodity.MoneySymbol(order.getTotal().toString())));
		
		String productNames = "";
		for (OrderLine orderLine : order.getOrderLines()) {
			//getProduct
			productNames = productNames + orderLine.getProductName() + "\n\n";
		}	
		table.addCell(new Phrase(productNames));
		
		String productPrices = "";
		for (OrderLine orderLine : order.getOrderLines()) {
			//getProduct
			productPrices = productPrices + Commodity.MoneySymbol(orderLine.getPrice()) + "\n\n";
		}	
		table.addCell(new Phrase(productPrices));
	}
	
	//PDF exportieren
	public void export(HttpServletResponse response) {
		if (response == null) {
			throw new IllegalArgumentException();
		}
		
		
		try {
			PdfWriter.getInstance(document, response.getOutputStream());
		} catch (DocumentException | IOException exception) {
			exception.printStackTrace();
		}
		

		// set Header and Footer
		setHeader("Transaktionen");
		document.setHeader(header);
		document.setFooter(footer);
		
		document.open();
		Paragraph head = new Paragraph("Das sind ihre Transaktionen", headFont);
		head.setAlignment(1);
		document.add(head);
		
		// import Image to PDF 
		// https://github.com/LibrePDF/OpenPDF/blob/master/pdf-toolbox/src/test/java/com/
		//		lowagie/examples/objects/images/Alignment.java
		try {																	// add Picture
		Image png = Image.getInstance("./src/main/resources/static/resources/bilder/person.png");
        png.setAlignment(Image.RIGHT);;
        document.add(png);
		} catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }
		
		// set personal information
		setPersonalInfo(userAccount);
		
		
		LocalDate formerDate = null;
		PdfPTable table = null;
		
		// iterate trought all transactions and create new tables if necessery
		for (Order order : pdfContent) {
			LocalDate tempDate = order.getDateCreated().toLocalDate();
			
			if ((formerDate == null) || (formerDate.getMonth() != tempDate.getMonth())){
				if (table != null) {
					document.add(table);							// add table to document
				}
				document.add(new Paragraph(tempDate.getMonth().toString() + " - " + tempDate.getYear(), textFont));
				document.add(new Paragraph("Kosten für Mitgliedschaft: " 
						+ studioUserRepository.findByUserAccount(userAccount).getContract().getPrice().toString()));
				table = new PdfPTable(4);										// create table
				table.setWidthPercentage(100);
				table.setSpacingAfter(35);
				table.setSpacingBefore(8);
				
				List<String> headerList = Arrays.asList("Datum", "Preis", "Artikel", "Artikelpreis");
				writeTableHeader(table, headerList);										// add table header		
			}
			writeTableData(table, order);								// fill table with data
			formerDate = tempDate;												// vorgänger setzen
		}
		if (table != null) {
			document.add(table);													// letzte tabelle einfügen
		} else {
			document.add(new Paragraph("Sie haben keine Transaktionen getätigt!", textFont));
		}
			
		document.close();										
	}
}
