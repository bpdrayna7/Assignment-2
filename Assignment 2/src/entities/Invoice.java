package entities;

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
		
		//might not work if start/end date month is same as invoice but not same year
		else if(product instanceof LeaseAgreement) {
			if(((LeaseAgreement) product).dateTimeConverter(((LeaseAgreement) product).getStartDate()).monthOfYear() == 
					this.invoiceDate.monthOfYear()) {
				return ((LeaseAgreement) product).getMonthlyCost()*((LeaseAgreement) product).getUnits() + ((LeaseAgreement) product).getDeposit();
			}
			else if(((LeaseAgreement) product).dateTimeConverter(((LeaseAgreement) product).getEndDate()).monthOfYear() == 
					this.invoiceDate.monthOfYear()) {
				return ((LeaseAgreement) product).getMonthlyCost()*((LeaseAgreement) product).getUnits() - ((LeaseAgreement) product).getDeposit();
			}
			return ((LeaseAgreement) product).getMonthlyCost()*((LeaseAgreement) product).getUnits();
		}
		else if (product instanceof SaleAgreement) {
			return ((SaleAgreement) product).computeSubtotal(this.invoiceDate);
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
