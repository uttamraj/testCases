package com.nuance.newFrame.sali;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UserInfo {

	private Properties properties = new Properties();
	
	public UserInfo (String userPropath) throws IOException
	{
		InputStream is = getClass().getResourceAsStream(userPropath); //if i use this then i have to call file with prefix "/"
		properties.load(is);
		//properties.load(new FileInputStream(userPropath));
			
	}
	
	public String getProperties(String key)
	{
		return this.properties.getProperty(key);
	}

	public static void main(String[] args) throws IOException{
	 UserInfo user = new UserInfo("/userInfo.properties");
      System.out.println(user.getProperties("username"));
		
	}
}
