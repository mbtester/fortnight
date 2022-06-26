package init;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.FileOperations;
import constants.confg.Config;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	ThreadLocal<RemoteWebDriver> webDriver = new ThreadLocal<RemoteWebDriver>();
	private RemoteWebDriver remoteWebDriver;
	private static Logger log = Logger.getLogger(DriverFactory.class);
	FileOperations fileOperations = new FileOperations();
	static Map<String, RemoteWebDriver> map = new HashMap<String, RemoteWebDriver>();

	/**
	 * Create a WebDriver Instance
	 * 
	 * @param browser
	 * @param locale
	 * @throws Exception
	 */
	public DriverFactory() throws Exception {
		String browser = Config.BROWSER_NAME;
		String osType = getOperatingSystemType();

		switch (browser.toLowerCase()) {
		case "ie":
			log.info("Initializing driver for Internet Explorer...");
			if (osType.equals("Windows")) {
				System.setProperty("webdriver.ie.driver", "");
				InternetExplorerOptions options = new InternetExplorerOptions();
				options.introduceFlakinessByIgnoringSecurityDomains();
				webDriver.set(new InternetExplorerDriver(options));
				log.info("Driver for Internet Explorer initialized.");
				break;
			} else {
				throw new Exception("You are not running the tests on windows operating system");
			}
		case "edge":
			log.info("Initializing driver for Edge...");
			if (osType.equals("Windows")) {
				System.setProperty("webdriver.edge.driver", "");
				EdgeOptions options = new EdgeOptions(); // we can add the options for edge in future as we need
				webDriver.set(new EdgeDriver(options));
				log.info("Driver for Edge initialized.");
				break;
			} else {
				throw new Exception("You are not running the tests on windows operating system");
			}
		case "chrome":

			if (osType.equals("MacOS")) {
				WebDriverManager.chromedriver().setup();
				try {
					remoteWebDriver = new ChromeDriver();
					setDriver(remoteWebDriver);
				} catch (Exception e) {
					e.printStackTrace();
				}
				log.info("Driver for Chrome initialized.");
				break;
			} else if (osType.equals("Windows")) {
				WebDriverManager.chromedriver().setup();
				try {
					remoteWebDriver = new ChromeDriver();
					remoteWebDriver.manage().window().maximize();
					setDriver(remoteWebDriver);
				} catch (Exception e) {
					e.printStackTrace();
				}
				log.info("Driver for Chrome initialized.");
				break;
			} else if (osType.equals("Linux")) {

			} else {
				throw new Exception("Please check your operating system");
			}
		case "firefox":
			log.info("Initializing driver for Firefox...");
			webDriver.set(new FirefoxDriver());
			log.info("Driver for Firefox initialized.");
			break;
		default:
			break;
		}
		try {
			getDriver().manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);
		} catch (NoSuchSessionException e) {
			getDriver().manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);
		}

	}

	/**
	 * detect the OS on which tests are running
	 */
	public static String getOperatingSystemType() {
		String OS = System.getProperty("os.name", "generic").toLowerCase();
		String identifiededOS;

		if ((OS.indexOf("mac") >= 0)) {
			identifiededOS = "MacOS";
		} else if (OS.indexOf("win") >= 0) {
			identifiededOS = "Windows";
		} else if (OS.indexOf("nux") >= 0) {
			identifiededOS = "Linux";
		} else {
			identifiededOS = "Other";
		}

		return identifiededOS;
	}

	public static synchronized void setDriver(RemoteWebDriver remote) {
		String id = Thread.currentThread().getName().trim();
		map.put(id, remote);
	}

	public static synchronized RemoteWebDriver getDriver() {
		String id = Thread.currentThread().getName().trim();
		return map.get(id);
	}

	public static synchronized void setDriverFactory(DriverFactory factory) {
		factory = factory;
	}
}