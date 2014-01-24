package com.ez2sugul.sample.client;

public class SWShutdown extends Thread{
	private Process child = null;
	
	public SWShutdown(Process c) {
		this.child = c;
	}
	
	public void run() {
		child.destroy();
	}
}