package com.nuance.newFrame.New;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	//String x = args[0];
        System.out.println( "Hello World!" );
        ProcessXMLFile y = new ProcessXMLFile();
        y.readXml("/testcases.xml");
    }
}
