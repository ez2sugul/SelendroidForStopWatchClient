package com.ez2sugul.sample.aut;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;

import com.ez2sugul.sample.client.SWShutdown;
import com.ez2sugul.sample.util.Util;

public class AutDaummap extends AbstractAut {
	
	private Process child;

	public AutDaummap() {
		super("/Users/skplanet/Documents/Projects/robotium", "net.daum.android.map", "net.daum.android.map-2.apk", "3.7.0", "", 5555);

	}
	@Override
	public void run() {
		String[] commands = {"java", 
				"-jar",
				"autRunner.jar", 
				getPath(), 
				getPackageName(), 
				getApkName(), 
				getAppVersion(), 
				String.valueOf(getPort())}; 
		String command = StringUtils.join(commands, " ");
				
		
		System.out.println(command);
		
		try {
			child = Util.execCommand(command);
			Runtime.getRuntime().addShutdownHook(new SWShutdown(child));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		InputStream serverIn = child.getErrorStream();
		
		BufferedReader reader = null;
		reader = new BufferedReader(new InputStreamReader(serverIn));
		String line = null;
		
		try {
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		synchronized(lock) {
			lock.notify();
		}
	}

	@Override
	public boolean loadingDone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void notice() {
		// TODO Auto-generated method stub

	}

}
