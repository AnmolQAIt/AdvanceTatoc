package TEST;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class advance_tatoc_test
{
WebDriver driver;
Connect c;
RestPage page;
FileHandle file;

@BeforeClass
public void invokePage()
{
driver=new ChromeDriver();
driver.get("http://10.0.1.86/tatoc/advanced/hover/menu");
c=new Connect(driver);
}

@Test
public void firstPage()
{
boolean status=c.Gotopage();
Assert.assertTrue(status);
System.out.println("succesfully going to next page");
}

@Test
public void login() throws Exception
{
	String name=driver.findElement(By.cssSelector("[id=\"symboldisplay\"]")).getText();
	boolean status=c.ConnectingJDBC(name);
	Assert.assertTrue(status);
	System.out.println("Successfull login");
	c.playvideo();
}

@Test
public void videoplay()
{
	page=c.playvideo();
}

@Test
public void Step1_RestfulPAgeTest() throws IOException, InterruptedException {
	file=page.GetAndPostToken();
}

@Test(dependsOnMethods= {"Step1_RestfulPAgeTest"})
public void Step2_FileHandleTest() throws InterruptedException {
	file.DownloadFile();
	
}
}
