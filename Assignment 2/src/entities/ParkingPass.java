package entities;

public class ParkingPass extends Service {

	private double parkingFee;
	private Agreement agreement;
	
	public ParkingPass(String productCode, String productType, int quantity, double parkingFee, Agreement agreement) {
		super(productCode, productType, quantity);
		this.parkingFee = parkingFee;
		this.agreement = agreement;
	}
	
	public ParkingPass(String productCode, String productType, int quantity, double parkingFee) {
		super(productCode, productType, quantity);
		this.parkingFee = parkingFee;
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
		return String.format("%s %s $%s", this.getProductCode(), this.getProductType(), parkingFee);
	}
	
}