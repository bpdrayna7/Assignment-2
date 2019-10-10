package entities;

import java.util.ArrayList;

import org.joda.time.DateTime;

public interface Product {

	public abstract double getTax();
	public abstract double computeSubtotal(DateTime invoiceDate);
	public double computeGrandtotal(Customer customer, ArrayList<Product> products, double subtotal);
	public abstract String getProductCode();
	public abstract String getProductType();
	
}