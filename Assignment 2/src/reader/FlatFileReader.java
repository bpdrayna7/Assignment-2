package reader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import org.joda.time.DateTime;

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

public class FlatFileReader {

	public ArrayList<Invoice> readInvoice(){
		
		ArrayList<Invoice> invoices = new ArrayList<Invoice>();
		Scanner scanInvoice = null;
		
		try {
		
		scanInvoice = new Scanner(new FileReader("data/Invoices.dat"));
		scanInvoice.nextLine();
		
		while(scanInvoice.hasNextLine()) {
			String line = scanInvoice.nextLine();
			String[] attributes = line.split(";");
			
			String invoiceCode = attributes[0];
			Customer customer = readCustomer(attributes[1]);
			Person realtor = readPerson(attributes[2]);
			DateTime invoiceDate = new DateTime(attributes[3]);
			ArrayList<Product> products = readProducts(attributes, customer);
			
			invoices.add(new Invoice(invoiceCode, invoiceDate, customer, realtor, products));
			
		}
		
		scanInvoice.close();
		return invoices;
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	
	public Customer readCustomer(String customerCode) {
		
		Scanner scanCustomer = null;
		
		try {
			
			scanCustomer = new Scanner(new FileReader("data/Customers.dat"));
			scanCustomer.nextLine();
			
			while(scanCustomer.hasNextLine()) {
				String line = scanCustomer.nextLine();
				String[] attributes = line.split(";");
				
				if(attributes[0].equals(customerCode)){
					
					String code = attributes[0];
					String type = attributes[1];
					Person primaryContact = readPerson(attributes[2]);
					String name = attributes[3];
					
					String[] addressParts = attributes[4].split(",");
					Address address = new Address(addressParts[0], addressParts[1], addressParts[2], addressParts[3], addressParts[4]);
					
					Customer customer = null;
					
					switch(type) {
					case "G":
						customer = new General(code, type, primaryContact, name, address);
						break;
					case "L":
						customer = new LowIncome(code, type, primaryContact, name, address);
					}
					
					scanCustomer.close();
					return customer;
					
				}
				
			}
			
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
		scanCustomer.close();
		return null;
	}
	
	
	public Person readPerson(String personCode) {
		
		Scanner scanPerson = null;
		
		try {
			
			scanPerson = new Scanner(new FileReader("data/Persons.dat"));
			scanPerson.nextLine();
			
			while(scanPerson.hasNextLine()) {
				String line = scanPerson.nextLine();
				String[] attributes = line.split(";");
				
				if(attributes[0].equals(personCode)) {
					scanPerson.close();
					
					String code = attributes[0];
					String[] nameParts = attributes[1].split(",");
					String firstName = nameParts[1];
					String lastName = nameParts[0];
					
					String[] addressParts = attributes[2].split(",");
					Address address = new Address(addressParts[0], addressParts[1], addressParts[2], addressParts[3], addressParts[4]);
					
					ArrayList<String> emails = new ArrayList<String>();
					if(attributes.length == 4) {
						String[] emailList = attributes[3].split(",");
						for(String email : emailList) {
							emails.add(email);
						}
						return new Person(code, firstName, lastName, address, emails);
					}
					else {
						return new Person(code, firstName, lastName, address);
					}	
				}
			}	
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
		scanPerson.close();
		return null;
	}
	
	
	
	
	public ArrayList<Product> readProducts(String[] attributes, Customer customer){
		
		Scanner scanProducts = null;
		ArrayList<Product> productList = new ArrayList<Product>();
		
		try {
			
			String[] products = attributes[4].split(",");
			
			
			
			for(String product : products) {
				scanProducts = new Scanner(new FileReader("data/Products.dat"));
				scanProducts.nextLine();
				
				String[] productParts = product.split(":");
				
				
				while(scanProducts.hasNextLine()) {
					
					String line = scanProducts.nextLine();
					String[] elements = line.split(";");
					String[] addressParts;
					Address address;
					
					if(elements[0].equals(productParts[0])) {
						switch(elements[1]) {
						case "A":
							Amenity amenity = new Amenity(elements[0], elements[1], Integer.parseInt(productParts[1]), elements[2], Double.parseDouble(elements[3]));
							productList.add(amenity);
							break;
						case "S":
							addressParts = elements[3].split(",");
							address = new Address(addressParts[0], addressParts[1], addressParts[2], addressParts[3], addressParts[4]);
							
							SaleAgreement saleAgreement = new SaleAgreement(elements[0], elements[1], Integer.parseInt(productParts[1]), elements[2], address, 
									Double.parseDouble(elements[4]), Double.parseDouble(elements[5]), Double.parseDouble(elements[6]),
									Integer.parseInt(elements[7]), Double.parseDouble(elements[8]));
							productList.add(saleAgreement);
							break;
						case "L":
							addressParts = elements[4].split(",");
							address = new Address(addressParts[0], addressParts[1], addressParts[2], addressParts[3], addressParts[4]);
									
							LeaseAgreement leaseAgreement = new LeaseAgreement(elements[0], elements[1], Integer.parseInt(productParts[1]),
									elements[2], elements[3], address, customer, Double.parseDouble(elements[6]), Double.parseDouble(elements[7]));
							
							productList.add(leaseAgreement);
							break;
						case "P":
							ParkingPass parkingPass = null;
							if(productParts.length == 3) {
								for(int i = 0; i < productList.size(); i++) {
									if(productList.get(i).getProductCode().equals(productParts[2])) {
										parkingPass = new ParkingPass(elements[0], elements[1], Integer.parseInt(productParts[1]), 
												Double.parseDouble(elements[2]), (Agreement)productList.get(i));
									}
								}
							}
							else {
								parkingPass = new ParkingPass(elements[0], elements[1], Integer.parseInt(productParts[1]), 
										Double.parseDouble(elements[2]));
							}
							productList.add(parkingPass);
							break;
						}
						
					}
					
				}
			}
			
			scanProducts.close();
			return productList;
			
			
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
}
