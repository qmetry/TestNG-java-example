# Testng-maven-example
This is sample Testng + maven  project in Java. It shows how to upload test result file on JIRA instance using [QMetry for JIRA - Test Management](https://marketplace.atlassian.com/plugins/com.infostretch.QmetryTestManager/cloud/overview).  


### Run test

please update these details in `pom.xml` file. 

<div id="automationFramework" class="border-top m-t-10 p-t-10"><div class="m-t-sm">
    <label class="bold">Step 1: Add the following to the &lt;build&gt; -&gt; &lt;plugins&gt; block in your
				pom.xml:</label> 
    <pre class="code-block">
<code>&lt;build&gt;</code>
	<code>&lt;plugins&gt;</code>
		<code>&lt;plugin&gt;</code>
			<code>&lt;groupId&gt;org.apache.maven.plugins&lt;/groupId&gt;</code>
			<code>&lt;artifactId&gt;maven-surefire-plugin&lt;/artifactId&gt;</code>
			<code>&lt;version&gt;2.22.0&lt;/version&gt;</code>
		<code>&lt;/plugin&gt;</code>
		<code>&lt;plugin&gt;</code>
			<code>&lt;groupId&gt;com.qmetry&lt;/groupId&gt;</code>
			<code>&lt;artifactId&gt;automation&lt;/artifactId&gt;</code>
			<code>&lt;version&gt;1.0.2&lt;/version&gt;</code>
			<code>&lt;executions&gt;</code>
				<code>&lt;execution&gt;</code>
					<code>&lt;phase&gt;test&lt;/phase&gt;</code>
					<code>&lt;goals&gt;</code>
						<code>&lt;goal&gt;TestngFileUpload&lt;/goal&gt;</code>
					<code>&lt;/goals&gt;</code>
				<code>&lt;/execution&gt;</code>
			<code>&lt;/executions&gt;</code>
		<code>&lt;/plugin&gt;</code>
	<code>&lt;/plugins&gt;</code>
<code>&lt;/build&gt;</code>
	</pre>   
    
</div>


<div class="m-t-sm">
    <label class="bold">Step 2: Add the following to the &lt;pluginRepositories&gt; block in pom.xml like:</label>
    <pre class="code-block">
<code>&lt;pluginRepositories&gt;</code>
	<code>&lt;pluginRepository&gt;</code>
		<code>&lt;id&gt;qmetrytestmanager-mvn-repo&lt;/id&gt;</code>
		<code>&lt;name&gt;QMetry Test Manager Maven Repository&lt;/name&gt;</code>
		<code>&lt;url&gt;https://raw.github.com/qmetry/qtm4j-maven-uploader/mvn-repo/&lt;/url&gt;</code>
		<code>&lt;releases&gt;</code>
			<code>&lt;enabled&gt;true&lt;/enabled&gt;</code>
			<code>&lt;updatePolicy&gt;always&lt;/updatePolicy&gt;</code>
		<code>&lt;/releases&gt;</code>
	v&lt;/pluginRepository&gt;</code>
<code>&lt;/pluginRepositories&gt;</code>
	</pre>
</div>
<div class="m-t-sm">
    <label class="bold">Step 3: Add qmetry.properties file to root directory of your project</label>
</div>
<div class="m-t-sm">
	<label>Configure below properties:</label>
    <pre class="select-block code-block"><code>automation.qmetry.enabled = true</code>
<code>automation.qmetry.url = https://importresults.qmetry.com/QmetryWisdom/importresults-qtm4j</code>
<code>automation.qmetry.apikey = aed606a7609feca7c4ac801677b7f825249d7358df9b72c3a2b9a91b046efb8e</code>
<code>automation.qmetry.filepath = /target/surefire-reports/testng-results.xml</code>
<code>automation.qmetry.testrunname = Test Run</code>
<code>automation.qmetry.labels = lbl1,lbl2</code>
<code>automation.qmetry.components = com1,com2</code>
<code>automation.qmetry.version = v1,v2</code>
<code>automation.qmetry.sprint = sprint1</code>
<code>automation.qmetry.platform = chrome</code>
<code>automation.qmetry.comment = this is test run comment</code>
<code>automation.qmetry.testrunkey = </code>
<code>automation.qmetry.testassethierarchy = TestCase-TestStep</code>
<code>automation.qmetry.jirafields = </code>
<code>automation.qmetry.debug = true</code>
<code>automation.qmetry.testcaseupdatelevel=0</code>
	</pre>
</div>

<div class="m-t-sm">
    <label>if you are using on premise JIRA, then configure below properties as well:</label>
    <pre class="select-block code-block"><code>automation.qmetry.authorization=Basic YWRtaW46YWRtaW4=</code>
    	<code>OR</code>
<code>automation.qmetry.username = admin</code>
<code>automation.qmetry.password = admin</code>	
	</pre>
    <label>Once the file is configured, the automation test results will get uploaded automatically whenever the user executes the automation project (e.g. using 'mvn test').</label>
</div></div>

After providing these details, you are ready to start test.

```
mvn test
```

It will generate `surefile-reports`. 

Addionally, right after test completion, test result file will be uploaded on your JIRA instance if you have provided correct details in properties file. 
