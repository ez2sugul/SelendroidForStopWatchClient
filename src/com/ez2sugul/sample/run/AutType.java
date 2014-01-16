package com.ez2sugul.sample.run;

public enum AutType {
	Hybrid ("WEBVIEW"),
	Native ("NATIVE_APP");
	
	private final String value;
	
	private AutType(String value) {
		this.value = value;
	}
	
	public String toString() {
		return this.value;
	}
}
