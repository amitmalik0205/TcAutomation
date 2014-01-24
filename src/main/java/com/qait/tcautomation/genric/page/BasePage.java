package com.qait.tcautomation.genric.page;

import static org.junit.Assert.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qait.tcautomation.util.PropertyFileReaderUtil;
import com.qait.tcautomation.util.WaitUtil;

public class BasePage {

	protected WebDriver driver;

	protected String baseUrl;

	protected String loginUrl;
	
	public BasePage() {
		init();
	}

	public BasePage(WebDriver driver) {
		this.driver = driver;
	}
	
	private void init() {
		baseUrl = PropertyFileReaderUtil.getLoginProperty("base.url");
		loginUrl = PropertyFileReaderUtil.getLoginProperty("login.url");
	}
	
	/**
	 * Utility method to perform login functionality
	 * 
	 * @param driver
	 *            : Web driver reference
	 * @param userName
	 *            : User name
	 * @param password
	 *            : Password
	 * @param successPageTitle
	 *            Title of the success page
	 */
	public void login(WebDriver driver, String userName, String password,
			String successPageTitle) {

		loadPage(baseUrl + "/" + loginUrl, "Login");
		driver.findElement(By.id("ctl02_TextBoxUsername")).sendKeys(userName);
		driver.findElement(By.id("ctl02_TextBoxPassword")).sendKeys(password);
		driver.findElement(By.id("ctl02_ButtonLogin")).click();

		boolean isSuccess = WaitUtil.waitForPageTitle(driver, 100,
				successPageTitle);

		assertEquals("Login Failed: Problem while loggin in... ", isSuccess,
				true);

	}
	
	/**
	 * Method to load a page with URL
	 * 
	 * @param Url
	 *            : URL of the page
	 */
	protected void loadPage(String Url, String pageTitle) {
		driver.get(Url);
		boolean isSuccess = WaitUtil.waitForPageTitle(driver, 100, pageTitle);
		assertTrue("Page with title '" + pageTitle + "' not loaded..",
				isSuccess);
	}
	
	/**
	 * Method to click on an anchor tag having link text as @param linkText
	 * 
	 * @param linkText
	 *            label of the Anchor
	 */
	public void clickAnchorWithLinkText(String linkText) {
		WebElement link = driver.findElement(By.xpath("(//a[contains(text(),'"
				+ linkText + "')])"));
		link.click();
	}
	
	
	/**
	 * Method to click <a> or button
	 * 
	 * @param by
	 */
	public void clickButtonBy(By by) {
		WebElement link = driver.findElement(by);
		
		link.click();
	}
	
	public List<WebElement> getElementListBy(By by) {
		List<WebElement> list = driver.findElements(by);
		assertNotNull("Assertion Error : List of elements is null..", list);
		return list;
	}

	public WebElement getElementBy(By by) {
		WebElement e = driver.findElement(by);
		assertNotNull("Assertion Error : Element is null..", e);
		return e;
	}
}
