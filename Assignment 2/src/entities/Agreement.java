package entities;

import java.util.ArrayList;

import org.joda.time.DateTime;

public class Agreement implements Product{

	private String productCode;
	private String productType;
	private int units;
	
	public Agreement(String productCode, String productType, int units) {
		super();
		this.productCode = productCode;
		this.productType = productType;
		this.units = units;
	}
	
	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
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
		//All agreements have a 6% tax
		return 0.06;
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
