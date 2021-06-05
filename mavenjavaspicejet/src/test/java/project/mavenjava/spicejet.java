package project.mavenjava;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;




@Test
public class spicejet {
	WebDriver driver;
	SoftAssert a = new SoftAssert();

	
	@Test (priority=1)
	public void tab() throws IOException, InterruptedException {
		
		stopwatchtime.reset("timer reset");
		stopwatchtime.start("timer started");
		ScreenCapture.baselines(driver);
		Reporter.log("1st baseline screenshot captured; \n");
		System.out.println("*Checking tab order* \n");		
		String element1 = driver.findElement(By.id("header-book")).getText();
		String element2 = driver.findElement(By.id("header-addons")).getText();
		String element3 = driver.findElement(By.id("header-vacations")).getText();
		String element4 = driver.findElement(By.id("gift-card")).getText();
		String element5 = driver.findElement(By.id("SpecialAssistanceToPLink")).getText();
		String element6 = driver.findElement(By.id("spicescreen")).getText();
		String element7 = driver.findElement(By.id("cargoheader")).getText();
		String element8 = driver.findElement(By.id("spicestyleheader")).getText();
		String element9 = driver.findElement(By.id("spiceshuttle")).getText();
		String element10 = driver.findElement(By.id("charterreq")).getText();

		List<WebElement> t = driver.findElements(By.xpath("//*[@id='smoothmenu1']/ul/li[@id]"));

		String[] tabOrder = { element1, element2, element3, element4, element5, element6, element7, element8, element9,
				element10 };
		
		for (int i = 0; i < t.size(); i++) {
			String f = t.get(i).getText();
			System.out.println("Expected = " + tabOrder[i]);
			System.out.println("Actual = " + f +"\n");
			a.assertEquals(tabOrder[i], f);

		}
		a.assertAll();
		Reporter.log("Tab order checked; \n");
		
		ScreenCapture.testcasepictures(driver);
		Reporter.log("1st testcase screenshot captured; \n");
		ScreenCapture.compare();
		Reporter.log("1st testcase screenshot compared with 1st baseline screenshot; \n");
		
		stopwatchtime.stop("timer stopped");
		stopwatchtime.timetaken();
		
		System.out.println("********************************************** \n");
	}


//---------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@Test(priority=2)
	public void count() throws IOException {
		stopwatchtime.reset("timer reset");
		stopwatchtime.start("timer started");
		
		//chkboxes count
		Reporter.log("counting checkboxes; \n");
		System.out.println("*Counting checkboxes* \n");
		List<WebElement> checkboxes = driver.findElements(By.cssSelector("[id='discount-checkbox'] div input"));
		int checkboxcount = checkboxes.size();
		for(WebElement chb : checkboxes)
		{
			String chbc= chb.findElement(By.xpath("following-sibling::label")).getText();
			System.out.println(chbc);
		}
		
		System.out.println("\n"+"Total checkboxes present on page: "+checkboxcount+"\n");
		Reporter.log("checkboxes count: "+checkboxcount +"; \n");

		//radiobtn count
		Reporter.log("counting radio buttons; \n");
		System.out.println("*Counting radio buttons* \n");
		List<WebElement> radiobtn = driver.findElements(By.cssSelector("[id='travelOptions'] [type='radio']"));
		int rbtncount = radiobtn.size();
		for(WebElement rbt : radiobtn)
		{
			String rbtc= rbt.findElement(By.xpath("following-sibling::label")).getText();
			System.out.println(rbtc);
		}
		System.out.println("\n"+"Total radio buttons present on page: "+rbtncount+"\n");	
		Reporter.log("radio button count: "+rbtncount +"; \n");
		
		//tabs count
		Reporter.log("counting tabs; \n");
		System.out.println("*Counting tabs* \n");
		List<WebElement> tabs = driver.findElements(By.xpath("//*[@id='smoothmenu1']/ul/li[@id]"));
		int tabcount = tabs.size();
		for(WebElement tab : tabs)
		{
			String tabc= tab.getText();
			System.out.println(tabc);
		}
		System.out.println("\n"+"Total tabs present on page: "+tabcount+"\n");
		Reporter.log("tabs count: "+tabcount +"; \n");
		
		//links count
		String url="";
		Reporter.log("counting links; \n");
		System.out.println("*Counting links* \n");
		List<WebElement> links = driver.findElements(By.cssSelector("[id='aspnetForm'] a"));
		int i=0, j=0, k=0;  //i=working links count, j=mail links count, k=broken links count
		for (WebElement link : links)

		{

			url = link.getAttribute("href");
			String urlname = link.getText();
			
			if(url==null || url.isEmpty()) {
				continue;
			}

				if (urlname==null || urlname.isEmpty()) {
					urlname= link.getAttribute("title");
				}
				                
			if(url.contains("mailto")) {
				j++;
				System.out.println(url+" is a mail link - "+urlname);
				continue;
			}
	
			if(!(url.startsWith("javascript")))
			{

			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

	//		conn.setRequestMethod("HEAD");

			conn.connect();

			int respCode = conn.getResponseCode();
			
				if(respCode < 400) 
				{	i++;
				System.out.println(url+" is a valid link - "+urlname+ " " +conn.getResponseMessage());
			}
				
				else {
					k++;
					System.out.println(url+" is an invalid link - "+urlname+" " +conn.getResponseMessage());
				
			}
		}}  int z= i+j+k;
		System.out.println("\n"+"total valid links on page: "+i);
		System.out.println("total invalid links on page: "+k);
		System.out.println("total mail links on page: "+j);	
		System.out.println("total links on page: "+z+"\n");
		
		Reporter.log("total links count: "+z+"; \n");
		
		stopwatchtime.stop("timer stopped");
		stopwatchtime.timetaken();
		
		System.out.println("********************************************** \n");
	}

	
//--------------------------------------------------------------------------------------------------------------------------------------------------------
	
	

