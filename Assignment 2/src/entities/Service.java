package entities;

import java.util.ArrayList;

import org.joda.time.DateTime;

public class Service implements Product{

	private String productCode;
	private String productType;
	private int quantity;
	
	public Service(String productCode, String productType, int quantity) {
		super();
		this.productCode = productCode;
		this.productType = productType;
		this.quantity = quantity;
	}
	
	
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	@Override
	public double getTax() {
		//All services have a 4% tax
		return 0.04;
	}

	@Override
	public double computeSubtotal(DateTime invoiceDate) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double computeGrandtotal(Customer customer, ArrayList<Product> products, double subtotal) {
		// TODO Auto-generated method stub
		return 0;
	}
}
