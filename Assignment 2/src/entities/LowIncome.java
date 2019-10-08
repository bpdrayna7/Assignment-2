package entities;

public class LowIncome extends Customer{

	public LowIncome(String customerCode, String type, Person primaryContact, String name, Address address) {
		super(customerCode, type, primaryContact, name, address);
	}

	@Override
	public double getTax() {
		//Low income customers are exempt from all taxes
		return 0;
	}

	@Override
	public double getDiscount() {
		//Low income customers are exempt from all taxes
		return 0.1;
	}

	@Override
	public double getAdditionalFee() {
		//Low income customers must pay a processing fee of $50.75
		return 50.75;
	}

	@Override
	public double getCredit() {
		//Low income customers receive a $1000/month housing credit
		return 1000;
	}

	
	
	
}
