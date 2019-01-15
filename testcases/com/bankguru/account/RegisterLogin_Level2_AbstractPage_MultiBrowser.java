package com.bankguru.account;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.AbstractPage;

public class RegisterLogin_Level2_AbstractPage_MultiBrowser {
	WebDriver driver;
	private AbstractPage abstractPage;
	private String loginUrl, email, userID, password;
	
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browserName) {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		abstractPage = new AbstractPage();
		email = "Seleniumclass" + randomNumber() + "@gmail.com";
		System.out.println("Email =" + email);
	}

	@Test
	public void TC01_RegisterToSystem() {

		abstractPage.openAnyUrl(driver, "http://demo.guru99.com/v4/index.php");

		loginUrl = abstractPage.getCurrentUrl(driver);

		abstractPage.clickToElement(driver, "//a[text() ='here']");
		Assert.assertTrue(abstractPage.isControlDisplayed(driver, "//input[@name='emailid']"));

		abstractPage.sendkeyToElement(driver, "//input[@name='emailid']", email);
		abstractPage.clickToElement(driver, "//input[@name= 'btnLogin']");
		userID = abstractPage.getTextElement(driver, "//td[text() ='User ID :']/following-sibling::td");
		password = abstractPage.getTextElement(driver, "//td[text() ='Password :']/following-sibling::td");

		System.out.println("UserID =" + userID);
		System.out.println("Password =" + password);
	}

	@Test
	public void TC02_LoginWithAboveInformation() {
		abstractPage.openAnyUrl(driver, loginUrl);
		abstractPage.sendkeyToElement(driver, "//input[@name='uid']", userID);
		abstractPage.sendkeyToElement(driver, "//input[@name='password']", password);
		abstractPage.clickToElement(driver, "//input[@name='btnLogin']");
		Assert.assertTrue(abstractPage.isControlDisplayed(driver,
				"//marquee[text()= \"Welcome To Manager's Page of Guru99 Bank\"]"));
		Assert.assertTrue(abstractPage.isControlDisplayed(driver, "//td[text() ='Manger Id : " + userID + "']"));
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
