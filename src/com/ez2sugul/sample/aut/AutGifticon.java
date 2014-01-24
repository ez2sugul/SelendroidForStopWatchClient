package com.ez2sugul.sample.aut;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;

import javax.management.DescriptorKey;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.android.ddmlib.Log;
import com.android.uiautomator.core.UiObject;
import com.ez2sugul.sample.client.SWShutdown;
import com.ez2sugul.sample.util.Util;
import com.thoughtworks.selenium.SeleniumException;

import io.selendroid.SelendroidCapabilities;
import io.selendroid.SelendroidDriver;

public class AutGifticon extends AbstractAut {

	private Process child;
	
	public AutGifticon() {
		super("/Users/skplanet/Documents/Projects/robotium", "com.skmnc.gifticon", "com.skmnc.gifticon-1.apk", "3.0.6", "com.skmnc.gifticon.activity.MainActivity", 5555);
	}

	@Override
	public boolean loadingDone() {
		driver.switchTo().window(AutType.Hybrid.toString());
		String idBrand = "gnb_01";
		WebElement element = driver.findElement(By.id(idBrand));

		if (element != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date now = new Date();
			System.out.println(this.apkName + " elapsed milliseconds : " + Util.timeDiff(this.startTime, now));
			element.click();
			return true;
		}
		
		return false;
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
		String[] commands = {"java", 
				"-jar",
				"autRunner.jar", 
				getPath(), 
				getPackageName(), 
				getApkName(), 
				getAppVersion(),
				getMainActivity(),
				String.valueOf(getPort())}; 
		String command = StringUtils.join(commands, " ");
				
		
		System.out.println(command);
		
		try {
			child = Util.execCommand(command);
			Runtime.getRuntime().addShutdownHook(new SWShutdown(child));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		InputStream serverIn = child.getErrorStream();
		
		BufferedReader reader = null;
		reader = new BufferedReader(new InputStreamReader(serverIn));
		String line = null;
		
		try {
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		synchronized(lock) {
			lock.notify();
		}
		
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

	@Override
	public void notice() {
		// TODO Auto-generated method stub
		
	}
}
