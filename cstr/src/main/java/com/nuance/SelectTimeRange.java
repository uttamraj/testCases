package com.nuance;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Main;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//CSSR
public class SelectTimeRange extends Mainpage{
	
	public static Logger logger = LoggerFactory.getLogger(Main.class);
	
	public SelectTimeRange(){
	gotomainpage();
	}
	//odi.614 Able to generate reports for time range
	public void search(){
		cssrReport();
		try{
			driver.findElement(By.id("PARAM_START_DATE")).clear();
			removeAlert();
			driver.findElement(By.id("PARAM_START_DATE")).sendKeys("8/11/2013");
			driver.findElement(By.id("PARAM_END_DATE")).clear();
			removeAlert();
			driver.findElement(By.id("PARAM_END_DATE")).sendKeys("9/11/2013");
			driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement page =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewer")));
			WebElement rangeStart=page.findElement(By.xpath("//*[contains(text(),'8/11/2013')]"));
			WebElement rangeEnd=page.findElement(By.xpath("//*[contains(text(),'9/11/2013')]"));
				if(rangeStart!=null&&rangeEnd!=null)
				{
				logger.info("Report for selected range is showed");
				ReportFile.addTestCase("ODI6.x-614:CSSR-Search", "ODI6.x-614:CSSR-Search => Pass");
				}
				else ReportFile.addTestCase("ODI6.x-614:CSSR-Search", "ODI6.x-614:CSSR-Search => Fail");
			}
			catch (NoSuchElementException e)
			{
				ReportFile.addTestCase("ODI6.x-614:CSSR-Search", "ODI6.x-614:CSSR-Search => Fail");
				logger.info("Caught NoSuchElementException:"+ e.getMessage());
			}
		ReportFile.WriteToFile();
		driver.switchTo().defaultContent();
		gotomainpage();
	}		
	//odi.618 Method for finding the lables of filters 
	public void findTheLables(){
		String Element1 = "TimeRange";
		String Element2 = "StartTime";
		String Element3 = "EndTime";
		String Element4 = "TimeZone";
		String Element5 = "Application";
		String Element6 = "DNIS";
		String Element7 = "Language";
		cssrReport();
		try{	
			Boolean filter1= isElementpresent(Element1, By.id("date_range_label"));
			Boolean filter2=isElementpresent(Element2, By.id("PARAM_START_DATE_label"));
			Boolean filter3=isElementpresent(Element3, By.id("PARAM_END_DATE_label"));
			Boolean filter4=isElementpresent(Element4, By.id("PARAM_TIME_ZONE_label"));
			Boolean filter5=isElementpresent(Element5, By.id("PARAM_APP_ID_label"));
			Boolean filter6=isElementpresent(Element6, By.id("PARAM_DNIS_label"));
			Boolean filter7=isElementpresent(Element7, By.id("PARAM_LOCALE_label"));
			if(filter1 && filter2 && filter3 && filter4 && filter5 && filter6 && filter7 )
				ReportFile.addTestCase("ODI6.x-618:Labeling of Call Stat Summary filters", "ODI6.x-618:Labeling of Call Stat Summary filters => Pass");
			else
				ReportFile.addTestCase("ODI6.x-618:Labeling of Call Stat Summary filters", "ODI6.x-618:Labeling of Call Stat Summary filters => Fail");
		}
		catch (Exception e)
		{
			ReportFile.addTestCase("ODI6.x-618:Labeling of Call Stat Summary filters", "ODI6.x-618:Labeling of Call Stat Summary filters => Fail");
			logger.info("Exception: "+ e);
		}
		ReportFile.WriteToFile();
		driver.switchTo().defaultContent();
		gotomainpage();
	}
	
