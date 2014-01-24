package com.qait.tcautomation.teameds.pages;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qait.tcautomation.genric.page.BasePage;
import com.qait.tcautomation.teameds.pojo.Observation;
import com.qait.tcautomation.util.PropertyFileReaderUtil;
import com.qait.tcautomation.util.WaitUtil;

/**
 * @author amitmalik
 * 
 * This page is corresponding to Teacher compass school net observation form and SLO
 */
public class ClassRoomObservationFormPage extends BasePage {

	private final String successPageTile = "Classroom Observation Form";
	private final String successPageTile2 = "Student Learning Objective Form";
	private Observation observation;
	private String notepadComment;
	private String directComment;

	public ClassRoomObservationFormPage(WebDriver driver,
			Observation observation) {
		this.driver = driver;
		this.observation = observation;
		init();
	}

	public ClassRoomObservationFormPage(WebDriver driver) {
		this.driver = driver;
		init();
	}
	
	private void init() {
		this.notepadComment = PropertyFileReaderUtil
				.getSloProperty("notepad.comment");
		this.directComment = PropertyFileReaderUtil
				.getSloProperty("direct.comment");
	}
	
	public String getSuccessPageTile() {
		return successPageTile;
	}

	
	public String getSuccessPageTile2() {
		return successPageTile2;
	}

