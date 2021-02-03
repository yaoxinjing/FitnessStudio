package fitnessStudio.printToPDF;

import java.awt.Color;
import java.util.List;
import org.salespointframework.useraccount.UserAccount;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

public class PDFDocument {

	public Font headFont = FontFactory.getFont(FontFactory.HELVETICA);				// Schriftart der Ueberschrift
	public Font textFont = FontFactory.getFont(FontFactory.HELVETICA);			// Schriftart fuer Namen
	public Font font = FontFactory.getFont(FontFactory.HELVETICA);
	public HeaderFooter header;
	public HeaderFooter footer = new HeaderFooter(new Phrase("Seite "), new Phrase(""));
	public Document document = new Document(PageSize.A4);
	
	public PDFDocument() {
		headFont.setColor(Color.black);
		headFont.setSize(30);
		textFont.setColor(Color.black);
		textFont.setSize(20);
		footer.setAlignment(2);
		font.setColor(Color.black);
	}
	
	public void setHeader(String title) {
		header = new HeaderFooter(new Phrase("Fitnesstudio.de - " + title), false);
		header.setAlignment(1);
	}


	public void setPersonalInfo(UserAccount userAccount) {
		document.add(new Paragraph("Nutzername:   " + userAccount.getUsername(), textFont));
		document.add(new Paragraph("Vor-Name:      " + userAccount.getFirstname(), textFont));
		document.add(new Paragraph("Nach-Name:   " + userAccount.getLastname(), textFont));
		document.add(new Paragraph("\n\n"));
	}
	
	public void writeTableHeader(PdfPTable table, List<String> spalten) {
		if ((table == null) || (spalten == null)) {
			throw new IllegalArgumentException();
		}
			
		PdfPCell cell = new PdfPCell();				
		cell.setBackgroundColor(Color.gray);
		cell.setPadding(10);
		
		for (String string : spalten) {
			cell.setPhrase(new Phrase(string, font));
			table.addCell(cell);
		}	
	}
}