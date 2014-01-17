package com.ez2sugul.sample.run;

import java.util.Date;
import java.util.Observable;

import org.openqa.selenium.WebDriver;

import com.thoughtworks.selenium.SeleniumException;

import io.selendroid.SelendroidCapabilities;
import io.selendroid.SelendroidDriver;

public abstract class AbstractAut{
	protected SelendroidCapabilities capa;
	protected String packageName = "";
	protected String apkName = "";
	protected String appVersion = "";
	protected StopWatchDriver driver = null;
	protected Date startTime = null;
	protected String path = "";
	protected int port = 5555;
	
	public int getPort() {
		return port;
	}

	public AbstractAut(String path, String packageName, String apkName, String appVersion, int port) {
		this.path = path;
		this.packageName = packageName;
		this.apkName = apkName;
		this.appVersion = appVersion;
		this.port = port;
	}
	
	public String getPackageName() {
		return packageName;
	}

	public String getApkName() {
		return apkName;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public String getPath() {
		return path;
	}

	public abstract boolean loadingDone();
	public abstract boolean initializeApp();
	public void runApp() throws Exception, SeleniumException {
		capa = SelendroidCapabilities.device(packageName + ":" + appVersion);

		if (capa == null) {
			throw new SeleniumException("Invalid Selendroid Capabilities");
		}
		
		System.out.println("APPINFO " + packageName + " " + appVersion);

		driver = new StopWatchDriver("http://localhost:" + port + "/wd/hub", capa);
		driver.getSessionId();
		
	}
	public boolean terminateApp() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
		
		return true;
	}
	public abstract void notice();
	public void setStartTime(Date date) {
		this.startTime = date;
	}
}
