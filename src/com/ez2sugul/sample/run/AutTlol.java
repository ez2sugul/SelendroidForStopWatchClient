package com.ez2sugul.sample.run;

public class AutTlol extends AbstractAut {
	
	public AutTlol() {
		super("com.skmnc.gifticon", "kr.co.ulike.tesports-1.apk", "0.0.9");
	}

	@Override
	public boolean loadingDone() {
		driver.switchTo().window(AutType.Native.toString());
		
		return false;
	}

	@Override
	public boolean initializeApp() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean terminateApp() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void notice() {
		// TODO Auto-generated method stub

	}

}
