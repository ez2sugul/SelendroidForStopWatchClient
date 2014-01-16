package com.ez2sugul.sample.util;

import java.io.IOException;
import java.util.Date;

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
	
	public static String getCurrentMethodName(int n) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		
		return ste[n].getMethodName();
	}
}
