package com.nuance.newFrame.MainFrame;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import static java.lang.System.err;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Main;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
 
public class ProcessXMLFile {
	public static Logger logger = LoggerFactory.getLogger(Main.class);
 

	public void readXml(String xmlFile){
    try {
    	  String mname = null;
    	//InputStream is = getClass().getResourceAsStream(xmlFile);
    	  NodeList tLists = null;
    	  NodeList mainpagename=null;
    	  NodeList quitBrowser= null;
    	  Class<?> noparams[] = {};
    	//File fXmlFile = new File();
    	File fXmlFile = new File(xmlFile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
	 	logger.info("Root element: " + doc.getDocumentElement().getNodeName());//company
	 	NodeList nList = doc.getElementsByTagName("classname");
	 	
		//This for loop execute only twice one for id = select time range another for id = timestep
	 	
	 	for (int temp = 0; temp < nList.getLength(); temp++) {
	 		Node nNode = nList.item(temp);
			String name=nNode.getAttributes().getNamedItem("id").getNodeValue();
			logger.info("\nCurrent Class: " + name);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nNode;
			tLists = eElement.getElementsByTagName("testname");
			mainpagename = eElement.getElementsByTagName("mainpage");
			quitBrowser = eElement.getElementsByTagName("quitbrowser");
			}
			try{
			Class<?> cls = Class.forName(name); //name is the id attribute which i have to extract from xml file
			Object obj = cls.newInstance();
			
			
				
//				NodeList tLists = eElement.getElementsByTagName("testname");
//				NodeList mainpagename = eElement.getElementsByTagName("mainpage");
//				NodeList quitBrowser = eElement.getElementsByTagName("quitbrowser");
				for (int j = 0; j < tLists.getLength(); j++)
			    {
					try{
			        Element node = (Element) tLists.item(j);
			        logger.info("Current Method: "+node.getFirstChild().getNodeValue());
			        Method method = cls.getDeclaredMethod(node.getFirstChild().getNodeValue(), noparams);
			        mname = method.getName();
			        method.invoke(obj);
			/*        String returnToMainpage = nNode.getAttributes().getNamedItem("mainpage").getNodeValue();
			        logger.info("Current class: "+ returnToMainpage);
			        Class<?> mainpage = Class.forName(returnToMainpage);
			        Object home = mainpage.newInstance();
			        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						//NodeList mainpagename = ele.getElementsByTagName("mainpage");
						System.out.println(mainpagename);
						for(int k=0; k<mainpagename.getLength();k++){
						Element node2 = (Element) mainpagename.item(k);
						String mainMethod = node2.getFirstChild().getNodeValue();
						logger.info(mainMethod);
						Method defaultRun = mainpage.getDeclaredMethod(mainMethod, noparams);
						defaultRun.invoke(home);
						}
			        }*/
					        
					}catch(InvocationTargetException e)
					{
						 Throwable cause = e.getCause();
						    err.format("Invocation of %s failed: %s%n",mname, cause.getMessage());
					}
					finally{
						 String returnToMainpage = nNode.getAttributes().getNamedItem("mainpage").getNodeValue();
					        logger.info("Current class: "+ returnToMainpage);
					        Class<?> mainpage = Class.forName(returnToMainpage);
					        Object home = mainpage.newInstance();
					        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
								//NodeList mainpagename = ele.getElementsByTagName("mainpage");
								System.out.println(mainpagename);
								for(int k=0; k<mainpagename.getLength();k++){
								Element node2 = (Element) mainpagename.item(k);
								String mainMethod = node2.getFirstChild().getNodeValue();
								logger.info(mainMethod);
								Method defaultRun = mainpage.getDeclaredMethod(mainMethod, noparams);
								defaultRun.invoke(home);
						
								}
					        }
						logger.info("this is in final block");
					}
			    }
			
	 	}catch(Exception e){
	 		logger.info("Exception trace" + e);
	 	}finally{
	 		String quitSession = nNode.getAttributes().getNamedItem("mainpage").getNodeValue();
	 		logger.info("Current class: "+ quitSession);
	 		Class<?> session = Class.forName(quitSession);
	 		Object exit = session.newInstance();
	 		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				//NodeList mainpagename = ele.getElementsByTagName("mainpage");
				System.out.println(quitBrowser);
				for(int k=0; k<quitBrowser.getLength();k++){
				Element node2 = (Element) quitBrowser.item(k);
				String quitMethod = node2.getFirstChild().getNodeValue();
				logger.info(quitMethod);
				Method defaultRun = session.getDeclaredMethod(quitMethod, noparams);
				defaultRun.invoke(exit);
		
				}
	        }
		logger.info("this is in final block");
	}	 	
	    
    }
    }catch (Exception e) {
    	logger.info("Exception trace" + e);
    }finally{
    	logger.info("this is in final block 2");
    }

  }
}