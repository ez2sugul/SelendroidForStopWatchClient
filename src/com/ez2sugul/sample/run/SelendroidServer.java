package com.ez2sugul.sample.run;

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

import android.R.bool;

import com.ez2sugul.sample.util.Util;

public class SelendroidServer implements Runnable {
	private String selendroidPath = "/Users/skplanet/Documents/Projects";
	private String targetPath = "/Users/skplanet/Documents/Projects/robotium";
	private String selendroidBinary = "selendroid-standalone-0.8.0-SNAPSHOT-with-dependencies.jar";
	private String targetBinary = "com.skmnc.gifticon-1_debug.apk";
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
	
	public SelendroidServer(Lock lock) {
		serverIn = null;
		serverErr= null;
		this.isServerOn = false;
		this.lock = lock;
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
		isRunning = false;
		return true;
	}
	
	public void runSelendroidServer() {
		List commands = new ArrayList<String>();
		String command = "java -jar " + 
				selendroidPath + File.separator + 
				selendroidBinary + 
//				" -installed com.skmnc.gifticon/MainActivity:3.0.4" +
				" -app " + targetPath + File.separator + targetBinary;
		
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
}
