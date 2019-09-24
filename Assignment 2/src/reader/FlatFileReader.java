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
			scan = new Scanner(new FileReader("Persons.dat"));
			scan.nextLine();
			
			ArrayList<Person> people = new ArrayList<Person>();
			
			while(scan.hasNext()) {
				String line = scan.nextLine();
				String[] attributes = line.split(";");
				
				String personCode = attributes[0];
				String firstName = attributes[1].split(", ")[0];
				String lastName = attributes[1].split("; ")[1];
				String[] ad = attributes[2].split(",");
				
				Address address = new Address(ad[0], ad[1], ad[2], ad[3], ad[4]);
				Person p = new Person(personCode, firstName, lastName, address);
				
				if(attributes.length == 4) {
					String[] emails = attributes[3].split(", ");
					for(String e : emails) {
						p.addEmail(e);
					}
				}
				people.add(p);
				
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
