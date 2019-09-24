package writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;



public class XMLFileWriter {

	public <T> void xmlConverter(ArrayList<T> objects, String fileName, String alias) {
		
		XStream xstream = new XStream();
		File xmlFile = new File(fileName);
		PrintWriter xmlPrinter = null;
		
		try {
			xmlPrinter = new PrintWriter(xmlFile);
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		xstream.alias(alias, objects.getClass());
		
		xmlPrinter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
		for(T obj : objects) {
			xmlPrinter.write(xstream.toXML(obj));
		}
		xmlPrinter.close();
		
	}
}
