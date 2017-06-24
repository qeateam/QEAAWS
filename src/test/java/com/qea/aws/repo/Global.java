package com.qea.aws.repo;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.xml.stream.XMLStreamException;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.xerces.util.URI.MalformedURIException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.qea.aws.utils.GlobalObjects;
import com.qea.aws.utils.InvokeBrowser.browsers;
import com.qea.aws.utils.ReadConfigProps;

import io.appium.java_client.AppiumDriver;

public class Global implements GlobalObjects{
	
	public WebElement element;
	public RemoteWebDriver driver;
	//public AppiumDriver appiumDriver;
	public WebElement webelement;
	private List<WebElement> lsElements;
	ReadConfigProps readConfigProperties= new ReadConfigProps();
	
	
	//Applicable locator types.
	public enum locator {
		ID, XPATH
	}
	
	/**
	 * Find elements based on the locator reference and type.
	 * @param locatorReference
	 * @param locatorType
	 * @return
	 */
	public WebElement findElementType(String locatorReference, locator locatorType){
		
		element = null;
		WebDriverWait wait = new WebDriverWait(driver, 90);
		
		switch (locatorType) {
		
		case ID:
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorReference)));
			element = driver.findElement(By.id(locatorReference));
			break;
			
		case XPATH:
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorReference)));
			element = driver.findElement(By.xpath(locatorReference));
			break;
			
		default:
			throw new IllegalArgumentException("Invalid selection method specified !!!");	
		}	
		return element;
	
	}
	
	/**
	 * This will return a list of elements based on locator type and reference.
	 * @param locatorReference
	 * @param locatorType
	 * @return
	 */
	public List<WebElement> findElements(String locatorReference, locator locatorType){
		switch (locatorType) {
		
		case ID:
			lsElements = driver.findElements(By.id(locatorReference));
			break;
			
		case XPATH:
			lsElements = driver.findElements(By.xpath(locatorReference));
			break;
			
		default:
			throw new IllegalArgumentException("Invalid selection method specified !!!");
			
		}		
		return lsElements;
		
	}
	
	
	public String[] genGetLocator(String field, String screenName) throws XMLStreamException, FileNotFoundException {
		
		String[] objectPropertyArray = new String[2];
		String objectProperty;
		
		try {
			//Call object map parser
			objectProperty = objParser.objectParser(field, screenName);
			//Locator Type
			objectPropertyArray[0] = objectProperty.substring(0, (objectProperty.indexOf("="))).toUpperCase();
			//Locator Value
			objectPropertyArray[1] = objectProperty.substring((objectProperty.indexOf("=")+1), (objectProperty.length()));
		}catch(Exception e){
			throw e;
		}
		
		return objectPropertyArray;
		
	}
	
	public void launchApp() {
		DOMConfigurator.configure("log4j.xml");
		readConfigProperties.retrieve_configProperties();
	/*	invokeBrowser(browsers.valueOf(BROWSER.toUpperCase()));*/
		try {
			System.out.println("TEST>>>>>>>>>>>>>>>>>>>"+objReadConfigProps.BROWSER);
			objInvokeBrowser.invokeBrowser(browsers.valueOf(objReadConfigProps.BROWSER.toUpperCase()));
		} catch (MalformedURIException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	// segregate element into its locator and locator type and then click the element onto the app.
	public boolean genericClick(String element, String screen, String userStoryName) throws Exception {
		try{
			String[] objectPropertyArray = genGetLocator(element, screen);
			System.out.println("Object property element 1 : "+ objectPropertyArray[0]);
			System.out.println("Object property element 2: "+ objectPropertyArray[1]);
			
			webelement= findElementType(objectPropertyArray[1],locator.valueOf(objectPropertyArray[0]));
			webelement.click();
			Thread.sleep(5000);
		}catch(Exception e){
			throw e;
		}
		return false;
	}

	public boolean genericScroll(String element, String screen, String userStoryName) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * This method will enter values into an element on the app/ webapp.
	 */
	public boolean enterElement(String columnName, String field, String screenName, String userStoryName) throws Exception {
		try{
			String[] objectPropertyArray= new String[2];
			objectPropertyArray=genGetLocator(field, screenName);
			
			webelement=findElementType(objectPropertyArray[1], locator.valueOf(objectPropertyArray[0].toString().toUpperCase()));
			webelement.sendKeys(columnName);			
		}catch(Exception e){
			throw e;
		}
		return false;
	}

	public boolean validateDataFromJson(String detail, String userStoryName) {
	
		return false;
	}

	public boolean validateDataFromDB(String field, String database, String dataToBeReferred, String userStoryName) {
		
		return false;
	}

	public boolean enterDate(String dataToBeReferred, String date, String keyboard) {
		
		return false;
	}

	public boolean genIsDisplayedWebElement(String field, String screenName) throws FileNotFoundException, XMLStreamException {
		boolean isDisplayed = false;
		try{
			if(genFindWebElements(field, screenName).size()!=0){
				isDisplayed= true;
			}else{
				isDisplayed= false;
			}
		}catch(NoSuchElementException e){
			throw e;			
		}
		return isDisplayed;
	}

	private List<WebElement> genFindWebElements(String field, String screenName) throws FileNotFoundException, XMLStreamException {
		List<WebElement> isElements;
		String[] objectPropertyArray= genGetLocator(field, screenName);
		isElements= findElements(objectPropertyArray[1],locator.valueOf(objectPropertyArray[0].toUpperCase()));
		return isElements;
}
}
