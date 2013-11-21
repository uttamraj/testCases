package com.nuance.testMethods;
//CSTR
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.SendKeysAction;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.Sets.SetView;

public class Timestep extends Mainpage {
	
	private String timestepTestresult;
		
	public Timestep()
	{
		gotomainpage();
	}
	//	ODI6.x-646: CSTR - Search// he is doing no search here
	public void search(){
		transferReport();
			try{
			driver.findElement(By.id("PARAM_START_DATE")).clear();
			removeAlert();
			driver.findElement(By.id("PARAM_START_DATE")).sendKeys("8/11/2013");
			driver.findElement(By.id("PARAM_END_DATE")).clear();
			removeAlert();
			driver.findElement(By.id("PARAM_END_DATE")).sendKeys("9/11/2013");
			driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement page =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewer")));
			WebElement rangeStart=page.findElement(By.xpath("//*[contains(text(),'8/11/2013')]"));
			WebElement rangeEnd=page.findElement(By.xpath("//*[contains(text(),'9/11/2013')]"));
				if(rangeStart!=null&&rangeEnd!=null)
				{
				logger.info("Report for selected range is showed");
				ReportFile.addTestCase("ODI6.x-646:CSTR - Search", "ODI6.x-646:CSTR - Search=> Pass");
				}
			}
			catch (NoSuchElementException e)
			{
				ReportFile.addTestCase("ODI6.x-646:CSTR - Search", "ODI6.x-646:CSTR - Search=> Fail");
				
			}
		ReportFile.WriteToFile();
		driver.switchTo().defaultContent();
		gotomainpage();
		}
	
