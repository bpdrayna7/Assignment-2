package entities;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class SaleAgreement extends Agreement {

	private String dateTime;
	private Address address;
	private double totalCost;
	private double downPayment;
	private double monthlyPayment;
	private int payableMonths;
	private double interestRate;
	
	public SaleAgreement(String productCode, String productType, int units, String dateTime, Address address, 
			double totalCost, double downPayment, double monthlyPayment, int payableMonths, 
			double interestRate) {
		super(productCode, productType, units);
		this.dateTime = dateTime;
		this.address = address;
		this.totalCost = totalCost;
		this.downPayment = downPayment;
		this.monthlyPayment = monthlyPayment;
		this.payableMonths = payableMonths;
		this.interestRate = interestRate/100;
	}

	@Override
	public double computeSubtotal(DateTime invoiceDate) {
		double subtotal = 0;
		DateTime startDate = dateTimeConverter(this.dateTime);
		double numOfMonths = Math.floor((invoiceDate.getMillis() - startDate.getMillis())/(2.628*Math.pow(10, 9)));
		return this.getUnits()*(this.monthlyPayment + (this.totalCost - this.downPayment - (numOfMonths*this.monthlyPayment))
				*this.interestRate);
	}

	@Override
	public double computeGrandtotal(Customer customer, ArrayList<Product> products, double subtotal) {
		return (subtotal + subtotal*.06*customer.getTax())*(1-customer.getDiscount(subtotal))
				- customer.getCredit(products) + customer.getAdditionalFee();
	}
	
	//Used Strings to display DateTime attribute but added a converter for possible later use
	public DateTime dateTimeConverter(String input) {
		final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");
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

	@Override
	public String toString() {
		return String.format("Sale Agreement @ %s", this.getAddress().getStreet());
	}
	
	@Override
	public String additionalString() {
		return String.format("%-11s%d units @ $%.2f monthly, $%d interest payment/unit\n", "", this.getUnits(),
				this.monthlyPayment, (int)(this.interestRate*this.monthlyPayment));
	}
	
}