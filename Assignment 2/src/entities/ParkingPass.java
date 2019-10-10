package entities;

import java.util.ArrayList;

import org.joda.time.DateTime;

public class ParkingPass extends Service {

	private double parkingFee;
	private Agreement agreement;
	private String withFree = "";
	
	public ParkingPass(String productCode, String productType, int quantity, double parkingFee, Agreement agreement) {
		super(productCode, productType, quantity);
		this.parkingFee = parkingFee;
		this.agreement = agreement;
	}
	
	public ParkingPass(String productCode, String productType, int quantity, double parkingFee) {
		super(productCode, productType, quantity);
		this.parkingFee = parkingFee;
	}
	
	@Override
	public double computeSubtotal(DateTime invoiceDate) {
		int quantity = this.getQuantity();
		if(this.agreement != null) {
			if(quantity < this.agreement.getUnits()) {
				quantity = 0;
			}
			else {
				quantity-=this.agreement.getUnits();
			}
		}
		return this.parkingFee*quantity;
	}

	@Override
	public double computeGrandtotal(Customer customer, ArrayList<Product> products, double subtotal) {
		return (subtotal + subtotal*.04*customer.getTax())*(1-customer.getDiscount(subtotal)) + customer.getAdditionalFee();
	}

	public double getParkingFee() {
		return parkingFee;
	}

	public void setParkingFee(double parkingFee) {
		this.parkingFee = parkingFee;
	}
	
	public Agreement getAgreement() {
		return agreement;
	}

	public void setAgreement(Agreement agreement) {
		this.agreement = agreement;
	}

	@Override
	public String toString() {
		return String.format("Parking Pass%s (%d units @ $%.2f%s)", this.agreementString(), 
				this.getQuantity(), this.getParkingFee(), withFree);
	}
	
	public void setWithFree(int num) {
		withFree = " with " + num + " free";
	}
	
	public String agreementString() {
		if(this.getAgreement() != null) {
			return " " + this.getAgreement().getProductCode();
		}
		else {
			return "";
		}
	}
	
	
}