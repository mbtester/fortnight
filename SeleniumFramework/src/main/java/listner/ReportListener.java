package listner;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.IClassListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.MediaType;
import com.aventstack.extentreports.model.ScreenCapture;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import constants.confg.Config;
import constants.path.Path;
import init.DriverFactory;
import utils.FileOperations;

public class ReportListener implements ITestListener, IClassListener, ISuiteListener {

	private ExtentReports extent;
	private ExtentTest test;
	private ExtentHtmlReporter htmlReporter;
	private String extentScreen = null;
	private static ScreenCapture med;
	private static MediaEntityModelProvider mp;
	private static ThreadLocal<Integer> testMethodCount = new ThreadLocal<Integer>();
	private ThreadLocal<String> testRunStatus = new ThreadLocal<String>();
	private static String filePathex = System.getProperty("user.dir") + "/Extent Reports/extentreport.html";
	private static Map<String, RemoteWebDriver> driverMap = new HashMap<String, RemoteWebDriver>();
	private static Map<String, ExtentTest> extentTestMap = new HashMap<String, ExtentTest>();
	String totalRecord;

	public synchronized ExtentTest startTest(String testName) {
		ExtentTest classNode = getExtent().createTest(testName);
		extentTestMap.put(testName, classNode);
		return classNode;
	}

	public static synchronized ExtentTest getTest(String testName) {
		return extentTestMap.get(testName);
	}

	public void onStart(ISuite suite) {
		this.extent = getExtent();
		suite.setAttribute("extent", this.extent);
		testRunStatus.set("pass");

	}

	public void onStart(ITestContext context) {
	}

	public void onBeforeClass(ITestClass testclass) {
		this.test = startTest(testclass.getRealClass().getSimpleName());
		System.out.println("listener before class");
		FileOperations.cleanDir(System.getProperty("user.dir") + "/Extent Reports/Passed Screenshots/");
		FileOperations.cleanDir(System.getProperty("user.dir") + "/Extent Reports/Failure Screenshots/");
		FileOperations.cleanDir(System.getProperty("user.dir") + "/Extent Reports/Skip Screenshots/");
		testMethodCount.set(-1);
}

	public void onTestStart(ITestResult result) {
		String testClassName = result.getTestClass().getRealClass().getSimpleName();
		System.out.println("class name: " + testClassName + " with instance id :" + DriverFactory.getDriver());
		driverMap.put(testClassName, DriverFactory.getDriver());
		getTest(result.getInstanceName().replace(".", " ")
				.split(" ")[result.getInstanceName().replace(".", " ").split(" ").length - 1].trim())
						.createNode(result.getMethod().getMethodName(), result.getMethod().getDescription())
						.log(Status.INFO, "Test method " + result.getMethod().getMethodName() + " started");
		Integer testMethodCountValue = testMethodCount.get();
		testMethodCountValue = testMethodCountValue + 1;
		testMethodCount.set(testMethodCountValue);
	}

