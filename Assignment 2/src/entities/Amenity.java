package entities;

public class Amenity extends Product {

	private String name;
	private double cost;
	
	public Amenity(String productCode, String productType, String name, double cost) {
		super(productCode, productType);
		this.name = name;
		this.cost = cost;
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