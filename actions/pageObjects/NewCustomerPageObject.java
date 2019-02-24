package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPage;
import pageUIs.HomePageUI;
import pageUIs.NewCustomerPageUI;

public class NewCustomerPageObject extends AbstractPage {
	WebDriver driver;
	
	public NewCustomerPageObject(WebDriver driverMapping) {
		driver = driverMapping;
		System.out.println("Driver in HomePageObject =" + driver);
	}

	public boolean isNewCustomerPageDisplayed() {
		waitToElementVisible(driver, NewCustomerPageUI.NEW_CUSTOMER_TEXT);
		return isControlDisplayed(driver, NewCustomerPageUI.NEW_CUSTOMER_TEXT);
	}
	
	public boolean isHomePageUnDisplayed() {
		waitToElementInVisible(driver, HomePageUI.HOMEPAGE_WELLCOME_MESSAGE);
		return isControlUndisplayed(driver, HomePageUI.HOMEPAGE_WELLCOME_MESSAGE);
	}
	
	public boolean isAddCustomerFormUnDisplay() {
		waitToElementInVisible(driver, NewCustomerPageUI.CUSTOMER_FORM);
		return isControlUndisplayed(driver, NewCustomerPageUI.CUSTOMER_FORM);
	}
}
