package TEST;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.w3c.dom.Document;





public class Connect
{ 
	boolean status;
	WebDriver driver;
	Connection con;
	ResultSet rs,pswd;
	Statement st;
	String id,usr,paski;
	ResultSetMetaData rsmd;
	
	public Connect(WebDriver driver)
	{
	this.driver=driver;	
	}
	
	public boolean Gotopage()
	{   
//		JavascriptExecutor js=(JavascriptExecutor)driver;
//		js.executeScript("",null);
		WebElement web_Element_To_Be_Hovered = driver.findElement(By.cssSelector("span[class='menutitle']"));
		Actions builder = new Actions(driver);
		builder.moveToElement(web_Element_To_Be_Hovered).build().perform();
		driver.findElement(By.cssSelector("div[class='menutop m2']  span[onclick='gonext();']")).click();
		if(driver.getCurrentUrl().equals("http://10.0.1.86/tatoc/advanced/query/gate"))
		return true;
		else
		return false;
		
	}
	
	public boolean ConnectingJDBC(String name) throws Exception
	{
	String dburl="jdbc:mysql://10.0.1.86:3306/tatoc";
	String username="tatocuser";
	String password="tatoc01";
	
	//-------------------------------------JDBC STEPS-----------------------------------------------//
	Class.forName("com.mysql.jdbc.Driver");	
	Connection con=DriverManager.getConnection(dburl,username,password);
	st=con.createStatement();
	System.out.println("the symbol is="+ name);
	String query = "select id from identity where symbol= '"+name+"'"; 
	rs=st.executeQuery(query);
//	rsmd = rs.getMetaData();
//	System.out.println(rsmd.getColumnCount()); 
//	System.out.println(rsmd.getColumnTypeName(1)+" "+rsmd.getColumnName(1));
//	System.out.println(rsmd.getColumnTypeName(2)+" "+rsmd.getColumnName(2));
	
	while(rs.next())
	id=rs.getString(1);
	
	pswd=st.executeQuery("select name,passkey from credentials where id= '"+id+"'");
	while(pswd.next())
	{
		 usr=pswd.getString(1);
		 paski=pswd.getString(2);
		System.out.println("username is="+usr + " " +"and the passkey is="+paski);
	}
	
	driver.findElement(By.cssSelector("input[id='name']")).sendKeys(usr);
	driver.findElement(By.cssSelector("input[id='passkey']")).sendKeys(paski);
	driver.findElement(By.xpath("//input[@id='submit']")).click();
	if(driver.getCurrentUrl().equals("http://10.0.1.86/tatoc/advanced/query/gate"))
		status=false;
	else
		status=true;
	return status;
	}
	
	public RestPage playvideo()
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.played=true");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.linkText("Proceed")).click();
		//JavascriptExecutor js1=(JavascriptExecutor)driver;
		return new RestPage(js);
	}
	
	}
