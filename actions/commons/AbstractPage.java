package commons;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObjects.DepositPageObject;
import pageObjects.FundTransferPageObject;
import pageObjects.HomePageObject;
import pageObjects.NewAccountPageObject;
import pageObjects.NewCustomerPageObject;
import pageObjects.PageFactoryManager;
import pageUIs.AbstractPageUI;
import pageUIs.DepositPageUI;
import pageUIs.HomePageUI;
import pageUIs.NewAccountPageUI;
import pageUIs.NewCustomerPageUI;

public class AbstractPage {
	// Web Browser
	public void openAnyUrl(WebDriver driver, String url) {
		driver.get(url);
	}

	public String getCurrentUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	public String getTitle(WebDriver driver) {
		return driver.getTitle();
	}

	public String getPageSource(WebDriver driver) {
		return driver.getPageSource();
	}

	public void back(WebDriver driver) {
		driver.navigate().back();
	}

	public void forword(WebDriver driver) {
		driver.navigate().forward();
	}

	public void refresh(WebDriver driver) {
		driver.navigate().refresh();
	}

	public void acceptAlert(WebDriver driver) {
		driver.switchTo().alert().accept();
	}

	public void cancelAlert(WebDriver driver) {
		driver.switchTo().alert().dismiss();
	}

	public String getTextAlert(WebDriver driver) {
		return driver.switchTo().alert().getText();
	}

	// Web Element
	public void sendkeyToAlert(WebDriver driver, String value) {
		driver.switchTo().alert().sendKeys(value);
	}

	public void clickToElement(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();
	}
	
