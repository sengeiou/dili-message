package com.dili.message.sdk.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dili.message.sdk.common.AccessTokenUtil;
import com.dili.ss.util.RedisUtil;

/**
 * @description：
 * 			获取微信、小程序accessToken,获取到后将token存入redis并设置有效期
 * @author ：WangBo
 * @time ：2018年11月12日上午10:33:39
 */
public class AccessTokenWork implements Runnable {
	private String appId;
	private String appsecret;
	private RedisUtil redisUtil;
	Logger log = LoggerFactory.getLogger(AccessTokenWork.class);

	public AccessTokenWork(String appId, String appsecret,RedisUtil redisUtil) {
		this.appId = appId;
		this.appsecret = appsecret;
		this.redisUtil = redisUtil;
	}

	@Override
	public void run() {
		try {
			log.info("******************");
			String accessToken = AccessTokenUtil.getNewToken(appId, appsecret);
			redisUtil.set(AccessTokenUtil.token_redis_key, accessToken,AccessTokenUtil.expireTime);
		} catch (Exception e) {
			log.error("定时获取access_token出错.",e);
		}
	}

}
