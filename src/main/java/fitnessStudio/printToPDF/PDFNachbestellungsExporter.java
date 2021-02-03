package fitnessStudio.printToPDF;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.salespointframework.quantity.Quantity;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import fitnessStudio.catalog.Commodity;



public class PDFNachbestellungsExporter extends PDFDocument{

	@SuppressWarnings("unused")
	private List<String> pdfContent;
	@SuppressWarnings("rawtypes")
	private Iterator iterator;
	@SuppressWarnings("unused")
	private List<Commodity> overMHBContent;

	public PDFNachbestellungsExporter() {
		super();
	}



	// Konstruktor
	public PDFNachbestellungsExporter(List<String> pdfContent, Iterator iterator, List<Commodity> overMHBContent) {
		super();
		if ((pdfContent == null) || (iterator == null)) {
			throw new IllegalArgumentException();
		}
		this.pdfContent = pdfContent;
		this.iterator = iterator;
		this.overMHBContent = overMHBContent;
	}
	
	
	//paste content to Table
	public void writeTableData(PdfPTable table, String article, Quantity amount) {
		if ((table == null) || (article == null) || (amount == null)) {
			throw new IllegalArgumentException();
		}
	
		// Zellen befüllen
		table.addCell(new Phrase(article));				// article name
		table.addCell(new Phrase(amount.toString()));				// amount
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
		setHeader("Nachbestellliste");
		document.setHeader(header);
		document.setFooter(footer);

		document.open();
		Paragraph head = new Paragraph("Das ist die Nachbestelliste", headFont);
		head.setAlignment(1);
		document.add(head);
		document.add(new Paragraph("\n\n"));
		
		
		if (!iterator.hasNext()) {
			document.add(new Paragraph("Die Nachbestellliste ist leer."));
		} else {
			PdfPTable table = new PdfPTable(2);										// create table
			table.setWidthPercentage(60);
			table.setSpacingAfter(35);
			table.setSpacingBefore(8);
			
			List<String> headerList = Arrays.asList("Artikel", "Anzahl");
			writeTableHeader(table, headerList);
			
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry)iterator.next();
				writeTableData(table, ((Commodity)entry.getKey()).getName(), (Quantity)entry.getValue());
			} 
			document.add(table);	
		}
		
		document.add(new Paragraph("\n\n\n\n\n"));
		
		PdfPTable table = new PdfPTable(1);										// create table
		table.setWidthPercentage(40);
		table.setSpacingAfter(35);
		table.setSpacingBefore(8);
		
		List<String> headerList = Arrays.asList("Artikel");
		writeTableHeader(table, headerList);
		
		
		if (!overMHBContent.isEmpty()) {
			document.add(new Paragraph("Folgende Produkte sind abgelaufen und müssen nachbestellt werden:"));
			document.add(new Paragraph("\n"));
			
			for (Commodity element : overMHBContent) {
				table.addCell(new Phrase(element.getDescription()));
			}
		}
		
		document.add(table);
		
		document.close();										
	}
}