	public void onTestSuccess(ITestResult result) {
		String methodName = result.getName().trim();
		String filePath = System.getProperty("user.dir") + "/Extent Reports/Passed Screenshots/";
		takeScreenShot(filePath, methodName+testMethodCount.get(), driverMap.get(result.getTestClass().getRealClass().getSimpleName()));

		Log logObj = new Log(getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ")
				.split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length - 1]
						.trim()));
		logObj.setDetails("Test method " + result.getMethod().getMethodName() + " completed successfully");
		logObj.setStatus(Status.PASS);

		getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ")
				.split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length - 1]
						.trim()).getModel().getNodeContext().get(testMethodCount.get()).getLogContext().add(logObj);
		med = new ScreenCapture();
		med.setMediaType(MediaType.IMG);
		med.setPath(extentScreen);
		getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ")
				.split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length - 1]
						.trim()).getModel().getNodeContext().get(testMethodCount.get()).setScreenCapture(med);

	}

	public void onTestFailure(ITestResult result) {
		testRunStatus.set("fail");
		String methodName = result.getName().trim();
		String filePath = System.getProperty("user.dir") + "/Extent Reports/Failure Screenshots/";
		System.out.println(driverMap.get(result.getTestClass().getRealClass().getSimpleName()));
		takeScreenShot(filePath, methodName+testMethodCount.get(), driverMap.get(result.getTestClass().getRealClass().getSimpleName()));

		Log logObj = new Log(getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ")
				.split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length - 1]
						.trim()));
		logObj.setDetails("Test method " + result.getMethod().getMethodName() + " failed due to Exception -----> "
				+ Reporter.getCurrentTestResult().getThrowable().toString());
		logObj.setStatus(Status.FAIL);

		getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ")
				.split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length - 1]
						.trim()).getModel().getNodeContext().get(testMethodCount.get()).getLogContext().add(logObj);

		med = new ScreenCapture();
		med.setMediaType(MediaType.IMG);
		med.setPath(extentScreen);
		getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ")
				.split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length - 1]
						.trim()).getModel().getNodeContext().get(testMethodCount.get()).setScreenCapture(med);

	}

	public void onTestSkipped(ITestResult result) {
		String methodName = result.getName().trim();
		String filePath = System.getProperty("user.dir") + "/Extent Reports/Skip Screenshots/";
		System.out.println(filePath);
		System.out.println(methodName);
		System.out.println(driverMap.get(result.getTestClass().getRealClass().getSimpleName()));
		takeScreenShot(filePath, methodName+testMethodCount.get(), driverMap.get(result.getTestClass().getRealClass().getSimpleName()));
		Log logObj = new Log(getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ")
				.split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length - 1]
						.trim()));
		logObj.setDetails("Test method " + result.getMethod().getMethodName() + " skip due to Exception -----> "
				+ Reporter.getCurrentTestResult().getThrowable().toString());
		logObj.setStatus(Status.SKIP);

		med = new ScreenCapture();
		med.setMediaType(MediaType.IMG);
		med.setPath(extentScreen);
		mp = new MediaEntityModelProvider(med);
		getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ")
				.split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length - 1]
						.trim()).createNode(result.getMethod().getMethodName(), result.getMethod().getDescription())
								.skip(MarkupHelper.createLabel("SKIP", ExtentColor.BLUE))
								.skip("Screenshot " + result.getMethod().getMethodName(), mp).log(Status.SKIP,
										"Test method " + result.getMethod().getMethodName()
												+ " skip due to Exception -----> "
												+ Reporter.getCurrentTestResult().getThrowable().toString());

		Integer testMethodCountValue = testMethodCount.get();
		testMethodCountValue = testMethodCountValue + 1;
		testMethodCount.set(testMethodCountValue);
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	public void onAfterClass(ITestClass testclass) {
	}

	public void onFinish(ITestContext context) {
	}

	public void onFinish(ISuite suite) {

		if (testRunStatus.get().equalsIgnoreCase("fail")) {
			try {
				FileOperations.updateConfigValue(Path.CONFIG_FILE_PATH, "testRunStatus", "fail");
			} catch (ConfigurationException | IOException e) {
				e.printStackTrace();
			} catch (javax.naming.ConfigurationException e) {
				e.printStackTrace();
			}
		} else {
			try {
				FileOperations.updateConfigValue(Path.CONFIG_FILE_PATH, "testRunStatus", "pass");
			} catch (ConfigurationException | IOException e) {
				e.printStackTrace();
			} catch (javax.naming.ConfigurationException e) {
				e.printStackTrace();
			}
		}
		this.extent.flush();
	}

	public void takeScreenShot(String filePath, String methodName, RemoteWebDriver driver) {
		try {
			TakesScreenshot d = ((TakesScreenshot) driver);
			File scrFile = d.getScreenshotAs(OutputType.FILE);
			File scrFile2 = new File(filePath + methodName + ".png");
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			FileUtils.copyFile(scrFile, scrFile2);
			System.out.println("***Placed screen shot in " + filePath + "***");
			extentScreen = scrFile2.toString();
			Reporter.setEscapeHtml(false);
			Reporter.log("<a href='" + scrFile2.toString() + "'><img src ='" + scrFile2.toString()
					+ "' width='200' height='200'></a>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ExtentReports getExtent() {
		if (extent == null) {
			extent = new ExtentReports();
			extent.setSystemInfo("Name", "Hemna");
			extent.setSystemInfo("Browser", Config.BROWSER_NAME);
			extent.attachReporter(getHtmlReporter());
		}
		return extent;
	}

	private ExtentHtmlReporter getHtmlReporter() {
		htmlReporter = new ExtentHtmlReporter(filePathex);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName("Test Automation Report");
		return htmlReporter;
	}

	public static void logToReport(String msg) {
		Log logObj = new Log(getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ")
				.split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length - 1]
						.trim()));
		logObj.setDetails(msg);
		logObj.setStatus(Status.INFO);
		getTest(Reporter.getCurrentTestResult().getInstanceName().replace(".", " ")
				.split(" ")[Reporter.getCurrentTestResult().getInstanceName().replace(".", " ").split(" ").length - 1]
						.trim()).getModel().getNodeContext().get(testMethodCount.get()).getLogContext().add(logObj);
	}
}