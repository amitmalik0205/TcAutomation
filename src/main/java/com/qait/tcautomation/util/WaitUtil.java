package com.qait.tcautomation.util;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author amitmalik
 * 
 */

public class WaitUtil {

	public static final int DEFAULT_WAIT_FOR_ELEMENT = 30;

	public static final int DEFAULT_WAIT_FOR_PAGE = 90;

	/**
	 * Wait for the element to be present in the DOM, and displayed on the page.
	 * 
	 * @param driver
	 *            Driver object to be used
	 * @param by
	 *            selector to find the element
	 * @param timeOutInSeconds
	 *            time in seconds to wait
	 * 
	 * @return WebElement the first WebElement if found in the specified time
	 *         interval otherwise null
	 */
	public static WebElement waitForElementVisiblity(WebDriver driver, By by,
			long timeOutInSeconds) {

		WebElement element = null;

		/*
		 * To use the explicit wait we will have to first set the implicit wait
		 * to zero(default value by selenium). Then after wait completes reset
		 * the implicit wait again.
		 */
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

		WebDriverWait driverWait = new WebDriverWait(driver, timeOutInSeconds);
		element = driverWait.until(ExpectedConditions
				.visibilityOfElementLocated(by));

		driver.manage().timeouts()
				.implicitlyWait(DEFAULT_WAIT_FOR_ELEMENT, TimeUnit.SECONDS);

		return element;
	}

	/**
	 * Wait for the all elements with selector specified by @param By to be
	 * visible in the DOM.
	 * 
	 * @param driver
	 *            Driver object to be used
	 * @param by
	 *            selector to find the element
	 * @param timeOutInSeconds
	 *            time in seconds to wait
	 * @return List<WebElement> the list of all WebElements if found in the
	 *         specified time interval otherwise null
	 */
	public static List<WebElement> waitForAllElementsVisiblity(
			WebDriver driver, By by, long timeOutInSeconds) {

		List<WebElement> webElementList = null;

		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

		WebDriverWait driverWait = new WebDriverWait(driver, timeOutInSeconds);
		webElementList = driverWait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(by));

		driver.manage().timeouts()
				.implicitlyWait(DEFAULT_WAIT_FOR_ELEMENT, TimeUnit.SECONDS);
		return webElementList;
	}

	/**
	 * Wait for the element to be present in the DOM. It will not wait for
	 * element to be visible.
	 * 
	 * @param driver
	 *            Driver object to be used
	 * @param by
	 *            selector to find the element
	 * @param timeOutInSeconds
	 *            time in seconds to wait
	 * 
	 * @return WebElement the first WebElement if found in the specified time
	 *         interval otherwise null
	 */
	public static WebElement waitForElementPresence(WebDriver driver, By by,
			long timeOutInSeconds) {

		WebElement element = null;

		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

		WebDriverWait driverWait = new WebDriverWait(driver, timeOutInSeconds);
		element = driverWait.until(ExpectedConditions
				.presenceOfElementLocated(by));

		driver.manage().timeouts()
				.implicitlyWait(DEFAULT_WAIT_FOR_ELEMENT, TimeUnit.SECONDS);
		return element;
	}

	/**
	 * Wait for the all elements with selector specified by @param By to be
	 * present in the DOM. It will not wait for all elements to be visible.
	 * 
	 * @param driver
	 *            Driver object to be used
	 * @param by
	 *            selector to find the element
	 * @param timeOutInSeconds
	 *            time in seconds to wait
	 * @return List<WebElement> the list of all WebElements if found in the
	 *         specified time interval otherwise null
	 */
	public static List<WebElement> waitForAllElementsPresence(WebDriver driver,
			By by, long timeOutInSeconds) {

		List<WebElement> webElementList = null;

		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

		WebDriverWait driverWait = new WebDriverWait(driver, timeOutInSeconds);
		webElementList = driverWait.until(ExpectedConditions
				.presenceOfAllElementsLocatedBy(by));

		driver.manage().timeouts()
				.implicitlyWait(DEFAULT_WAIT_FOR_ELEMENT, TimeUnit.SECONDS);
		return webElementList;
	}
	
	
	/**
	 * Method to wait for a page to load having title @param pageTitle
	 * 
	 * @param driver
	 *            Driver object to be used
	 * @param timeOutInSeconds
	 *            time in seconds to wait
	 * @param pageTitle
	 *            title of the page to be matched
	 * @return true if page with required title is loaded in @param
	 *         timeOutInSeconds otherwise false
	 */
	public static boolean waitForPageTitle(WebDriver driver,
			long timeOutInSeconds, String pageTitle) {
		boolean isSuccess = false;
		setImplicitWaitToDefault(driver);

		WebDriverWait driverWait = new WebDriverWait(driver, timeOutInSeconds);
		isSuccess = driverWait.until(ExpectedConditions.titleIs(pageTitle));

		driver.manage().timeouts()
				.implicitlyWait(DEFAULT_WAIT_FOR_ELEMENT, TimeUnit.SECONDS);

		return isSuccess;
	}

	/**
	 * Method to set implicit wait to default value
	 * 
	 * @param driver
	 *            Driver object to be used
	 */
	public static void setImplicitWaitToDefault(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}

	/**
	 * Method to reset implicit wait to a user defined value.
	 * 
	 * @param driver
	 *            Driver object to be used
	 * @param timeInSeconds
	 *            time in seconds to set wait value
	 */
	public static void resetImplicitWait(WebDriver driver, long timeInSeconds) {

		setImplicitWaitToDefault(driver);

		driver.manage().timeouts()
				.implicitlyWait(timeInSeconds, TimeUnit.SECONDS);
	}
	
	
	public static boolean waitForJavaScriptCondition(WebDriver driver,
			final String javaScript, int timeOutInSeconds) {
		boolean jscondition = false;
		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify
																			// implicitlyWait()
			new WebDriverWait(driver, timeOutInSeconds) {
			}.until(new ExpectedCondition<Boolean>() {

				@Override
				public Boolean apply(WebDriver driverObject) {
					return (Boolean) ((JavascriptExecutor) driverObject)
							.executeScript(javaScript);
				}
			});
			jscondition = (Boolean) ((JavascriptExecutor) driver)
					.executeScript(javaScript);
			driver.manage().timeouts()
					.implicitlyWait(10, TimeUnit.SECONDS); // reset
																			// implicitlyWait
			return jscondition;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
