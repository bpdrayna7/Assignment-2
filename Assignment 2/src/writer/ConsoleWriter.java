package writer;

import java.util.ArrayList;

import entities.Invoice;
import entities.Product;
import entities.SaleAgreement;

public class ConsoleWriter {
	public void writeInvoice(ArrayList<Invoice> invoices) {
		StringBuilder summary = new StringBuilder();
		summary.append(String.format("%-8s %-40s %-20s %-13s %-9s %-11s %-11s %-14s\n", 
				"Invoice", "Customer", "Realtor", "Subtotal", "Fees", "Taxes", "Discount", "Total"));
		StringBuilder detailedReports = new StringBuilder();
		for(Invoice i:invoices) {
			//Writes summary
			double subtotalTotal = 0;
			double taxTotal = 0;
			for(Product p:i.getProducts()) {
				double subtotal = p.computeSubtotal(i.getInvoiceDate());
				subtotalTotal += subtotal;
				taxTotal += subtotal * p.getTax();
				
			}
			double grandtotal = subtotalTotal + taxTotal*i.getCustomer().getTax() + i.getCustomer().getAdditionalFee() - i.getCustomer().getDiscount(subtotalTotal);
			
			summary.append(String.format("%-8s %-40s %-20s $%12.2f $%8.2f $%10.2f $%10.2f $%14.2f\n",
					i.getInvoiceCode(), i.getCustomer().toSummaryString(), i.getRealtor().toString(), subtotalTotal, i.getCustomer().getAdditionalFee(),
					taxTotal*i.getCustomer().getTax(), i.getCustomer().getDiscount(subtotalTotal)*-1, grandtotal));
			//Writes detailed reports
			
		}
		System.out.println(summary);
		System.out.println(detailedReports);
	}
}