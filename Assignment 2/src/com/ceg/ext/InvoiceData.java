package com.ceg.ext;

import java.sql.*;

/*
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 * 15 methods in total, add more if required.
 * Donot change any method signatures or the package name.
 * 
 */

public class InvoiceData {

//	Creates connection to the database
	public static Connection createConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://cse.unl.edu:3306/rhruby", "rhruby", "");
		}
		catch(ClassNotFoundException e) {
			System.err.println(e);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
//	Closes connection to the database
	public static void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
//	Returns countryId if country record exists already; otherwise, creates country record and then returns countryId of new record
	public static int addCountry(String country, Connection conn) {
		String query = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		
		int countryId = 0;
		try {
			//Checks if record for this country already exists
			query = "SELECT countryId FROM Country WHERE country = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, country);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				countryId = rs.getInt("countryId");
			}
			//Inserts record if country record does not already exist
			else {
				query = "INSERT INTO Country(country) VALUES(?)";
				ps = conn.prepareStatement(query);
				ps.setString(1, country);
				ps.executeUpdate();
				query = "SELECT LAST_INSERT_ID()";
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				rs.next();
				countryId = rs.getInt("LAST_INSERT_ID()");
			}
			ps.close();
			rs.close();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return countryId;
	}
	
//	Returns stateId if state record exists already; otherwise, creates state record and then returns stateId of new record
	public static int addState(String state, Connection conn) {
		String query = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int stateId = 0;
		try {
			//Checks if record for this state already exists
			query = "SELECT stateId FROM State WHERE state = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, state);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				stateId = rs.getInt("stateId");
			}
			//Inserts record if state record does not already exist
			else {
				query = "INSERT INTO State(state) VALUES (?)";
				ps = conn.prepareStatement(query);
				ps.setString(1, state);
				ps.executeUpdate();
				query = "SELECT LAST_INSERT_ID()";
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				rs.next();
				stateId = rs.getInt("LAST_INSERT_ID()");
			}
			ps.close();
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return stateId;
	}
	
//	Returns addressId if address record exists already; otherwise, creates address record and then returns addressId of new record
	public static int addAddress(String street, String city, String state, String zip, String country, Connection conn) {
		String query = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int addressId = 0;
		//Gets stateId
		int stateId = addState(state, conn);
		//Gets countryId
		int countryId = addCountry(country, conn);
		try {
			//Checks if record for this address already exists
			query = "SELECT addressId FROM Address WHERE street = ? AND city = ? AND stateId = ? AND zipcode = ? AND countryId = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setInt(3, stateId);
			ps.setString(4, zip);
			ps.setInt(5, countryId);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				addressId = rs.getInt("addressId");
			}
			//Inserts record if address record does not already exist
			else {
				query = "INSERT INTO Address(street, city, stateId, zipcode, countryId) VALUES (?,?,?,?,?)";
				ps = conn.prepareStatement(query);
				ps.setString(1, street);
				ps.setString(2, city);
				ps.setInt(3, stateId);
				ps.setString(4, zip);
				ps.setInt(5, countryId);
				ps.executeUpdate();
				query = "SELECT LAST_INSERT_ID()";
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				rs.next();
				addressId = rs.getInt("LAST_INSERT_ID()");
			}
			ps.close();
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return addressId;
	}
	
	
	/**
	 * 1. Method that removes every person record from the database
	 */
	public static void removeAllPersons() {
		
		Connection conn = createConnection();
		Statement statement = null;
		String query = null;
		
		try {
			statement = conn.createStatement();
			//Since Email references Person
			query = "DELETE FROM Email";
			statement.executeUpdate(query);
			//Since InvoiceProduct references Invoice
			query = "DELETE FROM InvoiceProduct";
			statement.executeUpdate(query);
			//Since Invoice references Person
			query = "DELETE FROM Invoice";
			statement.executeUpdate(query);
			//Since Customer references Person
			query = "DELETE FROM Customer";
			statement.executeUpdate(query);
			//Deletes all Person records
			query = "DELETE FROM Person";
			statement.executeUpdate(query);
			statement.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeConnection(conn);
		}
	}

	/**
	 * 2. Method to add a person record to the database with the provided data.
	 * 
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country) {
		Connection conn = createConnection();
		PreparedStatement ps = null;
		String query = null;
		
		try {
			
			//Gets addressId for address of this person
			int addressId = addAddress(street, city, state, zip, country, conn);
			//Inserts person record
			query = "INSERT INTO Person(personCode, firstName, lastName, addressId) VALUES (?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			ps.setString(2, firstName);
			ps.setString(3, lastName);
			ps.setInt(4, addressId);
			
			ps.executeUpdate();
			ps.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeConnection(conn);
		}
		
	}

	/**
	 * 3. Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * 
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		Connection conn = createConnection();
		PreparedStatement ps = null;
		String query = null;
		ResultSet rs = null;
		
		try {
			
			//Gets personId for this email's person
			query = "SELECT personId FROM Person WHERE personCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			rs = ps.executeQuery();
			int personId = 0;
			if(rs.next()) {
				personId = rs.getInt("personId");
				//Inserts email record
				query = "INSERT INTO Email(personId, emailAddress) VALUES (?,?)";
				ps = conn.prepareStatement(query);
				ps.setInt(1, personId);
				ps.setString(2, email);
				ps.executeUpdate();
			}
			else {
				System.out.println("No person with that person code exists.");
			}
			ps.close();
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeConnection(conn);
		}

	}

	/**
	 * 4. Method that removes every customer record from the database
	 */
	public static void removeAllCustomers() {
		Connection conn = createConnection();
		Statement statement = null;
		String query = null;
		
		try {
			statement = conn.createStatement();
			//Since InvoiceProduct references Invoice
			query = "DELETE FROM InvoiceProduct";
			statement.executeUpdate(query);
			//Since Invoice references Customer
			query = "DELETE FROM Invoice";
			statement.executeUpdate(query);
			//Deletes all records from Customer
			query = "DELETE FROM Customer";
			statement.executeUpdate(query);
			statement.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeConnection(conn);
		}
	}

	public static void addCustomer(String customerCode, String customerType, String primaryContactPersonCode, String name, String street, String city, String state, String zip, String country) {
		
		Connection conn = createConnection();
		PreparedStatement ps = null;
		String query = null;
		ResultSet rs = null;
		
		try {
			//Gets addressId
			int addressId = addAddress(street, city, state, zip, country, conn);
			//Gets personId
			query = "SELECT personId FROM Person WHERE personCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, primaryContactPersonCode);
			rs = ps.executeQuery();
			rs.next();
			int personId = rs.getInt("personId");
			
			//Inserts Customer record
			query = "INSERT INTO Customer(customerCode, type, personId, name, addressId) VALUES (?,?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, customerCode);
			ps.setString(2, customerType);
			ps.setInt(3, personId);
			ps.setString(4, name);
			ps.setInt(5, addressId);
			ps.executeUpdate();
		
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeConnection(conn);
		}
		
	}
	
	/**
	 * 5. Removes all product records from the database
	 */
	public static void removeAllProducts() {
		Connection conn = createConnection();
		Statement statement = null;
		String query = null;
		
		try {
			statement = conn.createStatement();
			//Since InvoiceProduct references Product
			query = "DELETE FROM InvoiceProduct";
			statement.executeUpdate(query);
			//Since Amenity references Product
			query = "DELETE FROM Amenity";
			statement.executeUpdate(query);
			//Since ParkingPass references Product
			query = "DELETE FROM ParkingPass";
			statement.executeUpdate(query);
			//Since SaleAgreement references Product
			query = "DELETE FROM SaleAgreement";
			statement.executeUpdate(query);
			//Since LeaseAgreement references Product
			query = "DELETE FROM LeaseAgreement";
			statement.executeUpdate(query);
			//Deletes all records from Product
			query = "DELETE FROM Product";
			statement.executeUpdate(query);
			statement.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeConnection(conn);
		}
		
	}
	
//	Adds a Product record to the database
	public static int addProduct(String productCode, String type, Connection conn) {
		
		String query = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int productId = 0;
		
		try {
			query = "INSERT INTO Product(productCode, type) VALUES (?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, productCode);
			ps.setString(2, type);
			ps.executeUpdate();
			
			query = "SELECT LAST_INSERT_ID()";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			rs.next();
			productId = rs.getInt("LAST_INSERT_ID()");
			
			ps.close();
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return productId;
	}
	
	

	/**
	 * 6. Adds a SaleAgreement record to the database with the provided data.
	 */
	public static void addSaleAgreement(String productCode, String dateTime, String street, String city,String state, String zip, String country,
			double totalCost, double downPayment, double monthlyPayment, int payableMonths, double interestRate) {
		
		Connection conn = createConnection();
		PreparedStatement ps = null;
		String query = null;
		
		//Gets productId
		int productId = addProduct(productCode, "S", conn);
		//Gets addressId
		int addressId = addAddress(street, city, state, zip, country, conn);
		
		try {
			
			//Inserts SaleAgreement record
			query = "INSERT INTO SaleAgreement"
					+ "(productId, dateTime, addressId, totalCost, downPayment, monthlyPayment, payableMonths, interestRate)"
					+ " VALUES(?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setInt(1, productId);
			ps.setString(2, dateTime);
			ps.setInt(3, addressId);
			ps.setDouble(4, totalCost);
			ps.setDouble(5, downPayment);
			ps.setDouble(6, monthlyPayment);
			ps.setInt(7, payableMonths);
			ps.setDouble(8, interestRate);
			ps.executeUpdate();
			
			ps.close();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeConnection(conn);
		}
		
	}

	/**
	 * 7. Adds a LeaseAgreement record to the database with the provided data.
	 */
	public static void addLeaseAgreement(String productCode, String name, String startDate, String endDate, String street, String city, String state, String zip, String country,
			double deposit, double monthlyCost) {
		
		Connection conn = createConnection();
		PreparedStatement ps = null;
		String query = null;
		
		//Gets productId
		int productId = addProduct(productCode, "L", conn);
		//Gets addressId
		int addressId = addAddress(street, city, state, zip, country, conn);
		
		try {
			
			//Inserts LeaseAgreement record
			query = "INSERT INTO LeaseAgreement(productId, startDate, endDate, addressId, customerName, deposit, monthlyCost)"
					+ " VALUES(?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setInt(1, productId);
			ps.setString(2, startDate);
			ps.setString(3, endDate);
			ps.setInt(4, addressId);
			ps.setString(5, name);
			ps.setDouble(6, deposit);
			ps.setDouble(7, monthlyCost);
			ps.executeUpdate();
			
			ps.close();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeConnection(conn);
		}
		
	}

	/**
	 * 8. Adds a ParkingPass record to the database with the provided data.
	 */
	public static void addParkingPass(String productCode, double parkingFee) {
		
		Connection conn = createConnection();
		PreparedStatement ps = null;
		String query = null;
		
		//Gets productId
		int productId = addProduct(productCode, "P", conn);
		
		try {
			
			//Inserts ParkingPass record
			query = "INSERT INTO ParkingPass(productId, parkingFee) VALUES(?,?)";
			ps = conn.prepareStatement(query);
			ps.setInt(1, productId);
			ps.setDouble(2, parkingFee);
			ps.executeUpdate();
			
			ps.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeConnection(conn);
		}
	}

	/**
	 * 9. Adds an Amenity record to the database with the provided data.
	 */
	public static void addAmenity(String productCode, String name, double cost) {
		
		Connection conn = createConnection();
		PreparedStatement ps = null;
		String query = null;
		
		//Gets produtId
		int productId = addProduct(productCode, "A", conn);
		
		try {
			
			//Inserts Amenity record
			query = "INSERT INTO Amenity(productId, name, cost) VALUES(?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setInt(1, productId);
			ps.setString(2, name);
			ps.setDouble(3, cost);
			ps.executeUpdate();
			
			ps.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeConnection(conn);
		}
	}

	/**
	 * 10. Removes all invoice records from the database
	 */
	public static void removeAllInvoices() {
		Connection conn = createConnection();
		Statement statement = null;
		String query = null;
		
		try {
			statement = conn.createStatement();
			//Since InvoiceProduct references Invoice
			query = "DELETE FROM InvoiceProduct";
			statement.executeUpdate(query);
			//Deletes all records from Invoice
			query = "DELETE FROM Invoice";
			statement.executeUpdate(query);
			statement.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeConnection(conn);
		}
	}

	/**
	 * 11. Adds an invoice record to the database with the given data.
	 */
	public static void addInvoice(String invoiceCode, String customerCode, String realtorCode, String invoiceDate) {
		
		Connection conn = createConnection();
		PreparedStatement ps = null;
		String query = null;
		ResultSet rs = null;
		
		try {
			
			//Gets personId for this realtor
			query = "SELECT personId FROM Person WHERE personCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, realtorCode);
			rs = ps.executeQuery();
			rs.next();
			int personId = rs.getInt("personId");
			
			//Gets customerId for this customer
			query = "SELECT customerId FROM Customer WHERE customerCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, customerCode);
			rs = ps.executeQuery();
			rs.next();
			int customerId = rs.getInt("customerId");
			
			rs.close();
			
			//Inserts Invoice record
			query = "INSERT INTO Invoice(invoiceCode, customerId, personId, invoiceDate) VALUES(?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, invoiceCode);
			ps.setInt(2, customerId);
			ps.setInt(3, personId);
			ps.setString(4, invoiceDate);
			ps.executeUpdate();
			
			ps.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeConnection(conn);
		}
		
	}

	/**
	 * 12. Adds a particular SaleAgreement (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given number of units
	 */

	public static void addSaleAgreementToInvoice(String invoiceCode, String productCode, int quantity) {
		
		Connection conn = createConnection();
		PreparedStatement ps = null;
		String query = null;
		ResultSet rs = null;
		
		try {
			
			//Gets invoiceId
			query = "SELECT invoiceId FROM Invoice WHERE invoiceCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, invoiceCode);
			rs = ps.executeQuery();
			rs.next();
			int invoiceId = rs.getInt("invoiceId");
			
			//Gets productId
			query = "SELECT productId FROM Product WHERE productCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, productCode);
			rs = ps.executeQuery();
			rs.next();
			int productId = rs.getInt("productId");
			
			rs.close();
			
			//Adds SaleAgreement to InvoiceProduct
			query = "INSERT INTO InvoiceProduct(invoiceId, productId, quantity) VALUES (?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setInt(1, invoiceId);
			ps.setInt(2, productId);
			ps.setInt(3, quantity);
			ps.executeUpdate();
			
			ps.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeConnection(conn);
		}
		
		
	}

	/**
	 * 13. Adds a particular LeaseAgreement (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given begin/end dates
	 */
	public static void addLeaseAgreementToInvoice(String invoiceCode, String productCode, int quantity) {
		
		Connection conn = createConnection();
		PreparedStatement ps = null;
		String query = null;
		ResultSet rs = null;
		
		try {
			
			//Gets invoiceId
			query = "SELECT invoiceId FROM Invoice WHERE invoiceCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, invoiceCode);
			rs = ps.executeQuery();
			rs.next();
			int invoiceId = rs.getInt("invoiceId");
			
			//Gets productId
			query = "SELECT productId FROM Product WHERE productCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, productCode);
			rs = ps.executeQuery();
			rs.next();
			int productId = rs.getInt("productId");
			
			rs.close();
			
			//Adds LeaseAgreement to InvoiceProduct
			query = "INSERT INTO InvoiceProduct(invoiceId, productId, quantity) VALUES (?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setInt(1, invoiceId);
			ps.setInt(2, productId);
			ps.setInt(3, quantity);
			ps.executeUpdate();
			
			ps.close();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeConnection(conn);
		}
	}

     /**
     * 14. Adds a particular ParkingPass (corresponding to <code>productCode</code> to an 
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * number of quantity.
     * NOTE: agreementCode may be null
     */
    public static void addParkingPassToInvoice(String invoiceCode, String productCode, int quantity, String agreementCode) {
    	
    	Connection conn = createConnection();
		PreparedStatement ps = null;
		String query = null;
		ResultSet rs = null;
		
		try {
			
			//Gets invoiceId
			query = "SELECT invoiceId FROM Invoice WHERE invoiceCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, invoiceCode);
			rs = ps.executeQuery();
			rs.next();
			int invoiceId = rs.getInt("invoiceId");
			
			//Gets productId
			query = "SELECT productId FROM Product WHERE productCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, productCode);
			rs = ps.executeQuery();
			rs.next();
			int productId = rs.getInt("productId");
			
			//If no agreement associated with ParkingPass
			if(agreementCode == null) {
				//Insert ParkingPass record into InvoiceProduct
				query = "INSERT INTO InvoiceProduct(invoiceId, productId, quantity) VALUES (?,?,?)";
				ps = conn.prepareStatement(query);
				ps.setInt(1, invoiceId);
				ps.setInt(2, productId);
				ps.setInt(3, quantity);
				ps.executeUpdate();
			}
			//If agreement associated with ParkingPass
			else {
				//Get productId of associated agreement
				query = "SELECT productId FROM Product WHERE productCode = ?";
				ps = conn.prepareStatement(query);
				ps.setString(1, agreementCode);
				rs = ps.executeQuery();
				rs.next();
				int agreementId = rs.getInt("productId");
				
				//Insert ParkingPass into InvoiceProduct
				query = "INSERT INTO InvoiceProduct(invoiceId, productId, quantity, agreementId) VALUES (?,?,?,?)";
				ps = conn.prepareStatement(query);
				ps.setInt(1, invoiceId);
				ps.setInt(2, productId);
				ps.setInt(3, quantity);
				ps.setInt(4, agreementId);
				ps.executeUpdate();
			}
			
			rs.close();
			ps.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeConnection(conn);
		}
    }
	
    /**
     * 15. Adds a particular amenity (corresponding to <code>productCode</code> to an 
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * number of quantity. 
     */
    public static void addAmenityToInvoice(String invoiceCode, String productCode, int quantity) {
    	Connection conn = createConnection();
		PreparedStatement ps = null;
		String query = null;
		ResultSet rs = null;
		
		try {
			
			//Gets invoiceId
			query = "SELECT invoiceId FROM Invoice WHERE invoiceCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, invoiceCode);
			rs = ps.executeQuery();
			rs.next();
			int invoiceId = rs.getInt("invoiceId");
			
			//Gets productId
			query = "SELECT productId FROM Product WHERE productCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, productCode);
			rs = ps.executeQuery();
			rs.next();
			int productId = rs.getInt("productId");
			
			rs.close();
			
			//Inserts Amenity into InvoiceProduct
			query = "INSERT INTO InvoiceProduct(invoiceId, productId, quantity) VALUES (?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setInt(1, invoiceId);
			ps.setInt(2, productId);
			ps.setInt(3, quantity);
			ps.executeUpdate();
			
			ps.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeConnection(conn);
		}
    }

}
