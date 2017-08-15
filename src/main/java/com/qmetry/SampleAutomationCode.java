
package com.qmetry;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SampleAutomationCode {

	public static ChromeDriver browser;
	public static String chromeDriverPath = "/Users/dhara/Downloads/chromedriver";

	/**
	 * Navigate to qmetry web site.
	 * 
	 * @return the title of the web site we just navigated to.
	 * @throws InterruptedException
	 *             if interrupted during sleep.
	 */
	public static String navigateToGoogle() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		browser = new ChromeDriver();
		browser.navigate().to("http://www.qmetry.com");
		TimeUnit.SECONDS.sleep(2);
		return browser.getTitle();

	}

	/**
	 * Enter text in the search box and then submit form.
	 * @throws InterruptedException
	 *             if interrupted during sleep.
	 */
	public static void queryText(String text) throws InterruptedException {

		WebElement searchBox = browser.findElement(By.id("s"));
		searchBox.sendKeys(text); // Enter text into the box.
		searchBox.submit(); // Submit the query -- hitting the return key.
		TimeUnit.SECONDS.sleep(2);

	}


}
