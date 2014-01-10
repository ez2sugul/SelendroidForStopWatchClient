package com.ez2sugul.sample.run;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.HTMLDocument.Iterator;

public class Watcher {
	private boolean isStarted;
	private Pattern pattern;
	private Matcher matcher;
	private LinkedHashMap<String, String> watchTable;
	
	public Watcher() {
		isStarted = false;
		watchTable = new LinkedHashMap<String, String>();
	}
	
	public boolean watchServerOn(String line) {
		pattern = Pattern.compile(".*server has been started.*");
		matcher = pattern.matcher(line);
		
		if (matcher.matches()) {
			return true;
		}
		
		return false;
	}
	
	public String watchStartTime(String text) {
		pattern = Pattern.compile(".*(shell am instrument).*");
		matcher = pattern.matcher(text);
		
		if (matcher.matches()) {
			SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date date = new Date();
			watchTable.put("start", now.format(date));
			System.out.println(now.format(date) + " " + matcher.group(1));
			return now.format(date);
		}
		
		return null;
	}
	
	public String watchStartTime2(String text) throws ParseException {
		if (!isStarted) {
			pattern = Pattern.compile(".*(shell am instrument).*");
			matcher = pattern.matcher(text);
			
			if (matcher.matches()) {
				isStarted = true;
				return "";
			}
		} else {
			pattern = Pattern.compile("(\\d{1,2}.*\\d{2}, \\d{4} [0-9]{1,2}:[0-9]{2}:[0-9]{2}.*) io.selendroid.io.ShellCommand exec");
			matcher = pattern.matcher(text);
			
			
			if (matcher.matches()) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a");
				SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				String currentMilliseconds = now.format(new Date());
				Date date = dateFormat.parse(matcher.group(1));
				System.out.println(date.toString());
				System.out.println(currentMilliseconds);
				return date.toString();
			}
		}
		
		return null;
	}
	
	public void putWatchTime(String action, String date) {
		watchTable.put(action, date);
	}
	
	public String[] elapsedTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		java.util.Iterator<String> it = watchTable.values().iterator();
		return null;
	}

}