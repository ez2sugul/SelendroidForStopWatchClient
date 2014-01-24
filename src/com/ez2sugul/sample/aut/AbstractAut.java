package com.ez2sugul.sample.aut;

import java.util.Date;
import java.util.Observable;
import java.util.concurrent.locks.Lock;

import org.openqa.selenium.WebDriver;

import com.ez2sugul.sample.client.StopWatchDriver;
import com.thoughtworks.selenium.SeleniumException;

import io.selendroid.SelendroidCapabilities;
import io.selendroid.SelendroidDriver;

public abstract class AbstractAut implements Runnable {
	protected SelendroidCapabilities capa;
	protected String packageName = "";
	protected String apkName = "";
	protected String appVersion = "";
	protected StopWatchDriver driver = null;
	protected Date startTime = null;
	protected String path = "";
	protected int port = 5555;
	protected Lock lock = null;
	protected String mainActivity = "";
	
	public String getMainActivity() {
		return mainActivity;
	}

	public void setLock(Lock lock) {
		this.lock = lock;
	}

	public int getPort() {
		return port;
	}

	public AbstractAut(String path, String packageName, String apkName, String appVersion, String mainActivity, int port) {
		this.path = path;
		this.packageName = packageName;
		this.apkName = apkName;
		this.appVersion = appVersion;
		this.mainActivity = mainActivity;
		this.port = port;
		this.startTime = new Date();
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
	public void runApp() throws Exception, SeleniumException {
		capa = SelendroidCapabilities.device(packageName + ":" + appVersion);

		if (capa == null) {
			throw new SeleniumException("Invalid Selendroid Capabilities");
		}
		
		System.out.println("APPINFO " + packageName + " " + appVersion);

		driver = new StopWatchDriver("http://localhost:" + port + "/wd/hub", capa);
		if (driver == null) {
			throw new SeleniumException("can't load StopWatchDriver " + capa.toString());
		}
		
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
