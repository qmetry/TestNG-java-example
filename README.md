# TestNG-java-example

This is sample Test NG + selenium project in Java. It shows how to upload test result file on JIRA instance using [QMetry for JIRA - Test Management](https://marketplace.atlassian.com/plugins/com.infostretch.QmetryTestManager/cloud/overview).  

### Install chromedriver  

You need to install [chromedriver](https://sites.google.com/a/chromium.org/chromedriver/) to run test on Google Chrome. Then set chromedriver path in `src/main/java/com/qmetry/SampleAutomationCode.java`.

### Run test

First you need to provide few details in properties file. `qtm4j.properties`. 

1. API Key - You can get this value by logging into your JIRA instance. Then click on QMetry Menu -> Automation API page. 
2. Base URL - QMetry Automation API URL. This information is also available in Automation API page. 
3. Username/password - This information is required for server/On premise version. (For cloud, you can skip this)
4. JIRA Hosting type - Put `C` for Cloud and `S` for on premise/ Server. 

After providing these details, you are ready to start test.

```
mvn test
```

It will generate `testng-result.xml` test result file. 

Addionally, right after test completion, test result file will be uploaded on your JIRA instance. 
