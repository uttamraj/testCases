package com.nuance.newFrame.testCases;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Main;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class Mainpage {

	public static Logger logger = LoggerFactory.getLogger(Main.class);
	protected static WebDriver driver;
	protected String webUrl; 
	private static final String IEpath = System.getProperty("user.dir");
	protected UserInfo user;
	protected WebElement submit;
	public static WriteXmlFile ReportFile;
	public WebElement startdate;
	public WebElement enddate;
	public WebElement startdatetime;
	public WebElement enddatetime;
	public DateFormat format;
	
	public Mainpage()
	{
		try {
			//user =  new UserInfo("userinfo.xml");
			user = new UserInfo("/userInfo.properties");
			}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		webUrl = user.getProperties("weburl");
		//webUrl = "https://sun-qa-ncp03clone.engca.bevocal.com:8443/np";
	}
	
	public void choosedomain(String domainname)
	{//i have to put a try catch block here because what if it doesnt have the domain
		WebElement domainlist = driver.findElement(By.id("dropdown"));
		Select select = new Select(domainlist);
		select.selectByValue(domainname);
	}
	
	public void setDriver()
	{
		//String webdriver = user.getProperties("browser");
		String webdriver = "ie";
		//String webdriver = getClass().getClassLoader().getResource("IEDriverServer.exe").getFile();
		if (webdriver.toLowerCase().equals("ie"))
		{
			System.setProperty("webdriver.ie.driver", "C:\\IEDriverServer.exe");
			//System.setProperty("webdriver.ie.driver", IEpath+"\\iedriver\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		else
			driver = new FirefoxDriver();
		
		 ReportFile = new WriteXmlFile();

		
	}
	
	public void gotomainpage()
	{
		
		//WebElement home = new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.linkText("Home")));
		try{
			//WebElement home = driver.findElement(By.linkText("Home"));
			driver.switchTo().defaultContent();
			WebElement home = new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.linkText("Home")));
			home.click();
		}
		catch(NoSuchElementException e)
		{
			logger.info("can't home button on the page");
			driver.switchTo().defaultContent();
			gotomainpage();
		}
		
	}
	public void quit(){
		driver.quit();
	}
	
	public void TrendReport ()
	{
		
		try{
					
			WebElement OnDemandInsight = new WebDriverWait(driver,5).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'On Demand Insight')]")));
			//actionClick(OnDemandInsight);
			OnDemandInsight.click();
		
			WebElement Reports=(new WebDriverWait(driver,10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Reports')]")));
			//actionClick(Reports);
			Reports.click();
		
			WebElement CallStatisticsTrendReport= (new WebDriverWait(driver,10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Call Statistics Trend Report')]")));
			//actionClick(CallStatisticsTrendReport);
			CallStatisticsTrendReport.click();
			
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			String mainWinhandler = driver.getWindowHandle();
		
			Set<String> handles = driver.getWindowHandles();
		
		for (String handle : handles){
			if (!mainWinhandler.equals(handle))
			{
				WebDriver popup  = driver.switchTo().window(handle);
				popup.close();
			}
		}
		
		driver.switchTo().defaultContent();
		
		WebElement submittd = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.id("submittd")));
		submit = submittd.findElement(By.xpath("//input[@value='Submit']"));
		//submit = driver.findElement(By.xpath("//input[@value='Submit']"));
		}
		catch (TimeoutException e)
		{
			logger.info("timeout exception"); 
			gotomainpage();
			TrendReport();
		}
	//	driver.switchTo().activeElement();
		
	}
	public void cssrReport ()
	{
		try {
			//click on On Demand Insight
			WebElement odi= (new WebDriverWait(driver,10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'On Demand Insight')]")));
			odi.click();
			WebElement report=(new WebDriverWait(driver,10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Reports')]")));
			report.click();
			WebElement cssr= (new WebDriverWait(driver,10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Call Statistics Summary Report')]")));
			cssr.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			String mainWinhandler = driver.getWindowHandle();
		
			Set<String> handles = driver.getWindowHandles();
		
		for (String handle : handles){
			if (!mainWinhandler.equals(handle))
			{
				WebDriver popup  = driver.switchTo().window(handle);
				popup.close();
			}
		}
		
		driver.switchTo().defaultContent();
		
		WebElement submittd = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.id("submittd")));
		submit = submittd.findElement(By.tagName("input"));
		
		
			}
			catch (TimeoutException e)
			{
				logger.info("timeout exception"); 
				gotomainpage();
				cssrReport();
						
		}
	}
	public void transferReport ()
	{
		try{
			WebElement odi= (new WebDriverWait(driver,10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'On Demand Insight')]")));
			odi.click();
			WebElement report=(new WebDriverWait(driver,10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Reports')]")));
			report.click();
			WebElement tReport= (new WebDriverWait(driver,10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Call Transfer Report')]")));
			tReport.click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			String mainWinhandler = driver.getWindowHandle();
		
			Set<String> handles = driver.getWindowHandles();
		
		for (String handle : handles){
			if (!mainWinhandler.equals(handle))
			{
				WebDriver popup  = driver.switchTo().window(handle);
				popup.close();
			}	
		}
		driver.switchTo().defaultContent();
		
		// This is to just initialise the submit string to element location to use it in the methods
		WebElement submittd = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.id("submittd")));
		submit = submittd.findElement(By.tagName("input"));
		
		
			}
			catch (TimeoutException e)
			{
				logger.info("timeout exception"); 
				gotomainpage();
				transferReport();
						
		}
	}

public void setTime (String period)
	{
	startdate = driver.findElement(By.id("PARAM_START_DATE"));
	enddate = driver.findElement(By.id("PARAM_END_DATE"));
	String enddateValue = enddate.getAttribute("value");
	
	Calendar cal = Calendar.getInstance();
	format = new SimpleDateFormat("MM/dd/yyyy");
	try {
	Date date = format.parse(enddateValue);
	cal.setTime(date);
	} catch (ParseException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	}
	
	startdate.clear();
	removeAlert();
	
	if(period.equals("1 month"))
	{
	cal.add(Calendar.MONTH, -1);
	String starttime = format.format(cal.getTime());
	startdate.sendKeys(starttime);
	}
	else if(period.equals("3 months"))
	{
	cal.add(Calendar.MONTH, -3);
	String starttime = format.format(cal.getTime());
	startdate.sendKeys(starttime);
	}
	else if(period.equals("1 year"))
	{
	cal.add(Calendar.YEAR, -1);
	String starttime = format.format(cal.getTime());
	startdate.sendKeys(starttime);
	}
	else if(period.equals("13 months"))
	{
	cal.add(Calendar.MONTH, -13);
	String starttime = format.format(cal.getTime());
	startdate.sendKeys(starttime);
	}
	else if(period.equals("2 years"))
	{
	cal.add(Calendar.YEAR, -2);
	String starttime = format.format(cal.getTime());
	startdate.sendKeys(starttime);
	}
	else if (period.equals("3 years"))
	{
	cal.add(Calendar.YEAR, -3);
	String starttime = format.format(cal.getTime());
	startdate.sendKeys(starttime);
	}	
	else if (period.equals("random"))
	{
	
	cal.add(Calendar.MONTH, -1);
	String starttime = format.format(cal.getTime());
	startdate.sendKeys(starttime);
	
	enddate.clear();
	removeAlert();
	
	cal.add(Calendar.HOUR, 24);
	String endtime = format.format(cal.getTime());
	enddate.sendKeys(endtime);
	}
	else if (period.equals("noData"))
	{
	cal.add(Calendar.YEAR, -5);
	String starttime = format.format(cal.getTime());
	startdate.sendKeys(starttime);
	enddate.clear();
	removeAlert();
	cal.add(Calendar.MONTH, 1);
	String endtime=format.format(cal.getTime());
	enddate.sendKeys(endtime);
	}
	
}
	
	public void actionClick (WebElement element)
	{
		Actions mover = new Actions(driver);
		mover.moveToElement(element).click(element).perform();
	}
	public void removeAlert() {
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
	}
	public static void main(String[] args)
	{
		
		//WebElement home = mm.driver.findElement(By.xpath(homePath));
		//home.submit();
	}
	
}
