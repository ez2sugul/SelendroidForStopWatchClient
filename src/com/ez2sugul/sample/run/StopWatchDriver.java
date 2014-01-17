package com.ez2sugul.sample.run;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;

import io.selendroid.SelendroidDriver;

public class StopWatchDriver extends SelendroidDriver {
	private long lastDiff = 0l;

	public StopWatchDriver(String url, Capabilities caps) throws Exception {
		super(url, caps);
	}
	
	
	public WebElement findElement(By by) {
		lastDiff = 0l;
		Date start = new Date();
		WebElement element = super.findElement(by);
		Date finish = new Date();
		this.lastDiff = finish.getTime() - start.getTime();
		
		return element;
	}
	
	public long getLastFindTime() {
		return lastDiff;
	}
}
