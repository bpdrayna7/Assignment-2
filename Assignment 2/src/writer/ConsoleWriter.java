package writer;

import java.util.ArrayList;

import entities.Agreement;
import entities.Amenity;
import entities.Invoice;
import entities.LeaseAgreement;
import entities.LowIncome;
import entities.ParkingPass;
import entities.Product;
import entities.SaleAgreement;

public class ConsoleWriter {
	public void writeInvoice(ArrayList<Invoice> invoices) {
		StringBuilder summary = new StringBuilder();
		summary.append(String.format("%-8s %-40s %-20s %-13s %-11s %-11s %-11s %-14s\n", 
				"Invoice", "Customer", "Realtor", "Subtotal", "Fees", "Taxes", "Discount", "Total"));
		StringBuilder detailedReports = new StringBuilder();
		detailedReports.append("Individual Invoice Detail Reports\n================================");
		
		double subtotalGrandTotal = 0;
		double taxGrandTotal = 0;
		double feesGrandTotal = 0;
		double discountGrandTotal = 0;
		double grandtotal = 0;
		
		for(Invoice i:invoices) {
			//Writes summary
			double subtotal = 0;
			double taxTotal = 0;
			for(Product p: i.getProducts()) {
				subtotal += i.computeSubtotal(p, i.getProducts());
				taxTotal += i.computeSubtotal(p, i.getProducts())*p.getTax();
			}
			taxTotal *= i.getCustomer().getTax();
			double fees = i.getCustomer().getAdditionalFee();
			double discount = (i.getCustomer().getCredit(i.getProducts()) + i.getCustomer().getDiscount(subtotal))*-1;
			double total = subtotal + fees + taxTotal + discount;

			summary.append(String.format("%-8s %-40s %-20s $%12.2f $%10.2f $%10.2f $%10.2f $%14.2f\n",
					i.getInvoiceCode(), i.getCustomer().toSummaryString(), i.getRealtor().toString(), subtotal, fees, taxTotal,
					discount, total));
			subtotalGrandTotal += subtotal;
			taxGrandTotal += taxTotal;
			feesGrandTotal += fees;
			discountGrandTotal += discount;
			grandtotal += total;
			//Writes detailed reports
			
			
			detailedReports.append(String.format("\nInvoice %s\n============================\n", i.getInvoiceCode()));
			detailedReports.append(String.format("Realtor: %s\nCustomer Info:\n %s", i.getRealtor().toString(), i.getCustomer().toDetailedString()));
			detailedReports.append("-------------------------------------------\n");
			detailedReports.append(String.format("%-10s %-70s %16s %16s %16s\n", "Code", "Item", "Subtotal", "Tax", "Total"));
			
			double subtotalSubtotal = 0;
			double taxSubtotal = 0;
			double totalSubtotal = 0;
			
			for(Product p : i.getProducts()) {
				double productSubtotal = 0;
				
				if(p instanceof Amenity) {
					Amenity amenity = (Amenity)p;
					productSubtotal = amenity.computeSubtotal(i.getProducts());
				}
				else {
					productSubtotal = p.computeSubtotal(i.getInvoiceDate());
				}
				
				detailedReports.append(String.format("%-10s %-70s $%15.2f $%15.2f $%15.2f\n", p.getProductCode(), p.toString(), productSubtotal,
						i.getCustomer().getTax()*productSubtotal*p.getTax(), productSubtotal + i.getCustomer().getTax()*productSubtotal*p.getTax()));
				if(p instanceof Agreement) {
					Agreement a = (Agreement)p;
					detailedReports.append(a.additionalString());
				}
				
				subtotalSubtotal += productSubtotal;
				taxSubtotal += i.getCustomer().getTax()*productSubtotal*p.getTax();
				totalSubtotal += productSubtotal + i.getCustomer().getTax()*productSubtotal*p.getTax();
			}
			
			detailedReports.append(String.format("%-82s==================================================\n", ""));
			detailedReports.append(String.format("%-81s $%15.2f $%15.2f $%15.2f\n", "SUBTOTALS", subtotalSubtotal, taxSubtotal, totalSubtotal));
			double changes = 0;
			//discount + housing credit
			if(i.getCustomer() instanceof LowIncome) {
				if(i.getCustomer().getCredit(i.getProducts()) == 0) {
					detailedReports.append(String.format("%-115s $%15.2f\n", "DISCOUNT (10% LOW INCOME)", (i.getCustomer().getDiscount(subtotal))*-1));
				}
				else {
					detailedReports.append(String.format("%-115s $%15.2f\n", "DISCOUNT (10% LOW INCOME + $1000 HOUSING CREDIT)",
							(i.getCustomer().getCredit(i.getProducts()) + i.getCustomer().getDiscount(subtotal))*-1));
				}
				detailedReports.append(String.format("%-115s $%15.2f\n", "ADDITIONAL FEE (LOW INCOME)", ((LowIncome)i.getCustomer()).getAdditionalFee()));
				changes += ((LowIncome)i.getCustomer()).getAdditionalFee() + (i.getCustomer().getCredit(i.getProducts()) + i.getCustomer().getDiscount(subtotal))*-1;
			}
			detailedReports.append(String.format("%-115s $%15.2f\n", "TOTAL", totalSubtotal + changes));
			detailedReports.append(String.format("%-15s Thank you for your purchase!\n", ""));
			
		}
		summary.append("=========================================================================================================================================\n");
		summary.append(String.format("%-70s $%12.2f $%10.2f $%10.2f $%10.2f $%14.2f\n", "Grand Total",
				subtotalGrandTotal, feesGrandTotal, taxGrandTotal, discountGrandTotal, grandtotal));
		System.out.println(summary);
		System.out.println(detailedReports);
	}
}