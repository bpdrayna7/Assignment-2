package entities;

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

	//HOW TO ADD 5% OFF W/ LEASE AGREEMENT
	@Override
	public double computeGrandtotal(Customer customer, double subtotal) {
		return subtotal + subtotal*.04*customer.getTax();
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