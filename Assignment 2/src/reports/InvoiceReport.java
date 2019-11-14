package reports;

import java.util.ArrayList;
import entities.Invoice;
import listADT.InvoiceList;
import listADT.TotalComparator;
import reader.DBReader;
import writer.ConsoleWriter;

public class InvoiceReport {

	public static void main(String[] args) {		
		
		//Database reader object
		DBReader reader = new DBReader();
		
		//ArrayList of invoices (unordered)
		ArrayList<Invoice> invoices = reader.getInvoices();
		
		//Create sorted InvoiceList
		InvoiceList invoiceList = new InvoiceList(new TotalComparator());
		for(Invoice i: invoices) {
			invoiceList.add(i);
		}

		
		//ConsoleWriter object
		ConsoleWriter writer = new ConsoleWriter();
		writer.writeInvoice(invoiceList);
			//Formats output of invoices into general invoice report
			//For-each loop (for each invoice) that outputs detailed invoice report

	}

}
