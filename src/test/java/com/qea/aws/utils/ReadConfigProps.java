package com.qea.aws.utils;

import java.util.ResourceBundle;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.*;

public class ReadConfigProps {
	public void retrieve_configProperties() {

		ResourceBundle objResBundle = ResourceBundle.getBundle("proprties.config");
		String var1 = (String) objResBundle.getObject("");
		String var2 = (String) objResBundle.getObject("");
		String var3 = (String) objResBundle.getObject("");
		String var4 = (String) objResBundle.getObject("");
		String var5 = (String) objResBundle.getObject("");

	}
}