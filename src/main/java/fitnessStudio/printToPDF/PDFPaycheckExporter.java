package fitnessStudio.printToPDF;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.salespointframework.useraccount.UserAccount;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import fitnessStudio.user.Employee;


public class PDFPaycheckExporter extends PDFDocument{

	private UserAccount userAccount;
	private Employee employee;
	private String gehalt;
	private List<String> tasks;
	private LocalDate currentdate = LocalDate.now();

	//Konstruktor
	public PDFPaycheckExporter(Optional<UserAccount> userAccount, Employee employee) {
		super();
		if (employee == null) {
			throw new IllegalArgumentException();
		} else {
			this.userAccount = userAccount.get(); 
			this.employee = employee;
			this.gehalt = employee.getSalary().toString();
			this.tasks = employee.getTasks();
		}
	}
	
	// Empty konstruktor
	public PDFPaycheckExporter() {
		super();
	}
	
	// translate tasks List to String
	public String printTasksToString(List<String> tasks) {
		if (tasks == null) {
			throw new IllegalArgumentException();
		}
		String tasksString = "";
		for (String task : tasks) {
			if (tasks.indexOf(task) == 0) {
				tasksString = task;
			} else {
				tasksString += ", " + task;
			}
		}
		return tasksString;
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
		setHeader("Gehaltscheck " + currentdate.getMonth().toString() + " " + currentdate.getYear());
		document.setHeader(header);
		document.setFooter(footer);
		
		
		document.open();
		
		// add head
		Paragraph head = new Paragraph("Gehaltscheck " + currentdate.getMonth().toString() 
				+ " " + currentdate.getYear(), headFont);
		head.setAlignment(1);
		document.add(head);
		
		// import Image to PDF 
		// https://github.com/LibrePDF/OpenPDF/blob/master/pdf-toolbox/src/test/java/com/
		//			lowagie/examples/objects/images/Alignment.java
		try {																	// add Picture
		Image png = Image.getInstance("./src/main/resources/static/resources/bilder/person.png");
        png.setAlignment(Image.RIGHT);;
        document.add(png);
		} catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }
		
		// set personal information
		setPersonalInfo(userAccount);
		
		//Vertrag
		document.add(new Paragraph("Vertrag des Mitarbeiters: " + employee.getContract().toString(), textFont));
		document.add(new Paragraph("Vertrag aktiv seit: " + employee.getContract().startTime().toString(), textFont));
		document.add(new Paragraph("Vertrag läuft aus am: " + employee.getContract().endTime().toString(), textFont));
		document.add(new Paragraph("\n\n"));
		
		// Aufgaben des Mitarbeiters
		String tasksString = printTasksToString(tasks);
		document.add(new Paragraph("Aufgaben des Mitarbeiters: " + tasksString, textFont));
		document.add(new Paragraph("\n\n"));
		
		// Gehalt
		document.add(new Paragraph("Gehalt erhalten: " + gehalt, headFont));
		
		document.close();
	}
}