	public void clickToElement(WebDriver driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[])dynamicValue);
		System.out.println("Click to dynamic locator =" + locator );
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();
	}

	public void sendkeyToElement(WebDriver driver, String locator, String value) {
		WebElement element = driver.findElement(By.xpath(locator));
		element.clear();
		element.sendKeys(value);
	}

	public void selectItemInHtmlDropdown(WebDriver driver, String locator, String valueInDropdown) {
		WebElement element = driver.findElement(By.xpath(locator));
		Select select = new Select(element);
		select.selectByVisibleText(valueInDropdown);
	}

	public String getSelectItemInHtmlDropdown(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		Select select = new Select(element);
		return select.getFirstSelectedOption().getText();
	}

	public void selectItemInCustomDropDown(WebDriver driver, String scrollToXpath, String parentXpath,
			String childXpath, String expectedItem) throws InterruptedException {

		WebDriverWait waitExplicit = new WebDriverWait(driver, 30);
		JavascriptExecutor javaExecutor = (JavascriptExecutor) driver;
		// scroll to element (cha)
		javaExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(scrollToXpath)));
		Thread.sleep(1000);

		// Click vao dropdown
		WebElement element = driver.findElement(By.xpath(parentXpath));
		Thread.sleep(1000);
		element.click();

		// Get tat ca item trong dropdown vao 1 list element (List <WebElement>)
		List<WebElement> childList = driver.findElements(By.xpath(childXpath));

		// Wait de tat ca cac phan tu trong dropdown duoc hien thi
		waitExplicit.until(ExpectedConditions.visibilityOfAllElements(childList));

		// Dung vong lap for duyet qua tung phan tu sau do getText
		for (WebElement child : childList) {
			String textItem = child.getText().trim();
			System.out.println("Text in drop dow =" + textItem);

			// Neu actual text = expected text thi click vao phan tu do va break khoi vong
			// lap
			if (textItem.equals(expectedItem)) {
				javaExecutor.executeScript("arguments[0].scrollIntoView(true);", child);
				Thread.sleep(1000);
				child.click();
				break;
			}
		}

	}

	public String getAttributeValue(WebDriver driver, String locator, String attributeValue) {
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getAttribute(attributeValue);
	}

	public String getTextElement(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getText();
	}

	public int countElementNumber(WebDriver driver, String locator) {
		List<WebElement> elements = driver.findElements(By.xpath(locator));
		return elements.size();
	}

	public void checkTheCheckbox(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (!element.isSelected()) {
			element.click();
		}
	}

	public void uncheckTheCheckbox(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.isSelected()) {
			element.click();
		}
	}
	
	//Xpath cố định
	public boolean isControlDisplayed(WebDriver driver, String locator) {
		try {
			WebElement element = driver.findElement(By.xpath(locator));
			boolean status = element.isDisplayed();
			System.out.println("Element =" + status);
			return true;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
	}
	
	// Case 1: Element ko co trong DOM
	// Case 2: Element co trong DOM nhưng ko visible
	public boolean isControlUndisplayed(WebDriver driver, String locator) {
    	Date date = new Date();
		System.out.println("Started time = " + date.toString());
		// 5s
		overrideGlobalTimeout(driver, shortTimeout);
		List<WebElement> elements = driver.findElements(By.xpath(locator));

		if (elements.size() > 0 && elements.get(0).isDisplayed()) {
			date = new Date();
			System.out.println("End time = " + date.toString());
			// 30s
			overrideGlobalTimeout(driver, longTimeout);
			return false;
		} else {
			date = new Date();
			System.out.println("End time = " + date.toString());
			// 30s
			overrideGlobalTimeout(driver, longTimeout);
			return true;
		}
	}
	
	public boolean isControlUndisplayed(WebDriver driver, String locator, String... value) {
		Date date = new Date();
		System.out.println("Started time = " + date.toString());
		overrideGlobalTimeout(driver, shortTimeout);
		locator = String.format(locator, (Object[]) value);
		List<WebElement> elements = driver.findElements(By.xpath(locator));
		
		if (elements.size() > 0 && elements.get(0).isDisplayed()) {
			date = new Date();
			System.out.println("End time = " + date.toString());
			// 30s. Quan trong, ktra set timeout
			overrideGlobalTimeout(driver, longTimeout);
			return false;
		} else {
			date = new Date();
			System.out.println("End time = " + date.toString());
			// 30s
			overrideGlobalTimeout(driver, longTimeout);
			return true;
		}
	}

	public void overrideGlobalTimeout(WebDriver driver, int timeOut) {
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
	}
	
	//xpath, locator động
	public boolean isControlDisplayed(WebDriver driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[])dynamicValue);
		System.out.println("Check dynamic locator displayed =" + locator );
		WebElement element = driver.findElement(By.xpath(locator));
		return element.isDisplayed();
	}

	public boolean isControlSelected(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		return element.isSelected();
	}

	public boolean isControlEnabled(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		return element.isEnabled();
	}

	public void switchToWindowByID(WebDriver driver, String parentID) {
		// Get ra tat ca cac cua so dang co
		Set<String> allWindows = driver.getWindowHandles();
		// dung vong lap de kiem tra. Vong lap for x duyet qua tat ca cua so
		for (String runWindow : allWindows) {
			System.out.println(runWindow);
			// Moi lan duyet kiem tra dieu kien neu ID khac vs parentID thi no switch qua
			if (!runWindow.equals(parentID)) {
				driver.switchTo().window(runWindow);
				// break khoi vong lap for khong kiem tra nua
				break;
			}
		}
	}

	public void switchToWindowByTitle(WebDriver driver, String expectedTitle) {
		// Get ra tat ca cac cua so dang co
		Set<String> allWindows = driver.getWindowHandles();
		// Dung vong lap duyet qua tat ca cac ID
		for (String runWindows : allWindows) {

			// Switch qua tung cua so truoc sau do ms kiem tra
			driver.switchTo().window(runWindows);
			// Get ra title cua page moi switch
			String actualTitle = driver.getTitle();
			// Kiem tra neu title cua page = title truyen vao
			if (actualTitle.equals(expectedTitle)) {
				// Thoat khoi vong lap- ko kiem tra nhung thang khac nua
				break;
			}
		}
	}

	public void closeAllWindowsWithoutParentWindows(WebDriver driver, String parentWindowID) {
		// Get ra tat ca cac ID cua cac cua so
		Set<String> allWindows = driver.getWindowHandles();
		// Dung vong lap for de duyet qua tung ID
		for (String runWindows : allWindows) {
			// Neu ID khong bang parentID
			if (!runWindows.equals(parentWindowID)) {
				// No se switch qua
				driver.switchTo().window(runWindows);
				// Dong tab hien tai
				driver.close();
			}
		}
		// Chi con lai 1 cua so duy nhat (parent)
		driver.switchTo().window(parentWindowID);

	}

	public void switchToIframe(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		driver.switchTo().frame(element);
	}

	public void doubleClickToElement(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		Actions action = new Actions(driver);
		action.doubleClick(element);
	}

	public void hoverMouseToElement(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		Actions action = new Actions(driver);
		action.moveToElement(element);
	}

	public void rightClick(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		Actions action = new Actions(driver);
		action.contextClick(element).perform();
	}

	public void dragAndDrop(WebDriver driver, String locatorOne, String locatorTwo) {
		WebElement elementOne = driver.findElement(By.xpath(locatorOne));
		WebElement elementTwo = driver.findElement(By.xpath(locatorTwo));
		Actions action = new Actions(driver);
		action.dragAndDrop(elementOne, elementTwo).build().perform();
	}

	public void keyPress(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		Actions action = new Actions(driver);
		action.sendKeys(element, Keys.ENTER).perform();
	}

	public void uploadSingleFile(WebDriver driver, String locator, String filename) {
		WebElement element = driver.findElement(By.xpath(locator));
		element.sendKeys(filename);
	}

	public void uploadMultipleFile(WebDriver driver, String locator, String filename01, String filename02,
			String filename03) {
		WebElement element = driver.findElement(By.xpath(locator));
		element.sendKeys(filename01 + "\n" + filename02 + "\n" + filename03);
	}

	public Object executeJavascriptToBrowser(WebDriver driver, String javaSript) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			return js.executeScript(javaSript);
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	public Object executeJavascriptToElement(WebDriver driver, WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			return js.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	public Object scrollToBottomPage(WebDriver driver) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			return js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	public void scrollToElement(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

	}

	public void highlightElement(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='6px groove red'", element);
	}

	public Object removeAttributeInDOM(WebDriver driver, WebElement element, String attribute) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			return js.executeScript("arguments[0].removeAttribute('" + attribute + "');", element);
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	public boolean isImageDisplayed(WebDriver driver, String locator) {
		try {
			WebElement element = driver.findElement(By.xpath(locator));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			return (boolean) js.executeScript(
					"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
					element);
		} catch (Exception e) {
			e.getMessage();
			return false;
		}
	}

	public void waitToElementVisible(WebDriver driver, String locator) {
		By byLocator = By.xpath(locator);
		WebDriverWait waitExplicit = new WebDriverWait(driver, 30);
		try {
			waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	//waitToElementVisible(driver, "//a[text()='']", "New Customer"];
	public void waitToElementVisible(WebDriver driver, String locator, String... dynamicValue) {
		locator = String.format(locator, (Object[])dynamicValue);
		System.out.println("Wait to dynamic locator =" + locator );
		By byLocator = By.xpath(locator);
		WebDriverWait waitExplicit = new WebDriverWait(driver, 30);
		waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
	}

	public void waitToElementPresence(WebDriver driver, String locator) {
		By byLocator = By.xpath(locator);
		WebDriverWait waitExplicit = new WebDriverWait(driver, 30);
		waitExplicit.until(ExpectedConditions.presenceOfElementLocated(byLocator));
	}

	public void waitToElementClickable(WebDriver driver, String locator) {
		By byLocator = By.xpath(locator);
		WebDriverWait waitExplicit = new WebDriverWait(driver, 30);
		waitExplicit.until(ExpectedConditions.elementToBeClickable(byLocator));
		driver.findElement(byLocator).click();
	}

	public void waitToElementInVisible(WebDriver driver, String locator) {
		By byLocator = By.xpath(locator);
		WebDriverWait waitExplicit = new WebDriverWait(driver, 30);
		waitExplicit.until(ExpectedConditions.invisibilityOfElementLocated(byLocator));
	}

	public void waitForAlertPresence(WebDriver driver) {
		WebDriverWait waitExplicit = new WebDriverWait(driver, 30);
		waitExplicit.until(ExpectedConditions.alertIsPresent());
	}
	
	//open it page
	public AbstractPage openDynamicPage (WebDriver driver, String pageName) {
		waitToElementVisible(driver, AbstractPageUI.DYNAMIC_LINK, pageName);
		clickToElement(driver, AbstractPageUI.DYNAMIC_LINK, pageName);
		
		switch(pageName) {
		case "New Customer":
			return PageFactoryManager.getNewCustomerPage(driver);
		case "New Account":
			return PageFactoryManager.getNewAccountPage(driver);
		case "Deposit":
			return PageFactoryManager.getDepositPage(driver);
		case "Fund Transfer":
			return PageFactoryManager.getFundTransferPage(driver);
		default:
			return PageFactoryManager.getHomePage(driver);
		}
	}
	
	//open nhieu page (100-1000 pages)
	public void openDynamicMorePage (WebDriver driver, String pageName) {
		waitToElementVisible(driver, AbstractPageUI.DYNAMIC_LINK, pageName);
		clickToElement(driver, AbstractPageUI.DYNAMIC_LINK, pageName);
	}
	
	// 13 ham mo ra 13 page ma khong can viet di viet lai (169 page trong tang Page
	// Object)
	public NewCustomerPageObject openNewCustomerPage(WebDriver driver) {
		waitToElementVisible(driver, AbstractPageUI.NEW_CUSTOMER_LINK);
		clickToElement(driver, AbstractPageUI.NEW_CUSTOMER_LINK);
		return PageFactoryManager.getNewCustomerPage(driver);
	}

	public NewAccountPageObject openNewAccountPage(WebDriver driver) {
		waitToElementVisible(driver, AbstractPageUI.NEW_ACCOUNT_LINK);
		clickToElement(driver, AbstractPageUI.NEW_ACCOUNT_LINK);
		return PageFactoryManager.getNewAccountPage(driver);
	}

	public DepositPageObject openDepositPage(WebDriver driver) {
		waitToElementVisible(driver, AbstractPageUI.DEPOSIT_LINK);
		clickToElement(driver, AbstractPageUI.DEPOSIT_LINK);
		return PageFactoryManager.getDepositPage(driver);
	}

	public FundTransferPageObject openFundTransferPage(WebDriver driver) {
		waitToElementVisible(driver, AbstractPageUI.FUND_TRANSFER_LINK);
		clickToElement(driver, AbstractPageUI.FUND_TRANSFER_LINK);
		return PageFactoryManager.getFundTransferPage(driver);
	}

	public HomePageObject openHomePage(WebDriver driver) {
		waitToElementVisible(driver, AbstractPageUI.HOME_PAGE_LINK);
		clickToElement(driver, AbstractPageUI.HOME_PAGE_LINK);
		return PageFactoryManager.getHomePage(driver);
	}
	
	int shortTimeout =5;
	int longTimeout = 30;
}
