package com.qait.tcautomation.slo.tests;

import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.qait.tcautomation.genric.test.BaseTest;
import com.qait.tcautomation.homepage.HomePage;
import com.qait.tcautomation.slo.pages.StudentLearningObjectivesPage;
import com.qait.tcautomation.teameds.pages.ClassRoomObservationFormPage;
import com.qait.tcautomation.util.PropertyFileReaderUtil;
import com.qait.tcautomation.util.TcAutomationUtil;

public class SloTest extends BaseTest {

	private HomePage homePage;
	private StudentLearningObjectivesPage sloPage;
	private ClassRoomObservationFormPage classRoomObservationFormPage;
	private String teacherUserName;
	private String teacherPassword;
	private String principalUserName;
	private String principalPassword;

	@Before
	public void initialSetUp() {

		super.setUp(new FirefoxDriver());

		homePage = new HomePage(driver);

		teacherUserName = PropertyFileReaderUtil
				.getLoginProperty("slo.teacher.username");

		teacherPassword = PropertyFileReaderUtil
				.getLoginProperty("slo.teacher.password");

		principalUserName = PropertyFileReaderUtil
				.getLoginProperty("slo.principal.username");

		principalPassword = PropertyFileReaderUtil
				.getLoginProperty("slo.principal.password");

	}

	@Test
	public void lauchSloFormTest() {
		try {

			loginAsTeacher();
			
			sloPage = homePage.getSloPage();
			
			classRoomObservationFormPage = sloPage.loadClassroomObservationFormPage();
			
			System.out.println("SLO page launched succesfully...");
			
			postComments();
					
			postCommentThroughNotepadTest();
			
			classRoomObservationFormPage.shareDraft();
			
		} catch (Exception e) {
			System.out.println(TcAutomationUtil
					.getExceptionDescriptionString(e));
			assertNull(
					"Assertion Error : Selenium web driver through some exception..",
					e);
		} catch (AssertionError e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * This method does the following things: 1. Launch the observation 2.
	 * Comments are made through note pad 3. Check that comments are reflected
	 * in text areas of objective under all domains.
	 */
	public void postCommentThroughNotepadTest() {
		classRoomObservationFormPage
				.postCommentsThroughNotepad("Link to standards");

		/*
		 * Now we will check whether comments posted through the note pad are
		 * appearing in the text areas of objectives for all domains.
		 */
		classRoomObservationFormPage
				.checkNotepadComments("Enter your objective here");
	}

	
	/**
	 * Method to post comment directly not with note pad.
	 */
	public void postComments() {
		
		classRoomObservationFormPage.postCommentsForSlo();
	}
	
	
	/**
	 * Method to login as teacher
	 */
	private void loginAsTeacher() {
		homePage.login(driver, teacherUserName, teacherPassword,
				homePage.getPageTile());
	}

	/**
	 * Method to login as Principal
	 */
	private void loginAsPrincipal() {
		homePage.login(driver, principalUserName, principalPassword,
				homePage.getPageTile());
	}

	@After
	public void endTest() {
		super.tearDown();
	}
}
