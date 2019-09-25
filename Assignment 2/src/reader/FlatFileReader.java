package reader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import entities.Address;
import entities.Amenity;
import entities.Customer;
import entities.LeaseAgreement;
import entities.ParkingPass;
import entities.Person;
import entities.Product;
import entities.SaleAgreement;

public class FlatFileReader {
	public ArrayList<Person> readPeople(){
		
		Scanner scan = null;
		try {
			scan = new Scanner(new FileReader("data/Persons.dat"));
			scan.nextLine();
			
			ArrayList<Person> people = new ArrayList<Person>();
			
			while(scan.hasNext()) {
				String line = scan.nextLine();
				String[] attributes = line.split(";");
				
				String personCode = attributes[0];
				String[] name = attributes[1].split(",");
				String firstName = name[1];
				String lastName = name[0];
				String[] ad = attributes[2].split(",");
				
				Address address = new Address(ad[0], ad[1], ad[2], ad[3], ad[4]);
				
				
				ArrayList<String> emails = new ArrayList<>();
				if(attributes.length == 4) {
					String[] emailAddresses = attributes[3].split(",");
					for(String e : emailAddresses) {
						emails.add(e);
					}
				}
				
				if(emails.isEmpty()) {
					Person p = new Person(personCode, firstName, lastName, address);
					people.add(p);
				}
				else {
					Person p = new Person(personCode, firstName, lastName, address, emails);
					people.add(p);
				}
				
			}
			scan.close();
			
			return people;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Customer> readCustomers(ArrayList<Person> people){
		Scanner scan = null;
		try {
			scan = new Scanner(new FileReader("data/Customers.dat"));
			scan.nextLine();
		
			ArrayList<Customer> customers = new ArrayList<Customer>();
		
			while(scan.hasNext()) {
				String line = scan.nextLine();
				String[] attributes = line.split(";");
				
				String customerCode = attributes[0];
				String type = attributes[1];
				Person primaryContact = null;
				for(Person p:people) {
					if(p.getPersonCode().equals(attributes[2])) {
						primaryContact = p;
						break;
					}
				}
				String name = attributes[3];
				String[] ad = attributes[4].split(",");
				Address address = new Address(ad[0], ad[1], ad[2], ad[3], ad[4]);

				Customer c = new Customer(customerCode, type, primaryContact, name, address);
				customers.add(c);
			}
			scan.close();
			return customers;
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<Product> readProducts(ArrayList<Customer> customers) {
		final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");
		final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("HH:mm");
		Scanner scan = null;
		try {
			scan = new Scanner(new FileReader("data/Products.dat"));
			scan.nextLine();
		
			ArrayList<Product> products = new ArrayList<Product>();
		
			while(scan.hasNext()) {
				String line = scan.nextLine();
				String[] attributes = line.split(";");
				String productCode = attributes[0];
				String productType = attributes[1];
				Product p = null;
				String[] ad = null;
				Address address = null;
				switch(productType) {
					case "L":
						DateTime startDate = DATE_FORMATTER.parseDateTime(attributes[2]);
						DateTime endDate = DATE_FORMATTER.parseDateTime(attributes[3]);
						ad = attributes[4].split(",");
						address = new Address(ad[0], ad[1], ad[2], ad[3], ad[4]);
						Customer customerName = null;
						for(Customer c:customers) {
							if(c.getName().equals(attributes[5])) {
								customerName = c;
								break;
							}
						}
						double deposit = Double.parseDouble(attributes[6]);
						double monthlyCost = Double.parseDouble(attributes[7]);
						p = new LeaseAgreement(productCode, productType, startDate, endDate, 
								address, customerName, deposit, monthlyCost);
						break;
					case "S":
						DateTime dateTime = DATE_TIME_FORMATTER.parseDateTime(attributes[2]);
						ad = attributes[3].split(",");
						address = new Address(ad[0], ad[1], ad[2], ad[3], ad[4]);
						double totalCost = Double.parseDouble(attributes[4]);
						double downPayment = Double.parseDouble(attributes[5]);
						double monthlyPayment = Double.parseDouble(attributes[6]);
						int payableMonths = Integer.parseInt(attributes[7]);
						double interestRate = Double.parseDouble(attributes[8]);
						p = new SaleAgreement(productCode, productType, dateTime, address, totalCost,
								downPayment, monthlyPayment, payableMonths, interestRate);
						break;
					case "P":
						double parkingFee = Double.parseDouble(attributes[2]);
						p = new ParkingPass(productCode, productType, parkingFee);
						break;
					case "A":
						String name = attributes[2];
						double cost = Double.parseDouble(attributes[3]);
						p = new Amenity(productCode, productType, name, cost);
						break;
					default:
						break;
				}
				products.add(p);
			}
			scan.close();
			return products;
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}