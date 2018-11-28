package com.ytoxl.module.uhome.uhomeInterface.model.duomai;

public class OrderPush extends OrderContent{
	private String hash;  //电商身份标识
	private String euid;  //网站主标识

	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getEuid() {
		return euid;
	}
	public void setEuid(String euid) {
		this.euid = euid;
	}
}
