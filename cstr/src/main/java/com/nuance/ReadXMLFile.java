package com.nuance;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.lang.reflect.Method;
 
public class ReadXMLFile {
 
  public static void main(String args[]) {
 
    try {
   	File fXmlFile = new File("testcases.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);
	
	doc.getDocumentElement().normalize();
 
	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());//company
 
	NodeList nList = doc.getElementsByTagName("classname");
	
	System.out.println("----------------------------");
 
	/*?Class cls = Class.forName(name); //name is the id attribute which i have to extract from xml file
	Object obj = cls.newInstance();
	Class noparams[] = {};*/
	//This for loop execute only twice one for id = select time range another for id = timestep
	for (int temp = 0; temp < nList.getLength(); temp++) {
 		Node nNode = nList.item(temp);
		String name=nNode.getAttributes().getNamedItem("id").getNodeValue();
		System.out.println("\nCurrent Element :" + name);
 //i think i can use the above block here
		Class cls = Class.forName(name); //name is the id attribute which i have to extract from xml file
		Object obj = cls.newInstance();
		Class noparams[] = {};
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nNode;
			NodeList tLists = eElement.getElementsByTagName("testname");
			for (int j = 0; j < tLists.getLength(); j++)
		    	{
		        Element x = (Element) tLists.item(j);
		        System.out.println(x.getFirstChild().getNodeValue());
		        Method method = cls.getDeclaredMethod(x.getFirstChild().getNodeValue(), noparams);
				method.invoke(obj, null);
		    }
		}
	}
		GenerateHtml FinalRep = new GenerateHtml();
	    FinalRep.execute();
	
    } catch (Exception e) {
    	System.out.println("trace:");
	e.printStackTrace();
    }
  }
 
}