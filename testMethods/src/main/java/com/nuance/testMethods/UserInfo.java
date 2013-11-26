package com.nuance.testMethods;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class UserInfo {

	private Properties properties = new Properties();
	
	public UserInfo (String userPropath) throws IOException
	{
	//	properties.load(new FileInputStream(userPropath));
		//InputStream prop = getClass().getResourceAsStream(userPropath);
		properties.load(UserInfo.class.getClassLoader().getResourceAsStream(userPropath));
	}
	
	public String getProperties(String key)
	{
		return this.properties.getProperty(key);
	}

	public static void main(String[] args) throws IOException{
		
	 UserInfo user = new UserInfo("userinfo.properties");
      System.out.println(user.getProperties("username"));
		
	}
}
