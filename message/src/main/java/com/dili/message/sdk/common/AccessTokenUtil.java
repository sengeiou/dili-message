package com.dili.message.sdk.common;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.dili.http.okhttp.OkHttpUtils;
import com.dili.message.sdk.AccessTokenWork;
import com.dili.message.sdk.constants.UrlConstants;
import com.dili.message.sdk.exception.ApplicationException;
import com.dili.ss.util.RedisUtil;

import okhttp3.Response;

/**
 * @description： 获取AccessToken
 * 
 * @author ：WangBo
 * @time ：2018年11月13日下午5:12:36
 */
@Component
public class AccessTokenUtil {
	static Logger log = LoggerFactory.getLogger(AccessTokenUtil.class);
	@Autowired
	RedisUtil redisUtil;
	
	/** access_token在redis中的key*/
	public final static String token_redis_key = "access_token";
	
	/** 定时任务运行间隔 */
	public final static Long task_delay = 7100L;
	
	/** 微信token失效时间为7200S */
	public final static Long expireTime = 7200L;
	
	/** 定时获取任务线程池 */
	ScheduledExecutorService pool; 

	/**
	 * 
	 * 判断redis是否有缓存的access_token，如果有则直接使用;
	 * 如果没有则微信接口获取access_token，保存到redis设置7100s的有效期，
	 * 并开启一个定时任务每隔7100s自动去微信接口获取最新的token替换redis中的token
	 * 在获取新token5分钟以内新旧token都可以使用
	 *<br><i>
	 * 当多个应用使用消息模块时，避免重复启动线程获取access_token，所以使用redis保存token,
	 * 能最大限度减少重复获取的情况
	 */
	public String getToken(String appId, String appsecret) {
		String accessToken = "";
		try {
			Object object = redisUtil.get(token_redis_key);
			if (object == null) {
				HashMap<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("appid", appId);
				paramMap.put("secret", appsecret);
				paramMap.put("grant_type", "client_credential");
				Response execute = OkHttpUtils.get().headers(null).url(UrlConstants.GET_WEAPP_MP_ACCESS_TOKEN).params(paramMap).build()
						.connTimeOut(1000L * 60L * 60L * 3).readTimeOut(1000L * 60L * 60L * 3).writeTimeOut(1000L * 60L * 60L * 3).execute();
				String tokenResponse = execute.body().string();
				log.info("access_token_response>" + tokenResponse);
				JSONObject tokenObj = JSONObject.parseObject(tokenResponse);
				accessToken = tokenObj.getString(AccessTokenUtil.token_redis_key);
				redisUtil.set("access_token", accessToken,AccessTokenUtil.expireTime);
				if (pool == null) {
					pool = Executors.newScheduledThreadPool(1);
					AccessTokenWork work = new AccessTokenWork(appId, appsecret, redisUtil);
					pool.scheduleWithFixedDelay(work, task_delay, task_delay, TimeUnit.SECONDS);
				}
				return accessToken;
			} else {
				accessToken = object.toString();
			}
		} catch (Exception e) {
			log.error("获取accessToken出错", e);
			throw new ApplicationException("获取accessToken出错！");

		}
		return accessToken;
	}
}
