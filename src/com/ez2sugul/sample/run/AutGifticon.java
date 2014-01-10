package com.ez2sugul.sample.run;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.android.ddmlib.Log;
import com.android.uiautomator.core.UiObject;

import io.selendroid.SelendroidCapabilities;
import io.selendroid.SelendroidDriver;

public class AutGifticon extends AbstractAut {

	private DateFormat now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private boolean isReady = false;
	private Watcher watcher;
	
	public AutGifticon() {
		this.aut = "com.skmnc.gifticon:3.0.4";
		this.driver = null;
	}
	
	private void printLog(String msg) {
		Date date = new Date();
		System.out.println("[" + now.format(date) + "][" + getCurrentMethodName() + "] " + msg);
		Log.d("selendroid", msg);
	}

	private String getCurrentMethodName() {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		
		return ste[3].getMethodName();
	}

	@Override
	public boolean loadingDone() {
		driver.switchTo().window("WEBVIEW");
//		System.out.println(driver.getPageSource());
		String xpathExpression = "//WebView[@id='mWebView']";
		String idBrand = "gnb_01";
		WebElement element = driver.findElement(By.id(idBrand));

		if (element != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date now = new Date();
			System.out.println("elapsed milliseconds : " + timeDiff(startTime, now));
			element.click();
			return true;
		}
		
		return false;
	}
	
	private long timeDiff(Date old, Date now) {
		long millisecond = now.getTime() - old.getTime();
		return millisecond;
	}
	
	public void notice() {
		this.isReady = true;
	}

	@Override
	public void runApp() {
		System.out.println(new Date().toString() + "runApp");
		capa = SelendroidCapabilities.device(aut);
		try {
			driver = new SelendroidDriver("http://localhost:5555/wd/hub", capa);
		} catch (Exception e) {
			e.printStackTrace();
			driver.quit();
		}
	}

	@Override
	public void run() {
//		while (!this.isReady) {
			System.out.println("isReady");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		}
		runApp();
		loadingDone();
		driver.quit();
	}

	public void registerWatcher(Watcher watcher) {
		this.watcher = watcher;
	}

	@Override
	public void setStartTime(Date date) {
		this.startTime = date;
	}
}
