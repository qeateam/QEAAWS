package com.qea.aws.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;

//The Class javax.xml.stream.XMLInputFactory is a root  component of the javaStAX API. Fom this class you can create both an XMLStreamReader and an XMLEventReader

import javax.xml.stream.XMLInputFactory;
//this interface declares the constants used in this api.

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;

//This is the base event interface for handling markup events.

import javax.xml.stream.events.XMLEvent;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.qea.aws.utils.GlobalObjects;

@SuppressWarnings("unchecked")
/*****************************************************************************
 * Title - This java class consists of parsers to read XML version 1.1
 ******************************************************************************/

public class StaxParser implements GlobalObjects {

	/**
	 * Copy text parser to read locator and copy text for a given field and
	 * screen name
	 * 
	 * @param:field:field on
	 *                        screen
	 * @param screenName:
	 *            Screen reference on which the field resides
	 * @return strArrCopyTextLocator
	 */

	public String[] copyTextParser(String fieldTextRef, String field, String screenName) throws Exception {
		String[] strArrCopytextLocator = new String[3];
		// String objectText=null;
		boolean bObjectProperty = false;
		boolean bobjectText = false;
		boolean bProperty = false;
		boolean bText = false;
		boolean bScreen = false;
		String eventID = null;
		// String locatorValue;

		try {
			// Create a new instance of the factory.
			javax.xml.stream.XMLInputFactory factory = javax.xml.stream.XMLInputFactory.newInstance();
			// This class provides interator of events which can be used to
			// iterate ofver events as they occur while parsing the XML document
			XMLEventReader eventReader;
			eventReader = factory.createXMLEventReader(
					new FileReader(new File("src/test/java/com/qea/aws/objectmap/CopyTextMap.xml")));
			factory.setProperty(javax.xml.stream.XMLInputFactory.IS_COALESCING, true);

			// Used to check further events exists or not

			while (eventReader.hasNext()) {

				// Used to retrieve next event
				XMLEvent event = eventReader.nextEvent();
				switch (event.getEventType()) {
				case XMLStreamConstants.START_ELEMENT:
					// The StartElement interface provides access to information
					// about start elements
					StartElement startElement = event.asStartElement();
					// qName is nnode name //getLocalPart() is usd to get name
					// of an element
					String qName = startElement.getName().getLocalPart();

					if (qName.equalsIgnoreCase("screen")) {
						Iterator<Attribute> attributes = startElement
								.getAttributes();
						eventID = attributes.next().getValue();

						if (eventID.equals(screenName)) {
							bScreen = true;
							while ((bProperty != true || bText != true) && eventReader.hasNext()) {
								// used to retrieve next event
								event = eventReader.nextEvent();
								switch (event.getEventType()) {
								case XMLStreamConstants.START_ELEMENT:
									// The Start Elementinterface provides
									// access to information
									// about start elements.
									startElement = event.asStartElement();
									// qName is node name //getLocalPart() is
									// used to get name
									// of an element
									qName = startElement.getName().getLocalPart();
									if (qName.equalsIgnoreCase("object")) {
										attributes = startElement.getAttributes();
										eventID = attributes.next().getValue();
									} else if (qName.equalsIgnoreCase("objectProperty")) {
										bObjectProperty = true;
									} else if (qName.equalsIgnoreCase(fieldTextRef)) {
										bobjectText = true;
									}
									break;
								case XMLStreamConstants.CHARACTERS:
									// Returns this event as Characters, may
									// result in a class cast exception if
									// thhiis event is not characters
									/*
									 * Characters characters= event
									 * .as.Character();
									 */
									if (bObjectProperty) {
										if (eventID.equals(field)) {
											// Code change to read all the
											// characters
											/* Removed characters .getData(); */
											String strObjectLocator = "";
											while (event.isCharacters()) {
												strObjectLocator = strObjectLocator + event.asCharacters();
												event = eventReader.nextEvent();
											}
											strArrCopytextLocator[0] = strObjectLocator;
											bProperty = true;
										}
										bObjectProperty = false;
									}
									if (bobjectText) {
										// code change to read all the
										// characters
										String strObjectText = "";
										while (event.isCharacters()) {
											strObjectText = strObjectText + event.asCharacters();
											event = eventReader.nextEvent();
										}
										strArrCopytextLocator[1] = strObjectText;
										bText = true;
									}
									bobjectText = false;
								}
								break;
							}
						} // End While Flag
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					break;
				}
				if (bScreen == true) {
					break;
				}
			}
		} catch (FileNotFoundException e) {
			throw e;
		}
		// Return the locator value
		if (bScreen == false || bText == false || bProperty == false) {
			// objReporter.ReportStep("Verify copytext:","Copy text parser:
			// element not available.", false, true);
			// Add reporting step
		}
		return strArrCopytextLocator;
	}

	/**
	 * Object parser to read objectmap xml and return a locator for field
	 * provided
	 * 
	 * @param field:
	 *            field on screen on which operation would be performed
	 * @param screenName:
	 *            Screen reference on whic the field reside
	 * @return objectProperty: Locator for field
	 * @throws FileNotFoundException
	 * @throws XMLStreamException
	 */
	public String objectParser(String field, String screenName) throws FileNotFoundException, XMLStreamException {
		String objectProperty = null;
		boolean bObjectProperty = false;
		boolean bProperty = false;
		boolean bScreen = false;
		String eventID = null;

		try {
			// create a new instance of the factory.
			javax.xml.stream.XMLInputFactory factory = javax.xml.stream.XMLInputFactory.newInstance();
			/*
			 * This class provides iterator of events which can be used to
			 * iterate over events as they occur while parsing the XML document
			 */
			// Added for Jenkins setup
			XMLEventReader eventReader;
			eventReader = factory.createXMLEventReader(
					new FileReader(new File("src/test/Java/com/qea/aws/objectmap/ObjectMap.xml")));
			factory.setProperty(XMLInputFactory.IS_COALESCING, true);
			// Used to check further events exists or not
			while (eventReader.hasNext()) {
				// Used to retrieve next event
				XMLEvent event = eventReader.nextEvent();
				switch (event.getEventType()) {
				case XMLStreamConstants.START_ELEMENT:
					// The StartElement interface provides access to information
					// about start elements.
					StartElement startElement = event.asStartElement();
					// qName is node name
					// getLocalPart() is used to get name
					// of an element
					String qName = startElement.getName().getLocalPart();
					if (qName.equalsIgnoreCase("screen")) {
						Iterator<Attribute> attributes = startElement
								.getAttributes();
						eventID = attributes.next().getValue();
						if (eventID.equals(screenName)) {
							bScreen = true;
							while (bProperty != true) {
								// Used to retrieve next event
								event = eventReader.nextEvent();
								switch (event.getEventType()) {
								case XMLStreamConstants.START_ELEMENT:
									// The StartElement interface provides
									// access to information about start
									// elements
									startElement = event.asStartElement();
									// qName is node name //getLocalPart() is
									// used to get name of an element
									qName = startElement.getName().getLocalPart();
									if (qName.equalsIgnoreCase("object")) {
										// System.out.println("qname value is
										// "+qName);
										attributes = startElement.getAttributes();
										eventID = attributes.next().getValue();
										// System.out.println ("qName value is
										// "+qName);
									} else if (qName.equalsIgnoreCase("objectProperty")) {
										bObjectProperty = true;
									}
									break;
								case XMLStreamConstants.CHARACTERS:
									if (bObjectProperty) {
										if (eventID.equals(field)) {
											/*
											 * code change to read all charactr
											 */
											String strObjectLocator = "";
											while (event.isCharacters()) {
												strObjectLocator = strObjectLocator + event.asCharacters();
												event = eventReader.nextEvent();
											}
											objectProperty = strObjectLocator;
											bProperty = true;
										}
										bObjectProperty = false;

									}
									break;
								}// End While Flag
							}
						}
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					break;
				}
				if (bScreen == true) {
					break;
				}
			}
		} catch (FileNotFoundException e) {
			throw e;
		} catch (XMLStreamException e) {
			throw e;
		}
		// return the locator value
		return objectProperty;
	}

	/**
	 * This parser to read MentionFlow xml and return list of userstory name to
	 * execute
	 * 
	 * @param testid:Prefefine
	 *            test id
	 * @return value: Array of UserStory name against Testid
	 * @throws FileNotFoundException
	 * @throws XMLStreamException
	 */

	public String[] getUserStoryName(String testid) throws FileNotFoundException, XMLStreamException {
		// String userStoryName;
		String dataref = null;
		String listofUserStories = "";
		boolean bobjectproperty = false;
		boolean bData = false;
		String[] value = new String[2];
		String eventID = null;
		try {
			javax.xml.stream.XMLInputFactory factory = javax.xml.stream.XMLInputFactory.newInstance();
			// create a new instance of the factory
			// start added for jenkins
			XMLEventReader eventReader;
			eventReader = factory.createXMLEventReader(
					new FileReader(new File("src/test/java/com/qea/aws/objectmap/MentionFlow.xml")));
			/*
			 * This class provides iterator of events which can be used to
			 * iterate over events as they occur while parsing the XML document
			 * *
			 */
			while (eventReader.hasNext()) {
				/*
				 * Used to retrieve next event *
				 */
				XMLEvent event = eventReader.nextEvent();
				switch (event.getEventType()) {
				case XMLStreamConstants.START_ELEMENT:
					StartElement startElement = event.asStartElement();
					String qName = startElement.getName().getLocalPart();
					if (qName.equalsIgnoreCase("Test")) {
						Iterator<Attribute> attributes = startElement
								.getAttributes();
						eventID = attributes.next().getValue();
					} else if (qName.equalsIgnoreCase("Flow")) {
						bobjectproperty = true;
					} else if (qName.equalsIgnoreCase("DataRef")) {
						bData = true;
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					Characters characters = event.asCharacters();
					if (bobjectproperty) {
						if (eventID.equals(testid)) {
							// code change to read all the characters
							// Removed characters.getdata
							String struserStoryName = "";
							while (event.isCharacters()) {
								struserStoryName = struserStoryName + event.asCharacters();
								event = eventReader.nextEvent();
							}
							listofUserStories = listofUserStories.concat("," + struserStoryName);
						}
						bobjectproperty = false;
					}
					if (bData) {
						if (eventID.equals(testid)) {
							dataref = characters.getData();
						}
						bData = false;
					}
					break;
				}
			}
		} catch (FileNotFoundException e) {
			throw e;

		} catch (XMLStreamException e) {
			throw e;
		}
		value[0] = listofUserStories;
		value[1] = dataref;
		System.out.println("List of user Strories is:" + value[0]);
		System.out.println("Data to be referred is " + value[1]);
		return value;
	}

	/**
	 * This method gets the list of test ids from Mentionflow.xml
	 * 
	 * @returns list of test ids
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	public List<String> getTestIDNames()
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		List<String> strListTestID = new ArrayList<String>();
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		// Start Added
		Document doc;
		doc = builder.parse("./src/test/java/com/barclaycar/qe/as2/objectmap/MentionFlow.xml");
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile("//class/Test[@id]");
		NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		for (int i = 0; i < nl.getLength(); i++) {
			Node currentItem = nl.item(i);
			String key = currentItem.getAttributes().getNamedItem("id").getNodeValue();
			strListTestID.add(key);
			System.out.println("Inside get test id names" + strListTestID);
		}
		return strListTestID;
	}

}