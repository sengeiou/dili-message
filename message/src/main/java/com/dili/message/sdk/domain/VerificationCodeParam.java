package com.dili.message.sdk.domain;

/**
 * @description： 消息推送-短信验证码
 * 
 * @author ：WangBo
 * @time ：2019年1月8日下午3:57:48
 */
public class VerificationCodeParam extends BaseParam {
	/** 验证码 */
	private String code;
	/** 过期时间 */
	private String expireTime;

	/** 验证码 */
	public String getCode() {
		return code;
	}

	/** 验证码 */
	public void setCode(String code) {
		this.code = code;
	}

	/** 过期时间 */
	public String getExpireTime() {
		return expireTime;
	}

	/** 过期时间 */
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

}