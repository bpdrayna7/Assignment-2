package entities;

import java.util.ArrayList;

public abstract class Customer {
	private String customerCode;
	private String type;
	private Person primaryContact;
	private String name;
	private Address address;
	
	public Customer(String customerCode, String type, Person primaryContact, String name, Address address) {
		super();
		this.customerCode = customerCode;
		switch(type) {
		case "G":
			this.type = "General";
			break;
		case "L":
			this.type = "Low Income";
			break;
		default:
			this.type = null;
			break;
		}
		this.primaryContact = primaryContact;
		this.name = name;
		this.address = address;
	}

	public abstract double getTax();
	public abstract double getDiscount(double subtotal);
	public abstract double getAdditionalFee();
	public abstract double getCredit(ArrayList<Product> products);
	
	public String toSummaryString() {
		return this.name + " [" + this.type + "]";
	}
	
	public String toDetailedString() {
		return String.format("%s(%s)\n [%s]\n %s\n %s\n %s, %s %s %s\n", this.getName(), this.getCustomerCode(), this.getType(), this.getPrimaryContact().toString(),
				this.getAddress().getStreet(), this.getAddress().getCity(), this.getAddress().getState(), this.getAddress().getZip(), this.getAddress().getCountry());
	}
	
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Person getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(Person primaryContact) {
		this.primaryContact = primaryContact;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
}