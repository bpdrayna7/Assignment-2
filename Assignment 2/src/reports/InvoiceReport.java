package reports;

import java.util.ArrayList;
import entities.Invoice;
import reader.DBReader;
import writer.ConsoleWriter;

public class InvoiceReport {

	public static void main(String[] args) {		
		
		//Database reader object
		DBReader reader = new DBReader();
		//ArrayList of invoices (unordered)
		ArrayList<Invoice> invoices = reader.getInvoices();
		
		//ConsoleWriter object
		ConsoleWriter writer = new ConsoleWriter();
		writer.writeInvoice(invoices);
			//Formats output of invoices into general invoice report
			//For-each loop (for each invoice) that outputs detailed invoice report

	}

}
