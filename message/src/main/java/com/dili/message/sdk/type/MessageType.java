package com.dili.message.sdk.type;

/**
 * @description： 消息推送方式
 * 
 * @author ：WangBo
 * @time ：2018年11月9日下午4:54:53
 */
public enum MessageType {
	/** 短信 */
	SMS(1, "短信"),
	
	/** 公众号 */
	MP(2, "公众号"),
	
	/** 小程序 */
	WEAPP(3, "小程序");

	private String name;
	private int code;

	MessageType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}
