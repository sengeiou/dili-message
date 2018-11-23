package com.dili.message.sdk;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.dili.http.okhttp.OkHttpUtils;
import com.dili.message.sdk.common.AccessTokenUtil;
import com.dili.message.sdk.constants.UrlConstants;
import com.dili.ss.util.RedisUtil;

import okhttp3.Response;

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
			HashMap<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("appid", appId);
			paramMap.put("secret", appsecret);
			paramMap.put("grant_type", "client_credential");
			Response execute = OkHttpUtils.get().headers(null).url(UrlConstants.GET_WEAPP_MP_ACCESS_TOKEN).params(paramMap).build().connTimeOut(1000L * 60L * 60L * 3)
					.readTimeOut(1000L * 60L * 60L * 3).writeTimeOut(1000L * 60L * 60L * 3).execute();
			String tokenResponse = execute.body().string();
			log.info("access_token_response>" + tokenResponse);
			JSONObject tokenObj = JSONObject.parseObject(tokenResponse);
			String accessToken = tokenObj.getString(AccessTokenUtil.token_redis_key);
			redisUtil.set("access_token", accessToken,AccessTokenUtil.expireTime);
		} catch (Exception e) {
			log.error("定时获取access_token出错.",e);
		}
	}

}
