package project.mavenjava;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



public class extraction {
	WebDriver driver;
	StopWatch stopwatch;
@Test
	public void ads() throws InterruptedException {
		
		int dsize=0;
		int j=1;
//		while ( j != dsize)   //for n products
		while (j <= 12)			//for 10 clicks on load more
		{
		int	n=dsize;
		List<WebElement> products = driver.findElements(By.className("card"));
		dsize= products.size();		//no. of ads present on current screen
		JavascriptExecutor js= (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,5000)");
		
		for (int i = 0+n; i < dsize; i++)
		{
			String name = products.get(i).getText();
			 String url = products.get(i).findElement(By.tagName("a")).getAttribute("href");
			
			System.out.println((i+1)+") "+name);
			System.out.println(url+"\n");
		}
		j++;
		if(j==12)
		{
			break;
		}
		System.out.println("updated products:\n");
		driver.findElement(By.id("load-more-btn")).click();		//click on load more
		Thread.sleep(3000);
		
/*		List<WebElement> upproducts = driver.findElements(By.cssSelector(".card"));
		if (dsize != upproducts.size())
		{
			System.out.println("updated products:\n");
			j=upproducts.size();   
			j++;
		}                   */					//for n products
		
		}
		System.out.println("total ad counts: "+ dsize);
		System.out.println("clicks on load more: "+ (j-2) +"\n");
		//driver.close();
	}
@BeforeMethod
public void beforeMethod() throws Exception  {
	  System.out.println("Starting the browser session");

	  ScreenRecorderUtil.startRecord("Recording");
	  stopwatchtime.reset("timer reset");
		stopwatchtime.start("timer started");
		
	  System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\chromedriver.exe");	//set driver path
		driver = new ChromeDriver();
		driver.manage().window().maximize();
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
