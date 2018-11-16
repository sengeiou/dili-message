package com.dili.message.sdk.domain;

/**
 * @description： 推送消息时的公共参数
 * 
 * @author ：WangBo
 * @time ：2018年11月12日下午3:06:47
 */
public class BaseParam {
	/** 手机号(短信必填) */
	private String mobile;
	/** 公众号或小程序必填 */
	private String openId;
	/** 表单号(小程序必填) */
	private String formId;
	/** 支付号(小程序必填) */
	private String payId;

	/** 手机号(短信必填) */
	public String getMobile() {
		return mobile;
	}

	/** 手机号(短信必填) */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/** 公众号或小程序必填 */
	public String getOpenId() {
		return openId;
	}

	/** 公众号或小程序必填 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/** 表单号(小程序必填) */
	public String getFormId() {
		return formId;
	}

	/** 表单号(小程序必填) */
	public void setFormId(String formId) {
		this.formId = formId;
	}

	/** 支付号(小程序必填) */
	public String getPayId() {
		return payId;
	}

	/** 支付号(小程序必填) */
	public void setPayId(String payId) {
		this.payId = payId;
	}

}
