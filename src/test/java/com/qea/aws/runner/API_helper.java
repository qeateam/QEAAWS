package com.qea.aws.runner;

import java.io.FileNotFoundException;
import java.util.Scanner;

import com.google.gson.JsonObject;
import junit.framework.Assert;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import java.io.File;
import java.io.FileNotFoundException;
import com.jayway.restassured.response.Response;
import static com.jayway.restassured.RestAssured.given;



public class API_helper {

	  static Response getResponse;
	  static Response postResponse;
	  static String baseline;
	  
	  /**
	   * Get the response from GET service
	   * @param requestGetURL - URL for the POST service
	   * @return  - response of the POST service
	  */
	  public static Response getResponse(String requestGetURL){
	  	getResponse = given().header("Content-Type", "application/json").when().get(requestGetURL);
	  	given().header("Content-Type", "application/json").when().get(requestGetURL);
		return getResponse;
	  }
	  
	 
	/**
	   * Get the response from POST service
	   * @param requestGetURL - URL for the GET service
	   * @return  - response of the GET service
	  */
	  public static Response postResponse(String requestGetURL){
		try
		{
			String Requestvalue = new Scanner(new File(
					      "src/test/resources/testData/Request.json"))
						  .useDelimiter("\\A").next();
			postResponse = given().header("Content-Type", "application/json")
		  			             .body(Requestvalue)
		  			             .when()
								 .post(requestGetURL);
			return postResponse;
		}catch(FileNotFoundException e){
			return postResponse;
		}
		
	  }
	  
	  /**
	   * Get the response from POST service with Parameters
	   * @param requestGetURL - URL for the GET service
	   * @return  - response of the GET service
	  */
	  public static Response postResponsewithParameters(String requestURLValue,String cardHolderIDValue,String accountNumberValue,String btOfferValue){
		try
		{
		  String Requestvalue = new Scanner(new File(
				      "src/test/resources/testData/Request.json"))
					  .useDelimiter("\\A").next();
		JSONObject baserequestJSON = new JSONObject(Requestvalue);
		baserequestJSON.put("cardHolderID",cardHolderIDValue);
		postResponse = given().header("Content-Type", "application/json")
	  			             .body(baserequestJSON.toString())
	  			             .when()
							 .post(requestURLValue);
		return postResponse;
		}catch(FileNotFoundException e)
		{
			return postResponse;
		}
	  }
	  

	   /**
	   * Get the response code from GET service
	   * @param restgetResponse - Response for the GET service
	   * @return  - response code of the GET service
	  */
	  public static int getResponseCode(Response restgetResponse){
	  	return restgetResponse.getStatusCode();
	  }
	  
	
	   /**
	   * Get the response code from POST service
	   * @param restgetResponse - Response for the POST service
	   * @return  - response code of the GET service
	  */
	  public static int postResponseCode(Response restpostResponse){
	  	int responsecode = restpostResponse.getStatusCode();
	  	return responsecode;
	  	//String responsecontent = restpostResponse.getBody().asString();
	  	//boolean responsecodestatus = responsecontent.contains("SUCCESS");
	  	
	  }

  
	  /**
	   * Get the baseline json request
	   * @param jsonFileName - file name for json request
	   * @return  - json request as a string
	  */
	  public static String getBaselineResponse(String jsonFileName){
	  
			try
			{
			 baseline = new Scanner(new File("src/test/resources/JSON/"+jsonFileName+".json"))
									.useDelimiter("\\A").next();
		    	 return baseline;
			}catch(FileNotFoundException e)
			{
				return baseline;
			}
			
	  }
	  
	  /**
	   * 
	   * Compare baseline request with  response
	   * 
	  */
	  public static void comparereGetResponseContent(){
	  
			JSONObject baseJSON = new JSONObject(baseline);
			JSONObject respJSON = new JSONObject(getResponse.asString());
			
			JSONAssert.assertEquals(baseJSON,respJSON,false);
			
	  }
	  
	  /**
	   * 
	   * Compare baseline request with  response
	   * 
	  */
	  public static void comparerePostResponseContent(){
	  
			JSONObject baseJSON = new JSONObject(baseline);
			JSONObject respJSON = new JSONObject(postResponse.asString());
			
			JSONAssert.assertEquals(baseJSON,respJSON,false);
			
	  }
	  
	  /**
	   * 
	   * Validate the response code for get service
	   * 
	  */
	  public static void validategetserviceresponsecode(){
	  Assert.assertEquals("Validate get  service response code",getResponseCode(getResponse),200);
	  }
	  
	  /**
	   * 
	   * Validate the response code for get service
	   * 
	  */
	  public static void validatepostserviceresponsecode(){
	  Assert.assertEquals("Validate get  service response code",getResponseCode(postResponse),200);
	  }
	  
	  //validate webservice with authentication
	  //Response response = given().auth().preemptive().basic(Userid,Password).header("Content-Type","application.json").when().get(appURL);
}
