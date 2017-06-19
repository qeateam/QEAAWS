package com.qea.aws.repo;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import com.qea.aws.utils.GlobalObjects;

public class Global implements GlobalObjects{
	
	public WebElement element;
	//public RemoteWebDriver driver;
	public AppiumDriver appiumDriver;
	private List<WebElement> lsElements;
	
	
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
		WebDriverWait wait = new WebDriverWait(appiumDriver, 90);
		
		switch (locatorType) {
		
		case ID:
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorReference)));
			element = appiumDriver.findElement(By.id(locatorReference));
			break;
			
		case XPATH:
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorReference)));
			element = appiumDriver.findElement(By.xpath(locatorReference));
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
			lsElements = appiumDriver.findElements(By.id(locatorReference));
			break;
			
		case XPATH:
			lsElements = appiumDriver.findElements(By.xpath(locatorReference));
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
		// TODO Auto-generated method stub
		
	}

	public boolean genericClick(String element, String screen, String userStoryName) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean genericScroll(String element, String screen, String userStoryName) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean enterElement(String columnName, String field, String screenName, String userStoryName) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean verifyElements(String detail) {
		// TODO Auto-generated method stub
		return false;
	}

	public void waitForScreenAvailable() {
		// TODO Auto-generated method stub
		
	}

	public Map<Integer, String> verifyUiElements(String deviceName, String string, String screenName, String string2) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean validateDataFromJson(String detail, String userStoryName) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean validateDataFromDB(String field, String database, String dataToBeReferred, String userStoryName) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean enterDate(String dataToBeReferred, String date, String keyboard) {
		// TODO Auto-generated method stub
		return false;
	}

}