	//	ODI6.x-650:Report look'n Feel: Enhancement from Portal
	public void EnhancementFromPortal()
	{
		TrendReport();
		try{			
		
			WebElement timeRange = driver.findElement(By.id("date_range"));
			Select select = new Select(timeRange);
			select.selectByValue("l30d");
		
			WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
			timesteps.click();
			
			select = new Select(timesteps); 
			select.selectByValue("WEEK");
			
			submit.click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement reportpage = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
			WebElement header = reportpage.findElement(By.xpath("//*[contains(text(),'Statistics')]"));
			if((header.getCssValue("font-family").equals("trebuchet ms"))&&(header.getCssValue("transparent").equals("transparent")))
				ReportFile.addTestCase("ODI6.x-650:Report look'n Feel: Enhancement from Portal", "Verify use the gradient as the background of the report header => Pass");
			else 
				ReportFile.addTestCase("ODI6.x-650:Report look'n Feel: Enhancement from Portal", "Verify use the gradient as the background of the report header => Fail");
		} catch(NoSuchElementException e)
		{
			logger.info("enhancementFromPotal find element exception"+ e);
			ReportFile.addTestCase("ODI6.x-650:Report look'n Feel: Enhancement from Portal", "Verify use the gradient as the background of the report header => Fail");
		} 
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();	
		gotomainpage();                                        
	}
//654
	public void abfilterselectable(){
		choosedomain("METROPCS");
		TrendReport();
		WebElement ABvalue = null;
		boolean result=false,selectValuesPresent =false;
		try{
			boolean isPresent = driver.findElements(By.id("PARAM_APP_VARIANT_ID")).size()>0;
			if(isPresent){
				logger.info("A/B filter values are present");
				ABvalue = driver.findElement(By.id("PARAM_APP_VARIANT_ID"));
				Select select = new Select(ABvalue);
				result= select.isMultiple();
				List<WebElement> selectValues=select.getOptions();
				for (int i=0;i<selectValues.size(); i++)
				{
					if(selectValues.get(i).getText().equals("All"))
					selectValuesPresent =true;
					else if(selectValues.get(i).getText().equals("UNKNOWN"))
					selectValuesPresent = true;
				}	
				if(result&&selectValuesPresent)
					ReportFile.addTestCase("ODI6.x-620:A/B filter: should be in the report footer", "ODI6.x-62x:A/B filter: should be in the report footer => Pass");
				else 
					ReportFile.addTestCase("ODI6.x-620:A/B filter: should be in the report footer", "ODI6.x-62x:A/B filter: should be in the report footer => Fail");
			}
			else ReportFile.addTestCase("ODI6.x-620:A/B filter: should be in the report footer", "ODI6.x-62x:A/B filter: should be in the report footer => Fail");
		}catch(Exception e)
		{
			ReportFile.addTestCase("ODI6.x-620:A/B filter: should be in the report footer", "ODI6.x-62x:A/B filter: should be in the report footer => Fail");
			logger.info("Exception" + e);
		}
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
	}
	//655
//odi.620 Method that the report footer a/b filter	
	public void abfilter(){
		choosedomain("METROPCS");
		TrendReport();
		try{
			WebElement ABvalue = driver.findElement(By.id("PARAM_APP_VARIANT_ID"));
			Select select = new Select(ABvalue);
			select.deselectAll();
			select.selectByIndex(1);
			String selectText = select.getFirstSelectedOption().getText();
			submit.click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement page =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewer")));
			WebElement tag=page.findElement(By.xpath("//*[contains(text(),'A/B')]"));
			String filter = tag.getText();
			if(filter.equals(String.format("A/B Combo ID: %s", selectText )))
				if(tag != null)
				{
				logger.info(String.format("A/B Combo ID: %s", selectText )+" is present");
				ReportFile.addTestCase("ODI6.x-620:A/B filter: should be in the report footer", "ODI6.x-620:A/B filter: should be in the report footer => Pass");
				}
		}catch (NoSuchElementException e)
		{
			ReportFile.addTestCase("ODI6.x-620:A/B filter: should be in the report footer", "ODI6.x-620:A/B filter: should be in the report footer => Fail");
			
		}
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
	}
	//ODI6.x-657:DNIS filter: new look of the drop down list
	/*
	public void DNISFilterNewLook()
	{
		TrendReport();
		WebElement DNIS;
		Select select;
		try{
			DNIS = driver.findElement(By.id("PARAM_DNIS"));
			select = new Select(DNIS);
			WebElement firstOption = select.getFirstSelectedOption();
			if (firstOption.getText().equals("All"))
			{
				List<WebElement> allop = select.getOptions();
				for (WebElement option : allop) { //iterate over the options
				    logger.info(option.getAttribute("bgcolor")); //why bgcolor
				}
				}
		} catch (NoSuchElementException e)
		{
			gotomainpage();
			DNISFilterNewLook();
		}
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();	
		gotomainpage();	
		//driver.quit();
		
	}*/
	public void DNISFilterNewLook()
	{
		choosedomain("US_AIRWAYS");
		TrendReport();
		WebElement DNIS;
		Select select;
		try{
			DNIS = driver.findElement(By.id("PARAM_DNIS"));
			select = new Select(DNIS);
			//WebElement firstOption = select.getFirstSelectedOption();
			//if (firstOption.getText().equals("All"))
			
				List<WebElement> allop = select.getOptions();
				for (WebElement option : allop) { //iterate over the options
				    if(option.getText().equals("All"))
				    	if(select.getFirstSelectedOption().equals("All"))//returns the current selected option
				    	if(select.isMultiple())
				    		select.selectByValue("All");
				    	if(select.getFirstSelectedOption().equals("None"))
				    		ReportFile.addTestCase("ODI6.x-657:DNIS filter: new look of the drop down list", "ODI6.x-657:DNIS filter: new look of the drop down list=> Pass");
				    	else
				    		ReportFile.addTestCase("ODI6.x-657:DNIS filter: new look of the drop down list", "ODI6.x-657:DNIS filter: new look of the drop down list => Fail");
					
				}
				
		} catch (NoSuchElementException e)
		{
			ReportFile.addTestCase("ODI6.x-657:DNIS filter: new look of the drop down list", "ODI6.x-657:DNIS filter: new look of the drop down list => Fail");
		}
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();	
		gotomainpage();	
		//driver.quit();
	}
	//658
	public void dnisfilter(){
		choosedomain("US_AIRWAYS");
		TrendReport();
		try{
		driver.switchTo().defaultContent();
		WebElement DNISvalue = driver.findElement(By.id("PARAM_DNIS"));
		Select select = new Select(DNISvalue);
		select.deselectAll();
		select.selectByIndex(1);
		String selectText = select.getFirstSelectedOption().getText();
		//select.selectByValue("2404953077");
		submit.click();
	
		new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
		WebElement page =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewer")));
		WebElement tag=page.findElement(By.xpath("//*[contains(text(),'DNIS:')]"));
		String filter = tag.getText();
		if(filter.equals(String.format("DNIS: %s", selectText )))
			if(tag != null)
			{
			logger.info(String.format("DNIS: %s", selectText )+" is present");
			ReportFile.addTestCase("ODI6.x-623:DNIS filter: selected DNIS filter should be shown in report result", "ODI6.x-623:DNIS filter: selected DNIS filter should be shown in report result => Pass");
			}
	
		}catch (NoSuchElementException e)
		{
			ReportFile.addTestCase("ODI6.x-623:DNIS filter: selected DNIS filter should be shown in report result", "ODI6.x-623:DNIS filter: selected DNIS filter should be shown in report result => Fail");
			
		}
		ReportFile.WriteToFile();
		driver.switchTo().defaultContent();
		gotomainpage();
	}
	//ODI6.x-660:Time Step filter should be selectable
	public void filterShouldBeSelectable()
	{
		TrendReport();
		boolean result = false;
		try{
			WebElement timeRange = driver.findElement(By.id("date_range"));
			Select select = new Select(timeRange);
			select.selectByValue("l30d");
			
			WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
			
			timesteps.click();
			
			select = new Select(timesteps);
			List<WebElement> timestep = select.getOptions();
			for (int i = 0; i <timestep.size(); i++)
			{
				select.selectByIndex(i);
				if (timestep.get(i).isSelected() == true)
				{
					result = true;
					break;
					}				
			}
			if(result)
				ReportFile.addTestCase("ODI6.x-660:Time Step filter should be selectable", "ODI6.x-660:Time Step filter should be selectable => Pass");
			else
				ReportFile.addTestCase("ODI6.x-660:Time Step filter should be selectable", "ODI6.x-660:Time Step filter should be selectable => Fail");
		}
		catch(NoSuchElementException e)
		{
			ReportFile.addTestCase("ODI6.x-660:Time Step filter should be selectable", "ODI6.x-660:Time Step filter should be selectable => Fail");
		}
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();		
		
	}
//	ODI6.x-662:Time step hour selection started"
	
