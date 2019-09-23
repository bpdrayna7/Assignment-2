package dataContainers;

public class Customer {
	private String customerCode;
	private String type;
	private Person primaryContact;
	private String name;
	private Address address;
	
	public Customer(String customerCode, String type, Person primaryContact, String name, Address address) {
		super();
		this.customerCode = customerCode;
		this.type = type;
		this.primaryContact = primaryContact;
		this.name = name;
		this.address = address;
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