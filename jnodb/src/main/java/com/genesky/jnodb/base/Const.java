package com.genesky.jnodb.base;

public enum Const {

	ok("OK");

	String value;

	private Const(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
