package com.dili.message.sdk.domain;

/**
 * @description： 消息推送-短信验证码
 * 
 * @author ：WangBo
 * @time ：2019年1月8日下午3:57:48
 */
public class VerificationCodeParam extends BaseParam {
	/** 区域 */
	private String code;
	/** 商品 */
	private String expireTime;

	/** 区域 */
	public String getCode() {
		return code;
	}

	/** 区域 */
	public void setCode(String code) {
		this.code = code;
	}

	/** 商品 */
	public String getExpireTime() {
		return expireTime;
	}

	/** 商品 */
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

}