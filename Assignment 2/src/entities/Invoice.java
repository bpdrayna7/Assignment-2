package entities;

import java.time.YearMonth;
import java.util.ArrayList;

import org.joda.time.DateTime;

public class Invoice {

	private String invoiceCode;
	private DateTime invoiceDate;
	private Customer customer;
	private Person realtor;
	private ArrayList<Product> products;
	
	public Invoice(String invoiceCode, DateTime invoiceDate, Customer customer, Person realtor,
			ArrayList<Product> products) {
		super();
		this.invoiceCode = invoiceCode;
		this.invoiceDate = invoiceDate;
		this.customer = customer;
		this.realtor = realtor;
		this.products = products;
	}
	
	public double computeSubtotal(Product product, ArrayList<Product> products) {
		if(product instanceof Amenity) {
			for(Product p:products) {
				if(p instanceof LeaseAgreement) {
					Amenity a = (Amenity)product;
					a.setDiscountString();
					return ((Amenity) product).getCost() * .95 * ((Amenity) product).getQuantity();
				}
			}
			return ((Amenity) product).getCost() * ((Amenity) product).getQuantity();
		}
		
		else if(product instanceof ParkingPass) {
			if(((ParkingPass) product).getAgreement() != null) {
				if(((ParkingPass) product).getQuantity() < ((ParkingPass) product).getAgreement().getUnits()) {
					ParkingPass pass = (ParkingPass)product;
					pass.setWithFree(pass.getQuantity());
					return 0;
				}
				ParkingPass pass = (ParkingPass)product;
				Agreement a = (Agreement)(pass.getAgreement());
				pass.setWithFree(a.getUnits());
				return (((ParkingPass) product).getQuantity()-((ParkingPass) product).getAgreement().getUnits())
						*((ParkingPass) product).getParkingFee();
			}
			return ((ParkingPass) product).getQuantity()*((ParkingPass) product).getParkingFee();
		}
		else if(product instanceof LeaseAgreement) {
			DateTime startDate = LeaseAgreement.dateTimeConverter(((LeaseAgreement)product).getStartDate());
			DateTime endDate = LeaseAgreement.dateTimeConverter(((LeaseAgreement)product).getEndDate());
			if(invoiceDate.getMonthOfYear() == startDate.getMonthOfYear()) {
				YearMonth startMonth = YearMonth.of(startDate.getYear(), startDate.getMonthOfYear());
				int daysInMonth = startMonth.lengthOfMonth();
				return ((LeaseAgreement) product).getUnits()*((((LeaseAgreement) product).getMonthlyCost()+((daysInMonth-startDate.getDayOfMonth())/daysInMonth))
						*((LeaseAgreement) product).getMonthlyCost()+((LeaseAgreement) product).getDeposit());
			}
			else if(invoiceDate.getMonthOfYear() == endDate.getMonthOfYear()) {
				YearMonth endMonth = YearMonth.of(endDate.getYear(), endDate.getMonthOfYear());
				int daysInMonth = endMonth.lengthOfMonth();
				return ((LeaseAgreement) product).getUnits()*(((LeaseAgreement) product).getMonthlyCost()+(((LeaseAgreement) product).getMonthlyCost()
						*(endDate.getDayOfMonth()/daysInMonth)-((LeaseAgreement) product).getDeposit()));
			}
			else {
				return ((LeaseAgreement) product).getUnits()*((LeaseAgreement)product).getMonthlyCost();
			}
		}
		else if (product instanceof SaleAgreement) {
			double subtotal = 0;
			DateTime startDate = ((SaleAgreement)product).dateTimeConverter(((SaleAgreement)product).getDateTime());
			double numOfMonths = Math.floor((invoiceDate.getMillis()-startDate.getMillis())/(2.628*Math.pow(10, 9)));
			if(startDate.getMonthOfYear() == invoiceDate.getMonthOfYear()) {
				return ((SaleAgreement) product).getUnits()*(((SaleAgreement) product).getMonthlyPayment()+((SaleAgreement) product).getDownPayment() +
						(((SaleAgreement) product).getTotalCost()-((SaleAgreement) product).getDownPayment())-((SaleAgreement) product).getMonthlyPayment())
						*((SaleAgreement) product).getInterestRate();
			}
			else {
				return ((SaleAgreement) product).getUnits()*(((SaleAgreement) product).getTotalCost()-((SaleAgreement) product).getDownPayment()-
						(numOfMonths*((SaleAgreement) product).getMonthlyPayment())*((SaleAgreement) product).getInterestRate());
			}
		}
		else {
			return -1;
		}
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public DateTime getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(DateTime invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Person getRealtor() {
		return realtor;
	}

	public void setRealtor(Person realtor) {
		this.realtor = realtor;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	
	
	
	
}