	@Test (priority=3)
	public void check() throws InterruptedException, IOException {
		stopwatchtime.reset("timer reset");
		stopwatchtime.start("timer started");
		
		
		// dynamic dropdown
		System.out.println("*Handing dropdowns* \n");
		
		driver.findElement(By.id("ctl00_mainContent_ddl_originStation1_CTXT")).click();
		driver.findElement(By.xpath("//a[@value='HYD']")).click();
		WebElement origin = driver.findElement(By.id("ctl00_mainContent_ddl_originStation1_CTXT"));
		if(origin.isDisplayed())
		{
			System.out.println("the element is visible as departure city ");
			Reporter.log("the element is visible as departure city - CHECKED; \n");
		}
		
		driver.findElement(By.xpath("(//a[@value='DEL'])[2]")).click();
		
		WebElement destination = driver.findElement(By.id("ctl00_mainContent_ddl_destinationStation1_CTXT"));
		if(destination.isDisplayed())
		{
			System.out.println("the element is visible as arrival city \n");
			Reporter.log("the element is visible as arrival city - CHECKED; \n");
		}
		
		ScreenCapture.testcasepictures(driver);
		Reporter.log("2nd testcase screenshot captured; \n");
		ScreenCapture.compare();
		Reporter.log("2nd testcase screenshot compared 1st with baseline screenshot; \n");
		
		
	// handling calendar ui
		System.out.println("*Handling calendar* \n");
		driver.findElement(By.cssSelector(".ui-state-default.ui-state-highlight")).click();
		WebElement departdate = driver.findElement(By.id("ctl00_mainContent_view_date1"));
		
		if(departdate.isDisplayed())
		{
			System.out.println("the element is visible as depart date \n");
			Reporter.log("the element is visible as depart date - CHECKED; \n");
		}
		
		
		ScreenCapture.testcasepictures(driver);
		Reporter.log("3rd testcase screenshot captured; \n");
		ScreenCapture.compare();
		Reporter.log("3rd testcase screenshot compared with 1st baseline screenshot; \n");
		
		// Validating if UI Elements are disabled or enabled with Attributes
		if (driver.findElement(By.id("Div1")).getAttribute("style").contains("0.5"))

		{

			System.out.println("Return date is disabled \n");
			Reporter.log("Return date is disabled - CHECKED; \n");
		
		}
		driver.findElement(By.id("ctl00_mainContent_rbtnl_Trip_1")).click();  //enable radio button
		System.out.println("Round trip radio button enabled \n");
		Reporter.log("Round trip radio button enabled; \n");

		ScreenCapture.testcasepictures(driver);
		Reporter.log("4th testcase screenshot captured; \n");
		ScreenCapture.compare();
		Reporter.log("4th testcase screenshot compared with 1st baseline screenshot; \n");
		
		if (driver.findElement(By.id("Div1")).getAttribute("style").contains("1"))

		{

			System.out.println("Return date Enabled \n");

			a.assertTrue(true);
			Reporter.log("Return date is enabled - CHECKED; \n");

		}

		else

		{

			a.assertTrue(false);

		}
		
		WebElement returndate = driver.findElement(By.id("ctl00_mainContent_view_date2"));
		if(returndate.isDisplayed())
		{
			System.out.println("the element is visible as return date \n");
			Reporter.log("the element is visible as return date - CHECKED; \n");

		}
	
		
		// static dropdown
		System.out.println("*Handling dropdowns* \n");
		driver.findElement(By.id("divpaxinfo")).click();
		Thread.sleep(1000L);
		WebElement dropdown2 = driver.findElement(By.id("ctl00_mainContent_ddl_Adult"));
		Select staticdropdown = new Select(dropdown2);
		staticdropdown.selectByIndex(2);
		System.out.println(driver.findElement(By.id("divpaxinfo")).getText()+" selected \n");
		a.assertEquals(driver.findElement(By.id("divpaxinfo")).getText(), "3 Adult");
		Reporter.log("3 adult passengers - CHECKED; \n");

		ScreenCapture.testcasepictures(driver);
		Reporter.log("5th testcase screenshot captured; \n");
		ScreenCapture.compare();
		Reporter.log("5th testcase screenshot compared with 1st baseline screenshot; \n");

		WebElement dropdown = driver.findElement(By.id("ctl00_mainContent_DropDownListCurrency"));
		Select sdropdown = new Select(dropdown);

		sdropdown.selectByValue("USD");
		System.out.println(sdropdown.getFirstSelectedOption().getText()+" selected \n");
		Reporter.log("USD currency selected; \n");

		ScreenCapture.testcasepictures(driver);
		Reporter.log("6th testcase screenshot captured; \n");
		ScreenCapture.compare();
		Reporter.log("6th testcase screenshot compared with 1st baseline screenshot; \n");
		
	
		// handling checkboxes
		System.out.println("*Handling checkboxes* \n");
		driver.findElement(By.cssSelector("input[id*='friendsandfamily']")).click();
		a.assertTrue(driver.findElement(By.cssSelector("input[id*='friendsandfamily']")).isSelected());
		System.out.println(driver.findElement(By.cssSelector("input[id*='friendsandfamily']")).isSelected()+" - friendsandfamily  selected \n");
		Reporter.log("Friends and family selected - CHECKED; \n");

		ScreenCapture.testcasepictures(driver);
		Reporter.log("7th testcase screenshot captured; \n");
		ScreenCapture.compare();
		Reporter.log("7th testcase screenshot compared with 1st baseline screenshot; \n");
		
		a.assertAll();
		// driver.findElement(By.xpath("//input[@value='Search']")).click();

		stopwatchtime.stop("timer stopped");
		stopwatchtime.timetaken();
		
		System.out.println("********************************************** \n");
	}
	

//-------------------------------------------------------------------------------------------------------------------------------------------------------------	
	

	
		@Test (priority=4)
		public void chatbot() throws InterruptedException, IOException {
				stopwatchtime.reset("timer reset");
				stopwatchtime.start("timer started");
			//chat bot
				System.out.println("*Handling chatbot* \n");
				
				JavascriptExecutor js= (JavascriptExecutor)driver;
				js.executeScript("window.scrollBy(0,3000)");
				ScreenCapture.baselines(driver);
				Reporter.log("2nd baseline screenshot captured; \n");
				driver.findElement(By.xpath("//div[@class='mybot mob-no']/span[1]/img")).click();
				Reporter.log("chatbot opened; \n");
				ScreenCapture.testcasepictures(driver);
				Reporter.log("8th testcase screenshot captured; \n");
				ScreenCapture.compare();
				Reporter.log("8th testcase screenshot compared with 2nd baseline screenshot; \n");
				driver.findElement(By.cssSelector("span[class='LandbotFrameHeader__nav__item']")).click();
				Reporter.log("chatbot closed; \n");
				ScreenCapture.testcasepictures(driver);
				Reporter.log("9th testcase screenshot captured; \n");
				ScreenCapture.compare();
				Reporter.log("9th testcase screenshot compared with 2nd baseline screenshot; \n");
				
				stopwatchtime.stop("timer stopped");
				stopwatchtime.timetaken();
				
				System.out.println("********************************************** \n");
			}
			
//-----------------------------------------------------------------------------------------------------------------------------------------------------
	
		
@Test (priority=5)
public void login() throws InterruptedException, IOException {
	
	//login
	System.out.println("*Login attempt* \n");
	stopwatchtime.reset("timer reset");
	stopwatchtime.start("timer started");
	
		Actions a=new Actions(driver);
		WebElement login=driver.findElement(By.xpath("//*[@id='smoothmenu1']/ul/li[@class][18]"));
		WebElement slogin=driver.findElement(By.xpath("//*[@id='smoothmenu1']/ul/li[@class][18]/ul/li[2]"));
		WebElement mlogin=driver.findElement(By.xpath("//*[@id='smoothmenu1']/ul/li[@class][18]/ul/li[2]/ul/li[1]"));
		a.moveToElement(login).build().perform();
		Thread.sleep(1000);
		a.moveToElement(slogin).build().perform();
		Thread.sleep(1000);
		a.moveToElement(mlogin).click().build().perform();
		Thread.sleep(1000);
		Reporter.log("clicked on member login; \n");
		ScreenCapture.baselines(driver);
		Reporter.log("3rd baseline screenshot captured; \n");
		ScreenCapture.testcasepictures(driver);
		Reporter.log("10th testcase screenshot captured; \n");
		ScreenCapture.compare();
		Reporter.log("10th testcase screenshot compared with 3rd baseline screenshot; \n");

		WebElement w= driver.findElement(By.id("ControlGroupLoginView_MemberLoginView2LoginView_TextBoxUserID"));
		WebElement p= driver.findElement(By.id("ControlGroupLoginView_MemberLoginView2LoginView_PasswordFieldPassword"));
		WebElement c=driver.findElement(By.id("ControlGroupLoginView_MemberLoginView2LoginView_ButtonLogIn"));
		
		w.sendKeys("name");
		p.sendKeys("pass12345");
		c.click();
		Reporter.log("login attempted; \n");
		ScreenCapture.testcasepictures(driver);
		Reporter.log("11th testcase screenshot captured; \n");
		ScreenCapture.compare();
		Reporter.log("11th testcase screenshot compared with 3rd baseline screenshot; \n");
		
		driver.findElement(By.id("popup_ok")).click();
		Reporter.log("Popup closed; \n");
		w.clear();
		p.clear();
		
		// Type 10 characters as max limit is defined as 10 as per requirement
		w.sendKeys("5454");
		p.sendKeys("pass12345");
		
		String typedValue = w.getAttribute("value");
		
		// Get the length of typed value
		int size = typedValue.length();
 
		// Assert with expected
		if (size == 10) {
			System.out.println("Max character functionality is working fine \n");
			Reporter.log("Max character functionality is working fine for member id; \n");
		}
 
		else if(size<10){
			System.out.println("less than required character \n");
			Reporter.log("less than required character for member id ; \n");
		}
		else 
		{
			System.out.println("exceeding max character limit \n");
			Reporter.log("exceeding max character limit for member id; \n");	
		}
		
		c.click();
		Reporter.log("login attempted; \n");
		
		ScreenCapture.testcasepictures(driver);
		Reporter.log("12th testcase screenshot captured; \n");
		ScreenCapture.compare();
		Reporter.log("12th testcase screenshot compared with 3rd baseline screenshot; \n");
		
		driver.findElement(By.id("popup_ok")).click();
		Reporter.log("Popup closed; \n");
		
		stopwatchtime.stop("timer stopped");
		stopwatchtime.timetaken();
		System.out.println("********************************************** \n");
}

	


//---------------------------------------------------------------------------------------------------------------------------------------------------------
	
	


	@Test (priority=0)
	public void openBrowser() throws Exception {

	
	ScreenRecorderUtil.startRecord("Recording");
	
	stopwatchtime.start("timer started");
	
		System.out.println("\n Starting the browser session");
		
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\chromedriver.exe"); // set driver path
																												
		driver = new ChromeDriver();
		Reporter.log("The browser is opened now; \n");
		driver.manage().window().maximize();
		Reporter.log("the browser is maximized; \n");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://www.spicejet.com/");
		Reporter.log("redirected to spicejet.com; \n");
		
		stopwatchtime.stop("timer stopped");
		stopwatchtime.timetaken();
		
		System.out.println("********************************************** \n");

	}
	
	
	
	
	
	
//---------------------------------------------------------------------------------------------------------------------------------------------------------	
	
	
	
	@Test(priority=6)
	public void closeBrowser() throws Exception {
		stopwatchtime.reset("timer reset");
		stopwatchtime.start("timer started");
		System.out.println("\n Closing the browser session \n");
		driver.quit();
		Reporter.log("driver is closed; \n");
	stopwatchtime.stop("timer stopped");
	stopwatchtime.timetaken();
	ScreenRecorderUtil.stopRecord();	

	}
}