	public void hourSelection()
	{
		TrendReport();
		
		setTime("random");
		
		driver.findElement(By.id("PARAM_START_DATE_label")).click();
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		Select select = new Select(timesteps); 
		select.selectByValue("HOUR");
		
		submit.click();
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
		try{
			
			WebElement hour = driver.findElement(By.xpath("//*[text()='Hour']"));
			
			ReportFile.addTestCase("ODI6.x-662:Time step hour selection started", "ODI6.x-664:Time step hour selection started => Pass");
		}
		catch(NoSuchElementException e)
		{
			ReportFile.addTestCase("ODI6.x-662:Time step hour selection started", "ODI6.x-662:Time step hour selection started => Fail");
		}
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
	}
	//ODI6.x-663:Time step day selection
	public void daySelection()
	{
		TrendReport();
			
		WebElement timeRange = driver.findElement(By.id("date_range"));
		Select select = new Select(timeRange);
		select.selectByValue("");
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		select = new Select(timesteps); 
		select.selectByValue("DAY");
		
		submit.click();
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
		try{
	
		WebElement Day = driver.findElement(By.xpath("//*[text()='Day']"));
		ReportFile.addTestCase("ODI6.x-663:Time step day selection started", "ODI6.x-663:Time step day selection started => Pass");

		}
		catch(NoSuchElementException e)
		{
			ReportFile.addTestCase("ODI6.x-663:Time step day selection started", "ODI6.x-663:Time step day selection started => Fail");
		}
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
	}
	//ODI6.x-664:Time step week selection
	public void weekSelection()
	{
		TrendReport();
		try{			
		
			WebElement timeRange = driver.findElement(By.id("date_range"));
			Select select = new Select(timeRange);
			select.selectByValue("l30d");
		
			WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
			timesteps.click();
			
			select = new Select(timesteps); 
			select.selectByValue("WEEK");
			
			submit.click();
		} catch (NoSuchElementException e)
		{
			gotomainpage();
			weekSelection();
		}
		
		try{
			
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
			WebElement week = driver.findElement(By.xpath("//*[text()='Week']"));
			
			ReportFile.addTestCase("ODI6.x-664:Time step week selection started", "ODI6.x-664:Time step week selection started => Pass");
		}
		catch(NoSuchElementException e)
		{
			ReportFile.addTestCase("ODI6.x-664:Time step week selection started", "ODI6.x-664:Time step week selection started => Fail");
		} catch(TimeoutException  e)
		{
			driver.switchTo().defaultContent();
			gotomainpage();
			weekSelection();
		}
		
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
		
	}
	//ODI6.x-665:Time step month selection
	public void monthSelection()
	{
		TrendReport();
		
		setTime("3 months");
		
		driver.findElement(By.id("PARAM_START_DATE_label")).click();
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		Select select = new Select(timesteps); 
		select.selectByValue("MONTH");
		
		submit.click();
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
		try{
			
			WebElement month = driver.findElement(By.xpath("//*[text()='Month']"));
			
			ReportFile.addTestCase("ODI6.x-665:Time step month selection started", "ODI6.x-665:Time step month selection started => Pass");
		}
		catch(NoSuchElementException e)
		{
			ReportFile.addTestCase("ODI6.x-665:Time step month selection started", "ODI6.x-665:Time step month selection started => Fail");
		}
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
			
	}
	//ODI6.x-666:Time step quarter selection
	public void quarterSelection()
	{
		TrendReport();
		
		setTime("1 year");
	
		driver.findElement(By.id("PARAM_START_DATE_label")).click();
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		Select select = new Select(timesteps); 
		select.selectByValue("QUARTER");
		
		submit.click();
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
		try{
			
			WebElement quarter = driver.findElement(By.xpath("//*[text()='Quarter']"));
			
			ReportFile.addTestCase("ODI6.x-666:Time step quater selection started", "ODI6.x-666:Time step quarter selection started => Pass");
		}
		catch(NoSuchElementException e)
		{
		ReportFile.addTestCase("ODI6.x-666:Time step quater selection started", "ODI6.x-666:Time step quarter selection started => Fail");
		}
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
			
	}
	//ODI6.x-667:Time step year selection
	public boolean yearSelection()
	{
		TrendReport();
		boolean result = false;
		setTime("2 years");
		
		driver.findElement(By.id("PARAM_START_DATE_label")).click();
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		Select select = new Select(timesteps); 
		select.selectByValue("YEAR");
		
		submit.click();
		
		if (checkifalert() == true)
		{
			ReportFile.addTestCase("ODI6.x-667:Time step year selection started", "ODI6.x-667:Time step Year selection started => Fail");
			ReportFile.WriteToFile();
			gotomainpage();
			return result;
		}
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
		try{
			
			WebElement Year = driver.findElement(By.xpath("//*[text()='Year']"));
			
			ReportFile.addTestCase("ODI6.x-667:Time step year selection started", "ODI6.x-667:Time step Year selection started => Pass");
		}
		catch(NoSuchElementException e)
		{
		ReportFile.addTestCase("ODI6.x-667:Time step year selection started", "ODI6.x-667:Time step Year selection started => Fail");
		}
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
		return result;	
	}
	
