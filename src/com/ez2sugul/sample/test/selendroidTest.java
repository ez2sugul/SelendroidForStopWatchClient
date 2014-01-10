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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.android.library.WebDriverViewClient;

import com.android.ddmlib.Log;

public class selendroidTest {

	private static String aut = "com.skmnc.gifticon:3.0.4";
	private static WebDriver driver = null;
	private static SelendroidCapabilities capa = null;
	
	@BeforeClass
	public static void initialize() {
		capa = SelendroidCapabilities.device(aut);
		try {
			driver = new SelendroidDriver("http://localhost:5555/wd/hub",	capa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void brandMenu() {
		printLog("start");
		driver.switchTo().window("WEBVIEW");
		printLog("swtich to webview");
		System.out.println(driver.getPageSource());
		String xpathExpression = "//WebView[@id='mWebView']";
		String idBrand = "gnb_01";
		WebElement element = driver.findElement(By.id(idBrand));
		if (element != null) {
			printLog("found brand");
			element.click();
			printLog("clicked");
		}
		
		
		printLog("finish");
		
	}
	
	@Test
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
