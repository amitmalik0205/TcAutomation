package com.qait.tcautomation.genric.test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import com.qait.tcautomation.util.WaitUtil;

public class BaseTest {

	protected WebDriver driver;
	
	/**
	 * Initial method to initialise web driver and implicit waits
	 */
	public void setUp(WebDriver driver) {

		this.driver = driver;
		driver.manage()
				.timeouts()
				.implicitlyWait(WaitUtil.DEFAULT_WAIT_FOR_ELEMENT,
						TimeUnit.SECONDS);
		driver.manage()
				.timeouts()
				.pageLoadTimeout(WaitUtil.DEFAULT_WAIT_FOR_PAGE,
						TimeUnit.SECONDS);
	}
	
	public void tearDown() {
		driver.close();
	}
}
