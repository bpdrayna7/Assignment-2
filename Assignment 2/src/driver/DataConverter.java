package driver;

import java.util.ArrayList;

import entities.Person;
import reader.FlatFileReader;
import writer.XMLFileWriter;

public class DataConverter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		//Creates FlatFileReader object
		FlatFileReader ffr = new FlatFileReader();
		
		
		ArrayList<Person> people = ffr.readPeople();
		//ArrayList<Customer> customers = ffr.readCustomers();
		//ArrayList<Product> products = ffr.readProducts();
		
		//Writes each ArrayList into a separate XML file
		XMLFileWriter xmlWriter = new XMLFileWriter();
		xmlWriter.xmlConverter(people, "data/Persons.xml", "person");
//		xmlWriter.xmlConverter(customers, "data/Customers.xml", "customer");
//		xmlWriter.xmlConverter(products, "data/Products.xml", "product");
		
	}

}
