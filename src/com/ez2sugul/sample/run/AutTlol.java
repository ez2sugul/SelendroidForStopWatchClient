package com.ez2sugul.sample.run;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AutTlol extends AbstractAut {
	
	public AutTlol() {
		super("/Users/skplanet/Documents/Projects/robotium", "kr.co.ulike.tesports", "kr.co.ulike.tesports-1.apk", "0.0.9", 5557);
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
	public boolean initializeApp() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void notice() {
		// TODO Auto-generated method stub

	}

}
