package entities;

import org.joda.time.DateTime;

public class SaleAgreement extends Product {
	private DateTime dateTime;
	private Address address;
	private double totalCost;
	private double downPayment;
	private double monthlyPayment;
	private int payableMonths;
	private double interestRate;
	
	public SaleAgreement(String productCode, String productType, DateTime dateTime, Address address, 
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

	public DateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(DateTime dateTime) {
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