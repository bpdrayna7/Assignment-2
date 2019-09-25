package writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;

import entities.Amenity;
import entities.Customer;
import entities.LeaseAgreement;
import entities.ParkingPass;
import entities.Person;
import entities.SaleAgreement;

public class XMLFileWriter {

	public <T> void xmlConverter(ArrayList<T> objects, String fileName, String alias, Class<T> c) {
		
		XStream xstream = new XStream();
		xstream.alias(alias, objects.getClass());
		File xmlFile = new File(fileName);
		PrintWriter xmlPrinter = null;
		
		try {
			xmlPrinter = new PrintWriter(xmlFile);
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		xstream.alias("Person", Person.class);
		xstream.alias("Customer", Customer.class);
		xstream.alias("Lease Agreement", LeaseAgreement.class);
		xstream.alias("Sale Agreement", SaleAgreement.class);
		xstream.alias("Parking Pass", ParkingPass.class);
		xstream.alias("Amenity", Amenity.class);
		
		xmlPrinter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
		xmlPrinter.write(xstream.toXML(objects));
		xmlPrinter.close();
	}
}
