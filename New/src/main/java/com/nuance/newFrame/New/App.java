package com.nuance.newFrame.New;

import com.nuance.newFrame.sali.LoginCheck;






/**
 * Hello world!
 *
 */
public class App 
{
	public App(){
	System.out.println("pass login fuction");
	}
	public void  exec(){
		   ProcessXMLFile y = new ProcessXMLFile();
	       y.readXml("/testcases.xml");

	   }
    public static void main( String[] args )
    {
    	//String x = args[0];
        System.out.println( System.getProperty("user.dir")+"\\reports\\ReportFormat.xsl");

//        ProcessXMLFile y = new ProcessXMLFile();
//	       y.readXml("/testcases.xml");
	      /* GenerateHtml z=new GenerateHtml();
	       z.execute();*/
	       /*give this C:"\"uttam\reports.html*/
    }

   
}
