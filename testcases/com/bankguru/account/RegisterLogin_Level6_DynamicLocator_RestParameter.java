package com.bankguru.account;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import commons.AbstractTest;
import pageObjects.DepositPageObject;
import pageObjects.FundTransferPageObject;
import pageObjects.HomePageObject;
import pageObjects.LoginPageObject;
import pageObjects.NewAccountPageObject;
import pageObjects.NewCustomerPageObject;
import pageObjects.PageFactoryManager;
import pageObjects.RegisterPageObject;

public class RegisterLogin_Level6_DynamicLocator_RestParameter extends AbstractTest {
	
	
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browserName) {
		driver = openMultiBrowser(browserName);
		System.out.println("Driver in Testcase =" + driver);
		email = "Seleniumclass" + randomNumber() + "@gmail.com";
		
		//1
		loginPage = PageFactoryManager.getLoginPage(driver);
	}

	@Test
	public void Account_01_RegisterToSystem() {
		loginUrl = loginPage.getLoginPageURL();
		//2
		registerPage= loginPage.clickToHereLink();
		registerPage.inputToEmailIDTextbox(email);
		registerPage.clickToSubmitButton();
		userID = registerPage.getUserIDText();
		password = registerPage.getPasswordText();
		
	}

	@Test
	public void Account_02_LoginWithAboveInformation() {
		//3
		loginPage = registerPage.openLoginPage(loginUrl);
		loginPage.inputToUserIDTextbox(userID);
		loginPage.inputToPasswordTextbox(password);
		homepage = loginPage.clickToLoginButton();
		//4
		Assert.assertTrue(homepage.isHomePageDisplayed());
	}
	
	@Test
	public void Account_03_WebDriverLifeCycle() {
		
		//NewCustomer > NewAccount > Deposit > FundTransfer
		newCustomerPage = (NewCustomerPageObject) homepage.openDynamicPage(driver, "New Customer");
		Assert.assertTrue(newCustomerPage.isNewCustomerPageDisplayed());
		
		newAccountPage = (NewAccountPageObject) newCustomerPage.openDynamicPage(driver, "New Account");
		Assert.assertTrue(newAccountPage.isNewAccountPageDisplayed());
		
		depositPage = (DepositPageObject) newAccountPage.openDynamicPage(driver, "Deposit");
		Assert.assertTrue(depositPage.isDepositPageDisplayed());
		
		fundTransferPage = (FundTransferPageObject) depositPage.openDynamicPage(driver, "Fund Transfer");
		Assert.assertTrue(fundTransferPage.isFundTransferPageDisplayed());
		
		//FundTransfer > HomePage
		homepage = (HomePageObject) fundTransferPage.openDynamicPage(driver, "Manager");
		Assert.assertTrue(homepage.isHomePageDisplayed());
		
		//HomePage > NewAccount
		newAccountPage = (NewAccountPageObject) homepage.openDynamicPage(driver, "New Account");
		Assert.assertTrue(newAccountPage.isNewAccountPageDisplayed());
		
		/*100- 1000 pages*/
		//New Account > New Customer
		newAccountPage.openDynamicPage(driver, "New Customer");
		newCustomerPage = PageFactoryManager.getNewCustomerPage(driver);
		Assert.assertTrue(newCustomerPage.isNewCustomerPageDisplayed());
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	private WebDriver driver;
	private String loginUrl, email, userID, password;
	private LoginPageObject loginPage;
	private RegisterPageObject registerPage;
	private HomePageObject homepage;
	private NewCustomerPageObject newCustomerPage;
	private NewAccountPageObject newAccountPage;
	private DepositPageObject depositPage;
	private FundTransferPageObject fundTransferPage;

}
