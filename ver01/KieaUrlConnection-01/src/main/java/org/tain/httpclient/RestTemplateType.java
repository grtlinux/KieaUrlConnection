package org.tain.httpclient;

public enum RestTemplateType {
	NORMAL("Normal"),
	SSL01("SSL-1"),
	SSL02("SSL-2"),
	SETENV("Set env");

	private String value;

	RestTemplateType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}