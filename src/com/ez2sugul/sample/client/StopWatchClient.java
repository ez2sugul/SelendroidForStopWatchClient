package com.ez2sugul.sample.client;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.ez2sugul.sample.aut.AbstractAut;
import com.ez2sugul.sample.aut.AutDaummap;
import com.ez2sugul.sample.aut.AutGifticon;
import com.ez2sugul.sample.aut.AutHoppin;
import com.ez2sugul.sample.aut.AutNinegag;
import com.ez2sugul.sample.aut.AutStyletag;
import com.ez2sugul.sample.aut.AutTlol;
import com.thoughtworks.selenium.SeleniumException;

public class StopWatchClient {
	private SelendroidServer server = null;
	private AbstractAut aut = null;
	private Condition done = null;
	private Lock lock = new ReentrantLock();
	
	public StopWatchClient() {
		server = null;
		aut = null;
		done = lock.newCondition();
	}
	

	public void runSingleApp(AbstractAut aut) {
		Lock serverLock = new ReentrantLock();
		Lock autLock = new ReentrantLock();
		Thread serverThread = null;
		Thread autThread = null;
		
		server = new SelendroidServer();
		server.setLock(serverLock);
		aut.setLock(autLock);
		server.assignAut(aut);
		server.setInstalledApp(true);

		serverThread = new Thread(server);
		serverThread.start();

		synchronized(serverLock) {
			try {
				System.out.println("waiting for server lock");
				serverLock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		autThread = new Thread(aut);
		autThread.start();

		// wait for aut done
		synchronized(autLock) {
			try {
				System.out.println("waiting for aut lock");
				autLock.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		server.terminateServer();

		synchronized(serverLock) {
			try {
				System.out.println("waiting for shutting server down");
				serverLock.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public void runMultipleApp() {
		runSingleApp(new AutGifticon());
		runSingleApp(new AutTlol());
		
		runSingleApp(new AutGifticon());
		runSingleApp(new AutTlol());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StopWatchClient client = new StopWatchClient();
		
		client.runSingleApp(new AutTlol());
	}

}
