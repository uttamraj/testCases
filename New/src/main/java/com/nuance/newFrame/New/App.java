package com.nuance.newFrame.New;







/**
 * Hello world!
 *
 */
public class App 
{
	public App(){
	System.out.println("pass login fuction");
	}
/*	public void  exec(){
		   ProcessXMLFile y = new ProcessXMLFile();
	       y.readXml("/testcases.xml");

	   }*/
    public static void main( String[] args )
    {
    	//String x = args[0];
        System.out.println( System.getProperty("user.dir")+"\\reports\\ReportFormat.xsl");
        System.out.println(System.getProperty("user.dir") + "\\classes\\reports\\Report.xml");
    	ProcessXMLFile y = new ProcessXMLFile();
    	y.readXml("/testcases.xml");
	    
    	GenerateHtml z=new GenerateHtml();
	       z.execute();
	       /*give this C:"\"uttam\reports.html*/
    }

   
}
