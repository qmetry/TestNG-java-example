/**
 * This class encapsulates the logic of deploying the cucumber result file to QMetry for 
 * JIRA-Cloud.
 * 
 * NO CHANGE NEEDED IN THIS CLASS
 */

package com.qmetry;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UploadToCloud {
	
	//Call to 1st URL which gets the response and passes the response to the method uploadToS3()
	public static void uploadToTheCloud(String apikey, String qtm4jurl, String file,
			String testrunname,String labels, String sprint, String versions, 
			String components, String platform, String comment) throws MalformedURLException
	, IOException, UnsupportedEncodingException, ProtocolException, Exception{
		String encoding = "UTF-8";
		String format="testng/xml";
		URL url = new URL(qtm4jurl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setDoInput(true);
		connection.setDoOutput(true);

		StringBuilder jsonBody = new StringBuilder("{");
		jsonBody.append("\"format\":" + "\""+format+"\"");
		jsonBody.append(",\"testRunName\":" + "\""+testrunname+"\"");
		jsonBody.append(",\"apiKey\":" + "\""+apikey+"\"");
		if(platform != null && !platform.isEmpty())
			jsonBody.append(",\"platform\":" + "\""+platform+"\"");
		if(labels != null && !labels.isEmpty())
			jsonBody.append(",\"labels\":" + "\""+labels+"\"");
		if(versions != null && !versions.isEmpty())
			jsonBody.append(",\"versions\":" + "\""+versions+"\"");
		if(components != null && !components.isEmpty())
			jsonBody.append(",\"components\":" + "\""+components+"\"");
		if(sprint != null && !sprint.isEmpty())
			jsonBody.append(",\"sprint\":" + "\""+sprint+"\"");		
		if(comment != null && !comment.isEmpty())
			jsonBody.append(",\"comment\":" + "\""+comment+"\"");
		jsonBody.append("}");
		
		System.out.println(jsonBody.toString());

		OutputStream os = connection.getOutputStream();
		os.write(jsonBody.toString().getBytes("UTF-8"));
		InputStream fis = connection.getInputStream();
		StringWriter response = new StringWriter();
		
		IOUtils.copy(fis, response, encoding);
		System.out.println(response.toString());
		
		//Call another method to upload to S3 bucket.
		System.out.println(uploadToS3(response.toString(),file));   	
		
	}
	
	// This method gets the response, grabs the url from response and uploads the file to that url.
	public static String uploadToS3(String response,String fileurl) throws IOException, Exception{
		// JSONParser to parse the response into JSON Object.
		 JSONParser parser = new JSONParser();
		 Object obj = parser.parse(response);
		 JSONObject jsonObject = (JSONObject) obj;
		 String urlForUpload = (String) jsonObject.get("url");
		 URL url = new URL(urlForUpload);
		 HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		 connection.setRequestMethod("PUT");
		 connection.setRequestProperty("Content-Type", "multipart/form-data");
		 connection.setDoOutput(true);
		 connection.setDoInput(true);
		 String encoding = "UTF-8";
		 String responseValue="";
		
		 try{
		 //Read the file from the path, copy the content to the url property of JSON Object.
		 FileInputStream file = new FileInputStream(fileurl);
		 try{OutputStream os = connection.getOutputStream();
		 IOUtils.copy(file, os);
		 } finally{
			 file.close();
		 	}
		 }
		 catch (IOException e) {
			 e.printStackTrace();
		}

		 InputStream fis = connection.getInputStream();
		 StringWriter writer = new StringWriter();	 
		 IOUtils.copy(fis, writer, encoding);
		 if (connection.getResponseCode() == 200) {
		 	responseValue="Publishing the result has been successful. \n Response: " + connection.getResponseMessage();
		 }else{
		 	responseValue="Error has occured while uploading the file to temporary S3 bucket.";
		 }			 
		 return responseValue;
	}
}