	public void checkForAutoDraftSave() {

	}

	
	/**
	 * Method will score all the domains and finally submit the observation
	 */
	public void submitObservation() {

		List<Integer> list = prepareDomainPositionListForObservation();

		// Score domains one by one
		for (int i : list) {

			String domainAnchorElementText = clickDomainAnchorsByPosition(i);
			
			int totalScore = scoreIndicators();

			postComments("");

			postOverallComments();

			clickSaveButton();

			/*
			 * Check whether the score is reflected under the domain <a> tag. It
			 * will be shown like score: 15/60
			 */
			String reflectedScore = driver.findElement(
					By.xpath("//div[@id='leftpanelnew']/ul/li[" + i
							+ "]/a/div[4]")).getText();
			System.out.println("Score reflected under domain <a> tag : "
					+ reflectedScore);

			assertThat("Indicator score not reflected..", reflectedScore,
					containsString(new Integer(totalScore).toString()));

			System.out
					.println("Indicatore scores correctly updated using save button for domain :"
							+ domainAnchorElementText);

		}

		clickSubmitButton();

		/*
		 * boolean wait = WaitUtil.waitForJavaScriptCondition(driver,
		 * "submitFormOnSave('performEvaluation', this )", 20);
		 * 
		 * WebElement draftsaveMessageElt =
		 * WaitUtil.waitForElementPresence(driver,
		 * By.xpath("div[@id='col-content']/div"), 20);
		 * assertNotNull("Draft not saved in 15 seconds..",
		 * draftsaveMessageElt);
		 */

	}
	
	
	/**
	 * Method to post direct comments for SLO 1. Post comment directly 2. Post
	 * overall comments 3. Check if comments are updated successfully
	 */
	public void postCommentsForSlo() {

		List<Integer> list = prepareDomainPositionListForObservation();

		// Score domains one by one
		for (int i : list) {

			String domainAnchorElementText = clickDomainAnchorsByPosition(i);

			postComments("Enter your objective here");

			postOverallComments();

			// clickSaveButton();
			clickAnchorWithLinkText("Save");

			/*
			 * Check whether status changes to 'completed' under the domain
			 * <div>. If 'completed' is shown then it is successful.
			 */
			String reflectedScore = driver
					.findElement(
							By.xpath("//div[@id='leftpanelnew']/ul/li[" + i
									+ "]/a/div")).getText();

			assertThat(
					"Assertion Error : Comments for all objectives are not posted...",
					reflectedScore, containsString("completed"));

			System.out
					.println("Comments updated using save button for domain :"
							+ domainAnchorElementText);

		}
	}
	
	
	/**
	 * This method will post the comments through note pad. It will post the
	 * comment for all the objectives in all under all domains i.e for all the
	 * objectives shown in the pop up except domains itself(i.e not post overall
	 * comments).
	 * 
	 * @param linkText
	 *            It is the text of the anchor tag to launch the standard
	 *            objective pop up. it is different for both SLO and
	 *            Observations.
	 */
	public void postCommentsThroughNotepad(String linkText) {

		clickButtonBy(By.linkText("Notepad"));

		writeCommentsThroughNotepad();

		clickAnchorWithLinkText(linkText);

		selectStandardAreaFromPopUp();

		System.out.println("Clicking save button of pop up...");
		
		clickSaveButtonOfPopUp();
		
		waitForParagraphMessageToFadeOut();
		
		System.out.println("Note pad comments posted...");
	}
	
	
	/**
	 * In this method we will check whether comments posted through the note pad
	 * are appearing in the text areas of objectives for all domains.
	 * 
	 * @param placeHolder
	 *            is the place holder for the text area used for comments. It is
	 *            different for SLO and observations
	 */
	public void checkNotepadComments(String placeHolder) {

		System.out.println("Checking for notepad comments...");
		
		List<Integer> list = prepareDomainPositionListForObservation();

		// Check domains one by one
		for (int i : list) {

			clickDomainAnchorsByPosition(i);

			// get list of comment text areas for the domain
			List<WebElement> listOfCommentTextArea = getCommentTextAreaListByPlaceHolder(placeHolder);
			assertThat("Assertion Error : No text area found for comment..",
					listOfCommentTextArea.size(), greaterThan(0));

			for (WebElement element : listOfCommentTextArea) {
				assertThat(
						"Assertion Error : Notepad comments not reflected in the text area..",
						element.getText(), containsString(notepadComment));
			}
		}

		System.out
				.println("Notepad comments are reflected in the comment text areas..");
	}
	
	
	/**
	 * Method to share the draft.
	 */
	public void shareDraft() {

	/*	String placeHolder = "Enter your objective here";
		
		List<WebElement> listOfCommentTextArea = WaitUtil
				.waitForAllElementsPresence(
						driver,
						By.xpath("//textarea[@placeholder='" + placeHolder
								+ "']"), WaitUtil.DEFAULT_WAIT_FOR_ELEMENT);
		
		listOfCommentTextArea.get(0).sendKeys("aa");*/
		System.out.println("Sharing draft....");
		
		clickButtonBy(By.id("sendDraft"));

		WebElement userEmailTextBox = getElementBy(By.id("associatedUser"));
		userEmailTextBox.sendKeys(PropertyFileReaderUtil
				.getSloProperty("principal.email"));

		WebElement emailMsgTextBox = getElementBy(By.id("sendDraftMessage"));
		emailMsgTextBox.sendKeys(PropertyFileReaderUtil
				.getSloProperty("teacher.email.msg")); 
				
		clickButtonBy(By.id("sendEvaluationDraft"));
		
		// Wait for div containing message 'Draft sent successfully' to appear
		waitForDivMessageToFadeOut();
		
		System.out.println("Draft sent successfully");	
	}
	
	
	/**
	 * This method will wait until message 'Your changes have been saved
	 * successfully' to is shown and then fade out. This message is shown in a
	 * <p>
	 * element when we post comments through note pad
	 */
	private void waitForParagraphMessageToFadeOut() {

		System.out.println("Inside waitForParagraphMessageToFadeOut()..");
		
		WebElement e = WaitUtil
				.waitForElementVisiblity(
						driver,
						By.xpath("//p[contains(@class, 'alert') and contains(@class, 'alert-success')]"),
						WaitUtil.DEFAULT_WAIT_FOR_PAGE);

		while (e.isDisplayed()) {
			System.out
					.println("inside while..Waiting for message in <p> to fade out");
		}
	}
	
	
	/**
	 * THis method will wait for message to become visible and then fade out in
	 * <div>. This message appear when we post comment directly and when draft
	 * is sent
	 */
	private void waitForDivMessageToFadeOut() {
		
		System.out.println("Inside waitForDivMessageToFadeOut()..");
		
		WebElement e = WaitUtil
				.waitForElementVisiblity(
						driver,
						By.xpath("//div[contains(@class, 'alert') and contains(@class, 'alert-success')]"),
						WaitUtil.DEFAULT_WAIT_FOR_ELEMENT);

		while (e.isDisplayed()) {
			System.out
					.println("inside while..Waiting for message in <div> to fade out");
		}

	}
	
	
	/**
	 * Method to click on the save button of the pop up.
	 */
	private void clickSaveButtonOfPopUp() {

		WebElement element = WaitUtil.waitForElementPresence(driver,
				By.xpath("//div[@id='viewShareData']/div[3]/a[1]"), 60);

		element.click();
	}
		
