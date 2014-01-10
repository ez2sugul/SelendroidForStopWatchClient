package com.ez2sugul.sample.run;

public class StopWatchClient {
	private SelendroidServer server = null;
	private Watcher watcher = null;
	private AutGifticon aut = null;
	
	public StopWatchClient() {
		server = new SelendroidServer();
		watcher = new Watcher();
		aut = new AutGifticon();
		
		aut.registerWatcher(watcher);
		server.registerWatcher(watcher);
		server.registerAut(aut);
	}
	
	public void run() {
		Thread serverThread = new Thread(server);
		serverThread.start();
		Thread autThread = new Thread(aut);
		autThread.start();
		
		while (true) {
			if (!autThread.isAlive()) {
				System.out.println("aut thread restart");
				aut = new AutGifticon();
				server.registerAut(aut);
				autThread = new Thread(aut);
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
