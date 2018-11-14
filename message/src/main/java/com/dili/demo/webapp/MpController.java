package com.dili.demo.webapp;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dili.http.okhttp.OkHttpUtils;

import io.swagger.annotations.Api;
import okhttp3.Response;

/**
 * @description：
 *               公众号接口
 * @author     ：WangBo
 * @time       ：2018年11月2日上午11:17:52
 */
@Api("/mp")
@Controller
@RequestMapping("/mp")
public class MpController {
	Logger LOG = LoggerFactory.getLogger(getClass());
	String appId = "wxd1405e5c40ff05db";
	String appSecret = "2c34b95ab3a5ff4f77763ba7931dcc4d";
	String openId = "oK0VK5FPNwYVy_UjdV63hRj67PpY"; // 加林微信

	@RequestMapping(value = "/login.action", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String login(String code) {
		try {
			LOG.info("小程序登录code = " + code);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("appid", appId);
			paramMap.put("secret", appSecret);
			paramMap.put("js_code", code);
			paramMap.put("grant_type", "authorization_code");
			String loginUrl = String.format("https://api.weixin.qq.com/sns/jscode2session", appId, appSecret, code);
			Response execute = OkHttpUtils.get().headers(null).url(loginUrl).params(paramMap).build()
					// 3小时过期
					.connTimeOut(1000L * 60L * 60L * 3).readTimeOut(1000L * 60L * 60L * 3).writeTimeOut(1000L * 60L * 60L * 3).execute();
			String loginResponse = execute.body().string();
			LOG.info("login>" + loginResponse);
			JSONObject jobj = JSONObject.parseObject(loginResponse);
			String openid = jobj.getString("openid");

			// 获取 access_token
			paramMap = new HashMap<String, String>();
			paramMap.put("appid", appId);
			paramMap.put("secret", appSecret);
			String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
			execute = OkHttpUtils.get().headers(null).url(tokenUrl).params(paramMap).build()
					// 3小时过期
					.connTimeOut(1000L * 60L * 60L * 3).readTimeOut(1000L * 60L * 60L * 3).writeTimeOut(1000L * 60L * 60L * 3).execute();
			String tokenResponse = execute.body().string();
			LOG.info("token>" + tokenResponse);
			JSONObject tokenObj = JSONObject.parseObject(tokenResponse);
			String accessToken = jobj.getString("access_token");

			// 发送模板消息
			String templateId = "uBWlGrCDLb9pRXmpozxPdKPet0bkrMm8YEtjLHzf5t4"; // 取货消息
			String sendMessageUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send";
			paramMap = new HashMap<String, String>();
			paramMap.put("access_token", accessToken);
			paramMap.put("touser", openid); // 接收者（用户）的 openid
			paramMap.put("template_id", templateId); // 所需下发的模板消息的id
			// paramMap.put("form_id", openid); //表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的
			// prepay_id
			OkHttpUtils.post().headers(null).url(sendMessageUrl).params(paramMap)
					// .mediaType(MediaType.parse("application/json; charset=utf-8"))
					// .mediaType(MediaType.parse("application/x-www-form-urlencoded;
					// charset=UTF-8"))
					.build()
					// 3小时过期
					.connTimeOut(1000L * 60L * 60L * 3).readTimeOut(1000L * 60L * 60L * 3).writeTimeOut(1000L * 60L * 60L * 3).execute();
			return "sucess";
		} catch (Exception e) {
			LOG.error("小程序登录异常！", e);
		}
		return null;
	}
}
