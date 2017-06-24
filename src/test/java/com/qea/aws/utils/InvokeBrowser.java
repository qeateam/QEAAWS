package com.qea.aws.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.apache.xerces.util.URI.MalformedURIException;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.chrome.*;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class InvokeBrowser implements GlobalObjects {
	public enum browsers {
		FIRFOX, CHROME
	}

	public void invokeBrowser(browsers browserName) throws MalformedURIException, MalformedURLException {
		// File app = new File("C:\\Program
		// Files\\Experitest\\SeeTest\\bin\\ipas","eribank.apk");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "CHROME");
		capabilities.setCapability("deviceName", "ZY2232GXWX");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "6.0.1");
		// capabilities.setCapability("app", app.getAbsolutePath());
		/*
		 * capabilities.setCapability("app-package",
		 * "com.experitest.ExperiBank");
		 * capabilities.setCapability("app-activity", ".LoginActivity")
		 */;
		 System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
		URL url = new URL("http://127.0.0.1:4723/wd/hub");

		switch (browserName) {
		case FIRFOX:
			if (objReadConfigProps.PLATFORM.equals("WEB")) {
				objGlobal.driver = new FirefoxDriver();
				objGlobal.driver.get(objReadConfigProps.URL);
			}

			if (objReadConfigProps.PLATFORM.equals("MOBILE")) {
				objGlobal.driver = new AppiumDriver(url, capabilities) {
					@Override
					public MobileElement scrollToExact(String arg0) {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public MobileElement scrollTo(String arg0) {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public DeviceRotation rotation() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public void rotate(DeviceRotation arg0) {
						// TODO Auto-generated method stub

					}
				};
				objGlobal.driver.get(objReadConfigProps.URL);
			}
			break;

		case CHROME:
			if (objReadConfigProps.PLATFORM.equals("WEB")) {
				//System.setProperty("webdriver.chrome.driver", "---PATH TO CHROME EXE FILE---");
				System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
				objGlobal.driver = new ChromeDriver();
				objGlobal.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				objGlobal.driver.get(objReadConfigProps.URL);
			}

			if (objReadConfigProps.PLATFORM.equals("MOBILE")) {
				objGlobal.driver = new AppiumDriver(url, capabilities) {
					@Override
					public MobileElement scrollToExact(String arg0) {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public MobileElement scrollTo(String arg0) {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public DeviceRotation rotation() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public void rotate(DeviceRotation arg0) {
						// TODO Auto-generated method stub

					}
				};
				objGlobal.driver.navigate();
				objGlobal.driver.get(objReadConfigProps.URL);
				
			}
			break;

		default:
			throw new IllegalArgumentException("Invalid selection method");

		}

	}
}
