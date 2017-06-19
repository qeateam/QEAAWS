package com.qea.aws.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;


public class JsonDataReader{
	
	/*
	 * This method is to beread data from data.json
	 * 
	 * @param Datatobereffred
	 * @param fieldName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * */
		
	
	public String getValueFromJSONFile(String Datatobereffred, String fieldName)
		throws FileNotFoundException, IOException{
		
		String strValue ="";
		try (InputStream is= new FileInputStream("./src/test/resources/testData/data.json");
				JsonReader objReader = Json.createReader(is)){
			
			JsonObject objJson = objReader.readObject();
			JsonArray results = objJson.getJsonArray(Datatobereffred);
			for(JsonObject result: results.getValuesAs(JsonObject.class))
			{
				strValue=strValue+result.getString(fieldName,"");
				
			}
		}
			return strValue;
		}
}