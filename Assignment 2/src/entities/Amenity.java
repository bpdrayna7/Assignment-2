package entities;

import java.util.ArrayList;

import entities.LeaseAgreement;

public class Amenity extends Service {

	private String name;
	private double cost;
	private String discountString = "";
	
	public Amenity(String productCode, String productType, int quantity, String name, double cost) {
		super(productCode, productType, quantity);
		this.name = name;
		this.cost = cost;
	}

	public double computeSubtotal(ArrayList<Product> products) {
		for(Product p:products) {
			if(p.getProductType().equals("L")) {
				return this.cost*.95*this.getQuantity();
			}
		}
		return this.cost*this.getQuantity();
	}

	@Override
	public double computeGrandtotal(Customer customer, ArrayList<Product> products, double subtotal) {
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
		return String.format("%s (%d units @ $%.2f/unit%s)", this.getName(), this.getQuantity(), this.getCost(), discountString);
	}
	
	public void setDiscountString() {
		discountString = " with 5% off";
	}
	
}