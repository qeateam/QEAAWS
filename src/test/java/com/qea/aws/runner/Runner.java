package com.qea.aws.runner;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;

import com.qea.aws.utils.GlobalObjects;

public class Runner implements GlobalObjects {
	
	String strListOfUserStory = null;
	static int intListUStorySize;
	
	public static void main (String [] args){
		
		Runner objRunner = new Runner();
		
		try{
			String strText = "";
			int intTestIDCount;
			String[] strArrUserStoryData;
			List<String> strListTestID = new ArrayList<String>();
			
			//get the list of TestIDs
			strListTestID = objParser.getTestIDNames();
			//get number of TestIDs
			intTestIDCount = strListTestID.size();
			
			//Iterate over the number of test ids that needs to be executed
			for(int intCounter=0; intCounter < intTestIDCount; intCounter++) {
				//get the list of BDDs and Data Refs for the Test ID
				strArrUserStoryData = objParser.getUserStoryName(strListTestID.get(intCounter));
				
				//get the list of the BDDs to be executed
				objRunner.strListOfUserStory = strText.concat(strArrUserStoryData[0].substring(1));
				objStepDefinition.dataref = strArrUserStoryData[1].trim();
				
				//get number of BDDs to be executed
				intListUStorySize = strArrUserStoryData.length;
				
				//get the project POM location
				InvocationRequest request = new DefaultInvocationRequest();
				request.setPomFile(new File("pom.xml"));
				Invoker invoker = new DefaultInvoker();
				request.setGoals(Arrays.asList("install"));
				//For Debugging
				//request.setGoals(Arrays.asList("install -Dmaven.surefire.debug"));
				Properties props = new Properties();
				props.setProperty("cucumber.options", "--tags " +objRunner.strListOfUserStory+", --format json:target/cucumber"+intCounter+".json");
				
				//Set DataRef property dynamically
				props.setProperty("data", strArrUserStoryData[1].trim());
				request.setProperties(props);
				
				//get maven home location
				invoker.setMavenHome(new File(System.getenv("MAVEN_HOME")));
				invoker.execute(request);
				
				
			}//for end
			
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
