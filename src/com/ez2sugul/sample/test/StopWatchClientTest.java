package com.ez2sugul.sample.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ez2sugul.sample.aut.AutGifticon;
import com.ez2sugul.sample.client.SelendroidServer;
import com.ez2sugul.sample.client.StopWatchClient;

public class StopWatchClientTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
		StopWatchClient client = new StopWatchClient();
		client.runSingleApp(new AutGifticon());
	}

}
