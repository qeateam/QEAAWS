package com.qea.aws.tests;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.qea.aws.utils.GlobalObjects;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class StepDefinition implements GlobalObjects {

	/* Added by Shalini on 17th-June-17 */
	public Scenario scenario;
	public static String strCurrentTagName; 
	public static Set<String> beforePopUp;
	public static String strScenarioName;
	public String dataref;
	public static int globalAccessbilityCOunter=1;
	boolean found= false;
	public static boolean flag;
	public String deviceName=System.getProperty("config.setting.DeviceId");
	public static String userStoryName=System.getProperty("config.setting.Flow");
	
	@Before
	public void init(Scenario scenario){
	this.scenario=scenario;
	Collection<String> tags= scenario.getSourceTagNames();
	for(String tag: tags){
		strCurrentTagName=tag;
		strScenarioName= scenario.getName();
		System.out.println("tag: "+ tag);
		System.out.println("strCurrentTagName: "+ strCurrentTagName);
		}
	}
	
	/*StepDef to launch Application*/
	@Given("^I launch app$")
	public void launchApp() throws Throwable{
			try{
				gen.launchApp();}
			catch(Exception e){
				e.printStackTrace();
				System.out.println("Failed to launch app");
			}
	}
	
	/* Clicking an element on app screen */
	@When("^I click \"(.*?)\" on \"(.*?)\" screen$")
	public void clickElement(String element, String screenName) throws Throwable{
		found = gen.genericClick(element,screenName,userStoryName);
		if(found){
			report.reportStep(userStoryName.substring(1),"User is able to click "+element+" on "+screenName+" screen", true, true);
		}else{
			report.reportStep(userStoryName.substring(1),"User is unable to click "+element+" on "+screenName+" screen", false, true);	
		}
		Assert.assertTrue("Unable to click element :"+element, found);
	}

	
	/* Scrolling an element on app screen */
	@Then("^I scroll \"(.*?)\" element on \"(.*?)\" screen$")
	public void scrollElement(String element, String screenName) throws Throwable{
		found = gen.genericScroll(element,screenName,userStoryName);
		if(found){
			report.reportStep(userStoryName.substring(1),"User is able to scroll "+element+" on "+screenName+" screen", true, true);
		}else{
			report.reportStep(userStoryName.substring(1),"User is unable to scroll "+element+" on "+screenName+" screen", false, true);	
		}
		Assert.assertTrue("Unable to scroll element: "+element, found);
	}
	
	
	/* Enter details in a field on app screen */
	@Then("^I enter \"(.*?)\" details in \"(.*?)\" on \"(.*?)\" screen$")
	public void enterElement(String detail, String field, String screenName) throws Throwable{
		found = gen.enterElement(detail,field,screenName,userStoryName);
		if(found){
			report.reportStep(userStoryName.substring(1),"User is able to enter following "+detail+" on "+screenName+" screen", true, true);
		}else{
			report.reportStep(userStoryName.substring(1),"User is unable to enter following "+detail+" on "+screenName+" screen", false, true);	
		}
		Assert.assertTrue("Unable to enter value :"+detail+ "on the field "+ field, found);
	}
	
	
	/* Verify Elements are present on the screen */
	@Then("^I verify elements on \"(.*?)\" screen$")
	public void verifyElementsPresent(String screenName) throws Throwable{
		gen.waitForScreenAvailable();
		Map<Integer, String> objectProp= new HashMap<Integer, String>();
		objectProp=obj.verifyUiElements(deviceName,"screen",screenName,"element");
		for(int key: objectProp.keySet()){
			System.out.println("Object property is: "+ objectProp.get(key));
			found= gen.verifyElements(objectProp.get(key));
		}
		if(found){
			report.reportStep(userStoryName.substring(1),"User is able to verify all the elements" +screenName+ "screen", true, true);
		}else{
			report.reportStep(userStoryName.substring(1),"User is unable to verify all the elements" +screenName+ "screen", false, true);	
		}
		Assert.assertTrue("Unable to verify elements on "+ screenName, found);
	}
	
	
	
	
}
