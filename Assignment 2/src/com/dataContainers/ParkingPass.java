package com.dataContainers;

public class ParkingPass extends Product {

	private double parkingFee;
	
	public ParkingPass(String productCode, String productType, double parkingFee) {
		super(productCode, productType);
		this.parkingFee = parkingFee;
	}

	public double getParkingFee() {
		return parkingFee;
	}

	public void setParkingFee(double parkingFee) {
		this.parkingFee = parkingFee;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s $%s", this.getProductCode(), this.getProductType(), parkingFee);
	}
	
}