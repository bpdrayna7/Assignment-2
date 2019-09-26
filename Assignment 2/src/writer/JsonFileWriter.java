package writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import org.joda.time.DateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import entities.SaleAgreement;


public class JsonFileWriter {
		
	public <T> void jsonConverter(ArrayList<T> objects, String fileName) {
		
		//Format created to make the json file look nicer
		Gson defaultGson = new GsonBuilder().registerTypeAdapter(DateTime.class, 
				defaultSerializer).setPrettyPrinting().create();
		Gson dateTimeGson = new GsonBuilder().registerTypeAdapter(DateTime.class, 
				dateTimeSerializer).setPrettyPrinting().create();
		
		File jsonOutput = new File(fileName);
		
		PrintWriter jsonPrintWriter = null;
		try {
			jsonPrintWriter = new PrintWriter(jsonOutput);
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//Loops through the ArrayList of objects and outputs them to the json file 
		for(T object:objects) {
			String objectOutput;
			if(object instanceof SaleAgreement) {
				objectOutput = dateTimeGson.toJson(object);
			}
			else {
				objectOutput = defaultGson.toJson(object);
			}
			jsonPrintWriter.write(objectOutput + "\n");
		}
		jsonPrintWriter.close();		
	}
	
	//originally we used DateTime objects to store attributes in the LeaseAgreement and SaleAgreement classes
	//these methods were to format those, no longer needed
	private static JsonSerializer<DateTime> defaultSerializer = new JsonSerializer<DateTime>() {
		@Override
		public JsonElement serialize(DateTime dateTime, Type typeOfSrc, 
			JsonSerializationContext context) {
			String dtString = dateTime.toString("yyyy-MM-dd");
		    return new JsonPrimitive(dtString);  
		}
	};
	
	private static JsonSerializer<DateTime> dateTimeSerializer = new JsonSerializer<DateTime>() {
		@Override
		public JsonElement serialize(DateTime dateTime, Type typeOfSrc, 
			JsonSerializationContext context) {
			String dtString = dateTime.toString("yyyy-MM-dd HH:mm");
		    return new JsonPrimitive(dtString);  
		}
	};
}