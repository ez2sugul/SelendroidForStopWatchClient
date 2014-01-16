package com.ez2sugul.sample.test;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.selendroid.SelendroidCapabilities;
import io.selendroid.SelendroidDriver;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Beta;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.android.library.WebDriverViewClient;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.ScreenshotException;

import com.android.ddmlib.Log;

public class selendroidTest {

	private static String aut = "com.skmnc.gifticon:3.0.4";
	private static WebDriver driver = null;
	private static SelendroidCapabilities capa = null;
	
	@BeforeClass
	public static void initialize() {
		capa = SelendroidCapabilities.device(aut);
		capa.setJavascriptEnabled(true);

		try {
			driver = new SelendroidDriver("http://localhost:5555/wd/hub",	capa);
		} catch (Exception e) {
			Throwable cause = e.getCause();
			if (cause instanceof ScreenshotException) {
//				((ScreenshotException) cause).getBase64EncodedScreenshot();
			}
			e.printStackTrace();
		}
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	public void pageLoad() {
		String script = "alert('hello')";
		
		driver.switchTo().window("WEBVIEW");
		WebDriverBackedSelenium selenium = new WebDriverBackedSelenium(driver, "http://localhost:5555/wd/hub");
		selenium.getEval("");
		((RemoteWebDriver)driver).executeScript(script);
	}
	
	@Test
	public void imageLoad() {
		driver.switchTo().window("WEBVIEW");
		String script = "return arguments[0].complete && " +
   "typeof arguments[0].naturalWidth != \"undefined\" && " +
   "arguments[0].naturalWidth > 0";
		
		String cssImg = "img[alt='카페모카(R) Hot/Ice']";
		
		WebElement image = driver.findElement(By.cssSelector(cssImg));
		
		if (image == null) {
			System.out.println("image null");
		}
		
		System.out.println(new Date().toString() + " loading done");
		
		while(!image.isDisplayed()) {
			
		}
		
		System.out.println(new Date().toString() + " diplayed");
		
//		Object loaded = ((RemoteWebDriver)driver).executeScript(script, image);
//		
//		if (loaded instanceof Boolean) {
//			assertEquals(true, (Boolean)loaded);
//		}
	}

	
	public void brandMenu() {
		printLog("start");
		
		driver.switchTo().window("WEBVIEW");
		
		printLog("swtich to webview");
		System.out.println(driver.getPageSource());
		String xpathExpression = "//WebView[@id='mWebView']";
		String idBrand = "gnb_01";
		String linkText = "dfb6f953-e8b4-45af-8779-50093ce0a2f5_1375352157441.jpg";
		WebElement element = driver.findElement(By.partialLinkText(linkText));
		if (element != null) {
			printLog("found brand");
			element.click();
			printLog("clicked");
		}
		
		
		printLog("finish");
		
	}
	
	public void findUtilBarBack() {
		String id = "com.skmnc.gifticon:id/utilbar_back";
		driver.switchTo().window("NATIVE_APP");
		driver.findElement(By.id(id));
	}
	
	private void printLog(String msg) {
		DateFormat now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date = new Date();
		System.out.println("[" + now.format(date) + "][" + getCurrentMethodName() + "] " + msg);
		Log.d("selendroid", msg);
	}

	private String getCurrentMethodName() {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		
		return ste[3].getMethodName();
	}
}
