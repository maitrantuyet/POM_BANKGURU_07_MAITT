package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPage;
import pageUIs.FundTransferPageUI;
import pageUIs.HomePageUI;

public class FundTransferPageObject extends AbstractPage {
	WebDriver driver;
	
	public FundTransferPageObject(WebDriver driverMapping) {
		driver = driverMapping;
		System.out.println("Driver in HomePageObject =" + driver);
	}

	public boolean isFundTransferPageDisplayed() {
		waitToElementVisible(driver, FundTransferPageUI.FUND_TRANSFER_TEXT);
		return isControlDisplayed(driver, FundTransferPageUI.FUND_TRANSFER_TEXT);
	}
	
	
}
