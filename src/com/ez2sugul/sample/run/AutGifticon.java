package com.ez2sugul.sample.run;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;

import javax.management.DescriptorKey;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.android.ddmlib.Log;
import com.android.uiautomator.core.UiObject;
import com.ez2sugul.sample.util.Util;
import com.thoughtworks.selenium.SeleniumException;

import io.selendroid.SelendroidCapabilities;
import io.selendroid.SelendroidDriver;

public class AutGifticon extends AbstractAut {

	private DateFormat now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private boolean isReady = false;
	private Lock lock;
	
	public AutGifticon() {
		super("com.skmnc.gifticon", "com.skmnc.gifticon-1_debug.apk", "3.0.4");
	}

	@Override
	public boolean loadingDone() {
		driver.switchTo().window(AutType.Hybrid.toString());
		String idBrand = "gnb_01";
		WebElement element = driver.findElement(By.id(idBrand));

		if (element != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date now = new Date();
			System.out.println("elapsed milliseconds : " + timeDiff(this.startTime, now));
			element.click();
			return true;
		}
		
		return false;
	}
	
	private long timeDiff(Date old, Date now) {
		long millisecond = now.getTime() - old.getTime();
		return millisecond;
	}
	
	@Deprecated
	public void notice() {
		this.isReady = true;
	}



	public void run2() {

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			runApp();
		} catch (SeleniumException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		loadingDone();
		terminateApp();
	}
	
	public void run() {

		try {
			runApp();
		} catch (SeleniumException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		loadingDone();
		terminateApp();
		
		synchronized(lock) {
			lock.notify();
		}
	}

	@Override
	public boolean terminateApp() {
		if (driver != null) {
			driver.quit();
			
			driver = null;
			
		}
		
		return true;
	}

	@Deprecated
	public boolean initializeApp() {
		try {
			Util.execCommand("/Users/skplanet/adt-bundle-mac-x86_64-20131030/sdk/platform-tools/adb kill-server");
			Util.execCommand("/Users/skplanet/adt-bundle-mac-x86_64-20131030/sdk/platform-tools/adb start-server");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
