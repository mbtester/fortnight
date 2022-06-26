package tests;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import constants.confg.Config;
import constants.testdata.Excel;
import init.DriverFactory;
import listner.ReportListener;
import pages.AppPage;

public class AppTest {
	Logger log = null; 
	SoftAssert softAssert;
	DriverFactory driverFactory;
	RemoteWebDriver driver;
	AppPage app = new AppPage();

	@BeforeTest(alwaysRun = true)
	public void startUp() throws Exception {
		this.driverFactory = new DriverFactory();
		this.driver = DriverFactory.getDriver();
		DriverFactory.setDriverFactory(this.driverFactory);
		this.driver.get(Config.URL);
		log.info("URL is entered in broswer");
	}

	@Test(alwaysRun = true)
	public void testApp() throws Exception {
		log.info("testApp() test started");
		
		//fetch values from properties
		app.enterUserName(driver, Config.USER_NAME);
		app.enterPassword(driver, Config.PASSWORD);
		
		//fetch values from Excel
		app.enterUserName(driver, Excel.USER_NAME);
		app.enterPassword(driver, Excel.PASSWORD);
		
		app.clickOnSubmit(driver);
		ReportListener.logToReport("App test completed successfully");	
		log.info("testApp() test completed");
	}

	@AfterTest(alwaysRun = true)
	public void tearDown() throws Exception {
		this.driver.quit();
	}
}
