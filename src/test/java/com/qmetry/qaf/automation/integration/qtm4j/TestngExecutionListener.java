/**
 * This is the Listener/configuration class for users of QMetry for JIRA to upload results.
 * User can insert his API parameters in this class as variable values and run the 
 * project as maven test.
 * 
 * NOTE: You can change the automation code according to your use case in SampleAutomationCode.java
 * and respective tests in SampleAutomationCodeTest.java. 
 */

package com.qmetry.qaf.automation.integration.qtm4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.util.Properties;
import org.apache.http.auth.InvalidCredentialsException;
import org.testng.IExecutionListener;
import com.qmetry.UploadToCloud;
import com.qmetry.UploadToServer;

public class TestngExecutionListener implements IExecutionListener {


	private static final String API_TYPE = "integration.param.qtm4j.installation.type";
	private static final String INTEGRATION_EANBLED = "integration.param.qtm4j.enabled";
	public static final String KEEP_ZIP_DEBUG = "integration.param.qtm4j.debug";
	public static final String API_KEY = "integration.param.qtm4j.apikey";

	private static final String API_ENDPOINT = "integration.param.qtm4j.baseurl";
	private static final String API_PASSWORD = "integration.param.qtm4j.password";
	private static final String API_TEST_RUN_NAME = "integration.param.qtm4j.testrunname";
	private static final String API_LABELS = "integration.param.qtm4j.labels";
	private static final String API_PLATFORM = "integration.param.qtm4j.platform";
	private String FILE_URL=new File("test-output/testng-results.xml").getAbsolutePath();
	private static final String API_COMPONENTS = "integration.param.qtm4j.components";
	private static final String API_VERSION = "integration.param.qtm4j.versions";
	private static final String API_USERNAME = "integration.param.qtm4j.username";
	private static final String API_SPRINT = "integration.param.qtm4j.sprint";
	private static final String API_COMMENT = "integration.param.qtm4j.comment";

	Properties p;

	/**
	 * @return required properties is set or not to enable integration with
	 *         qtm4j
	 */

	public boolean isEnabled() {
		// get configuration values from pom
		return p.getProperty(INTEGRATION_EANBLED).equalsIgnoreCase("true");
	}

	public void onExecutionFinish() {
		
		// If integration not enabled then return
		if (!isEnabled()) {
			System.out.println("QTM4J integration is disabled !!!");
			return;
		}

		if (p.getProperty(API_TYPE).toUpperCase().contains("S")) {
			//code for server API
			System.out.println("Jira Hosting type : On Premise");
				try {
					UploadToServer.uploadToTheServer(p.getProperty(API_KEY), p.getProperty(API_ENDPOINT), p.getProperty(API_PASSWORD), p.getProperty(API_TEST_RUN_NAME), p.getProperty(API_LABELS),
							p.getProperty(API_SPRINT),  p.getProperty(API_VERSION), p.getProperty(API_COMPONENTS), p.getProperty(API_USERNAME), FILE_URL, p.getProperty(API_PLATFORM), p.getProperty(API_COMMENT));
				} catch (InvalidCredentialsException e) {
					
					e.printStackTrace();
				} catch (ProtocolException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			
		} else {
			// code for connect API
			System.out.println("Jira Hosting type : Cloud");	
			try {
				UploadToCloud.uploadToTheCloud(p.getProperty(API_KEY), p.getProperty(API_ENDPOINT),
						FILE_URL,
						p.getProperty(API_TEST_RUN_NAME), p.getProperty(API_LABELS), p.getProperty(API_SPRINT), p.getProperty(API_VERSION),
						p.getProperty(API_COMPONENTS), p.getProperty(API_PLATFORM), p.getProperty(API_COMMENT));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void onExecutionStart() {
		try {
			System.out.println("Test Run Started");
			InputStream is = new FileInputStream("qtm4j.properties");
			p = new Properties();
			p.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}