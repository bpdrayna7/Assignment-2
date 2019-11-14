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
	
	public double computeProductSubtotal(Product product, ArrayList<Product> products) {
		if(product instanceof Amenity) {
			for(Product p:products) {
				if(p instanceof LeaseAgreement) {
					Amenity a = (Amenity)product;
					a.setDiscountString();
					return ((Amenity) product).computeSubtotal(products);
				}
			}
			return ((Amenity) product).computeSubtotal(products);
		}
		
		else if(product instanceof ParkingPass) {
			if(((ParkingPass) product).getAgreement() != null) {
				if(((ParkingPass) product).getQuantity() < ((ParkingPass) product).getAgreement().getUnits()) {
					ParkingPass pass = (ParkingPass)product;
					pass.setWithFree(pass.getQuantity());
				}
				else {
					ParkingPass pass = (ParkingPass)product;
					Agreement a = (Agreement)(pass.getAgreement());
					pass.setWithFree(a.getUnits());
				}
			}
			return ((ParkingPass) product).computeSubtotal();
		}
		else if(product instanceof LeaseAgreement) {
			return ((LeaseAgreement)product).computeSubtotal(invoiceDate);
		}
		else if (product instanceof SaleAgreement) {
			return ((SaleAgreement)product).computeSubtotal(invoiceDate);
		}
		else {
			return -1;
		}
	}
	
	public double computeTotal() {
		double subtotal = 0;
		double taxTotal = 0;
		for(Product p:this.getProducts()) {
			subtotal += computeProductSubtotal(p, this.getProducts());
			taxTotal += computeProductSubtotal(p, this.getProducts())*p.getTax();
		}
		taxTotal *= this.getCustomer().getTax();
		double fees = this.getCustomer().getAdditionalFee();
		double discount = (this.getCustomer().getCredit(this.getProducts()) + this.getCustomer().getDiscount(subtotal))*-1;
		double total = subtotal + fees + taxTotal + discount;
		return total;
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
