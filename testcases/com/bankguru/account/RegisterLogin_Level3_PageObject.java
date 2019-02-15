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
import pageObjects.RegisterPageObject;

public class RegisterLogin_Level3_PageObject extends AbstractTest {
	private WebDriver driver;
	private String loginUrl, email, userID, password;
	private LoginPageObject loginPage;
	private RegisterPageObject registerPage;
	private HomePageObject homepage;
	
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browserName) {
		driver = openMultiBrowser(browserName);
		email = "Seleniumclass" + randomNumber() + "@gmail.com";
		//Mo URL len > vao LoginPage
		loginPage = new LoginPageObject();
	}

	@Test
	public void TC01_RegisterToSystem() {
		loginUrl = loginPage.getLoginPageURL();
		loginPage.clickToHereLink();
		//Click HereLink > Vao RegisterPage
		registerPage = new RegisterPageObject();
		registerPage.inputToEmailIDTextbox(email);
		registerPage.clickToSubmitButton();
		userID = registerPage.getUserIDText();
		password = registerPage.getPasswordText();
		
	}

	@Test
	public void TC02_LoginWithAboveInformation() {
		registerPage.openLoginPage(loginUrl);
		
		//Open Login URL > Vao Login lai
		loginPage = new LoginPageObject();
		
		loginPage.inputToUserIDTextbox(userID);
		loginPage.inputToPasswordTextbox(password);
		loginPage.clickToLoginButton();
		
		//Click to login vao trang home page
		homepage = new HomePageObject();
		Assert.assertTrue(homepage.isHomePageDisplayed());
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public int randomNumber() {
		Random random = new Random();
		int number = random.nextInt(999999);
		return number;
	}

}
