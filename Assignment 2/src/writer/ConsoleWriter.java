package writer;

import java.util.ArrayList;

import entities.Amenity;
import entities.Invoice;
import entities.LeaseAgreement;
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
			detailedReports.append(String.format("Realtor: %s\nCustomer Info:\n %s\n", i.getRealtor().toString(), i.getCustomer().toDetailedString()));
			detailedReports.append("-------------------------------------------\n");
			detailedReports.append(String.format("%-10s %-70s %10s %10s %10s\n", "Code", "Item", "Subtotal", "Tax", "Total"));
			
			for(Product p : i.getProducts()) {
				String description = "";
				String moreInfo = "";
				
				switch(p.getProductType()) {
				case "A":
					Amenity amenity = (Amenity)p;
					description = amenity.getName();
					break;
				case "P":
					ParkingPass pass = (ParkingPass)p;
					description = "Parking Pass ";
					break;
				case "L":
					LeaseAgreement lease = (LeaseAgreement)p;
					description = "Lease Agreement ";
					moreInfo = lease.getUnits() + " units\n";
					break;
				case "S":
					SaleAgreement sale = (SaleAgreement)p;
					description = "Sale Agreement ";
					moreInfo = sale.getUnits() + " units\n";
					break;
				}
				
				detailedReports.append(String.format("%-10s %-70s\n", p.getProductCode(), description));
				if(p.getProductType().equals("L") || p.getProductType().equals("S")) {
					detailedReports.append(String.format("%-10s %s", "", moreInfo));
				}
			
			}
			
		}
		summary.append("=========================================================================================================================================\n");
		summary.append(String.format("%-70s $%12.2f $%10.2f $%10.2f $%10.2f $%14.2f\n", "Grand Total",
				subtotalGrandTotal, taxGrandTotal, feesGrandTotal, discountGrandTotal, grandtotal));
		System.out.println(summary);
		System.out.println(detailedReports);
	}
}