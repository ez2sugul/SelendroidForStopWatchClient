package com.ez2sugul.sample.test;

import static org.junit.Assert.*;
import io.selendroid.SelendroidCapabilities;
import io.selendroid.SelendroidConfiguration;
import io.selendroid.SelendroidLauncher;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.ez2sugul.sample.client.StopWatchDriver;
import com.thoughtworks.selenium.SeleniumException;

public class SignTest {
	
	private static SelendroidLauncher selendroidServer = null;
	private WebDriver driver = null;
	private SelendroidCapabilities capa;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		SelendroidConfiguration config = new SelendroidConfiguration();
//		config.addSupportedApp("/Users/skplanet/Documents/Projects/robotium/kr.co.ulike.tesports-1.apk");
//		config.addSupportedApp("/Users/skplanet/Documents/Projects/robotium/com.skmnc.gifticon-1.apk");
		config.setInstalledApp("kr.co.ulike.tesports/kr.co.ulike.tesports.IntroActivity:1.0.0");
		config.setRestartAdb(true);
		config.setVerbose(true);
		selendroidServer = new SelendroidLauncher(config);
		selendroidServer.lauchSelendroid();
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
	public void test() {
		capa = SelendroidCapabilities.device("kr.co.ulike.tesports:1.0.0");

		if (capa == null) {
			throw new SeleniumException("Invalid Selendroid Capabilities");
		}
		
		try {
			driver = new StopWatchDriver("http://localhost:5555/wd/hub", capa);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (driver == null) {
			throw new SeleniumException("can't load StopWatchDriver " + capa.toString());
		}
	}
	
	@Test
	public void testd() {
		capa = SelendroidCapabilities.device("com.skmnc.gifticon-1.apk:3.0.6");

		if (capa == null) {
			throw new SeleniumException("Invalid Selendroid Capabilities");
		}
		
		try {
			driver = new StopWatchDriver("http://localhost:5555/wd/hub", capa);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (driver == null) {
			throw new SeleniumException("can't load StopWatchDriver " + capa.toString());
		}
	}

}
