package reports;

import java.util.ArrayList;

import entities.Invoice;
import reader.FlatFileReader;
import writer.ConsoleWriter;

public class InvoiceReport {

	public static void main(String[] args) {

		
		//FlatFileReader object
		FlatFileReader reader = new FlatFileReader();
		ArrayList<Invoice> invoices = reader.readInvoice();
		
		//ConsoleWriter object
		ConsoleWriter writer = new ConsoleWriter();
		writer.writeInvoice(invoices);
			//Format output of invoices into general invoice report
			//For-each loop (for each invoice) that outputs detailed invoice report

	}

}
