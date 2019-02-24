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

public class RegisterLogin_Level7_CheckUndisplayed_OverrideTimeout extends AbstractTest {
	
	
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
	public void Account_03_CheckUndisplayed_OverrideTimeout() {
		
		//NewCustomer > NewAccount > Deposit > FundTransfer
		newCustomerPage = (NewCustomerPageObject) homepage.openDynamicPage(driver, "New Customer");
		
		//Verify New customer page displayed
		Assert.assertTrue(newCustomerPage.isNewCustomerPageDisplayed());
		
		//Verify New Customer form un-displayed(Co trong DOM ko visible) - Case 01
		Assert.assertTrue(newCustomerPage.isAddCustomerFormUnDisplay());
		
		//Verify Home page un-displayed(Ko Co trong DOM) - Case 02
		Assert.assertTrue(newCustomerPage.isHomePageUnDisplayed());
		
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
	

}
