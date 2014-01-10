package com.ez2sugul.sample.run;

import java.util.Date;

import org.openqa.selenium.WebDriver;

import io.selendroid.SelendroidCapabilities;
import io.selendroid.SelendroidDriver;

public abstract class AbstractAut implements Runnable {
	protected SelendroidCapabilities capa;
	protected String aut = "";
	protected WebDriver driver;
	protected Date startTime;

	public abstract boolean loadingDone();
	public abstract void runApp();
	public abstract void notice();
	public abstract void setStartTime(Date date);
}