	//ODI6.x-668:Time step hour sorting
	public void hourSelectionSorting() {
		TrendReport();
		
		List<WebElement> hour;
		ArrayList<Date> Hourlist = new ArrayList<Date>();	
		DateFormat hourformat = new SimpleDateFormat("HH:MM");

		
		startdate = driver.findElement(By.id("PARAM_START_DATE"));
		enddate = driver.findElement(By.id("PARAM_END_DATE"));
		
		
		Calendar cal = Calendar.getInstance();
		format = new SimpleDateFormat("MM/dd/yyyy");
		
		try {
			Date date = format.parse("09/24/2013");
			cal.setTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	//	setTime("9/24");
		
		startdate.clear();
		removeAlert();
		
		startdate.sendKeys("09/24/2013");
		driver.findElement(By.id("PARAM_START_DATE_label")).click();

		enddate.clear();
		removeAlert();
		enddate.sendKeys("09/25/2013");
		
			
		driver.findElement(By.id("PARAM_START_DATE_label")).click();
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		Select select = new Select(timesteps); 
		select.selectByValue("HOUR");
		
		submit.click();
		
		try{	
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement reportpage = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
			
			
			hour = reportpage.findElements(By.tagName("div"));
			int startpos  = 0, endpos = 0;		
			
			for (int i = 0;i < hour.size(); i++)
			{				
				//logger.info(week.get(i).getText() + "    " + i);
				if (hour.get(i).getText().equals("Hour"))
					startpos = i; 
				else if(hour.get(i).getText().equals("Additional Filters:")) {
					{endpos = i; logger.info("endpos"+ endpos);}
					break;
				}
				else if(hour.get(i).getText().contains("partial")) {
					endpos = i; 
					break;
				}
				else if(hour.get(i).getText().contains("Page 1")) {
					endpos = i;
					break;
				}
					
			}
			startpos += 45;

			for (;startpos < endpos; startpos += 19)
			{

				String weeksub = hour.get(startpos).getText().substring(0, 5);
				try {
					Date weekdate = hourformat.parse(weeksub);
					Hourlist.add(weekdate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					driver.quit();
				}
				
			}
			
			boolean result = true;
			for (int i = 0; i < Hourlist.size() - 1; i ++)
			{	
				if (Hourlist.get(i).after(Hourlist.get(i+1)))
					result = false;
			}
			
			if (result == true)
				ReportFile.addTestCase("ODI6.x-668:Time step hour sorting", "ODI6.x-668:Time step hour sorting => Pass");
			else
				ReportFile.addTestCase("ODI6.x-668:Time step hour sorting", "ODI6.x-668:Time step hour sorting => Fail");
		
		}
			
		catch(NoSuchElementException e)
		{
			logger.info("can't find element");
		}	
		catch(TimeoutException e) 
		{
			gotomainpage();
			hourSelectionSorting();
		}
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
		//driver.quit();
	}
	//ODI6.x-670:Time step week sorting
	public void weekSelectionSorting()
	{
		TrendReport();
		
		List<WebElement> week;
		ArrayList<Date> Weeklist = new ArrayList<Date>();	
		
		setTime("3 months");
		
		driver.findElement(By.id("PARAM_START_DATE_label")).click();
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		Select select = new Select(timesteps); 
		select.selectByValue("WEEK");
		
		submit.click();
		
		try{	
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement reportpage = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
			
			
			week = reportpage.findElements(By.tagName("div"));
			int startpos  = 0, endpos = 0;		
			
			for (int i = 0;i < week.size(); i++)
			{				
				//logger.info(week.get(i).getText() + "    " + i);
				if (week.get(i).getText().equals("Week"))
					startpos = i;
				else if(week.get(i).getText().equals("Additional Filters:")) {
					endpos = i;
					break;
				}
				else if(week.get(i).getText().contains("partial")) {
					endpos = i; 
					break;
				}
				else if(week.get(i).getText().contains("Page 1")) {
					endpos = i;
					break;
				}
				
			}
			startpos += 45;
			format = new SimpleDateFormat("yyyy/MM/dd");
			for (;startpos < endpos; startpos += 19)
			{
				String weeksub = week.get(startpos).getText().substring(0, 10);
				try {
					Date weekdate = format.parse(weeksub);
					System.out.println(weekdate);
					Weeklist.add(weekdate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					driver.quit();
				}
				
			}
			
			boolean result = true;
			for (int i = 0; i < Weeklist.size() - 1; i ++)
			{
				if (Weeklist.get(i).after(Weeklist.get(i+1)))
					result = false;
			}
			
			if (result == true)
				ReportFile.addTestCase("ODI6.x-670:Time step week sorting", "ODI6.x-670:Time step week sorting => Pass");
			else
				ReportFile.addTestCase("ODI6.x-670:Time step week sorting", "ODI6.x-670:Time step week sorting => Fail");
		
		}
			
		catch(NoSuchElementException e)
		{
			logger.info("can't find element");
		}	
		catch(TimeoutException e) 
		{
			gotomainpage();
			weekSelectionSorting();
		}
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
		driver.quit();
	}
	
//ODI6.x-669:Time step day sorting
	public void daySelectionSorting()
	{
		TrendReport();
		
		List<WebElement> days;
		ArrayList<Date> Daylist = new ArrayList<Date>();	
		
		WebElement timeRange = driver.findElement(By.id("date_range"));
		Select select = new Select(timeRange);
		select.selectByValue("l30d");
		
		enddate = driver.findElement(By.id("PARAM_END_DATE"));
		String enddateValue = enddate.getAttribute("value");
		format = new SimpleDateFormat("yyyy/MM/dd");
		
		//Calendar cal = Calendar.getInstance();
		try {
			Date date = format.parse(enddateValue);
			//cal.setTime(date);
						
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		select = new Select(timesteps); 
		select.selectByValue("DAY");
		
		submit.click();
		
		try{	
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement reportpage = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));		
			days = reportpage.findElements(By.tagName("div"));
			int startpos  = 0, endpos = 0;		
			
			for (int i = 0;i < days.size(); i++)
			{				
				if (days.get(i).getText().equals("Day"))
					startpos = i;
				else if(days.get(i).getText().equals("Additional Filters:")) {
					endpos = i;
					break;
				}
				else if(days.get(i).getText().contains("partial")) {
					endpos = i; 
					break;
				}
				else if(days.get(i).getText().contains("Page 1")) {
					endpos = i;
					break;
				}
			}
			startpos += 43;
			
			boolean result = true;
			for (;startpos < endpos; startpos += 17)
			{
				String weeksub = days.get(startpos).getText().substring(0, 10);
				try {
					Date weekdate = format.parse(weeksub);
					System.out.println(weekdate);
					Daylist.add(weekdate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					driver.quit();
				}
				
			}
			
			for (int i = 0; i < Daylist.size() - 1; i ++)
			{
				if (Daylist.get(i).after(Daylist.get(i+1)))
					result = false;
					//ReportFile.addTestCase("ODI6.x-669:Time step day sorting", "ODI6.x-670:Time step day sorting => succeed");
					
			}
			if (result == true)
				ReportFile.addTestCase("ODI6.x-669:Time step day sorting", "ODI6.x-669:Time step day sorting => Pass");
			else
				ReportFile.addTestCase("ODI6.x-669:Time step day sorting", "ODI6.x-669:Time step day sorting => Fail");
		}
			
		catch(NoSuchElementException e)
		{
			logger.info("can't find element");
		}	
		catch(TimeoutException e) 
		{
			gotomainpage();
			weekSelectionSorting();
		}
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
		
	}
	//ODI6.x-671:Time step month sorting
	public void monthSelectionSorting()
	{
		TrendReport();
		
		List<WebElement> months;
		ArrayList<Date> Monthlist = new ArrayList<Date>();	
	
		DateFormat formatmonth = new SimpleDateFormat("MMM-yy");
		setTime("1 year");
		
		driver.findElement(By.id("PARAM_START_DATE_label")).click();
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		Select select = new Select(timesteps); 
		select.selectByValue("MONTH");
		
		submit.click();
		
		try{	
			new WebDriverWait(driver, 5).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement reportpage = new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
			
			
			months = reportpage.findElements(By.tagName("div"));
			int startpos  = 0, endpos = 0;		
			
			for (int i = 0;i < months.size(); i++)
			{				
				//logger.info(week.get(i).getText() + "    " + i);
				if (months.get(i).getText().equals("Month"))
					startpos = i;
				else if(months.get(i).getText().equals("Additional Filters:")) {
					endpos = i;
					break;
				}
				else if(months.get(i).getText().contains("partial")) {
					endpos = i; 
					break;
				}		
				else if(months.get(i).getText().contains("Page 1")) {
					endpos = i;
					break;
				}
			}
			
			startpos += 43;
			
			for (;startpos < endpos; startpos += 17)
			{
				logger.info(months.get(startpos).getText());
				String weeksub = months.get(startpos).getText().substring(0, 6);
				try {
					Date weekdate = formatmonth.parse(weeksub);
					Monthlist.add(weekdate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			boolean result = true;
			for (int i = 0; i < Monthlist.size() - 1; i ++)
			{
				if (Monthlist.get(i).after(Monthlist.get(i+1)))
					result = false;
			}
			
			if (result == true)
				ReportFile.addTestCase("ODI6.x-671:Time step month sorting", "ODI6.x-671:Time step month sorting => Pass");
			else
				ReportFile.addTestCase("ODI6.x-671:Time step month sorting", "ODI6.x-671:Time step month sorting => Fail");
		
		}
			
		catch(NoSuchElementException e)
		{
			logger.info("can't find element");
		}	
		catch(TimeoutException e) 
		{
			gotomainpage();
			monthSelectionSorting();
		}
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
		
	}
	//ODI6.x-672:Time step quarter sorting
	public boolean quarterSelectionSorting()
	{
		TrendReport();
		
		List<WebElement> quarters;
		ArrayList<Date> Quarterlist = new ArrayList<Date>();	
		HashMap<Date, String> quarterlist = new HashMap<Date, String>();
		DateFormat quarterformat = new SimpleDateFormat("yyyy");
		
		boolean result = false;
		setTime("13 months");
		
		driver.findElement(By.id("PARAM_START_DATE_label")).click();
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		Select select = new Select(timesteps); 
		select.selectByValue("QUARTER");
		
		submit.click(); 
		
		if (checkifalert() == true)
		{
			ReportFile.addTestCase("ODI6.x-672:Time step quarter sorting", "ODI6.x-672:Time step quarter sorting => fail");
			ReportFile.WriteToFile();
			gotomainpage();
			return result;
		}
			
		try{	
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement reportpage = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
			
			
			quarters = reportpage.findElements(By.tagName("div"));
			int startpos  = 0, endpos = 0;		
			
			for (int i = 0;i < quarters.size(); i++)
			{				
				//logger.info(week.get(i).getText() + "    " + i);
				if (quarters.get(i).getText().equals("Quarter"))
					startpos = i;
				else if(quarters.get(i).getText().equals("Additional Filters:")) {
					endpos = i;
					break;
				}
				else if(quarters.get(i).getText().contains("partial")) {
					endpos = i; 
					break;
				}
				else if(quarters.get(i).getText().contains("Page 1")) {
					endpos = i;
					break;
				}
			}
			startpos += 43;
			
			for (;startpos < endpos; startpos += 17)
			{
				logger.info(quarters.get(startpos).getText());
				String weeksub = quarters.get(startpos).getText().substring(0, 4);
				try {
					Date weekdate = quarterformat.parse(weeksub);
					quarterlist.put(weekdate, quarters.get(startpos).getText());
					Quarterlist.add(weekdate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					driver.quit();
				}
				
			}
			
			result = true;
			for (int i = 0; i < Quarterlist.size() - 1; i ++)
			{
				if (Quarterlist.get(i).after(Quarterlist.get(i+1)))
					result = false;
				else if (Quarterlist.get(i).equals(Quarterlist.get(i+1))) {
					String quarterbefore = quarterlist.get(Quarterlist.get(i));
					String quarterafter =  quarterlist.get(Quarterlist.get(i));
					int before = Integer.parseInt(quarterbefore.substring(quarterbefore.length() - 1));
					int after = Integer.parseInt(quarterafter.substring(quarterbefore.length() - 1));
				if (before > after)
					result = false;
				}
			}
			
			if (result == true)
				ReportFile.addTestCase("ODI6.x-672:Time step quarter sorting", "ODI6.x-672:Time step quarter sorting => succeed");
			else
				ReportFile.addTestCase("ODI6.x-672:Time step quarter sorting", "ODI6.x-672:Time step quarter sorting => fail");
		
		}
			
		catch(NoSuchElementException e)
		{
			logger.info("can't find element");
		}	
		catch(TimeoutException e) 
		{
			gotomainpage();
			quarterSelectionSorting();
		}
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
		
		return result;
		
	}
	//ODI6.x-673:Time step year sorting	
	public boolean yearSelectionSorting()
	{
		choosedomain("METROPCS");
		TrendReport();
		
		List<WebElement> years;
		ArrayList<Date> Weeklist = new ArrayList<Date>();	
		boolean result = false;
		setTime("3 years");
		
		driver.findElement(By.id("PARAM_START_DATE_label")).click();
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		Select select = new Select(timesteps); 
		select.selectByValue("YEAR");
		
		submit.click();
		
		if (checkifalert() == true)
		{
			ReportFile.addTestCase("ODI6.x-673:Time step year sorting", "ODI6.x-673:Time step year sorting => fail");
			ReportFile.WriteToFile();
			gotomainpage();
			return result;
		}
		
		try{	
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement reportpage = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
			
			
			years = reportpage.findElements(By.tagName("div"));
			int startpos  = 0, endpos = 0;		
			
			for (int i = 0;i < years.size(); i++)
			{
				if (years.get(i).getText().equals("Year"))
					startpos = i;
				else if(years.get(i).getText().equals("Additional Filters:"))
					endpos = i; 
			}
			
			startpos += 43;
			
			for (;startpos < endpos; startpos += 17)
			{
				logger.info(years.get(startpos).getText());
				String weeksub = years.get(startpos).getText().substring(0, 10);
				try {
					Date weekdate = format.parse(weeksub);
					Weeklist.add(weekdate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					driver.quit();
				}
				
			}
			
			for (int i = 0; i < Weeklist.size() - 1; i ++)
			{
				if (Weeklist.get(i).before(Weeklist.get(i+1)))
					{
						ReportFile.addTestCase("ODI6.x-673:Time step year sorting", "ODI6.x-673:Time step year sorting => succeed");
						result = true;
					}
				else
					{
						ReportFile.addTestCase("ODI6.x-673:Time step year sorting", "ODI6.x-673:Time step year sorting => failed");
						result = false;
					}
					
			}
		
		}
			
		catch(NoSuchElementException e)
		{
			logger.info("can't find element");
		}	
		catch(TimeoutException e) 
		{
			gotomainpage();
			yearSelectionSorting();
		}
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
		return result;
		
	}
	public void numberFormat () {
		String content;
		choosedomain("METROPCS");
		TrendReport();
		setTime("random");
		boolean result = false;
		String contentValue;
		try{
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement page =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewer")));
			List<WebElement> check= page.findElements(By.tagName("span"));
			for(int i=0;i<check.size();i++)
			{
				result=true;
				content=check.get(i).getText();
				//for all counts number(call volume and transfers(All) )
				if(content.equals("Call Volume")){
					contentValue = check.get(i+1).getText();
					result=USformatCheck(contentValue);
				}
				else if(content.equals("Transfers (All)"))
				{
					contentValue=check.get(i+1).getText();
					result=USformatCheck(contentValue);
				}	
				else if(content.equals("Call Duration (minutes)"))
				{
					contentValue=check.get(i+1).getText();
					result=decimalcheck(contentValue, "In call duration", 2);	
				}
				else if(content.equals("Average Call Duration (minutes)"))
				{
					contentValue=check.get(i+1).getText();
					result =decimalcheck(contentValue,"In average call duration",2 );
				}				
				else if(content.equals("Average Call Duration for Transferred Calls (minutes)"))
				{
					contentValue=check.get(i+1).getText();
					result=decimalcheck(contentValue, "In average call duration for transfered calls", 2);	
				}
				else if(content.equals("Peak Hour Average Call Duration (minutes)"))
				{
					contentValue=check.get(i+1).getText();
					result=decimalcheck(contentValue, "In peak hour average call duration", 2);
				}
				else if (content.equals("Average Weekly Call Volume")){
					contentValue=check.get(i+1).getText();
					result=decimalcheck(contentValue, "Average Weekly call volume", 1);
				}

				else if (content.equals("Transfer Rate (All)")){
					String percentage=check.get(i+1).getText();
					String[] contentValues= percentage.split("%"); 
					contentValue= contentValues[0];
					result=decimalcheck(contentValue, "In Transfer Rate ", 1);
				}
				else if(content.equals("Containment Rate (All)")){
					String percentage=check.get(i+1).getText();
					String[] contentValues= percentage.split("%"); 
					contentValue= contentValues[0];
					result=decimalcheck(contentValue, "In Containment Rate ", 1);
				}
				else
					result = false;
			}
			if(result)
			ReportFile.addTestCase("ODI6.x-628:CSSR numbers Formatting", "ODI6.x-628:CSSR numbers Formatting => Pass");
		}catch(Exception e)
		{
			ReportFile.addTestCase("ODI6.x-628:CSSR numbers Formatting", "ODI6.x-628:CSSR numbers Formatting => Fail");
			e.printStackTrace();
		}
		driver.switchTo().defaultContent();
		gotomainpage();
		//driver.quit();
	}

	//Method to invoke decimal checking in the above method
	private boolean decimalcheck(String wholeString, String logText, int precision) {
		String[] contents = wholeString.split("\\s");
		double contentsConvert= Double.parseDouble(contents[1]);
		if (checkDecimalPlaces(contentsConvert, precision) == true){
			logger.info(logText+" "+ "we have found the decimals with "+ precision +" precision");
		}
		else return false;
		return true;
		
	}
	

	//Method that checks for 2 decimal places in above function
	public boolean checkDecimalPlaces (double digit, int decimalPlaces){
		if (digit==0) return true;
	    double multiplier = Math.pow(10, decimalPlaces); 
	    double check  =  digit * multiplier;
	    check = Math.round(check);  	
	    check = check/multiplier; 
		return (digit==check);
	}
	public boolean USformatCheck(String x){
		String[] contents = x.split("\\s");	
		String regex = "^[0-9]{1,3}(?:,[0-9]{3})*$";
		boolean isMatched = contents[1].matches(regex);
		//	boolean f ="12".equals(nf_us);
	//	boolean a = contentsConvert.equals(nf_us.isGroupingUsed());
	//	boolean b = "1,000".equals(nf_us.isGroupingUsed());
		return isMatched;
		
	}
	//679
		public void noData(){
			transferReport();
			try{
				setTime("noData");			
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
				WebElement page =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
				WebElement content=page.findElement(By.xpath("//*[contains(text(),'No')]"));
				String data=content.getText();
				logger.info(data);
				if("No data was found for the selected criteria.".equals(data))
				ReportFile.addTestCase("ODI6.x-688:veriry the empty report information", "ODI6.x-688:veriry the empty report information=> Pass");
				else ReportFile.addTestCase("ODI6.x-688:veriry the empty report information", "ODI6.x-688:veriry the empty report information=> Fail");
			}catch (NoSuchElementException e)
			{
				e.printStackTrace();
				ReportFile.addTestCase("ODI6.x-688:veriry the empty report information", "ODI6.x-688:veriry the empty report information => Fail");
			}
			ReportFile.WriteToFile();
			driver.switchTo().defaultContent();
			gotomainpage();
		}

	//ODI6.x-682:CSTR: Trend Report Layout
	public boolean ReportLayout() {
		
		TrendReport();
		boolean result = false;
	
		try{			
		
			WebElement timeRange = driver.findElement(By.id("date_range"));
			Select select = new Select(timeRange);
			select.selectByValue("l30d");
		
			WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
			timesteps.click();
			
			select = new Select(timesteps); 
			select.selectByValue("WEEK");
			
			submit.click();
		} catch (NoSuchElementException e)
		{
			gotomainpage();
			ReportLayout();
		}
		
		try{
			
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
			
			if(driver.findElement(By.xpath("//*[contains(text(),'Transfer')]")).isDisplayed())
				driver.findElement(By.xpath("//*[contains(text(),'Rate')]"));
					
			if(driver.findElement(By.xpath("//*[contains(text(),'Average')]")).findElement(By.xpath("//*[contains(text(),'Call')]")).isDisplayed())
				if(driver.findElement(By.xpath("//*[contains(text(),'Duration')]")).isDisplayed())
					if(driver.findElement(By.xpath("//*[contains(text(),'Transferred')]")).isDisplayed())
						if(driver.findElement(By.xpath("//*[contains(text(),'Calls')]")).isDisplayed())
							driver.findElement(By.xpath("//*[contains(text(),'(minutes')]"));		
			
			ReportFile.addTestCase("ODI6.x-682:CSTR Trend Report Layout started", "ODI6.x-682:CSTR Trend Report Layout started => succeed");
		}
		catch(NoSuchElementException e)
		{
			ReportFile.addTestCase("ODI6.x-682:CSTR Trend Report Layout started", "ODI6.x-682:CSTR Trend Report Layout started => fail");
		} catch(TimeoutException  e)
		{
			driver.switchTo().defaultContent();
			gotomainpage();
			ReportLayout();
		}
		
		
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
		return result;
		
	}
	
	
	//operations for different time range
	public void timeperiodchange (String period)
	{
		boolean result = false;
		//String mainWinhandler = driver.getWindowHandle();
	
		startdate = driver.findElement(By.id("PARAM_START_DATE"));
		enddate = driver.findElement(By.id("PARAM_END_DATE"));
		startdatetime = driver.findElement(By.id("PARAM_START_DATEtime"));
		enddatetime = driver.findElement(By.id("PARAM_END_DATEtime"));
		
		String enddateValue = enddate.getAttribute("value");
		String enddatetimeValue = enddatetime.getAttribute("value");
		
		((JavascriptExecutor)driver).executeScript("$('#PARAM_START_DATEtime').attr('readonly',false)");	
		((JavascriptExecutor)driver).executeScript("$('#PARAM_END_DATEtime').attr('readonly',false)");	

		
		Calendar cal = Calendar.getInstance();
		format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		try {
			Date date = format.parse(enddateValue + " " + enddatetimeValue);
			cal.setTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		startdate.clear();
		removeAlert();
				
		if (period.equals("one hour"))
			{
			try {
			
				String[] starttimes = format.format(cal.getTime()).split(" ");
				
				cal.add(Calendar.HOUR,1);			
				String[] endblock = format.format(cal.getTime()).split(" ");
				
				startdate.sendKeys(starttimes[0]);
				startdatetime.sendKeys(starttimes[1]);
				
				enddate.clear();
				removeAlert();
				
				
				enddate.sendKeys(endblock[0]);
				
				enddatetime.clear();
							
				enddatetime.sendKeys(endblock[1]);
	
				WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
				timesteps.click();
				
				Select select = new Select(timesteps);
				List<WebElement> timestep = select.getOptions();
				
				if (timestep.get(0).isEnabled() == true  )
				{
					for (int i = 1; i <timestep.size(); i++)
					{
						select.selectByIndex(i);
						if (timestep.get(i).isEnabled() == true)
							{
							timestepTestresult = timestepTestresult + " Test case one hour period failed;";  
							break;
							}
						else
							timestepTestresult = timestepTestresult + " Test case one hour period succeed;";							
					}
			}
		}
		catch (NoAlertPresentException e)
		{
			e.printStackTrace();
			driver.quit();
		}
		}
		else if (period.equals("one day"))
		{
			cal.add(Calendar.DATE,-1);		
			result = teststeps(cal, true, true, false, false, false, false);		
			timestepTestresult += "Time period one day " + result + ";/s";

		}
		else if (period.equals("25 hours"))
		{
			cal.add(Calendar.HOUR,-25);
			result = teststeps(cal, true, true, false, false, false, false);
			timestepTestresult += "Time period 25 hours " + result + ";/s";

		}
		else if (period.equals("26 hours"))
		{
			cal.add(Calendar.HOUR,-26);
			result = teststeps(cal, false, true, false, false, false, false);
			timestepTestresult += "Time period 26 hours " + result + ";/s";

		}
		else if (period.equals("one week"))
		{
			cal.add(Calendar.DATE,-7);
			result = teststeps(cal, false, true, true, false, false, false);
			timestepTestresult += "Time period one week " + result + ";/s";

		}
		else if (period.equals("one month"))
		{
			cal.add(Calendar.MONTH, -1);
			result = teststeps(cal, false, true, true, true, false, false);
			timestepTestresult += "Time period one month " + result + ";/s";

		}
		else if (period.equals("32 days"))
		{
			cal.add(Calendar.DATE, -32);
			result = teststeps(cal, false, true, true, true, false, false);
			timestepTestresult += "Time period 32 days " + result + ";/s";

		}
		else if (period.equals("33 days"))
		{
			cal.add(Calendar.DATE, -33);
			result = teststeps(cal, false, false, true, true, false, false);		
			timestepTestresult += "Time period 33 days " + result + ";/s";

		}
		else if (period.equals("3 months"))
		{
			cal.add(Calendar.MONTH, -3);
			result = teststeps(cal, false, false, true, true, true, false);
			timestepTestresult += "Time period 3 months " + result + ";/s";

		}
		else if (period.equals("5 months"))
		{
			cal.add(Calendar.MONTH, -5);
			result = teststeps(cal, false, false, true, true, true, false);
			timestepTestresult += "Time period 5 months " + result + ";/s";

		}
		else if (period.equals("6 months & 1 day"))
		{
			cal.add(Calendar.MONTH, -6);
			cal.add(Calendar.DATE, -1);
			result = teststeps(cal, false, false, false, true, true, false);
			timestepTestresult += "Time period 6 months & 1 day " + result + ";/s";
		}
		else if (period.equals("7 months"))
		{
			cal.add(Calendar.MONTH, -7);
			result = teststeps(cal, false, false, false, true, true, false);
			timestepTestresult += "Time period 7 months " + result + ";/s";
		}
		else if (period.equals("1 year"))
		{
			cal.add(Calendar.YEAR, -1);
			result = teststeps(cal, false, false, false, true, true, true);
			timestepTestresult += "Time period 1 year " + result + ";";
		}
		else if (period.equals("13 months"))
		{
			cal.add(Calendar.MONTH, -13);
			result = teststeps(cal, false, false, false, true, true, true);
			timestepTestresult += "Time period 13 months " + result + ";/s";
		}
		else if (period.equals("5 years"))
		{
			cal.add(Calendar.YEAR, -5);
			result = teststeps(cal, false, false, false, false, true, true);
			timestepTestresult += "Time period 5 years " + result + ";/s";
		}
		else if (period.equals("6 years"))
		{
			cal.add(Calendar.YEAR, -6);
			result = teststeps(cal, false, false, false, false, false, true);
			timestepTestresult += "Time period 6 years " + result + ";/s";
		}
		
	
}
		
	//test different time period.	
	public boolean teststeps(Calendar calender, boolean hour, boolean day, boolean week, boolean month, boolean quarter,boolean year)
	{
		boolean result = false;
		
		String[] starttimes = format.format(calender.getTime()).split(" ");
		startdate.sendKeys(starttimes[0]);
		
		if (day == true)
			{
				startdatetime.clear();
				startdatetime.sendKeys(starttimes[1]);
			}
		
		driver.findElement(By.id("PARAM_START_DATE_label")).click();
		
		WebElement timesteps = driver.findElement(By.id("PARAM_TIME_STEP"));
		timesteps.click();
		
		Select select = new Select(timesteps);
		List<WebElement> timestep = select.getOptions();
		
		if(timestep.get(0).isEnabled() == hour )
			{			
				if(timestep.get(1).isEnabled() == day)
				{				
					if (timestep.get(2).isEnabled() == week)
					{				
						if (timestep.get(3).isEnabled() == month)
						{				
							if (timestep.get(4).isEnabled() == quarter)
							{			
								if (timestep.get(5).isEnabled() == year)
								{			
									result = true;
								}
							}
						}
					}
				}
			}
		
			return result;
	}
	
	// test case :updated based on time period
	public void updatedBasedOnTimePeriod()
	{
		String TestCase = "ODI6.x-661:Time Step value is updated based on time period selection";
		
		TrendReport();
		
		timestepTestresult = "";
		
		String[] timeperiod = {"one hour","one day","25 hours","26 hours","one week","one month","32 days","33 days",
				"3 months","5 months","6 months","7 months","1 year","13 months","5 years","6 years"}; 
		for (String s : timeperiod)
		{
			timeperiodchange(s);
		}
		logger.info(timestepTestresult);
		ReportFile.addTestCase(TestCase, timestepTestresult);
		
		//ReportFile.WriteToFile();
		//driver.quit();
	}

	public boolean checkifalert()
	{
		try {
			driver.switchTo().alert().dismiss();
			
			return true;
		}
		catch(NoAlertPresentException ex)
		{
			return false;
		}
	}
	public void exitdriver(){
		driver.quit();
	}
	
	
}
	

