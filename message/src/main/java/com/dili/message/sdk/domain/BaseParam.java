package com.dili.message.sdk.domain;

/**
 * @description： 推送消息时的公共参数
 * 
 * @author ：WangBo
 * @time ：2018年11月12日下午3:06:47
 */
public class BaseParam {
	/** 手机号 */
	private String mobile;
	/** 公众号或小程序的openId */
	private String openId;
	/** 小程序使用的表单号 */
	private String fromId;
	/** 小程序使用的支付号 */
	private String payId;
	/** 公众号或小程序接口使用 */
	private String accessToken;

	/** 手机号 */
	public String getMobile() {
		return mobile;
	}

	/** 手机号 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/** 公众号或小程序的openId */
	public String getOpenId() {
		return openId;
	}

	/** 公众号或小程序的openId */
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/** 小程序使用的表单号 */
	public String getFromId() {
		return fromId;
	}

	/** 小程序使用的表单号 */
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	/** 小程序使用的支付号 */
	public String getPayId() {
		return payId;
	}

	/** 小程序使用的支付号 */
	public void setPayId(String payId) {
		this.payId = payId;
	}

	/** 公众号或小程序接口使用 */
	public String getAccessToken() {
		return accessToken;
	}

	/** 公众号或小程序接口使用 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}
