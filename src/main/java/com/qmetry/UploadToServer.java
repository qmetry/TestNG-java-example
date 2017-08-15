/**
 * This class encapsulates the logic of deploying the cucumber result file to QMetry for 
 * JIRA-Server.
 * 
 * NO CHANGE NEEDED IN THIS CLASS
 */

package com.qmetry;

import java.io.File;
import java.io.IOException;
import java.net.ProtocolException;
import org.apache.http.HttpEntity;
import org.apache.http.auth.InvalidCredentialsException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import javax.xml.bind.DatatypeConverter;

public class UploadToServer {
	
	public static void uploadToTheServer(String apikeyserver, String jiraurlserver, String password,
			String testrunnameserver, String labelsserver, String sprintserver, 
			String versionserver, String componentserver, String username, 
			String fileserver, String platformserver,
			String commentserver) throws InvalidCredentialsException, ProtocolException, IOException{
					String selectionserver="testng/xml";
					CloseableHttpClient httpClient= HttpClients.createDefault();
					String toEncode=username+":"+password;
//					byte[] encodedBytes = Base64.getEncoder().encode(toEncode.getBytes(StandardCharsets.UTF_8));
//			    	String encodedString= new String(encodedBytes,StandardCharsets.UTF_8);
					byte[] message = toEncode.getBytes("UTF-8");
					String encoded = DatatypeConverter.printBase64Binary(message);
					//byte[] decoded = DatatypeConverter.parseBase64Binary(encoded);
					
			    	String basicAuth = "Basic " + encoded;
			    	
				    	HttpPost uploadFile = new HttpPost(jiraurlserver);
				    	uploadFile.addHeader("Authorization", basicAuth);
				    	
				    	MultipartEntityBuilder builder = MultipartEntityBuilder.create();
				    	builder.addTextBody("apiKey", apikeyserver, ContentType.TEXT_PLAIN);
				    	builder.addTextBody("testRunName", testrunnameserver, ContentType.TEXT_PLAIN);
				    	builder.addTextBody("format", selectionserver, ContentType.TEXT_PLAIN);
				    	
				    	if(platformserver != null && !platformserver.isEmpty())
				    		builder.addTextBody("platform", platformserver, ContentType.TEXT_PLAIN);
				    	if(labelsserver != null && !labelsserver.isEmpty())
				    		builder.addTextBody("labels", labelsserver, ContentType.TEXT_PLAIN);
				    	if(versionserver != null && !versionserver.isEmpty())
				    		builder.addTextBody("versions", versionserver, ContentType.TEXT_PLAIN);
				    	if(componentserver != null && !componentserver.isEmpty())
				    		builder.addTextBody("components", componentserver, ContentType.TEXT_PLAIN);
				    	if(sprintserver != null && !sprintserver.isEmpty())
				    		builder.addTextBody("sprint", sprintserver, ContentType.TEXT_PLAIN);
				    	if(commentserver != null && !commentserver.isEmpty())
				    		builder.addTextBody("comment", commentserver, ContentType.TEXT_PLAIN);

				    	// This attaches the file to the POST:
				    	
				    		File f = new File(fileserver);		
				    		try {
				    				builder.addPart("file", new FileBody(f));
							} catch (Exception e) {
								e.printStackTrace();
							}; 

				    	HttpEntity multipart = builder.build();
				    	uploadFile.setEntity(multipart);
				    	CloseableHttpResponse response = httpClient.execute(uploadFile);
				    	//HttpEntity responseEntity = response.getEntity();
				    	httpClient.close();
				    	//Execute and get the response.
				    	System.out.println(response.toString());
				
	}
}
