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
import java.util.Formatter;
import java.util.List;

public class SelendroidServer implements Runnable{
	private String selendroidPath = "/Users/skplanet/Documents/Projects";
	private String targetPath = "/Users/skplanet/Documents/Projects/robotium";
	private String selendroidBinary = "selendroid-standalone-0.7.0-with-dependencies.jar";
	private String targetBinary = "com.skmnc.gifticon-1_debug.apk";
	private InputStream serverIn;
	private InputStream serverErr;
	private Watcher hook;
	private Process p;
	private Process child = null;
	private OutputStream serverOut;
	private AbstractAut aut;
	private boolean isServerOn;
	
	public SelendroidServer() {
		serverIn = null;
		serverErr= null;
		this.isServerOn = false;
	}
	
	public void setEnv() {
		String androidHome = "export ANDROID_HOME=\"/Users/skplanet/adt-bundle-mac-x86_64-20131030/sdk\"";
		String androidRoot = "export ANDROID_SDK_ROOT=\"/Users/skplanet/adt-bundle-mac-x86_64-20131030/sdk\"";
		try {
			execCommand(androidHome);
			execCommand(androidRoot);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Process execCommand(String command) throws IOException  {
		System.out.println(command);
		p = Runtime.getRuntime().exec(command);
		
		return p;
	}
	
	public void runSelendroidServer() {
		List commands = new ArrayList<String>();
		String command = "java -jar " + 
				selendroidPath + File.separator + 
				selendroidBinary + 
//				" -installed com.skmnc.gifticon/MainActivity:3.0.4" +
				" -app " + targetPath + File.separator + targetBinary;
		
		try {
			child = execCommand(command);
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
			while ((line = reader.readLine()) != null) {
				String autStart = this.hook.watchStartTime(line);
				if (autStart != null) {
					try {
						this.aut.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(autStart));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (this.hook.watchServerOn(line)) {
					this.isServerOn  = true;
				}
				
				if (this.isServerOn) {
					this.aut.notice();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void registerWatcher(Watcher hook) {
		this.hook = hook;
	}
	
	public void registerAut(AbstractAut aut) {
		this.aut = aut;
	}

	@Override
	public void run() {
		this.runSelendroidServer();
	}

}
