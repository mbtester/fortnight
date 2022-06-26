package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AppPage {
	
	//locators
	By emailField = By.xpath("");
	By submitBtn = By.xpath("");
	By passwordField = By.xpath("");

	
	//basic methods
	public void enterUserName(RemoteWebDriver driver, String userName) throws InterruptedException {
		enterData(driver, emailField, userName);

	}

	public void enterPassword(RemoteWebDriver driver, String password) throws InterruptedException {
		enterData(driver, passwordField, password);
	}
	
	public void clickOnSubmit(RemoteWebDriver driver) {
		driver.findElement(submitBtn).click();
	}

	public void waitForElementClickability(RemoteWebDriver driver, By locator) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 60);
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void enterData(RemoteWebDriver driver, By locator, String value) throws InterruptedException {
		waitForElementClickability(driver, locator);
		driver.findElement(locator).click();
		driver.findElement(locator).clear();
		Thread.sleep(1000);
		driver.findElement(locator).sendKeys(value);
	}
}
