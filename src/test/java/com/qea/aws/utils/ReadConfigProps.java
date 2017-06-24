package com.qea.aws.utils;

import java.util.ResourceBundle;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.*;

public class ReadConfigProps {
	
	//Public global values
	public String URL, DBUSERNAME, DBPASSWORD, PLATFORM, BROWSER;
	public void retrieve_configProperties() {

		ResourceBundle objResBundle = ResourceBundle.getBundle("proprties.config");
		URL = (String) objResBundle.getObject("URL");
		System.out.println("URL>>"+URL);
		DBUSERNAME = (String) objResBundle.getObject("DBUSERNAME");
		System.out.println("DBUSERNAME>>"+DBUSERNAME);
		DBPASSWORD = (String) objResBundle.getObject("DBPASSWORD");
		System.out.println("DBPASSWORD>>"+DBPASSWORD);
		PLATFORM = (String) objResBundle.getObject("PLATFORM");
		System.out.println("PLATFORM>>"+PLATFORM);
		BROWSER = (String) objResBundle.getObject("BROWSER");
		System.out.println("BROWSER>>"+BROWSER);
		
		

	}
}