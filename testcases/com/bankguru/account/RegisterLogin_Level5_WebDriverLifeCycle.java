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

public class RegisterLogin_Level5_WebDriverLifeCycle extends AbstractTest {
	
	
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
		newCustomerPage = homepage.openNewCustomerPage(driver);
		Assert.assertTrue(newCustomerPage.isNewCustomerPageDisplayed());
		
		newAccountPage= newCustomerPage.openNewAccountPage(driver);
		Assert.assertTrue(newAccountPage.isNewAccountPageDisplayed());
		
		depositPage = newAccountPage.openDepositPage(driver);
		Assert.assertTrue(depositPage.isDepositPageDisplayed());
		
		fundTransferPage = depositPage.openFundTransferPage(driver);
		Assert.assertTrue(fundTransferPage.isFundTransferPageDisplayed());
		
		//FundTransfer > HomePage
		homepage = fundTransferPage.openHomePage(driver);
		Assert.assertTrue(homepage.isHomePageDisplayed());
		
		//HomePage > NewAccount
		newAccountPage = homepage.openNewAccountPage(driver);
		Assert.assertTrue(newAccountPage.isNewAccountPageDisplayed());
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
