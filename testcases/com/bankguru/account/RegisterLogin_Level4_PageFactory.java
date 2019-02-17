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
import pageObjects.HomePageObject;
import pageObjects.LoginPageObject;
import pageObjects.PageFactoryManager;
import pageObjects.RegisterPageObject;

public class RegisterLogin_Level4_PageFactory extends AbstractTest {
	
	
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browserName) {
		driver = openMultiBrowser(browserName);
		System.out.println("Driver in Testcase =" + driver);
		email = "Seleniumclass" + randomNumber() + "@gmail.com";
		
		//Mo URL len > vao LoginPage - 1 lan
		loginPage = PageFactoryManager.getLoginPage(driver);
	}

	@Test
	public void TC01_RegisterToSystem() {
		loginUrl = loginPage.getLoginPageURL();
		registerPage= loginPage.clickToHereLink();
		registerPage.inputToEmailIDTextbox(email);
		registerPage.clickToSubmitButton();
		userID = registerPage.getUserIDText();
		password = registerPage.getPasswordText();
		
	}

	@Test
	public void TC02_LoginWithAboveInformation() {
		loginPage = registerPage.openLoginPage(loginUrl);
		loginPage.inputToUserIDTextbox(userID);
		loginPage.inputToPasswordTextbox(password);
		homepage = loginPage.clickToLoginButton();
		Assert.assertTrue(homepage.isHomePageDisplayed());
	}
	
	@Test
	public void TC03_OpenMultiplePages() {
		
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

}
