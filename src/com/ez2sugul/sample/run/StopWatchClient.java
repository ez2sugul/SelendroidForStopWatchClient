package com.ez2sugul.sample.run;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.thoughtworks.selenium.SeleniumException;

public class StopWatchClient {
	private SelendroidServer server = null;
	private AutGifticon aut = null;
	private Condition done = null;
	private Lock lock = new ReentrantLock();
	
	public StopWatchClient() {
		server = null;
		aut = null;
		done = lock.newCondition();
	}
	
	public void run() {
		Thread serverThread = null;
		Thread autThread = null;
		
		server = new SelendroidServer(lock);
		aut = new AutGifticon();
		server.assignAut(aut);
		
		serverThread = new Thread(server);
		serverThread.start();
		
		synchronized(lock) {
			try {
				lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		while (true) {
			
			try {
				aut.runApp();
			} catch (SeleniumException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			aut.loadingDone();
			aut.terminateApp();

			System.out.println("fail to run app");
		}
	}
	
	@Deprecated
	public void run2() {
		server = new SelendroidServer(lock);
//		aut = new AutGifticon(lock);
		Thread serverThread = new Thread(server);
		serverThread.start();
		Thread autThread = null;
		
		while (true) {
			if (autThread == null || !autThread.isAlive()) {
				System.out.println("aut thread restart");
//				aut = new AutGifticon(lock);
				server.assignAut(aut);
//				autThread = new Thread(aut);
				autThread.start();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StopWatchClient client = new StopWatchClient();
		client.run();
	}

}
