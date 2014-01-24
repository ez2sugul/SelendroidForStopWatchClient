package com.ez2sugul.sample.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.locks.Lock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import android.R.bool;

import com.ez2sugul.sample.aut.AbstractAut;
import com.ez2sugul.sample.util.Util;

public class SelendroidServer implements Runnable {
	private String selendroidPath = "/Users/skplanet/Documents/Projects";
	private String selendroidBinary = "selendroid-standalone-0.7.0-with-dependencies.jar";
	private InputStream serverIn;
	private InputStream serverErr;
	private Process p;
	private Process child = null;
	private OutputStream serverOut;
	private AbstractAut aut;
	private boolean isServerOn;
	private Pattern pattern;
	private Matcher matcher;
	private Lock lock;
	private boolean isRunning;
	private boolean installedApp;
	
	public SelendroidServer() {
		serverIn = null;
		serverErr= null;
		this.isServerOn = false;
		isRunning = true;
	}
	
	public void setEnv() {
		String androidHome = "export ANDROID_HOME=\"/Users/skplanet/adt-bundle-mac-x86_64-20131030/sdk\"";
		String androidRoot = "export ANDROID_SDK_ROOT=\"/Users/skplanet/adt-bundle-mac-x86_64-20131030/sdk\"";
		try {
			Util.execCommand(androidHome);
			Util.execCommand(androidRoot);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Deprecated
	private Process execCommand(String command) throws IOException  {
		System.out.println(command);
		p = Runtime.getRuntime().exec(command);
		
		return p;
	}
	
	public boolean terminateServer() {
		child.destroy();
		return true;
	}
	
	public void runSelendroidServer() {
		String commandsForInstalled[] = {"java",
				"-jar", 
				selendroidPath + File.separator + selendroidBinary,
				"-verbose", 
				"-port", 
				String.valueOf(aut.getPort()),  
				"-installedApp",
				aut.getPackageName() + "/" + aut.getMainActivity() + ":" + aut.getAppVersion()};
		
		String commands[] = {"java",
				"-jar", 
				selendroidPath + File.separator + selendroidBinary,  
				"-port", 
				String.valueOf(aut.getPort()),  
				"-app", 
				aut.getPath() + File.separator + aut.getApkName()};
		
		String command = "";
		
		if (this.installedApp) {
			command = StringUtils.join(commandsForInstalled, " ");
		} else {
			command = StringUtils.join(commands, " ");	
		}
		
		System.out.println(command);
		
		try {
			child = Util.execCommand(command);
			Runtime.getRuntime().addShutdownHook(new SWShutdown(child));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		serverIn = child.getInputStream();
		serverErr = child.getErrorStream();
		
		BufferedReader reader = null;
		reader = new BufferedReader(new InputStreamReader(serverErr));
		String line = null;
		try {
			while ((line = reader.readLine()) != null && isRunning) {
//				System.out.println(line);
				String autStart = this.stareStartingLog(line);
				if (autStart != null) {
					try {
						this.aut.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(autStart));
						System.out.println(aut.getPackageName() + " starting at " + autStart);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (this.watchServerOn(line)) {
					synchronized(lock) {
						this.isServerOn  = true;
						lock.notify();
					}
				}
				if (this.watchServerShutdown(line)) {
					synchronized(lock) {
						this.isServerOn = false;
						lock.notify();
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		synchronized(lock) {
			lock.notify();
		}
	}
	
	public void assignAut(AbstractAut aut) {
		this.aut = aut;
	}

	@Override
	public void run() {
		this.runSelendroidServer();
	}
	
	private String stareStartingLog(String log) {
		pattern = Pattern.compile(".*(shell am instrument).*");
		matcher = pattern.matcher(log);

		if (matcher.matches()) {
			SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date date = new Date();
//			System.out.println(now.format(date) + " " + matcher.group(1));
			return now.format(date);
		}

		return null;
	}
	
	private boolean watchServerOn(String line) {
		pattern = Pattern.compile(".*server has been started.*");
		matcher = pattern.matcher(line);
		
		if (matcher.matches()) {
			return true;
		}
		
		return false;
	}
	
	private boolean watchServerShutdown(String line) {
		pattern = Pattern.compile(".*Shutting down Selendroid standalone");
		matcher = pattern.matcher(line);
		
		if (matcher.matches()) {
			return true;
		}
		
		return false;
	}
	
	public void setLock(Lock lock) {
		this.lock = lock;
	}

	public void setInstalledApp(boolean installed) {
		this.installedApp = true;
	}
}
