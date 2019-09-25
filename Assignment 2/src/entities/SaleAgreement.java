package entities;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class SaleAgreement extends Product {
	private String dateTime;
	private Address address;
	private double totalCost;
	private double downPayment;
	private double monthlyPayment;
	private int payableMonths;
	private double interestRate;
	
	public SaleAgreement(String productCode, String productType, String dateTime, Address address, 
			double totalCost, double downPayment, double monthlyPayment, int payableMonths, 
			double interestRate) {
		super(productCode, productType);
		this.dateTime = dateTime;
		this.address = address;
		this.totalCost = totalCost;
		this.downPayment = downPayment;
		this.monthlyPayment = monthlyPayment;
		this.payableMonths = payableMonths;
		this.interestRate = interestRate;
	}

	public DateTime dateTimeConverter(String input) {
		final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
		return DATE_TIME_FORMATTER.parseDateTime(input);
	}
	
	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public double getDownPayment() {
		return downPayment;
	}

	public void setDownPayment(double downPayment) {
		this.downPayment = downPayment;
	}

	public double getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(double monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public int getPayableMonths() {
		return payableMonths;
	}

	public void setPayableMonths(int payableMonths) {
		this.payableMonths = payableMonths;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
}