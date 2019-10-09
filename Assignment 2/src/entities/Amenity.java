package entities;

import java.util.ArrayList;

import org.joda.time.DateTime;

public class Amenity extends Service {

	private String name;
	private double cost;
	
	public Amenity(String productCode, String productType, int quantity, String name, double cost) {
		super(productCode, productType, quantity);
		this.name = name;
		this.cost = cost;
	}

	@Override
	public double computeSubtotal(DateTime invoiceDate) {
		return this.cost*this.getQuantity();
	}

	@Override
	public double computeGrandtotal(Customer customer, ArrayList<Product> products, double subtotal) {
		for(Product p: products) {
			if(p instanceof LeaseAgreement) {
				double newSubtotal = subtotal*.95;
				return newSubtotal + newSubtotal*.04*customer.getTax() + customer.getAdditionalFee();
			}
		}
		return subtotal + subtotal*.04*customer.getTax() + customer.getAdditionalFee() - customer.getDiscount(subtotal);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s $%s", this.getProductCode(), this.getProductType(), name, cost);
	}	
}