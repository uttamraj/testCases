package com.nuance;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


//CTS
public class CallTransferReportTests extends Mainpage{
	//constructor
	public CallTransferReportTests() {
		gotomainpage();
		}
	//686 transfercalls
	public void transferCall(){
		transferReport();
		String callCount = null;
		String callTransfer = null;
		try{
			driver.findElement(By.id("PARAM_START_DATE")).clear();
			removeAlert();
			driver.findElement(By.id("PARAM_START_DATE")).sendKeys("8/11/2013");
			driver.findElement(By.id("PARAM_END_DATE")).clear();
			removeAlert();
			driver.findElement(By.id("PARAM_END_DATE")).sendKeys("9/11/2013");
			driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement page =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
			List<WebElement> check= page.findElements(By.tagName("span"));
			for(int i=0; i <check.size(); i++)
			{
				String content = (check.get(i).getText());//get the webelement first and then its gets its text
				if(content.equals("%")){
					callCount = check.get(i-1).getText();
					callTransfer = check.get(i-3).getText();
					//logger.info(callCount +"more"+ callcount1 );
					break;
				}else
					ReportFile.addTestCase("ODI6.x-686:CTR-GUI:Transfer Calls", "ODI6.x-686:CTR-GUI:Transfer Calls => Fail");
				if("Call Count".equals(callCount)){
					if("Transfer".equals(callTransfer))
						ReportFile.addTestCase("ODI6.x-686:CTR-GUI:Transfer Calls", "ODI6.x-686:CTR-GUI:Transfer Calls => Pass");
				}
				else
						ReportFile.addTestCase("ODI6.x-686:CTR-GUI:Transfer Calls", "ODI6.x-686:CTR-GUI:Transfer Calls => Fail");
			}
		}catch(NoSuchElementException e)
		{
			logger.info("Caught NoSuchElementException:"+ e.getMessage());
			ReportFile.addTestCase("ODI6.x-686:CTR-GUI:Transfer Calls", "ODI6.x-686:CTR-GUI:Transfer Calls => Fail");
		} 
		ReportFile.WriteToFile();
		driver.switchTo().defaultContent();
		gotomainpage();
	}
	//688
	public void noData(){
		transferReport();
		try{
			driver.findElement(By.id("PARAM_START_DATE")).clear();
			removeAlert();
			driver.findElement(By.id("PARAM_START_DATE")).sendKeys("10/07/2008");
			driver.findElement(By.id("PARAM_END_DATE")).clear();
			removeAlert();
			driver.findElement(By.id("PARAM_END_DATE")).sendKeys("10/08/2008");
			driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
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
			logger.info("Caught NoSuchElementException:"+ e.getMessage());
			ReportFile.addTestCase("ODI6.x-688:veriry the empty report information", "ODI6.x-688:veriry the empty report information => Fail");
		}
		ReportFile.WriteToFile();
		driver.switchTo().defaultContent();
		gotomainpage();
	}
//	689
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
				ReportFile.addTestCase("ODI6.x-689:CTR-Search", "ODI6.x-689:CTR-Search=> Pass");
				}
			ReportFile = new WriteXmlFile();
			}
			catch (NoSuchElementException e)
			{
				ReportFile.addTestCase("ODI6.x-689:CTR-Search", "ODI6.x-689:CTR-Search=> Fail");
				
			}
		ReportFile.WriteToFile();
		driver.switchTo().defaultContent();
		gotomainpage();
		}
		
	//694 just check for matching left and right hand side
	public void abfilter(){
		//String dmName="METROPCS";
		
		choosedomain("METROPCS");
		transferReport();
		try{
			WebElement ABvalue = driver.findElement(By.id("PARAM_APP_VARIANT_ID"));
			Select select = new Select(ABvalue);
			select.deselectAll();
			select.selectByValue("NVP 5.0.0-NDF 6.1.0-0.0.0.1");
			submit.click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement page =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewer")));
			WebElement tag=page.findElement(By.xpath("//*[contains(text(),'A/B')]"));
			String content=tag.getText();
			logger.info(content);
			if("A/B Combo ID: NVP 5.0.0-NDF 6.1.0-0.0.0.1".equals(content))
				{
				logger.info("A/B Combo ID: NVP 5.0.0-NDF 6.1.0-0.0.0.1 filter is present");
				ReportFile.addTestCase("ODI6.x-694:A/B filter: should be in the report footer", "ODI6.x-694:A/B filter: should be in the report footer=> Pass");
				}
			else ReportFile.addTestCase("ODI6.x-694:A/B filter: should be in the report footer", "ODI6.x-694:A/B filter: should be in the report footer=> Fail");
		}catch (NoSuchElementException e)
		{
			ReportFile.addTestCase("ODI6.x-694:A/B filter: should be in the report footer", "ODI6.x-694:A/B filter: should be in the report footer=> Fail");
			
		}
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
		
	}
	//693 still write code for filter check on leftside  filter is multi-selectable and has values all, filter a, filter b, unknown
	public void abfilterselectable(){
		choosedomain("METROPCS");
		transferReport();
		boolean result;
		try{
			Boolean ABfilter= driver.findElements(By.id("PARAM_APP_VARIANT_ID_label")).isEmpty();
			WebElement ABvalue = driver.findElement(By.id("PARAM_APP_VARIANT_ID"));
			Select select = new Select(ABvalue);
				result= select.isMultiple();
				if(result&&(!ABfilter)){
					logger.info("A/B filter is present and multi-selectable");
					List<WebElement> filters=select.getOptions();
					for(int i=0;filters.size()<0;i++){
							if(filters.get(i).getText().equals("All"))
								logger.info("The filter has option: All");
							else if (filters.get(i).getText().equals("unknown"))
								logger.info("The filter has option: All");
								ReportFile.addTestCase("ODI6.x-693:A/B filter: should be in the filter panel", "ODI6.x-693:A/B filter: should be in the filter panel=> Pass");
					}			
					
					ReportFile.addTestCase("ODI6.x-693:A/B filter: should be in the filter panel", "ODI6.x-693:A/B filter: should be in the filter panel=> Pass");
				}
				else ReportFile.addTestCase("ODI6.x-693:A/B filter: should be in the filter panel", "ODI6.x-693:A/B filter: should be in the filter panel => Fail");
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		driver.switchTo().defaultContent();
		ReportFile.WriteToFile();
		gotomainpage();
	}
	//696 have to rearrange reportfile
	public void DNISFilterNewLook()
	{
		choosedomain("METROPCS");
		transferReport();
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
	 public void getAllSelectedOptions() { 
		 Select selectBox = new Select( driver.findElement(By.id("PARAM_DNIS")));
		 List<WebElement> selectOptions = selectBox.getAllSelectedOptions(); 
		 for (WebElement temp : selectOptions) 
		 {
			 System.out.println("getText:" + temp.getText()); 
		 } 
	 } 
		//697
	public void dnisfilter(){
		choosedomain("US_AIRWAYS");
		transferReport();
		try{
		driver.switchTo().defaultContent();
		WebElement DNISvalue = driver.findElement(By.id("PARAM_DNIS"));
		Select select = new Select(DNISvalue);
		select.deselectAll();
		select.selectByIndex(1);
		String selectText = select.getFirstSelectedOption().getText();
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
	public void pickAvalidDate (){
		driver.findElement(By.id("PARAM_START_DATE")).clear();
		removeAlert();
		driver.findElement(By.id("PARAM_START_DATE")).sendKeys("08/01/2013");
		driver.findElement(By.id("PARAM_END_DATE")).clear();
		removeAlert();
		driver.findElement(By.id("PARAM_END_DATE")).sendKeys("09/01/2013");
		//submit.click();
	}

	//708
	public void timeRange(){
		choosedomain("US_AIRWAYS");
		transferReport();
		try{
			pickAvalidDate();
			submit.click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement page =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewer")));
			WebElement rangeStart=page.findElement(By.xpath("//*[contains(text(),'8/11/2013')]"));
			WebElement rangeEnd=page.findElement(By.xpath("//*[contains(text(),'9/11/2013')]"));
				if(rangeStart!=null&&rangeEnd!=null)
				{
				logger.info("Report for selected range is showed");
				ReportFile.addTestCase("ODI6.x-708:CTR-GUI-Standard Input :Time Range Start/end date", "ODI6.x-708:CTR-GUI-Standard Input :Time Range Start/end date=> Pass");
				}
			ReportFile = new WriteXmlFile();
			}
			catch (NoSuchElementException e)
			{
				ReportFile.addTestCase("ODI6.x-708:CTR-GUI-Standard Input :Time Range Start/end date", "ODI6.x-708:CTR-GUI-Standard Input :Time Range Start/end date => Fail");
				
			}
		ReportFile.WriteToFile();
		driver.switchTo().defaultContent();
		gotomainpage();
		}
	//713 buffer
	public void transferPercentage(){
		choosedomain("US_AIRWAYS");
		transferReport();
		try{
			//i think i have to check for the tag td whether it has attribute align=right then i can confirm it has a column
			//pickAvalidDate();
			driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement page =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewer")));
			WebElement percent= page.findElement(By.xpath("//span[contains(text(),'%')]"));
			String x = percent.getText();
			logger.info(x);
			ReportFile.addTestCase("ODI6.x-713:CSR-GUI-118 Transfer percentage", "ODI6.x-713:CSR-GUI-118 Transfer percentage => Pass");
		}
		catch (NoSuchElementException e)
		{
			ReportFile.addTestCase("ODI6.x-713:CSR-GUI-118 Transfer percentage", "ODI6.x-713:CSR-GUI-118 Transfer percentage=> Fail");
			
		}
		ReportFile.WriteToFile();
		driver.switchTo().defaultContent();
		gotomainpage();
	}
	//716 yet to complete 
	public void destinationGroup(){
		choosedomain("US_AIRWAYS");
		transferReport();
		try{
			driver.findElement(By.id("PARAM_START_DATE")).clear();
			removeAlert();
			driver.findElement(By.id("PARAM_START_DATE")).sendKeys("08/01/2013");
			driver.findElement(By.id("PARAM_END_DATE")).clear();
			removeAlert();
			driver.findElement(By.id("PARAM_END_DATE")).sendKeys("09/01/2013");
			WebElement x = driver.findElement(By.id("PARAM_HIDE_label"));
			if (x!=null)
			{
				logger.info("Element  is Present");
			}
			WebElement HideValue = driver.findElement(By.id("PARAM_HIDE"));
			Select select = new Select(HideValue);
			select.deselectAll();
			select.selectByVisibleText("Destination Group");
			submit.click();
			driver.switchTo().defaultContent();
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement page =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewer")));
			WebElement y = page.findElement(By.xpath("//span[contains(text(),'Destination')]"));
			boolean fb = y.getText().contains("Group");
			logger.info(y.getText());
			Boolean HiddenElement= y.isDisplayed();
			
			if (HiddenElement == false)
			{
				logger.info("Element  is not Present");
			}
			//driver.quit();
		}catch (Exception e)
		{
			e.printStackTrace();
			logger.info("Exception");
			
		}
	}
	//715
	public void destinationNumber(){
		choosedomain("US_AIRWAYS");
		transferReport();
				try{
					driver.findElement(By.id("PARAM_START_DATE")).clear();
					removeAlert();
					driver.findElement(By.id("PARAM_START_DATE")).sendKeys("08/01/2013");
					driver.findElement(By.id("PARAM_END_DATE")).clear();
					removeAlert();
					driver.findElement(By.id("PARAM_END_DATE")).sendKeys("09/01/2013");
					WebElement x = driver.findElement(By.id("PARAM_HIDE_label")); //or i can get text from this element and compare string in the If loop
					if (x!=null)
					{
						logger.info("Element  is Present");
					}
					//driver.switchTo().defaultContent();
					WebElement HideValue = driver.findElement(By.id("PARAM_HIDE"));
					Select select = new Select(HideValue);
					select.deselectAll();
					select.selectByVisibleText("Destination");
					driver.findElement(By.cssSelector("input[type=\"submit\"]")).sendKeys(Keys.ENTER);
					driver.findElement(By.cssSelector("#openLeftPane > a > img")).click();
					new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
					WebElement page =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewer")));
					WebElement y = page.findElement(By.xpath("//span[contains(text(),'Destination')]"));
					boolean fb = y.getText().contains("Number");
					logger.info(y.getText());
					driver.switchTo().defaultContent();
					select.deselectByVisibleText("Destination");;
					
					driver.findElement(By.cssSelector("input[type=\"submit\"]")).sendKeys(Keys.ENTER);
					new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
					WebElement page2 =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewer")));
					WebElement y1 = page2.findElement(By.xpath("//span[contains(text(),'Destination')]"));
					boolean fb2 = y1.getText().contains("Number");
					if (fb){
						ReportFile.addTestCase("ODI6.x-715:CSR-GUI-117: Hide/Unhide group by options \"Destination Number\"", "ODI6.x-715:CSR-GUI-117: Hide/Unhide group by options \"Destination Number\"=> Fail");//fail
					}else
						{
						if(fb2)
						{
							ReportFile.addTestCase("ODI6.x-715:CSR-GUI-117: Hide/Unhide group by options \"Destination Number\"", "ODI6.x-715:CSR-GUI-117: Hide/Unhide group by options \"Destination Number\"=> Pass");
						}
					}
				}catch (Exception e)
				{
					e.printStackTrace();
					logger.info("Exception");
					
				}
				ReportFile.WriteToFile();
				driver.switchTo().defaultContent();
				gotomainpage();
	}
	//690
	public void inboundcount(){
		choosedomain("METROPCS");
		transferReport();
		try{
			driver.findElement(By.id("PARAM_START_DATE")).clear();
			removeAlert();
			driver.findElement(By.id("PARAM_START_DATE")).sendKeys("8/11/2013");
			driver.findElement(By.id("PARAM_END_DATE")).clear();
			removeAlert();
			driver.findElement(By.id("PARAM_END_DATE")).sendKeys("9/11/2013");
			driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement page =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
			List<WebElement> check= page.findElements(By.tagName("span"));
			for(int i=0; i <check.size(); i++)
			{
				String content = (check.get(i).getText());//get the webelement first and then its gets its text
				if(content.equals("Inbound")){
					String callcount = check.get(i+2).getText();
					logger.info(callcount +"more"+ content );
					break;
				}
			}
			ReportFile.addTestCase("ODI6.x-690:CTR-GUI:Inbound Calls", "ODI6.x-690:CTR-GUI:Inbound Calls=> Pass");
		}catch(NoSuchElementException e)
		{
			e.printStackTrace();
			ReportFile.addTestCase("ODI6.x-690:CTR-GUI:Inbound Calls", "ODI6.x-690:CTR-GUI:Inbound Calls => Fail");
		} 
		ReportFile.WriteToFile();
		driver.switchTo().defaultContent();
		gotomainpage();
	}	
	//692
	//public void languageFilter(String test){ and pass the test number to choose domain method if it fails it will print the testcase failed due to no such domain
	//or if we can also push this test name t reportFile.addtest case to update if a testcase is failed
	public void languageFilter(){	
		try{
			choosedomain("US_AIRWAYS");
			transferReport();
			WebElement Language = driver.findElement(By.id("PARAM_LOCALE"));
			ReportFile.addTestCase("ODI6.x-692:Verify the Language filter is not missing", "ODI6.x-692:Verify the Language filter is not missing=> Pass");
		}catch(NoSuchElementException e){
			ReportFile.addTestCase("ODI6.x-692:Verify the Language filter is not missing", "ODI6.x-692:Verify the Language filter is not missing=> Fail");
		}
	}

	//699
	public void treeView(){
		choosedomain("METROPCS");
		transferReport();
		int appLeft = 0;
		int dnis1Left=0;
		int destinationGroupLeft=0;
		int destinationNumberLeft=0;
		int dnis2Left=0;
		try{
			
			driver.findElement(By.id("PARAM_START_DATE")).clear();
			removeAlert();
			driver.findElement(By.id("PARAM_START_DATE")).sendKeys("10/24/2013");
			driver.findElement(By.id("PARAM_END_DATE")).clear();
			removeAlert();
			driver.findElement(By.id("PARAM_END_DATE")).sendKeys("10/25/2013");
			submit.click();
			/**/
			driver.switchTo().defaultContent();
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement page =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
			Boolean content=page.findElements(By.xpath("//*[contains(text(),'No')]")).size()>0;
			if(!content)
			{
			//WebElement x = page.findElement(By.xpath("//span[contains(text(),'From')]"));
			List<WebElement> alldiv = page.findElements(By.tagName("div"));
			for(int j=85; j<alldiv.size(); j++)
			{
				//System.out.println(alldiv.get(j).getCssValue("top")+ ":"+j);
				//System.out.println(alldiv.get(j).getCssValue("text-align"));
				System.out.println(alldiv.get(j).getText()+ ":"+j);
				//if(alldiv.get(j).getText()
				if(alldiv.get(j).getText().equals("CARE")&&(!alldiv.get(j).getCssValue("left").equals("auto"))) 
					appLeft = Integer.valueOf(alldiv.get(j).getCssValue("left").replaceAll("\\D","")); //replaces the pixel 'px' with empty space
				else if(alldiv.get(j).getText().equals("000-000-0000")&&(!alldiv.get(j).getCssValue("left").equals("auto")))
					dnis1Left = Integer.valueOf(alldiv.get(j).getCssValue("left").replaceAll("\\D",""));
				else if(alldiv.get(j).getText().equals("Default")&&(!alldiv.get(j).getCssValue("left").equals("auto")))
					destinationGroupLeft = Integer.valueOf(alldiv.get(j).getCssValue("left").replaceAll("\\D",""));
				else if(alldiv.get(j).getText().equals("tel-:83-2626")&&(!alldiv.get(j).getCssValue("left").equals("auto")))
					destinationNumberLeft = Integer.valueOf(alldiv.get(j).getCssValue("left").replaceAll("\\D",""));
				else if(alldiv.get(j).getText().equals("888-888-8888")&&(!alldiv.get(j).getCssValue("left").equals("auto")))
					dnis2Left = Integer.valueOf(alldiv.get(j).getCssValue("left").replaceAll("\\D",""));
				logger.info("Left:"+appLeft+"dnis1left:"+dnis1Left+"DGL:"+destinationGroupLeft+"DNL:"+destinationNumberLeft+"d2l:"+dnis2Left);
			}
			if(dnis1Left>appLeft && dnis1Left<destinationGroupLeft)
				if(destinationGroupLeft>dnis1Left && destinationGroupLeft<destinationNumberLeft)
					if(dnis2Left>appLeft && dnis2Left<destinationGroupLeft)
					logger.info("pass");	
			}
		}catch(NoSuchElementException e){
			logger.info("fail");
		}
	}
	/*public void treeView(){
		choosedomain("METROPCS");
		transferReport();
		try{
			submit.click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement page =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
			WebElement element = page.findElement(By.xpath("//span[contains(text(),'Destination')]"));
			WebElement appElement = page.findElement(By.xpath("//span[contains(text(),'CARE')]"));
			int width=element.getSize().getWidth();
			int height=element.getSize().getWidth();
			Point TopLeftLocation = element.getLocation();
			//Point TopLeftLocation2 = appElement.getLocation();
			logger.info(element.getText()+"\n"+ "left:"+TopLeftLocation.x+"\ttop:"+ TopLeftLocation.y+ "  w:" + width+"  h:"+height);
			//System.out.println(appElement.getText()+TopLeftLocation2.x+"\t"+ TopLeftLocation2.y+ "w: " + width2+"h:"+height2);
		}catch(NoSuchElementException e){
			ReportFile.addTestCase("ODI6.x-699:Verify the Language filter is not missing", "ODI6.x-692:Verify the Language filter is not missing=> Fail");
		}
	}*/
	//700//oct 24 and OCT25 i think here i have to compare the config.properties file to the crystal report or else what i am doing here is just reading config.properties file
	public void ApplicationNet(){
		Properties prop = new Properties();
		try {
        prop.load(new FileInputStream("config.properties"));
		String appname1 = prop.getProperty("app.name1");
 		String app1Inbound = prop.getProperty(appname1+".totalInbound");
 		String app1totalTransfer = prop.getProperty(appname1+".totaltransfer");
 		String dnis1 = prop.getProperty(appname1.concat(".dnis1"));
        String dnis2 = prop.getProperty(appname1.concat(".dnis2"));
        String dnis1_total = appname1+"."+dnis1+(".totalInbound");
        String dnis1_totalTransfer = appname1+"."+dnis1+(".totaltransfer");
        String dnis2_total = appname1+"."+dnis2+(".totalInbound");
        String dnis2_totalTransfer = appname1+"."+dnis2+(".totaltransfer");
        logger.info("Dnis_1_totalTransfer_count is:"+ prop.getProperty(dnis1_totalTransfer)+"\t"+"Dnis_2_totalTransfer_count is:"+prop.get(dnis2_totalTransfer));
        //logger.info(appname1+":"+app1Inbound+":"+app1totalTransfer+":"+dnis1+":"+dnis2+":"+prop.getProperty(dnis1_total)+prop.getProperty(dnis2_total));
 		int sumInbound =Integer.valueOf(prop.getProperty(dnis1_total))+Integer.valueOf(prop.getProperty(dnis2_total));
 		int sumTransfer=Integer.valueOf(prop.getProperty(dnis1_totalTransfer))+Integer.valueOf(prop.getProperty(dnis2_totalTransfer));
 		logger.info(sumInbound+":"+sumTransfer);
 		if (Integer.valueOf(app1Inbound)==sumInbound &&Integer.valueOf(app1totalTransfer)== sumTransfer){
 			ReportFile.addTestCase("ODI6.x-700:CTR-GUI : Application /Application Entry Point - Net Total", "ODI6.x-700:CTR-GUI : Application /Application Entry Point - Net Total=> Pass");
 			logger.info("true");
 		}
 		else{
 			logger.info("false");
 			ReportFile.addTestCase("ODI6.x-700:CTR-GUI : Application /Application Entry Point - Net Total", "ODI6.x-700:CTR-GUI : Application /Application Entry Point - Net Total=> Fail");
 		}
 	} catch (IOException ex) {
 		ex.printStackTrace();
 	}
}
	//703
	public void DestinationGroupTotal(){
		//atleast some data should be present precondition
		Properties prop = new Properties();
		try {
        prop.load(new FileInputStream("config.properties"));
		String appname1 = prop.getProperty("app.name1");
 		String app1Inbound = prop.getProperty(appname1+".totalInbound");
 		int x = Integer.valueOf(app1Inbound);
        String app1totalTransfer = prop.getProperty(appname1+".totaltransfer");
 		String dnis1 = prop.getProperty(appname1.concat(".dnis1"));
        String dnis2 = prop.getProperty(appname1.concat(".dnis2"));
    	String dGroup = prop.getProperty(appname1.concat(".destinationGroup"));
        String dNumber = prop.getProperty(appname1.concat(".destinationNumber"));
        String dGroup_totalInbound = appname1+"."+dnis1+"."+dGroup+(".inbound");
        String dGroup_totalTransfer = appname1+"."+dnis1+"."+dGroup+(".transfer");
        String dnumber_totalInbound = appname1+"."+dnis1+"."+dGroup+"."+dNumber+(".inbound");
        String dnumber_totalTransfer = appname1+"."+dnis1+"."+dGroup+"."+dNumber+(".transfer");
        
        logger.info("Dnis_1_DefaultInbound_count is:"+ prop.getProperty(dGroup_totalInbound)+"\t"+"Dnis_1_DefaultTransfer_count is:"+prop.get(dGroup_totalTransfer));
        //logger.info(appname1+":"+app1Inbound+":"+app1totalTransfer+":"+dnis1+":"+dnis2+":"+prop.getProperty(dnis1_total)+prop.getProperty(dnis2_total));
 		int u =Integer.valueOf(prop.getProperty(dGroup_totalInbound));
 		int v =Integer.valueOf(prop.getProperty(dGroup_totalTransfer));
 		int w =Integer.valueOf(prop.getProperty(dnumber_totalInbound));
 		int y =Integer.valueOf(prop.getProperty(dnumber_totalTransfer));
 		logger.info(u+":"+v+"-"+w+":"+y);
 		if (w==u && y== v){
 			ReportFile.addTestCase("ODI6.x-700:CTR-GUI : Application /Application Entry Point - Net Total", "ODI6.x-700:CTR-GUI : Application /Application Entry Point - Net Total=> Pass");
 			logger.info("true");
 		}
 		else{
 			logger.info("false");
 			ReportFile.addTestCase("ODI6.x-700:CTR-GUI : Application /Application Entry Point - Net Total", "ODI6.x-700:CTR-GUI : Application /Application Entry Point - Net Total=> Fail");
 		}
 	} catch (IOException ex) {
 		ex.printStackTrace();
 	}
		
	}
	//706
	public void rightAlign(){
		//706 right aligned
		choosedomain("METROPCS");
		transferReport();
		try{
			driver.findElement(By.id("PARAM_START_DATE")).clear();
			removeAlert();
			driver.findElement(By.id("PARAM_START_DATE")).sendKeys("10/24/2013");
			driver.findElement(By.id("PARAM_END_DATE")).clear();
			removeAlert();
			driver.findElement(By.id("PARAM_END_DATE")).sendKeys("10/25/2013");
			driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
			/* */
			driver.switchTo().defaultContent();
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement page =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
			Boolean content=page.findElements(By.xpath("//*[contains(text(),'No')]")).size()>0;
			if(!content)
			{
			WebElement x = page.findElement(By.xpath("//span[contains(text(),'From')]"));
			List<WebElement> alldiv = page.findElements(By.tagName("div"));
			System.out.println(alldiv.get(0).getText());
			for(int j=0; j<alldiv.size(); j++)
			{
				System.out.println(alldiv.get(j).getCssValue("top")+ ":"+j);
				System.out.println(alldiv.get(j).getCssValue("text-align"));
				System.out.println(alldiv.get(j).getText()+ ":"+j);
				//if(alldiv.get(j).getText()
				
			}
			}
			else ReportFile.addTestCase("ODI6.x-700:CTR-GUI : Application /Application Entry Point - Net Total", "ODI6.x-700:CTR-GUI : Application /Application Entry Point - Net Total=> Fail");
			driver.quit();
		}catch(Exception e)
		{
			e.printStackTrace();
			logger.info("Exception");
		}
	}
	//712
	/*public void timeRangeLayout(){
		transferReport();
		try{
			
		}
	}*/
	//714
	public void TransferPercentageCheck(){
		choosedomain("METROPCS");
		transferReport();
		try{
			submit.click();
			//driver.switchTo().defaultContent();
			String Total = null;
			String CallCount= null;
			//String[] a = null;
			List<String>a = new ArrayList<String>();
			
			
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement page =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewercridreportpage")));
			//WebElement total = page.findElement(By.xpath("//span[contains(text(),'Total')]"));
			
			List<WebElement> check= page.findElements(By.tagName("span"));
			for(int i=0; i <check.size(); i++)
			{
				String content = (check.get(i).getText());//get the webelement first and then its gets its text
				if(content.equals("Total")){
					//contains(getText) is different from contentequals
					Total = check.get(i+1).getText();
					CallCount = check.get(i+2).getText();
					logger.info("call count"+ CallCount);
					logger.info("total:"+Total );
					break;
				}
			}
			double result =  Double.parseDouble(CallCount)/Double.parseDouble(Total)  *100;
			 DecimalFormat f = new DecimalFormat("###0.0");
			 String res=f.format(result);
			logger.info("result:"+ result + " :"+res);
			List<WebElement> tdata= page.findElements(By.tagName("tbody"));
			for(int j=0; j<tdata.size(); j++)
			{
				
				if(tdata.get(j).getText().endsWith("%")){
					a.add(tdata.get(j).getText());
					System.out.println(j+": element is"+ tdata.get(j).getText());
				}
			}
			for(int k=0;k<a.size(); k++){
				System.out.println(a.get(k));
				String[] st = a.get(k).split("0\\s%");
				System.out.println(st[0]+res);
				if (Double.parseDouble(st[0])==Double.parseDouble(res))	System.out.println("pass");
				else 	System.out.println("fail");
			}
			
		}catch(NoSuchElementException e){
				e.printStackTrace();
			}
		driver.quit();
	}
	//717
	public void hideDNIS(){
		transferReport();
		WebElement heading = null;
		try{
			WebElement Hide = driver.findElement(By.id("PARAM_HIDE_label"));
			if (Hide!=null)
			{
				logger.info("Element  is Present");
			}
			WebElement HideSelect = driver.findElement(By.id("PARAM_HIDE"));
			Select select = new Select(HideSelect);
			select.deselectAll();
			select.selectByVisibleText("DNIS");
			submit.click();
			driver.findElement(By.cssSelector("#openLeftPane > a > img")).click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement page =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewer")));
			boolean headingf = page.findElements(By.xpath("//span[contains(text(),'Destination')]")).size()<0;
//			int i = page.findElements(By.xpath("//span[contains(text(),'Destination')]")).size();
//			System.out.println(i);
			if (headingf)
				heading = page.findElement(By.xpath("//span[contains(text(),'Destination')]"));
			else
				{
				System.out.println(headingf+": value");
				ReportFile.addTestCase("ODI6.x-614:CSSR-Search", "ODI6.x-614:CSSR-Search => Fail");//fail
				}
			boolean fb = heading.getText().contains("DNIS");
			logger.info(heading.getText());
			driver.switchTo().defaultContent();
			select.deselectByVisibleText("DNIS");
			submit.click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement page2 =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewer")));
			WebElement heading2 = page2.findElement(By.xpath("//span[contains(text(),'Destination')]"));
			boolean fb2 = heading2.getText().contains("DNIS");
			if (fb){
				ReportFile.addTestCase("ODI6.x-717:CSR-GUI-117: Hide/Unhide group by options \"TFN\"", "ODI6.x-717:CSR-GUI-117: Hide/Unhide group by options \"TFN\"=> Fail");//fail
			}else
				{
				if(fb2)
				{
					ReportFile.addTestCase("ODI6.x-717:CSR-GUI-117: Hide/Unhide group by options \"TFN\"", "ODI6.x-717:CSR-GUI-117: Hide/Unhide group by options \"TFN\"=> Pass");
				}else ReportFile.addTestCase("ODI6.x-717:CSR-GUI-117: Hide/Unhide group by options \"TFN\"", "ODI6.x-717:CSR-GUI-117: Hide/Unhide group by options \"TFN\"=> Fail");//fail
			}
		}catch (Exception e)
		{
			e.printStackTrace();
			logger.info("Exception");
			
		}	ReportFile.WriteToFile();
		driver.switchTo().defaultContent();
		gotomainpage();
}
	//718 will do later
	public void Tfn(){
		choosedomain("METROPCS");
		transferReport();
		WebElement heading = null;
		try{
			WebElement Hide = driver.findElement(By.id("PARAM_HIDE_label"));
			if (Hide!=null)
			{
				logger.info("Element  is Present");
			}
			WebElement HideSelect = driver.findElement(By.id("PARAM_HIDE"));
			Select select = new Select(HideSelect);
			select.deselectAll();
			select.selectByVisibleText("Application");
			submit.click();
			driver.findElement(By.cssSelector("#openLeftPane > a > img")).click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement page =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewer")));
			Boolean headingf = page.findElements(By.xpath("//span[contains(text(),'Destinat')]")).size()<0;
			heading = page.findElement(By.xpath("//span[contains(text(),'Destination')]"));
			if (headingf)
				heading = page.findElement(By.xpath("//span[contains(text(),'Destination')]"));
			else
				{
				System.out.println(headingf+": value");
				ReportFile.addTestCase("ODI6.x-614:CSSR-Search", "ODI6.x-614:CSSR-Search => Fail");//fail
				}
			boolean fb = heading.getText().contains("Number");
			logger.info(heading.getText());
			driver.switchTo().defaultContent();
			select.deselectByVisibleText("Destination");
			submit.click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement page2 =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewer")));
			WebElement heading2 = page2.findElement(By.xpath("//span[contains(text(),'Destination')]"));
			boolean fb2 = heading2.getText().contains("Number");
			if (fb){
				ReportFile.addTestCase("ODI6.x-614:CSSR-Search", "ODI6.x-614:CSSR-Search => Fail");//fail
			}else
				{
				if(fb2)
				{
					ReportFile.addTestCase("ODI6.x-614:CSSR-Search", "ODI6.x-614:CSSR-Search => Pass");
				}else ReportFile.addTestCase("ODI6.x-614:CSSR-Search", "ODI6.x-614:CSSR-Search => Fail");//fail
			}
		}catch (Exception e)
		{
			e.printStackTrace();
			logger.info("Exception");
			
		}
		ReportFile.WriteToFile();
		driver.switchTo().defaultContent();
		gotomainpage();
}
	//720
	public void ListBoxApplicationEntry(){
		transferReport();
		try{
			boolean appId = driver.findElements(By.id("PARAM_APP_ID")).size()>0;
			if (appId)
				ReportFile.addTestCase("ODI6.x-720:CTR-GUI-112:List Box Application Entry Point", "ODI6.x-720:CTR-GUI-112:List Box Application Entry Point=> Pass");
			else
				ReportFile.addTestCase("ODI6.x-720:CTR-GUI-112:List Box Application Entry Point", "ODI6.x-720:CTR-GUI-112:List Box Application Entry Point=> Fail");
		}catch(NoSuchElementException e){
		logger.info("Caught NoSuchElementException:"+ e.getMessage());
		ReportFile.addTestCase("ODI6.x-720:CTR-GUI-112:List Box Application Entry Point", "ODI6.x-720:CTR-GUI-112:List Box Application Entry Point=> Fail");
		}
	}
	
	//721
	public void applicationMultiSelectable(){
		transferReport();
		try{
			driver.findElement(By.id("PARAM_START_DATE")).clear();
			removeAlert();
			driver.findElement(By.id("PARAM_START_DATE")).sendKeys("8/11/2013");
			driver.findElement(By.id("PARAM_END_DATE")).clear();
			removeAlert();
			driver.findElement(By.id("PARAM_END_DATE")).sendKeys("9/11/2013");
			WebElement filterSelect = driver.findElement(By.id("PARAM_APP_ID"));
			Select select = new Select(filterSelect);
			select.deselectAll();
			select.selectByIndex(1);
			select.selectByIndex(2);
			List<WebElement> options =select.getOptions();
			String app1 = options.get(1).getText();
			String app2 = options.get(2).getText();
			submit.click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
			WebElement page =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewer")));
			WebElement appFilter = page.findElement(By.xpath("//span[contains(text(),'Application:')]"));
			logger.info(appFilter.getText()+":"+app1+":"+app2);
			WebElement rangeStart=page.findElement(By.xpath("//*[contains(text(),'8/11/2013')]"));
			WebElement rangeEnd=page.findElement(By.xpath("//*[contains(text(),'9/11/2013')]"));
			if(rangeStart!=null&&rangeEnd!=null)
				if(appFilter.getText().contains(app1))
					if(appFilter.getText().contains(app2))
					{
						logger.info(appFilter.getText());
						ReportFile.addTestCase("ODI6.x-721:CTR-GUI-112:Functionaliy of multi select Box for Application Entry Point", "ODI6.x-721:CTR-GUI-112:Functionaliy of multi select Box for Application Entry Point=> Pass");
					}
			else
						ReportFile.addTestCase("ODI6.x-721:CTR-GUI-112:Functionaliy of multi select Box for Application Entry Point", "ODI6.x-721:CTR-GUI-112:Functionaliy of multi select Box for Application Entry Point=> Fail");
			//submit.click();
			//same as search() the time string
		}catch (Exception e)
		{
			ReportFile.addTestCase("ODI6.x-721:CTR-GUI-112:Functionaliy of multi select Box for Application Entry Point", "ODI6.x-721:CTR-GUI-112:Functionaliy of multi select Box for Application Entry Point=> Fail");
			logger.info("Caught NoSuchElementException:"+ e.getMessage());
			
		}
	}
	//722 
	public void consistency(){
	try{
		List<String>a = new ArrayList<String>(); //line 678
		List<String>b = new ArrayList<String>(); //line 678
		transferReport();
		WebElement filterSelect = driver.findElement(By.id("PARAM_APP_ID"));
		Select select1 = new Select(filterSelect);
		List<WebElement> options1 =select1.getOptions();
		for (WebElement x: options1)
		{
		
			a.add(x.getText());
		}
		gotomainpage();
		cssrReport();
		WebElement filterSelect2 = driver.findElement(By.id("PARAM_APP_ID"));
		Select select2 = new Select(filterSelect2);
		List<WebElement> options2 =select2.getOptions();
		for (WebElement y: options2)
		{
			
			b.add(y.getText());
		}
		logger.info(a.get(1));
		if(a.size()==b.size())
			for (int i=0;i<a.size();i++)
			{
				logger.info(a.get(i)+b.get(i));
				if (a.get(i).equals(b.get(i)))
					logger.info("pass");
			}
	}catch (Exception e)
	{
		e.printStackTrace();
		logger.info("Exception");
	}
	}
	//724
public void timeZone(){
	choosedomain("US_AIRWAYS");
	transferReport();
	try{
		WebElement timeZone = driver.findElement(By.id("PARAM_TIME_ZONE"));
		Select select = new Select(timeZone);
		select.selectByIndex(0);
		List<WebElement> options =select.getOptions();
		String zone = options.get(0).getText();
		System.out.println(zone);
		if (zone.equals("CT")) ReportFile.addTestCase("ODI6.x-614:CSSR-Search", "ODI6.x-614:CSSR-Search => Pass");
		else ReportFile.addTestCase("ODI6.x-614:CSSR-Search", "ODI6.x-614:CSSR-Search => Fail");
		
	}catch (Exception e)
	{
		e.printStackTrace();
		logger.info("Exception");
	}
	
}
//725	
public void timeRangeCheck(){

	
}
//726
public void hideGroup(){
	choosedomain("METROPCS");
	transferReport();
	WebElement heading = null;
	try{
		WebElement HideSelect = driver.findElement(By.id("PARAM_HIDE"));
		Select select = new Select(HideSelect);
		select.deselectAll();
		select.selectByIndex(1);
		select.selectByIndex(3);
		List<WebElement> options =select.getOptions();
		String appName1= options.get(1).getText();
		String appName2= options.get(3).getText();
		System.out.println(appName1+appName2);
		submit.click();
		new WebDriverWait(driver, 10).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("reportContent"));
		WebElement page =new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("CrystalViewer")));
		boolean headingf = page.findElements(By.xpath("//span[contains(text(),'Destination')]")).size()<0;
		//.size returns number of elements that contains text destination, here it is 2 because it appears twice but for some reason it cannot detect it
		if (headingf)
			heading = page.findElement(By.xpath("//span[contains(text(),'Destination')]"));
		else
			{
			System.out.println(headingf+": value");
			ReportFile.addTestCase("ODI6.x-614:CSSR-Search", "ODI6.x-614:CSSR-Search => Fail");//fail
			}
		boolean fb1= heading.getText().contains(appName1);
		boolean fb2= heading.getText().contains(appName2);
		if(fb1 && fb2)ReportFile.addTestCase("ODI6.x-614:CSSR-Search", "ODI6.x-614:CSSR-Search => Pass");
		else ReportFile.addTestCase("ODI6.x-614:CSSR-Search", "ODI6.x-614:CSSR-Search => Fail");
	}catch(Exception e)
	{
		e.printStackTrace();
	}
}
}
