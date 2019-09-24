package entities;

public abstract class Product {

	private String productCode;
	private String productType;
	
	public Product(String productCode, String productType) {
		super();
		this.productCode = productCode;
		this.productType = productType;
	}

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
}