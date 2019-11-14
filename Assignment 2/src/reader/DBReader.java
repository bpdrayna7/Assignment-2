package reader;

import java.util.ArrayList;
import java.sql.*;

import com.ceg.ext.InvoiceData;

import entities.Address;
import entities.Agreement;
import entities.Amenity;
import entities.Customer;
import entities.General;
import entities.Invoice;
import entities.LeaseAgreement;
import entities.LowIncome;
import entities.ParkingPass;
import entities.Person;
import entities.Product;
import entities.SaleAgreement;

public class DBReader {
	
	Connection conn;
	int currentInvoice;

	public DBReader() {
		conn = InvoiceData.createConnection();
		currentInvoice = 0;
	}
	
	//Creates ArrayList of invoices and returns this ArrayList
	public ArrayList<Invoice> getInvoices(){
		
		Statement stmt = null;
		String query = null;
		ResultSet rs = null;
		
		ArrayList<Invoice> invoices = new ArrayList<Invoice>();
		
		try {
			
			stmt = conn.createStatement();
			query = "SELECT * FROM Invoice";
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				int invoiceId = rs.getInt("invoiceId");
				currentInvoice = invoiceId;
				String invoiceCode = rs.getString("invoiceCode");
				int customerId = rs.getInt("customerId");
				int personId = rs.getInt("personId");
				String invoiceDate = rs.getString("invoiceDate");
				//Creates and adds each invoice to ArrayList
				invoices.add(readInvoice(invoiceId, invoiceCode, customerId, personId, invoiceDate));
			}
			
			stmt.close();
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			//Closes database connection
			InvoiceData.closeConnection(conn);
		}
		return invoices;
	}
	
	//Creates Invoice object based on data from Invoice table and data selected from InvoiceProduct table
	public Invoice readInvoice(int invoiceId, String invoiceCode, int customerId, int personId, String invoiceDate) {
		
		Person person = readPerson(personId);
		Customer customer = readCustomer(customerId);
		ArrayList<Product> products = readProducts(invoiceId);
		
		Invoice invoice = new Invoice(invoiceCode, LeaseAgreement.dateTimeConverter(invoiceDate), customer, person, products);
		return invoice;
	}
	
	//Creates Person object based on personId from the invoice
	public Person readPerson(int personId) {
		
		Statement stmt = null;
		String query = null;
		Person person = null;
		ResultSet rs = null;
		
		try {
			
			//Selects record for person with specific personId
			query = "SELECT * FROM Person WHERE personId = " + personId;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			String personCode = rs.getString("personCode");
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			int addressId = rs.getInt("addressId");
			
			//Selects address record for this person
			Address address = readAddress(addressId);
			//Selects emails for this person
			ArrayList<String> emails = readEmail(personId);
			if(emails.isEmpty()) {
				//Creates person with no emails
				person = new Person(personCode, firstName, lastName, address);
			}
			else {
				//Creates person with emails
				person = new Person(personCode, firstName, lastName, address, emails);
			}
			
			stmt.close();
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return person;
	}
	
	//Creates Customer object based on customerId from the invoice
	public Customer readCustomer(int customerId) {
		
		Statement stmt = null;
		String query = null;
		Customer customer = null;
		ResultSet rs = null;
		
		try {
			//Selects record for specific customerId
			query = "SELECT * FROM Customer WHERE customerId = " + customerId;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			String customerCode = rs.getString("customerCode");
			String type = rs.getString("type");
			int personId = rs.getInt("personId");
			//Creates person (primaryContact) of this customer
			Person person = readPerson(personId);
			String name = rs.getString("name");
			int addressId = rs.getInt("addressId");
			//Creates address of this customer
			Address address = readAddress(addressId);
			
			//Creates Customer object depending on customer type
			if(type.equals("G") || type.equals("General")) {
				customer = new General(customerCode, type, person, name, address);
			}
			else {
				customer = new LowIncome(customerCode, type, person, name, address);
			}
			stmt.close();
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}
	
	//Creates Customer object based on customerName
	public Customer readCustomer(String customerName) {
		
		Statement stmt = null;
		String query = null;
		Customer customer = null;
		ResultSet rs = null;
		
		try {
			//Selects record for specific customerName
			query = "SELECT * FROM Customer WHERE name = '" + customerName + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			String customerCode = rs.getString("customerCode");
			String type = rs.getString("type");
			int personId = rs.getInt("personId");
			//Creates person (primaryContact) of this customer
			Person person = readPerson(personId);
			int addressId = rs.getInt("addressId");
			//Creates address of this customer
			Address address = readAddress(addressId);
			
			//Creates Customer object depending on customer type
			if(type.equals("G")) {
				customer = new General(customerCode, type, person, customerName, address);
			}
			else {
				customer = new LowIncome(customerCode, type, person, customerName, address);
			}
			stmt.close();
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}
	
	//Creates ArrayList of products based on invoiceId and InvoiceProduct table
	public ArrayList<Product> readProducts(int invoiceId){
		
		Statement stmt = null;
		String query = null;
		ArrayList<Product> products = new ArrayList<Product>();
		ResultSet rs = null;
		
		try {
			//Selects all productIds associated with a specific invoice
			query = "SELECT * FROM InvoiceProduct WHERE invoiceId = " + invoiceId;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				int productId = rs.getInt("productId");
				int quantity = rs.getInt("quantity");
				int agreementId = rs.getInt("agreementId");
				
				//Adds all products to ArrayList of products
				products.add(readProduct(productId, quantity, agreementId));
			}
			stmt.close();
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return products;
	}
	
	//Creates subclasses of Product based on given productId
	public Product readProduct(int productId, int quantity, int agreementId) {
		
		Statement stmt = null;
		String query = null;
		Product product = null;
		ResultSet rs = null;
		
		try {
			//Selects record for specific productId
			query = "SELECT * FROM Product WHERE productId = " + productId;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			String productCode = rs.getString("productCode");
			String type = rs.getString("type");
			
			//Creates Product subclass object based on product type
			switch(type) {
				case "L":
					product = readLeaseAgreement(productCode, type, productId, quantity);
					break;
				case "S":
					product = readSaleAgreement(productCode, type, productId, quantity);
					break;
				case "P":
					product = readParkingPass(productCode, type, productId, quantity, agreementId);
					break;
				case "A":
					product = readAmenity(productCode, type, productId, quantity);
					break;
			}
			stmt.close();
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return product;
	}
	
	//Creates LeaseAgreement object based on productId
	public LeaseAgreement readLeaseAgreement(String productCode, String type, int productId, int units) {
		
		Statement stmt = null;
		String query = null;
		LeaseAgreement leaseAg = null;
		ResultSet rs = null;
		
		try {
			//Selects LeaseAgreement record for this specific product
			query = "SELECT * FROM LeaseAgreement WHERE productId = " + productId;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			String startDate = rs.getString("startDate");
			String endDate = rs.getString("endDate");
			int addressId = rs.getInt("addressId");
			//Creates Address object for this lease agreement
			Address address = readAddress(addressId);
			String customerName = rs.getString("customerName");
			//Creates Customer object for this lease agreement
			Customer customer = readCustomer(customerName);
			double deposit = rs.getDouble("deposit");
			double monthlyCost = rs.getDouble("monthlyCost");
			
			//Creates LeaseAgreement object
			leaseAg = new LeaseAgreement(productCode, type, units, startDate, endDate, address, customer, deposit, monthlyCost);
			stmt.close();
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return leaseAg;
	}
	
	//Creates SaleAgreement object based on productId
	public SaleAgreement readSaleAgreement(String productCode, String type, int productId, int units) {
		
		Statement stmt = null;
		String query = null;
		SaleAgreement saleAg = null;
		ResultSet rs = null;
		
		try {
			//Selects SaleAgreement record for this specific product
			query = "SELECT * FROM SaleAgreement WHERE productId = " + productId;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			String dateTime = rs.getString("dateTime");
			int addressId = rs.getInt("addressId");
			//Creates Address object for this sale agreement
			Address address = readAddress(addressId);
			double totalCost = rs.getDouble("totalCost");
			double downPayment = rs.getDouble("downPayment");
			double monthlyPayment = rs.getDouble("monthlyPayment");
			int payableMonths = rs.getInt("payableMonths");
			double interestRate = rs.getDouble("interestRate");
			
			//Creates SaleAgreement object
			saleAg = new SaleAgreement(productCode, type, units, dateTime, address, totalCost, downPayment, 
					monthlyPayment, payableMonths, interestRate);
			stmt.close();
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return saleAg;
	}

	//Creates ParkingPass object based on productId
	public ParkingPass readParkingPass(String productCode, String type, int productId, int quantity, int agreementId) {
			
		Statement stmt = null;
		String query = null;
		ParkingPass pass = null;
		ResultSet rs = null;
		
		try {
			//
			query = "SELECT * FROM ParkingPass WHERE productId = " + productId;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			double parkingFee = rs.getDouble("parkingFee");
			
			
			if(agreementId != 0) {
				//Gets number of units for the associated agreement
				query = "SELECT quantity FROM InvoiceProduct WHERE productId = " + agreementId + " AND invoiceId = " + currentInvoice;
				rs = stmt.executeQuery(query);
				rs.next();
				int units = rs.getInt("quantity");
				//Gets Agreement object associated with this parking pass
				Product agreementProduct = readProduct(agreementId, units, 0);
				//Downcasts agreementProduct (a Product) to agreement (an Agreement)
				Agreement agreement = (Agreement)agreementProduct;
				
				//Creates ParkingPass with associated agreement
				pass = new ParkingPass(productCode, type, quantity, parkingFee, agreement);
			}
			//Creates ParkingPass with no associated agreement
			else {
				pass = new ParkingPass(productCode, type, quantity, parkingFee);
			}
			stmt.close();
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return pass;
	}
	
	//Creates Amenity object based on productId
	public Amenity readAmenity(String productCode, String type, int productId, int quantity) {
		
		Statement stmt = null;
		String query = null;
		Amenity amenity = null;
		ResultSet rs = null;
		
		try {
			//Selects Amenity record for this specific product
			query = "SELECT * FROM Amenity WHERE productId = " + productId;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			String name = rs.getString("name");
			double cost = rs.getDouble("cost");
			
			//Creates Amenity object
			amenity = new Amenity(productCode, type, quantity, name, cost);
			stmt.close();
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return amenity;
	}
	
	//Creates Address object based on given input id
	public Address readAddress(int addressId) {
		
		Statement stmt = null;
		String query = null;
		Address address = null;
		ResultSet rs = null;
		
		try {
			//Selects record for specific address
			query = "SELECT * FROM Address WHERE addressId = " + addressId;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			String street = rs.getString("street");
			String city = rs.getString("city");
			int stateId = rs.getInt("stateId");
			//Selects state record based on stateId of address
			String state = readState(stateId);
			String zipcode = rs.getString("zipcode");
			int countryId = rs.getInt("countryId");
			//Selects country record based on countryId of address
			String country = readCountry(countryId);
			
			//Creates Address object
			address = new Address(street, city, state, zipcode, country);
			stmt.close();
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return address;
	}
	
	//Creates ArrayList of email addresses based on personId
	public ArrayList<String> readEmail(int personId){
		
		Statement stmt = null;
		String query = null;
		ArrayList<String> emails = new ArrayList<String>();
		ResultSet rs = null;
		
		try {
			//Selects emailAddresses for specific person
			query = "SELECT emailAddress FROM Email WHERE personId = " + personId;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				//Adds all emails into ArrayList
				String email = rs.getString("emailAddress");
				emails.add(email);
			}
			stmt.close();
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return emails;
	}
	
	//Creates state String based on stateId
	public String readState(int stateId) {
		
		Statement stmt = null;
		String query = null;
		String state = null;
		ResultSet rs = null;
		
		try {
			//Selects state for specific stateId
			query = "SELECT state FROM State WHERE stateId = " + stateId;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			state = rs.getString("state");
			
			stmt.close();
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return state;
	}
	
	//Creates country String based on countryId
	public String readCountry(int countryId) {
		
		Statement stmt = null;
		String query = null;
		String country = null;
		ResultSet rs = null;
		
		try {
			//Selects country for specific countryId
			query = "SELECT country FROM Country WHERE countryId = " + countryId;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			country = rs.getString("country");
			
			stmt.close();
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return country;
	}
	
}
