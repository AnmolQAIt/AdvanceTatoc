package TEST;

import java.io.IOException;
import java.net.MalformedURLException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestFromRestfulPage {
	
	WebDriver driver;
	JavascriptExecutor js;
	RestPage restful;
	FileHandle fileHAndle;
	
	@BeforeClass
	public void LaunchBrowser() {
		System.setProperty("webdriver.chrome.driver","//home//qainfotech//Downloads//chromedriver");
		driver = new ChromeDriver();
		js=(JavascriptExecutor)driver;
		driver.get("http://10.0.1.86/tatoc/advanced/rest");
		restful = new RestPage(js);
	}
	
	@AfterClass
	public void closeWindow() {
		//driver.close();
	}
	
	@Test
	public void Step1_RestfulPAgeTest() throws IOException, InterruptedException {
		fileHAndle=restful.GetAndPostToken();
	}
	
	@Test(dependsOnMethods= {"Step1_RestfulPAgeTest"})
	public void Step2_FileHandleTest() throws InterruptedException {
		fileHAndle.DownloadFile();
		
	}
	
}
