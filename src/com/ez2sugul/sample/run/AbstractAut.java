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
	protected WebDriver driver = null;
	protected Date startTime = null;
	
	public AbstractAut(String packageName, String apkName, String appVersion) {
		this.packageName = packageName;
		this.apkName = apkName;
		this.appVersion = appVersion;
	}
	
	public abstract boolean loadingDone();
	public abstract boolean initializeApp();
	public void runApp() throws Exception, SeleniumException {
		capa = SelendroidCapabilities.device(packageName + ":" + appVersion);

		if (capa == null) {
			throw new SeleniumException("Invalid Selendroid Capabilities");
		}

		driver = new SelendroidDriver("http://localhost:5555/wd/hub", capa);

	}
	public abstract boolean terminateApp();
	public abstract void notice();
	public void setStartTime(Date date) {
		this.startTime = date;
	}
}
