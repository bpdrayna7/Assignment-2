package writers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dataContainers.Person;

public class JsonWriter {
	public void JsonConverter(ArrayList<Person> persons) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		File jsonOutput = new File("data/Persons.json");
		
		PrintWriter jsonPrintWriter = null;
		try {
			jsonPrintWriter = new PrintWriter(jsonOutput);
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for(Person p:persons) {
			String personOutput = gson.toJson(p);
			jsonPrintWriter.write(personOutput + "\n");
		}
		jsonPrintWriter.close();
	}
}