	/**
	 * Method to select Standard areas from the pop up. This method will check
	 * all the check boxes except Domain check box(at the top of each section
	 * e.g 'Learning Environment', 'Instruction')
	 */
	private void selectStandardAreaFromPopUp() {
		
		List<WebElement> popUpTable = WaitUtil
				.waitForAllElementsPresence(
						driver,
						By.xpath("//div[@id='viewShareData']/div[2]/div/div/table/tbody/tr/td[2]/input"),
						WaitUtil.DEFAULT_WAIT_FOR_ELEMENT);
		assertNotNull(
				"Assertion Error: table of objective in pop up not found...",
				popUpTable);
		assertThat("Assertion Error : No element found in pop table..",
				popUpTable.size(), greaterThan(0));

		for (WebElement element : popUpTable) {
			element.click();
		}
	}
	
	

	/**
	 * Method to write comments in Text area of note pad.
	 */
	private void writeCommentsThroughNotepad() {

		WebElement commentTextArea = getElementBy(By.id("commenttext"));

		commentTextArea.sendKeys(notepadComment);
	}


	/**
	 * Method to click submit button
	 */
	private void clickSubmitButton() {
		WebElement e = driver.findElement(By.linkText("Submit Observation"));
		assertNotNull("Button to submit observation not found: ", e);
		e.click();

		WebElement e1 = driver.findElement(By.id("sendEvaluation"));
		assertNotNull("Button to submit observation not found: ", e1);
		e1.click();
	}

	/**
	 * Method to click save button
	 */
	private void clickSaveButton() {

		WebElement savebutton = driver.findElement(By
				.xpath("(//a[contains(text(),'Save')])"));

		assertNotNull("Save button not found..", savebutton);

		savebutton.click();
	}

	/**
	 * Method to post overall comments
	 */
	public void postOverallComments() {

		WebElement element = getElementBy(By.id("overallComments"));

		element.sendKeys("Overall Comments for testing...");

		System.out.println("Overall Comments are posted...");
	}


	/**
	 * Method to post comment for each objective
	 * 
	 * @param placeHolder
	 *            place holder for text area where comments are to be posted
	 */
	public void postComments(String placeHolder) {

		List<WebElement> listOfCommentTextArea = getCommentTextAreaListByPlaceHolder(placeHolder);

		assertThat("Assertion Error : No text area found for comment..",
				listOfCommentTextArea.size(), greaterThan(0));

		for (WebElement element : listOfCommentTextArea) {
			element.sendKeys(directComment);
		}

		System.out.println("Comments are posted...");
	}
	
	
	/**
	 * Method will return the list of text areas used for comments with place
	 * holder as @param placeHolder
	 * 
	 * @param placeHolder
	 *            place holder for the text area
	 * @return List<WebElement>
	 */
	private List<WebElement> getCommentTextAreaListByPlaceHolder(
			String placeHolder) {

		List<WebElement> listOfCommentTextArea = WaitUtil
				.waitForAllElementsPresence(
						driver,
						By.xpath("//textarea[@placeholder='" + placeHolder
								+ "']"), WaitUtil.DEFAULT_WAIT_FOR_ELEMENT);

		return listOfCommentTextArea;
	}
	
