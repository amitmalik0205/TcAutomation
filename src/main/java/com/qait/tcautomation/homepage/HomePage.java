package com.qait.tcautomation.homepage;

import org.openqa.selenium.WebDriver;

import com.qait.tcautomation.genric.page.BasePage;
import com.qait.tcautomation.slo.pages.StudentLearningObjectivesPage;


/**
 * This class is corresponding to the home page(After login)
 * 
 * @author amitmalik
 */
public class HomePage extends BasePage {

	private final String  pageTile = "Schoolnet : My Schoolnet - Schoolnet";
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	
	/**
	 * Method will load SLO home page
	 * 
	 * @return If page loaded then return StudentLearningObjectivesPage object
	 *         otherwise exception will be thrown
	 */
	public StudentLearningObjectivesPage getSloPage() {

		StudentLearningObjectivesPage page = new StudentLearningObjectivesPage(driver);

		loadPage(baseUrl+"/"+page.getPageUrl(), page.getPageTile());

		return page;
	}
	
	
	public String getPageTile() {
		return pageTile;
	}
}
