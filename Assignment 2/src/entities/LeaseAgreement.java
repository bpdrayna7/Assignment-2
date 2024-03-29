package entities;

import java.time.YearMonth;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class LeaseAgreement extends Agreement {
	private String startDate;
	private String endDate;
	private Address address;
	private Customer customerName;
	private double deposit;
	private double monthlyCost;
	
	public LeaseAgreement(String productCode, String productType, int units, String startDate, String endDate, 
			Address address, Customer customerName, double deposit, double monthlyCost) {
		super(productCode, productType, units);
		this.startDate = startDate;
		this.endDate = endDate;
		this.address = address;
		this.customerName = customerName;
		this.deposit = deposit;
		this.monthlyCost = monthlyCost;
	}
	
	@Override
	public double computeSubtotal(DateTime invoiceDate) {
		DateTime startDate = dateTimeConverter(this.startDate);
		DateTime endDate = dateTimeConverter(this.endDate);
		if(invoiceDate.getMonthOfYear() == endDate.getMonthOfYear()) {
			YearMonth endMonth = YearMonth.of(endDate.getYear(), endDate.getMonthOfYear());
			int daysInMonth = endMonth.lengthOfMonth();
			return this.getUnits()*(this.monthlyCost+(this.monthlyCost*(endDate.getDayOfMonth()/daysInMonth)+this.deposit));
		}
		else if(invoiceDate.getMonthOfYear() == startDate.getMonthOfYear()) {
			YearMonth startMonth = YearMonth.of(startDate.getYear(), startDate.getMonthOfYear());
			int daysInMonth = startMonth.lengthOfMonth();
			return this.getUnits()*(this.monthlyCost*(((daysInMonth-startDate.getDayOfMonth())/daysInMonth))-this.deposit);
		}
		else {
			return this.monthlyCost*this.getUnits();
		}
	}

	@Override
	public double computeGrandtotal(Customer customer, ArrayList<Product> products, double subtotal) {
		return (subtotal + subtotal*.06*customer.getTax())*(1-customer.getDiscount(subtotal))
				- customer.getCredit(products) + customer.getAdditionalFee();
	}

	//Used Strings to display DateTime attributes but added a converter for possible later use
	public static DateTime dateTimeConverter(String input) {
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
	
	public String toString() {
		return String.format("Lease Agreement @ %s", this.getAddress().getStreet());
	}
	
	@Override
	public String additionalString() {
		return String.format("%-11s%s (%d units @ $%.2f/unit)\n", "", this.getStartDate(), 
				this.getUnits(), this.monthlyCost);
	}
	
	
}