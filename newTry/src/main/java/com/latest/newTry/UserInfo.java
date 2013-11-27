package com.latest.newTry;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UserInfo {

	private Properties properties = new Properties();
	
	public UserInfo (String userPropath) throws IOException
	{
		InputStream is = getClass().getResourceAsStream(userPropath);
		properties.load(is);
		//properties.load(new FileInputStream(userPropath));
			
	}
	
	public String getProperties(String key)
	{
		return this.properties.getProperty(key);
	}

	public static void main(String[] args) throws IOException{
	 UserInfo user = new UserInfo("userinfo.xml");
      System.out.println(user.getProperties("username"));
		
	}
}
