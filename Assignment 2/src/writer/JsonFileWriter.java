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