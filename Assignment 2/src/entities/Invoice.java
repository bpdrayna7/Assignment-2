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
