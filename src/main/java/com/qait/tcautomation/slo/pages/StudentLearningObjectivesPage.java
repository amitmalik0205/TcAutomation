package com.qait.tcautomation.slo.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qait.tcautomation.genric.page.BasePage;
import com.qait.tcautomation.teameds.pages.ClassRoomObservationFormPage;
import com.qait.tcautomation.util.WaitUtil;
/**
 * This page is corresponding to SLO home page
 * @author amitmalik
 *
 */
public class StudentLearningObjectivesPage extends BasePage {

	private final String pageUrl = "HCMS/EvaluationManager.mvc/Index/StudentLearningObjective";
	
	private final String  pageTile = "Student Learning Objectives";
	
	public StudentLearningObjectivesPage(WebDriver driver) {
		this.driver = driver;
	}
	
	/**
	 * Method will load the page containing SLO form
	 * 
	 * @return ClassRoomObservationFormPage object if page loaded successfully
	 *         otherwise time out exception will be thrown by selenium after 90
	 *         seconds
	 */
	public ClassRoomObservationFormPage loadClassroomObservationFormPage() {

		ClassRoomObservationFormPage page = new ClassRoomObservationFormPage(
				driver);

		clickButtonBy(By.id("btnCreateNewSLO"));

		WaitUtil.waitForPageTitle(driver, WaitUtil.DEFAULT_WAIT_FOR_PAGE,
				page.getSuccessPageTile2());

		return page;
	}

	
	/**
	 * Method returns all the rows of table containing list of SLO's
	 * 
	 * @return
	 */
	public List<WebElement> getSloList() {
		return getElementListBy(By
				.xpath("//div[@id='mainGrid']/table/tbody/tr"));
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public String getPageTile() {
		return pageTile;
	}

}
