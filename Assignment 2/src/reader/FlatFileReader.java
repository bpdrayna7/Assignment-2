package reader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import entities.Address;
import entities.Customer;
import entities.Person;
import entities.Product;

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
	
	
//	public ArrayList<Customer> readCustomers(){
//		
//	}
//	
//	public ArrayList<Product> readProducts(){
//		
//	}
	
	
}