	/**
	 * This method will click on the second indicator for each objective
	 * 
	 * @return total score
	 */
	public int scoreIndicators() {

		// Click on the second indicator
		List<WebElement> listOfIndicators = driver
				.findElements(By
						.xpath("//table[@class='scoringLevelItems']/tbody/tr/td[2]/div[2]"));
		assertNotNull("List of indicators is null..", listOfIndicators);

		Integer totalScore = 0;

		for (WebElement element : listOfIndicators) {
			element.click();
			String score = element.getText();
			assertNotNull("Score for indicator is null..", score);
			assertNotEquals("Score for indicator is empty..", score, "");
			totalScore += Integer.valueOf(score);
		}

		System.out.println("Indicators Score is :" + totalScore);

		return totalScore;
	}

	
	/**
	 * Method will click on the domain anchor link
	 * 
	 * @param pos
	 *            position of the <a> tag of domain in
	 *            <ul>
	 * @return String containing the name of domain
	 */
	private String clickDomainAnchorsByPosition(int pos) {

		System.out.println("Inside clickDomainAnchorsByPosition( )...");
		
		WebElement domainAnchorElement = driver.findElement(By
				.xpath("//div[@id='leftpanelnew']/ul/li[" + pos + "]/a"));

		domainAnchorElement.click();

		String domainAnchorElementText = driver.findElement(
				By.xpath("//div[@id='leftpanelnew']/ul/li[" + pos + "]/a/p"))
				.getText();
		System.out.println("Domain clicked is : " + domainAnchorElementText);

		return domainAnchorElementText;
	}
	
	/**
	 * This method returns a list of position for domain anchor tags in the <li>
	 * elements
	 * 
	 * @return list
	 */
	public List<Integer> prepareDomainPositionListForObservation() {

		List<WebElement> liElementList = getElementListBy(By
				.xpath("//div[@id='leftpanelnew']/ul/li"));

		System.out.println("Total <li> elements are :" + liElementList.size());

		int liElementCounter = 0;
		boolean isDomainNavHeaderReached = false;
		List<Integer> domainPositions = new LinkedList<Integer>();

		for (WebElement element : liElementList) {

			liElementCounter++;

			String listElementText = element.getText();
			System.out.println("<li> text :" + listElementText);
			System.out.println();

			/*
			 * Since domains are below the 'Domain' heading skip all <li> until
			 * it reaches
			 */
			if (!isDomainNavHeaderReached && listElementText != null
					&& !listElementText.equals("Domains")) {
				continue;
			}

			isDomainNavHeaderReached = true;

			// Skip <li> for Domain heading and divider
			String className = element.getAttribute("class").trim();
			if (className != null
					&& (className.equals("divider") || className
							.equals("nav-header"))) {
				continue;
			}

			WebElement domainAnchorElement = driver.findElement(By
					.xpath("//div[@id='leftpanelnew']/ul/li["
							+ liElementCounter + "]/a"));
			assertNotNull("Domain element not found..", domainAnchorElement);

			domainPositions.add(liElementCounter);

		}

		return domainPositions;
	}
	
	
	public void checkForIndicatorScoresWithDraft() {

		List<WebElement> listOfIndicators = driver
				.findElements(By
						.xpath("//table[@class='scoringLevelItems']/tbody/tr/td[2]/div[2]"));
		assertNotNull("List of indicators is null..", listOfIndicators);

		Integer totalScore = 0;
		for (WebElement element : listOfIndicators) {
			element.click();
			WebElement draftsaveMessageElt = WaitUtil.waitForElementPresence(
					driver, By.id("draft-save-message"), 15);
			assertNotNull("Draft not saved in 15 seconds..",
					draftsaveMessageElt);
			String score = element.getText();
			assertNotNull("Score for indicator is null..", score);
			assertNotEquals("Score for indicator is empty..", score, "");
			totalScore += Integer.valueOf(score);
		}

		System.out.println(totalScore);

		/*
		 * WebElement domainAnchorElement =
		 * driver.findElement(By.xpath("//div[@id='leftpanelnew']/ul/li[7]/a/div[4]"
		 * )); assertNotNull("Domain element not found..", domainAnchorElement);
		 */

		String reflectedScore = driver.findElement(
				By.xpath("//div[@id='leftpanelnew']/ul/li[7]/a/div[4]"))
				.getText();
		System.out.println("reflectedScore :" + reflectedScore);

		assertThat("Indicator score not reflected..", reflectedScore,
				containsString(totalScore.toString()));

		System.out
				.println("Indicatore scores correctly updated using draft...");
	}

	

	public Observation getObservation() {
		return observation;
	}

	public void setObservation(Observation observation) {
		this.observation = observation;
	}
}
