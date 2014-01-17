package com.ez2sugul.sample.run;

import java.util.ArrayList;
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
	

	public void runSingleApp() {
		Thread serverThread = null;
		
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
		}
	}
	
	public void runMultipleApp() {
		int appIndex = 0;
		Lock lock = null;
		ArrayList<AbstractAut> auts = new ArrayList<AbstractAut>();
		ArrayList<SelendroidServer> servers = new ArrayList<SelendroidServer>();
		ArrayList<Lock> locks = new ArrayList<Lock>();
		
		/**
		 * generate auts
		 */
		auts.add(new AutGifticon());
		auts.add(new AutTlol());
		
		for (int i = 0; i < auts.size(); i++) {
			// create lock object that is used at selendroid server
			locks.add(new ReentrantLock());
			// create selendroid server and assign lock object
			servers.add(new SelendroidServer(locks.get(i)));
			// assign aut to selendroid server
			servers.get(i).assignAut(auts.get(i));
		}
		
		
		while (true) {
			AbstractAut aut = auts.get(appIndex);
			server = servers.get(appIndex);
			
			Thread serverThread = new Thread(server);
			serverThread.start();
			
			synchronized(lock) {
				try {
					System.out.println("waiting for server start");
					locks.get(appIndex).wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			try {
				aut.runApp();
			} catch (SeleniumException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			aut.loadingDone();
			aut.terminateApp();
			
			server.terminateServer();
			
			appIndex = appIndex >= auts.size() - 1 ? 0 : ++appIndex;
			
			synchronized(lock) {
				try {
					System.out.println("waiting for server stop");
					locks.get(appIndex).wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StopWatchClient client = new StopWatchClient();
		client.runMultipleApp();
	}

}
