package com.ez2sugul.sample.test;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.locks.ReentrantLock;

import io.selendroid.SelendroidCapabilities;
import io.selendroid.SelendroidConfiguration;
import io.selendroid.SelendroidDriver;
import io.selendroid.SelendroidLauncher;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.ScreenshotException;

import com.ez2sugul.sample.aut.AutTlol;
import com.ez2sugul.sample.client.SelendroidServer;
import com.thoughtworks.selenium.SeleniumException;

public class AutTlolTest {
	private static AutTlol tlol;
	private static SelendroidLauncher server = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		tlol = new AutTlol();
		tlol.setLock(new ReentrantLock());
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

	
	public void test() {
		try {
			tlol.runApp();
		} catch (SeleniumException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean loaded = tlol.loadingDone();
		tlol.terminateApp();
		
		assertEquals(true, loaded);
	}
	
	@Test 
	public void popup() {
		Method method = null;
		try {
			method = AutTlol.class.getDeclaredMethod("closePopup", null);
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		method.setAccessible(true);
		
		
		try {
			tlol.runApp();
		} catch (SeleniumException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean result = false;
		try {
			result = (Boolean) method.invoke(tlol, null);
			
			assertEquals(true, result);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tlol.terminateApp();
	}

}
