package TEST;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class RestPage 
{
JavascriptExecutor js;

public RestPage(JavascriptExecutor js)
{
	this.js=js;
}

public String getSessionId()
{
	String SessionId= (String)js.executeScript("return document.getElementById('session_id').textContent");
    SessionId = SessionId.substring(SessionId.indexOf(":")+2);
	return SessionId;
}

public FileHandle GetAndPostToken() throws IOException, InterruptedException
{
	String token = getToken();
	 String id=getSessionId();
     PostToken(token,id);
    
     js.executeScript("document.querySelector('.page>a').click();");
     Thread.sleep(500);
		Assert.assertEquals(js.executeScript("return document.querySelector(\".page>h1\").textContent"), "File Handle");
	    return new FileHandle(js);
}

private void PostToken(String token, String id) throws IOException
{
	URL posturl = new URL("http://10.0.1.86/tatoc/advanced/rest/service/register");
	HttpURLConnection postconn = (HttpURLConnection) posturl.openConnection();
	postconn.setDoOutput(true);
	postconn.setRequestMethod("POST");

	postconn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

	String input = "id="+id+"& signature="+token+"&allow_access=1";

	DataOutputStream wr = new DataOutputStream(postconn.getOutputStream());
	wr.writeBytes(input);
	wr.flush();
	wr.close();

	int responseCode = postconn.getResponseCode();
    postconn.disconnect();
	
}

private String getToken() throws IOException
{
	String SessionId= getSessionId();
	URL url = new URL("http://10.0.1.86/tatoc/advanced/rest/service/token/"+SessionId);
	HttpURLConnection con = (HttpURLConnection) url.openConnection();
	con.setRequestMethod("GET");
	con.setRequestProperty("Accept","application/json");
	InputStreamReader reader = new InputStreamReader(con.getInputStream());
	BufferedReader br = new BufferedReader(reader);
	String output,token="";
	while((output=br.readLine())!=null) {
		token = token+output;
	}
	
	token = token.substring(token.indexOf(":")+2, token.indexOf(",")-1);
	return token;
}
}
