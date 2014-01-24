package com.ez2sugul.sample.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.android.ddmlib.Log;

public class Util {
	public static Process execCommand(String command) throws IOException {
		Process p = Runtime.getRuntime().exec(command);
		return p;
	}
	
	public static void printLogcat(String msg) {
		Date date = new Date();
		Log.d("selendroid", msg);
	}
	
	public static String autLog(String msg) {
		SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date = new Date();
		return "[" + now.format(date) + "] " + msg;
	}
	
	public static String getCurrentMethodName(int n) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		
		return ste[n].getMethodName();
	}
	
	public static WebElement findEmlement(WebDriver driver, By by, Long diff) {
		Date start = new Date();
		WebElement element = driver.findElement(by);
		Date finish = new Date();
		diff = finish.getTime() - start.getTime();
		
		return element;
	}
	
	public static long timeDiff(Date old, Date now) {
		long millisecond = now.getTime() - old.getTime();
		return millisecond;
	}
}
