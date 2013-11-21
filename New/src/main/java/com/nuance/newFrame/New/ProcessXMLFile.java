package com.nuance.newFrame.New;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;




import java.io.File;
import java.lang.reflect.Method;
 
public class ProcessXMLFile {
	public static Logger logger = LoggerFactory.getLogger(Main.class);
 

	public void readXml(String xmlFile){
    try {
	   	File fXmlFile = new File(xmlFile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
	 	logger.info("Root element :" + doc.getDocumentElement().getNodeName());//company
	 	NodeList nList = doc.getElementsByTagName("classname");
		//This for loop execute only twice one for id = select time range another for id = timestep
		for (int temp = 0; temp < nList.getLength(); temp++) {
	 		Node nNode = nList.item(temp);
			String name=nNode.getAttributes().getNamedItem("id").getNodeValue();
			logger.info("\nCurrent Element :" + name);
	 		Class<?> cls = Class.forName(name); //name is the id attribute which i have to extract from xml file
			Object obj = cls.newInstance();
			Class<?> noparams[] = {};
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				NodeList tLists = eElement.getElementsByTagName("testname");
				for (int j = 0; j < tLists.getLength(); j++)
			    {
			        Element node = (Element) tLists.item(j);
			        logger.info(node.getFirstChild().getNodeValue());
			        Method method = cls.getDeclaredMethod(node.getFirstChild().getNodeValue(), noparams);
			        method.invoke(obj);
			    }
			}
		}
	    
    }catch (Exception e) {
    	logger.info("trace" + e);
    }

  }
}