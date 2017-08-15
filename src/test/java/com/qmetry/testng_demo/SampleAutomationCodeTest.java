/**
 * Vaibhavsinh Vaghela
 * July 17th, 2017
 * --
 * TestNG class designed to test the functionality of all
 * methods in the Selenium SampleAutomationCode.java program.
 */

package com.qmetry.testng_demo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;
import org.testng.Reporter;
import com.qmetry.SampleAutomationCode;

public class SampleAutomationCodeTest {
    
    /**
     * Tests that Google chrome browser opens up 'qmetry.com'
     */
    @Test (testName="Navigate to QMetry Web Site.")
    public void navigateToQMetry() {
        
        try {
            String pageTitle = SampleAutomationCode.navigateToGoogle();
            assertEquals(pageTitle, "QMetry Test Management Tool for Agile Testing");
        } catch (InterruptedException e) {
            Reporter.log(e.getStackTrace().toString());
        }
        
    }
    
    /**
     * Tests that you can search for 'QMetry Automation Framework'.
     */
    @Test (dependsOnMethods = {"navigateToQMetry"})
    public void queryForProduct() {
        
        try {
            SampleAutomationCode.queryText("QMetry Automation Framework");
        } catch (InterruptedException e) {
            Reporter.log(e.getStackTrace().toString());
        } finally {
        	SampleAutomationCode.browser.close();
        }
        
    }
    
}
