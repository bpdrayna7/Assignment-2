package entities;

import java.util.ArrayList;

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
	public double getDiscount(double subtotal) {
		//Low income customers get a 10% discount
		return subtotal*0.1;
	}

	@Override
	public double getAdditionalFee() {
		//Low income customers must pay a processing fee of $50.75
		return 50.75;
	}

	@Override
	public double getCredit(ArrayList<Product> products) {
		//Low income customers receive a $1000/month housing credit
		for(Product p:products) {
			if(p instanceof LeaseAgreement || p instanceof SaleAgreement) {
				return 1000;
			}
		}
		return 0;
	}

}
