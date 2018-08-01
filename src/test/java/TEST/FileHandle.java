package TEST;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class FileHandle {
	WebDriver driver;
	JavascriptExecutor js;
	
	public FileHandle(JavascriptExecutor js) {
		this.js=js;
	}
	
	public File getTheNewestFile() {
	    File theNewestFile = null;
	    File dir = new File("/home/anmolaggarwal/Downloads/");
	    FileFilter fileFilter = new WildcardFileFilter("*.dat");
	    File[] files = dir.listFiles(fileFilter);

	    if (files.length > 0) {
	        /** The newest file comes first **/
	        Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
	        theNewestFile = files[0];
	    }

	    return theNewestFile;
	}

	public void DownloadFile() throws InterruptedException {
	     js.executeScript("document.querySelector('.page>a').click()");
	     Thread.sleep(2000);
	   //  File file = new File("/home/qainfotech/Downloads/file_handle_test.dat");
           File file=getTheNewestFile();
			FileInputStream fileInput = null;
			try {
				fileInput = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			Properties prop = new Properties();

						try {
				prop.load(fileInput);
			} catch (IOException e) {
				e.printStackTrace();
			}

			js.executeScript("document.getElementById('signature').value='"+prop.getProperty("Signature")+"'");
			js.executeScript("document.querySelector('.submit').click()");
			Assert.assertEquals(js.executeScript("return document.querySelector(\".page>h1\").textContent"), "End");


	}
	
	

}