	//Method that I invoke in findlablesfilter() function 
	public boolean isElementpresent(String name, By by){
		try
		{
			if(driver.findElements(by).isEmpty())
			{
				logger.info(name +" "+"Element is absent");
				return false;
			}else
			{
				logger.info(name+" "+"Element  is Present");			
			}	
		} 
		catch (Exception e) 
		{
			logger.info("Exception" + e);
		}
		return true;
	}
	//619
	public void abFilterSelectable(){
		choosedomain("METROPCS");
		cssrReport();
		WebElement ABvalue = null;
		boolean result,selectValuesPresent;
		try{
			boolean isPresent = driver.findElements(By.id("PARAM_APP_VARIANT_ID")).size()>0;
			if(isPresent){
				logger.info("A/B filter values are present");
				ABvalue = driver.findElement(By.id("PARAM_APP_VARIANT_ID"));
				Select select = new Select(ABvalue);
				result= select.isMultiple();
				List<WebElement> selectValues=select.getOptions();
				selectValuesPresent = false;
				for (int i=0;i<selectValues.size(); i++)
				{
					if(selectValues.get(i).getText().equals("All"))
					selectValuesPresent =true;
					else if(selectValues.get(i).getText().equals("UNKNOWN"))
					selectValuesPresent = true;
					else selectValuesPresent =false;
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
	//odi.620 Method that the report footer a/b filter	
	public void abFilter(){
		choosedomain("METROPCS");
		cssrReport();
		try{
			WebElement ABvalue = driver.findElement(By.id("PARAM_APP_VARIANT_ID"));
			Select select = new Select(ABvalue);
			select.deselectAll();
			select.selectByIndex(1);
			String selectText = select.getFirstSelectedOption().getText();
			//select.selectByValue("NVP 5.0.0-NDF 6.1.0-0.0.0.1");
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
			logger.info("Caught NoSuchElementException:"+ e.getMessage());
		}
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
	}
	
	//622
	public void DNISFilterNewLook()
	{
		choosedomain("METROPCS");
		cssrReport();
		WebElement DNIS;
		Select select;
		try{
			DNIS = driver.findElement(By.id("PARAM_DNIS"));
			select = new Select(DNIS);
			List<WebElement> allop = select.getOptions();
				for (int i=0;i<allop.size();i++) 
				{ //iterate over the options
				    if(allop.size()>0)
				    	if(allop.get(0).getText().equals("All"))
				    		if(select.getFirstSelectedOption().equals("All"))//returns the current selected option
				    			if(select.isMultiple())
				    			{
				    				select.selectByValue("All");//select option All
							    	if(select.getFirstSelectedOption().equals("All"))
							    		ReportFile.addTestCase("ODI6.x-622:DNIS filter: new look of the drop down list", "ODI6.x-622:DNIS filter: new look of the drop down list=> Pass");
							    	else
							    		ReportFile.addTestCase("ODI6.x-622:DNIS filter: new look of the drop down list", "ODI6.x-622:DNIS filter: new look of the drop down list => Fail");
				    			}
							    else
							    	ReportFile.addTestCase("ODI6.x-622:DNIS filter: new look of the drop down list", "ODI6.x-622:DNIS filter: new look of the drop down list => Fail");
				    		else
				    			ReportFile.addTestCase("ODI6.x-622:DNIS filter: new look of the drop down list", "ODI6.x-622:DNIS filter: new look of the drop down list => Fail");
				    	else
				    		ReportFile.addTestCase("ODI6.x-622:DNIS filter: new look of the drop down list", "ODI6.x-622:DNIS filter: new look of the drop down list => Fail");
				    else
				    	ReportFile.addTestCase("ODI6.x-622:DNIS filter: new look of the drop down list", "ODI6.x-622:DNIS filter: new look of the drop down list => Fail");
				}
				
		} catch (NoSuchElementException e)
		{
			ReportFile.addTestCase("ODI6.x-622:DNIS filter: new look of the drop down list", "ODI6.x-622:DNIS filter: new look of the drop down list => Fail");
		}
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();	
		gotomainpage();	
		
	}
	//odi.623.Method for DNIS value search
	public void dnisFilter(){
		choosedomain("US_AIRWAYS");
		cssrReport();
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
			{
			logger.info(String.format("DNIS: %s", selectText )+" is present");
			ReportFile.addTestCase("ODI6.x-623:DNIS filter: selected DNIS filter should be shown in report result", "ODI6.x-623:DNIS filter: selected DNIS filter should be shown in report result => Pass");
			}
	
		}catch (NoSuchElementException e)
		{
			ReportFile.addTestCase("ODI6.x-623:DNIS filter: selected DNIS filter should be shown in report result", "ODI6.x-623:DNIS filter: selected DNIS filter should be shown in report result => Fail");
			logger.info("Caught NoSuchElementException:"+ e.getMessage());
		}
		ReportFile.WriteToFile();
		driver.switchTo().defaultContent();
		gotomainpage();
	}
	
	
	//Method for selecting a date
	public void pickAvalidDate (String startDate, String endDate){
		driver.findElement(By.id("PARAM_START_DATE")).clear();
		removeAlert();
		driver.findElement(By.id("PARAM_START_DATE")).sendKeys(startDate);//"08/01/2013"
		driver.findElement(By.id("PARAM_END_DATE")).clear();
		removeAlert();
		driver.findElement(By.id("PARAM_END_DATE")).sendKeys(endDate);//"09/01/2013"
		submit.click();
	}
	
	//ODI6.x-627:generate cssr report with valid filters and check the number if they are reasonable
	public void cssrNumbers (){
		choosedomain("METROPCS");
		int CallVolumeValue =0, TransfersValue = 0, PeakHourValue = 0;
		double roundOff =0, AverageCallValue =0;
		boolean flag;
		cssrReport();
		pickAvalidDate("08/01/2013","09/01/2013");
		try{
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement page =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewer")));
			List<WebElement> check= page.findElements(By.tagName("span"));
				for(int i=0;i<check.size();i++)
				{
					String content= (check.get(i).getText());
					if (content.equals("Call Volume")){
						String CallVolume = (check.get(i+1).getText());
						String[] CallVolumes = CallVolume.split("\\s");
						CallVolumeValue= Integer.parseInt(CallVolumes[1]);
					}
					else if(content.equals("Transfers (All)")){
						String  TransfersAll= (check.get(i+1).getText());
						String[] Tvalues= TransfersAll.split("\\s");
						TransfersValue=Integer.parseInt(Tvalues[1]);
					}
					else if(content.equals("Peak Hour Call Volume")){
						String PeakHourCallVolume = (check.get(i+1).getText());
						String[] PeakHourCallValues= PeakHourCallVolume.split("\\s");
						PeakHourValue = Integer.parseInt(PeakHourCallValues[1]);
					}
					else if (content.equals("Call Duration (minutes)")){
						String CallDuration= (check.get(i+1).getText());
						String[] CallDurationValues= CallDuration.split("\\s");
						double CallDurationValue=Double.parseDouble(CallDurationValues[1]);
						double x= CallDurationValue/CallVolumeValue;
					    roundOff =Math.round(x*100.0)/100.0;
					}
					else if (content.equals("Average Call Duration (minutes)")){
						String AverageCallDuration= (check.get(i+1).getText());
						String[] AverageCallDurationValues= AverageCallDuration.split("\\s");
						AverageCallValue= Double.parseDouble(AverageCallDurationValues[1]);					
					}
					else flag = false;
				}
				try{
			
					if (CallVolumeValue >= TransfersValue){
						logger.info("the call volume is greater than the Transfer");
						flag = true;
					}
					else flag = false;
					if (PeakHourValue <= CallVolumeValue){
						logger.info("PeakHourValue <= CallVolumeValue");
						flag=true;
					}
					else flag = false;
					 if (AverageCallValue == roundOff){
						 logger.info("Average Call Duration =Call Duration (minutes)/Call Volume");
						 flag=true;
					 }
					 else flag = false;
					 if(flag)
						 ReportFile.addTestCase("ODI6.x-627:CSSR numbers must be reasonable", "ODI6.x-627:CSSR numbers must be reasonable => Pass");
					 else
						 ReportFile.addTestCase("ODI6.x-627:CSSR numbers must be reasonable", "ODI6.x-627:CSSR numbers must be reasonable => Fail");
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				
			}catch(Exception e)
			{
				ReportFile.addTestCase("ODI6.x-627:CSSR numbers must be reasonable", "ODI6.x-627:CSSR numbers must be reasonable => Fail");
				e.printStackTrace();
			}
			ReportFile.WriteToFile();
			driver.switchTo().defaultContent();
			gotomainpage();
			
		}
	
	//ODI6.x-628:Number formatting
	public void numberFormat () {
		String content;
		choosedomain("METROPCS");
		cssrReport();
		pickAvalidDate("08/01/2013","09/01/2013");
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
	public boolean decimalcheck(String wholeString, String logText, int precision) {
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
	//631
	public void noData(){
		transferReport();
		try{
			pickAvalidDate("10/07/2008","10/08/2008");//these date have no data			
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


}
