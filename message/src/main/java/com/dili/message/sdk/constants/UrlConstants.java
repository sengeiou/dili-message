package com.dili.message.sdk.constants;

/**
 * @description：
 * 			第三方接口URL
 * @author ：WangBo
 * @time ：2018年11月22日上午10:47:19
 */
public class UrlConstants {
	/** 获取access_token(小程序\公众号都可以使用) */
	public final static String GET_WEAPP_MP_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";
	
	/** 推送小程序和公众号统一服务消息*/
	public final static String SEND_UNIFORM_MESSAGE =" https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=";
}
