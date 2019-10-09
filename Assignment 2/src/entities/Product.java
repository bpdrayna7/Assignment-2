package entities;

import org.joda.time.DateTime;

public interface Product {

	public abstract double getTax();
	public abstract double computeSubtotal(DateTime invoiceDate);
	public abstract double computeGrandtotal(Customer customer, double subtotal);
	public abstract String getProductCode();
	
}