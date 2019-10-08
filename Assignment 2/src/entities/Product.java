package entities;

public interface Product {

	public abstract double getTax();
	public abstract double computeSubtotal();
	public abstract double computeGrandtotal();
	
}