package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPage;
import pageUIs.HomePageUI;
import pageUIs.NewAccountPageUI;

public class NewAccountPageObject extends AbstractPage {
	WebDriver driver;
	
	public NewAccountPageObject(WebDriver driverMapping) {
		driver = driverMapping;
		System.out.println("Driver in HomePageObject =" + driver);
	}

	public boolean isNewAccountPageDisplayed() {
		waitToElementVisible(driver, NewAccountPageUI.NEW_ACCOUNT_TEXT);
		return isControlDisplayed(driver, NewAccountPageUI.NEW_ACCOUNT_TEXT);
	}
	
}
