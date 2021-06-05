package project.mavenjava;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

public class ScreenCapture  {

static BufferedImage baseline;
static BufferedImage testcases;
    public static void baselines(WebDriver driver) throws IOException, InterruptedException
    {	
        Date d = new Date();
   //     System.out.println(d.toString());
        Thread.sleep(1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");         // Your each screenshot will be taken as this format "Year-Month-Date-Hours-Minutes-Seconds"
    	 Screenshot screenshot = new AShot().takeScreenshot(driver);
         ImageIO.write(screenshot.getImage(),"PNG",new File(System.getProperty("user.dir")+"\\screenshots\\baselines\\baseline "+sdf.format(d)+".png"));
         baseline= ImageIO.read(new File(System.getProperty("user.dir")+"\\screenshots\\baselines\\baseline "+sdf.format(d)+".png"));         
         System.out.println("baseline "+sdf.format(d)+" screenshot captured \n");
    }

    public static void testcasepictures(WebDriver driver) throws IOException, InterruptedException
    {
        Date d = new Date();
  //      System.out.println(d.toString());
        Thread.sleep(1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HHmmss");   ;
    	 Screenshot screenshot = new AShot().takeScreenshot(driver);
         ImageIO.write(screenshot.getImage(),"PNG",new File(System.getProperty("user.dir")+"\\screenshots\\testcases\\testcase "+sdf.format(d)+".png"));
         testcases= ImageIO.read(new File(System.getProperty("user.dir")+"\\screenshots\\testcases\\testcase "+sdf.format(d)+".png"));
         System.out.println("testcase "+sdf.format(d)+" screenshot captured ");
    }

    public static void compare() {
    
    	ImageDiffer imgDiff = new ImageDiffer();
        ImageDiff diff = imgDiff.makeDiff(baseline, testcases);
        if(diff.hasDiff()==true)
        {
         System.out.println("Images are Not Same \n");
        Reporter.log("Images are not Same; \n");
        }
        else {
         System.out.println("Images are Same \n");
         Reporter.log("Images are Same; \n");
        }
        
    
    }
}