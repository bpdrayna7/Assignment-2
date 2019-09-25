package entities;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class LeaseAgreement extends Product {
	private String startDate;
	private String endDate;
	private Address address;
	private Customer customerName;
	private double deposit;
	private double monthlyCost;
	
	public LeaseAgreement(String productCode, String productType, String startDate, String endDate, 
			Address address, Customer customerName, double deposit, double monthlyCost) {
		super(productCode, productType);
		this.startDate = startDate;
		this.endDate = endDate;
		this.address = address;
		this.customerName = customerName;
		this.deposit = deposit;
		this.monthlyCost = monthlyCost;
	}

	public DateTime dateTimeConverter(String input) {
		final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");
		return DATE_FORMATTER.parseDateTime(input);
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Customer getCustomerName() {
		return customerName;
	}

	public void setCustomerName(Customer customerName) {
		this.customerName = customerName;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	public double getMonthlyCost() {
		return monthlyCost;
	}

	public void setMonthlyCost(double monthlyCost) {
		this.monthlyCost = monthlyCost;
	}
		
}