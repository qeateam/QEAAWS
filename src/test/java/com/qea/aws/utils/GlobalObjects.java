package com.qea.aws.utils;

import com.qea.aws.repo.Global;
import com.qea.aws.tests.StepDefinition;

public interface GlobalObjects {
	
	Global objGlobal= new Global(); 
	ExtentReport objEReporter= new ExtentReport();
	StaxParser objParser= new StaxParser();
	ReadConfigProps objReadConfigProps = new ReadConfigProps();
	StepDefinition objStepDefinition = new StepDefinition();
}
