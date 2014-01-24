package com.ez2sugul.sample.aut;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.concurrent.locks.Lock;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ez2sugul.sample.client.SWShutdown;
import com.ez2sugul.sample.util.Util;
import com.thoughtworks.selenium.SeleniumException;

public class AutTlol extends AbstractAut {

	private Process child;
	
	public AutTlol() {
		super("/Users/skplanet/Documents/Projects/robotium", "kr.co.ulike.tesports", "kr.co.ulike.tesports-1_debug.apk", "1.0.0", "kr.co.ulike.tesports.IntroActivity", 5556);
	}

	@Override
	public boolean loadingDone() {
		driver.switchTo().window(AutType.Native.toString());
		Date now = new Date();
		String idLoading = "GameScheduleThumbLeft";
		
		WebElement schedule = driver.findElement(By.id(idLoading));
		long diff = driver.getLastFindTime();
		
		if (schedule == null) {
			return false;
		}
		
		if (schedule.isDisplayed()) {
			return false;
		}
		
		System.out.println(this.apkName + " elapsed milliseconds : " + (now.getTime() - startTime.getTime()));
		
		return true;
	}
	
	private boolean closePopup() {
		String idPopup = "IGDialogContentContainer";
		String idCheckBox = "IGDialogMsgCheckBox";
		String idConfirm = "IGDialogPositiveButton";
		String idClose = "IGDialogNegativeButton";
		String idText = "IGDialogMsg";
		String text = "T LOL을 데이터 부담없이";
		long diff = 0l;
		
		WebElement popupDialog = driver.findElement(By.id(idPopup));
		diff += driver.getLastFindTime();
		
		System.out.println(diff + " for popup");
		
		if (popupDialog == null) {
			return false;
		}
		
		WebElement checkBox = driver.findElement(By.id(idCheckBox));
		diff += driver.getLastFindTime();
		System.out.println(driver.getLastFindTime() + " for checkbox");
		WebElement confirm = driver.findElement(By.id(idConfirm));
		diff += driver.getLastFindTime();
		System.out.println(driver.getLastFindTime() + " for confirm");
		WebElement textBox = driver.findElement(By.id(idText));
		diff += driver.getLastFindTime();
		System.out.println(driver.getLastFindTime() + " for text");
		WebElement close = driver.findElement(By.id(idClose));
		System.out.println(driver.getLastFindTime() + " for close");
		diff += driver.getLastFindTime();
		
		if (checkBox == null) {
			return false;
		}
		
		if (confirm == null) {
			return false;
		}
		
		if (textBox == null) {
			return false;
		}
		
		if (textBox.getText().contains(text)) {
			checkBox.click();
			confirm.click();
		} else {
			checkBox.click();
			confirm.click();
		}
			
		System.out.println(diff);
		
		return true;
	}


	@Override
	public void notice() {
		// TODO Auto-generated method stub

	}

	@Override
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

}
