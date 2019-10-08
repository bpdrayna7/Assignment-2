package reports;

import java.util.ArrayList;

import entities.Invoice;
import reader.FlatFileReader;

public class InvoiceReport {

	public static void main(String[] args) {

		
		//FlatFileReader object
		FlatFileReader reader = new FlatFileReader();
		ArrayList<Invoice> invoices = reader.readInvoice();
		for(Invoice i : invoices) {
			System.out.println(i.getInvoiceCode());
		}
		
		//ConsoleWriter object
		

	}

}
