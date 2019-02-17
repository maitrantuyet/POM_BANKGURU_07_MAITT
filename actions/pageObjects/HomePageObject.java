package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPage;
import pageUIs.HomePageUI;

public class HomePageObject extends AbstractPage {
	WebDriver driver;
	
	public HomePageObject(WebDriver driverMapping) {
		driver = driverMapping;
		System.out.println("Driver in HomePageObject =" + driver);
	}
	
	public boolean isHomePageDisplayed () {
		waitToElementVisible(driver, HomePageUI.HOMEPAGE_WELLCOME_MESSAGE);
		return isControlDisplayed(driver, HomePageUI.HOMEPAGE_WELLCOME_MESSAGE);
	}

	
}
