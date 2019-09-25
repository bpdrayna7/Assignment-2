package driver;

import java.util.ArrayList;

import entities.Customer;
import entities.Person;
import entities.Product;
import reader.FlatFileReader;
import writer.JsonFileWriter;
import writer.XMLFileWriter;

public class DataConverter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		//Creates FlatFileReader object
		FlatFileReader ffr = new FlatFileReader();
		
		
		ArrayList<Person> people = ffr.readPeople();
		ArrayList<Customer> customers = ffr.readCustomers(people);
		ArrayList<Product> products = ffr.readProducts(customers);
		
		//Writes each ArrayList into a separate XML file
//		XMLFileWriter xmlWriter = new XMLFileWriter();
//		xmlWriter.xmlConverter(people, "data/Persons.xml", "person", Person.class);
//		xmlWriter.xmlConverter(customers, "data/Customers.xml", "customer", Customer.class);
//		xmlWriter.xmlConverter(products, "data/Products.xml", "product", Product.class);
		
		//Writes each ArrayList into a separate Json file
		JsonFileWriter jsonWriter = new JsonFileWriter();
		jsonWriter.jsonConverter(people, "data/Persons.json");
		jsonWriter.jsonConverter(customers, "data/Customers.json");
		jsonWriter.jsonConverter(products, "data/Products.json");		
	}
}