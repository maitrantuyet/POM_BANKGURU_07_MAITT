package com.bankguru.demo;

public class DynamicLocator {

	public static void main(String[] args) {
		String NEW_CUSTOMER_LINK = "//a[text() = 'New Customer']";
		String NEW_ACCOUNT_LINK = "//a[text()='New Account']";
		String DEPOSIT_LINK = "//a[text()='Deposit']";
		String HOME_PAGE_LINK = "//a[text()= 'Manager']";
		String DYNAMIC_LINK_1_PARAM = "//a[text()= '%s']";
		String DYNAMIC_LINK_2_PARAM = "//a[text()= '%s']//a[text()= '%s']";
		String DYNAMIC_LINK_3_PARAM = "//a[text()= '%s']//a[text()= '%s']//a[text()= '%s']";
		String AFGHANISTAN = "//td[@data-key='females' and text()='384187']/following-sibling::td[@data-key='country' and text()='Afghanistan']/following-sibling::td[@data-key='males' and text()='407124']/preceding-sibling::td[@class='qgrd-actions']/button[@class='qgrd-edit-row-btn']";
		

		clickToElement(NEW_CUSTOMER_LINK);
		clickToElement(NEW_ACCOUNT_LINK);
		clickToElement(DEPOSIT_LINK);
		clickToElement(HOME_PAGE_LINK);
		
		clickToElement(DYNAMIC_LINK_1_PARAM, "New Customer");
		clickToElement(DYNAMIC_LINK_1_PARAM, "New Account");
		clickToElement(DYNAMIC_LINK_1_PARAM, "Deposit");
		clickToElement(DYNAMIC_LINK_1_PARAM, "Manager");
		
		clickToElement(DYNAMIC_LINK_2_PARAM, "Female", "Afghanistan");
		clickToElement(DYNAMIC_LINK_2_PARAM, "Male", "Albania");
		
	}

	public static void clickToElement(String pageName) {
		//Click vao 1 page
		System.out.println(pageName);
	}

	public static void clickToElement(String pageName, String dynamicValue) {
		// Click vao 2 tham so dong
		System.out.println(String.format(pageName, dynamicValue));
	}
	public static void clickToElement(String pageName, String dynamicValue_01, String dynamicValue_02) {
		// Click vao 2 tham so dong
		System.out.println(String.format(pageName, dynamicValue_01, dynamicValue_02));
	}
}
