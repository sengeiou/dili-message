package com.dili.message.sdk.common;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.dili.http.okhttp.OkHttpUtils;
import com.dili.message.sdk.exception.ApplicationException;
import com.dili.ss.util.RedisUtil;

import okhttp3.Response;

/**
 * @description：
 * 		定时获取AccessToken
 * @author ：WangBo
 * @time ：2018年11月13日下午5:12:36
 */
@Component
public class AccessTokenUtil {
	static Logger log = LoggerFactory.getLogger(AccessTokenUtil.class);
	@Autowired
	RedisUtil redisUtil;
	private static String token_redis_key="access_token";
	private static Long expireTime = 7100L;
//	private static String accessToken;

	/**
	 * 从微信接口获取access_token,首次获取后保存到redis设置7100s的有效期,当redis中取值为空时再次从微信接口中获取
	 */
	public String getToken(String appId, String appsecret) {
		String accessToken = "";
		try {
			Object object = redisUtil.get(token_redis_key);
			if (object == null) {
				// 获取 access_token
				HashMap<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("appid", appId);
				paramMap.put("secret", appsecret);
				paramMap.put("grant_type", "client_credential");
				String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
				Response execute = OkHttpUtils.get().headers(null).url(tokenUrl).params(paramMap).build()
						// 3小时过期
						.connTimeOut(1000L * 60L * 60L * 3).readTimeOut(1000L * 60L * 60L * 3).writeTimeOut(1000L * 60L * 60L * 3).execute();
				String tokenResponse = execute.body().string();
				log.info("token>" + tokenResponse);
				JSONObject tokenObj = JSONObject.parseObject(tokenResponse);
				accessToken = tokenObj.getString("access_token");
				redisUtil.set(token_redis_key, accessToken, expireTime);
				return accessToken;
			}else {
				accessToken=object.toString();
			}
		} catch (Exception e) {
			log.error("获取accessToken出错",e);
			throw new ApplicationException("获取accessToken出错！");
			
		}
		return accessToken;
	}
}
