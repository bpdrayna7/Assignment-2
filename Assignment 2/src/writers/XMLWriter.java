package writers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;

import dataContainers.Person;

public class XMLWriter {

	public void xmlConverter(ArrayList<Person> people) {
		
		XStream xstream = new XStream();
		File xmlFile = new File("data/Persons.xml");
		PrintWriter xmlPrinter = null;
		
		try {
			xmlPrinter = new PrintWriter(xmlFile);
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		xstream.alias("person", Person.class);
		
		xmlPrinter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
		for(Person p : people) {
			xmlPrinter.write(xstream.toXML(p));
		}
		xmlPrinter.close();
		
	}
}
