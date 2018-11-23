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
	/** 表单号或支付号(小程序必填) */
	private String formOrPayId;
	/** 小程序或公众号要跳转的路径 */
	private String page;
	/** 备注 */
	private String remark;

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

	/** 表单号或支付号(小程序必填) */
	public String getFormOrPayId() {
		return formOrPayId;
	}

	/** 表单号或支付号(小程序必填) */
	public void setFormOrPayId(String formOrPayId) {
		this.formOrPayId = formOrPayId;
	}

	/** 小程序或公众号要跳转的路径 */
	public String getPage() {
		return page;
	}

	/** 小程序或公众号要跳转的路径 */
	public void setPage(String page) {
		this.page = page;
	}

	/** 备注 */
	public String getRemark() {
		return remark;
	}

	/** 备注 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
