package com.ez2sugul.sample.aut;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ez2sugul.sample.util.Util;
import com.thoughtworks.selenium.SeleniumException;

public class AutRunner extends AbstractAut {

	public AutRunner(String path, String packageName, String apkName,
			String appVersion, String mainActivity, int port) {
		super(path, packageName, apkName, appVersion, mainActivity, port);
	}

	@Override
	public void run() {

	}

	@Override
	public boolean loadingDone() {
		driver.switchTo().window(AutType.Hybrid.toString());
		String idBrand = "gnb_01";
		WebElement element = driver.findElement(By.id(idBrand));

		if (element != null) {
			Date now = new Date();
			System.err.println(this.apkName + " elapsed milliseconds : " + Util.timeDiff(this.startTime, now));
			element.click();
			return true;
		}
		
		return false;
	}


	@Override
	public void notice() {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length < 6) {
			System.err.println("required args are 6");
		}
		
		String path = args[0];
		String packageName = args[1];
		String apkName = args[2];
		String appVersion = args[3];
		String mainActivity = args[4];
		int port = Integer.parseInt(args[5]);
		System.err.println(Util.autLog("AUT_START " + packageName + " " + apkName + " " + appVersion + " " + mainActivity + " " + port));
		AbstractAut runner = new AutRunner(path, packageName, apkName, appVersion, mainActivity, port);
		try {
			runner.runApp();
		} catch (SeleniumException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		runner.loadingDone();
		runner.terminateApp();
		
		System.err.println(Util.autLog("AUT_DONE"));
	}

}
