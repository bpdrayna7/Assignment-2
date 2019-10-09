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
		summary.append(String.format("%-8s %-40s %-20s %-13s %-9s %-11s %-11s %-14s\n", 
				"Invoice", "Customer", "Realtor", "Subtotal", "Fees", "Taxes", "Discount", "Total"));
		StringBuilder detailedReports = new StringBuilder();
		detailedReports.append("Individual Invoice Detail Reports\n================================");
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
//			switch(p.getProductType()) {
//			case "L":
//				LeaseAgreement lease = (LeaseAgreement)p;
//				detailedReports.append(String.format("%-10s %-70s", "", lease.getUnits()));
//				break;
//			case "S":
//				SaleAgreement sale = (SaleAgreement)p;
//				detailedReports.append(String.format("%-10s %-70s", "", sale.getUnits()));
//				break;
//			}
			
			
//			detailedReports.append(String.format("%-10s &-70s", "", moreInfo));
			}
			
		}
		System.out.println(summary);
		System.out.println("\n\n\n");
		System.out.println(detailedReports);
	}
}