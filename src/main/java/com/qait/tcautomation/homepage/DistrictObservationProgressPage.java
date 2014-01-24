package com.qait.tcautomation.homepage;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qait.tcautomation.genric.page.BasePage;
import com.qait.tcautomation.teameds.pojo.Observation;
import com.qait.tcautomation.util.WaitUtil;

/**
 * @author amitmalik
 * 
 */

public class DistrictObservationProgressPage extends BasePage {

	private String successPageTile = "Schoolnet : My Schoolnet - Schoolnet";

	private String pageUrl = "HCMS/ObservationReportByInstitutionLevel.aspx";

	private Observation observation;

	public DistrictObservationProgressPage() {

	}

	public DistrictObservationProgressPage(WebDriver driver) {
		this.driver = driver;
	}

	
	public DistrictObservationProgressPage(WebDriver driver,
			Observation observation) {
		this.driver = driver;
		this.observation = observation;
	}

	/**
	 * This method loads the page containing the list of observation for a
	 * window.
	 * 
	 * @param windowName
	 *            Name of the window for which observations are to be loaded
	 */
	public void getObservationListForWindow(String windowName) {

		// It will display the list of all the schools in the default window
		driver.get(baseUrl + "/" + pageUrl);

		// Waits for the "waitAnimation" to load for maximum of 60 seconds..
		WebElement e = WaitUtil.waitForElementVisiblity(driver,
				By.id("waitAnimation"), 100);
		assertNotNull("Element not found in 100 seconds so it is null : ", e);

		// Wait while "waitAnimation" is present
		while (e.isDisplayed() == true) {
			System.out.println("inside while: waiting");
		}

		driver.findElement(By.linkText("Choose another window")).click();

		List<WebElement> list = driver
				.findElements(By
						.xpath("//div[@id='ctl00_MainContent_m_wsc_selector']/ul/li/strong/span"));

		for (WebElement element : list) {
			if (element.getText().equalsIgnoreCase(windowName))
				element.click();
		}

	}


	public String getSuccessPageTile() {
		return successPageTile;
	}

	public void setSuccessPageTile(String successPageTile) {
		this.successPageTile = successPageTile;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
}
