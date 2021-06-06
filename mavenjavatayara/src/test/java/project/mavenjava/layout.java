package project.mavenjava;

import java.util.concurrent.TimeUnit;
import org.apache.commons.lang.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.*;

public class layout {
	WebDriver driver;
	StopWatch stopwatch = new StopWatch();;
	SoftAssert a = new SoftAssert();

	@Test
	public void logo() {
	
		WebElement element = driver.findElement(By.id("logo"));
		Point point = element.getLocation();
		int xcord = point.getX();
		int ycord = point.getY();
		String size = String.valueOf(element.getSize());

		System.out.println("Position of the logo from left side is " + xcord + " pixels");
		System.out.println("Position of the logo from top side is " + ycord + " pixels ");
		System.out.println("size of logo : " + size + "\n");

		a.assertTrue(xcord == 8, "Position of the logo from left side is " + xcord + " pixels");
		a.assertTrue(ycord == 48, "Position of the logo from top side is " + ycord + " pixels");
		a.assertEquals("(160, 45)", size, "size");

		// Toute la Tunisie details
	
	WebElement attribute = driver.findElement(By.className("location__picker-selected"));
		String fontsize = attribute.getCssValue("font-size");
		String color = attribute.getCssValue("color");
		String fontfamily = attribute.getCssValue("font-family");

		System.out.println("fontsize of Toute la Tunisie: " + fontsize);
		System.out.println("color of Toute la Tunisie: " + color);
		System.out.println("fontfamily of Toute la Tunisie: " + fontfamily + "\n");

		a.assertEquals("16px", fontsize, "fontsize");
		a.assertEquals("rgba(238, 73, 58, 1)", color, "color");
		a.assertEquals("\"Open Sans\", Helvetica, sans-serif", fontfamily, "fontfamily");

		a.assertAll();
	}

	@BeforeMethod
	public void beforeMethod() throws Exception {

		ScreenRecorderUtil.startRecord("Recording");
		stopwatchtime.reset("timer reset");
		stopwatchtime.start("timer started");
		
		System.out.println("Starting the browser session");
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+ "\\chromedriver.exe"); //set driver path
		driver = new ChromeDriver();
		Dimension dm = new Dimension(1024,768);
		driver.manage().window().setSize(dm);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.tayara.tn/");

	}

	@AfterMethod
	public void afterMethod() throws Exception {
		System.out.println("Closing the browser session");
		driver.quit();
		stopwatchtime.stop("timer stopped");
		stopwatchtime.timetaken();
		ScreenRecorderUtil.stopRecord();
	}
}