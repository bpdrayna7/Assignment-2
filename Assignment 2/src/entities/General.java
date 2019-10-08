package entities;

public class General extends Customer{

	public General(String customerCode, String type, Person primaryContact, String name, Address address) {
		super(customerCode, type, primaryContact, name, address);
	}

	@Override
	public double getTax() {
		//General customers must pay taxes
		return 1;
	}

	@Override
	public double getDiscount() {
		//General customers receive no additional discount
		return 0;
	}

	@Override
	public double getAdditionalFee() {
		//General customers have no additional fee to pay
		return 0;
	}

	@Override
	public double getCredit() {
		//General customers receive no housing credit
		return 0;
	}

}
