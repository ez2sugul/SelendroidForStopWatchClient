package com.ez2sugul.sample.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ez2sugul.sample.run.Watcher;

public class WatcherTest {
	private static Watcher hook;
	private static ArrayList<String> readLine;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		hook = new Watcher();
		readLine = new ArrayList<String>();
		
		readLine.add("adb -s 42f7ecb874668f6f shell am instrument -e main_activity com.skmnc.gifticon.activity.MainActivity");
		readLine.add("1? 08, 2014 8:00:46 ?? io.selendroid.io.ShellCommand exec");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void lookupStartTime() {
		java.util.Iterator<String> it = readLine.iterator();
		String actual = "";
		String line;
		
		System.out.println(readLine.get(0));
		actual = hook.watchStartTime(readLine.get(0));
		assertEquals("", actual);
		
		
	}
	
	@Test
	public void lookupStartTime2() {
		java.util.Iterator<String> it = readLine.iterator();
		String actual = "";
		String line;
		System.out.println(readLine.get(1));
		actual = hook.watchStartTime(readLine.get(1));
		assertEquals("1? 08, 2014 8:00:46 ??", actual);
	}
	
	@Test
	public void extractStartTime() {
		
	}

}
