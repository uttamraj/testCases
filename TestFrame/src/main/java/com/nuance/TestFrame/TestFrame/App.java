package com.nuance.TestFrame.TestFrame;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        ProcessXMLFile x = new ProcessXMLFile();
        x.readXml("testcases.xml");
    }
}
