package com.dili.message.sdk.common;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.dili.http.okhttp.OkHttpUtils;
import com.dili.message.sdk.constants.UrlConstants;
import com.dili.message.sdk.exception.ApplicationException;
import com.dili.message.sdk.task.AccessTokenWork;
import com.dili.ss.util.RedisUtil;

import okhttp3.Response;

/**
 * @description： 获取AccessToken
 * 
 * @author ：WangBo
 * @time ：2018年11月13日下午5:12:36
 */
@Component
@Scope("singleton")
public class AccessTokenUtil {
	static Logger log = LoggerFactory.getLogger(AccessTokenUtil.class);
	@Autowired
	RedisUtil redisUtil;
	@Value("${weapp.appId}")
	public String appId;
	@Value("${weapp.appsecret}")
	public String appsecret;
	/** access_token在redis中的key */
	public final static String token_redis_key = "access_token";

	/** 定时任务运行间隔 */
	public final static Long task_delay = 20L;

	/** 微信token失效时间为7200S */
	public final static Long expireTime = 7200L;

	// public volatile static String access_token = "";

	/** 定时获取任务线程池 */
	ScheduledExecutorService pool;
	
	static boolean isInit=true;

	/**
	 * 初始定时任务获取access_token,该任务间隔时间和首次执行时的延时时间相同
	 */
	public void initGetTokenWork() {
//		if(isInit) {
//			redisUtil.set(token_redis_key, null);
//			isInit=false;
//		}
		if (pool == null) {
			Object object = redisUtil.get(token_redis_key);
			if (object == null || StringUtils.isBlank(object.toString())) {
				log.info("初始化获取access_token线程池");
				pool = Executors.newScheduledThreadPool(1);
				AccessTokenWork work = new AccessTokenWork(appId, appsecret, redisUtil);
				pool.scheduleWithFixedDelay(work, task_delay, task_delay, TimeUnit.SECONDS);
			}
		}
	}

	/**
	 * 
	 * 判断redis是否有缓存的access_token，如果有则直接使用;
	 * 如果没有则微信接口获取access_token，保存到redis设置7100s的有效期，
	 * 并开启一个定时任务每隔7100s自动去微信接口获取最新的token替换redis中的token 在获取新token后，5分钟以内新旧token都可以使用
	 * <br>
	 * <i> 当多个应用使用消息模块时，避免重复启动线程获取access_token，所以使用redis保存token, 能最大限度减少重复获取的情况
	 */
	public String getToken() {
		String accessToken = "";
		try {
			Object object = redisUtil.get(token_redis_key);
			if (object == null || StringUtils.isBlank(object.toString())) {
				initGetTokenWork();
				accessToken = getNewToken(appId, appsecret);
				redisUtil.set(AccessTokenUtil.token_redis_key, accessToken,AccessTokenUtil.expireTime);
				return accessToken;
			} else {
				accessToken = object.toString();
			}
		} catch (Exception e) {
			log.error("获取accessToken出错", e);
		}
		if (accessToken == null) {
			throw new ApplicationException("accessToken为空。");
		}
		return accessToken;
	}

	/**
	 * 调用微信接口获取最新的access_token
	 */
	public static String getNewToken(String appId, String appsecret) {
		String accessToken = null;
		try {
			HashMap<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("appid", appId);
			paramMap.put("secret", appsecret);
			paramMap.put("grant_type", "client_credential");
			Response execute = OkHttpUtils.get().headers(null).url(UrlConstants.GET_WEAPP_MP_ACCESS_TOKEN).params(paramMap).build()
					.connTimeOut(1000L * 60L * 60L * 3).readTimeOut(1000L * 60L * 60L * 3).writeTimeOut(1000L * 60L * 60L * 3).execute();
			String tokenResponse = execute.body().string();
			log.info("access_token_response>" + tokenResponse);
			JSONObject tokenObj = JSONObject.parseObject(tokenResponse);
			if (0 == tokenObj.getIntValue("errcode") || "0".equals(tokenObj.getString("errcode"))) {
				accessToken = tokenObj.getString("access_token");
			} else {
				String errmsg = tokenObj.getString("errmsg");
				log.error("从微信接口获取accessToken出错errmsg[" + errmsg + "]");
			}
			return accessToken;
		} catch (Exception e) {
			log.error("从微信接口获取accessToken出错", e);
		}
		return accessToken;
	}
}
