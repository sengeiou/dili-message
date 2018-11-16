package com.dili.demo.webapp;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dili.http.okhttp.OkHttpUtils;
import com.dili.message.sdk.MessageService;
import com.dili.message.sdk.common.AccessTokenUtil;
import com.dili.message.sdk.domain.DeliveryParam;
import com.dili.message.sdk.domain.GoodsWarningParam;
import com.dili.message.sdk.type.MessageType;

import io.swagger.annotations.Api;
import okhttp3.Response;

/**
 * @description：
 * 
 * @author ：WangBo
 * @time ：2018年11月1日下午4:51:27
 */
@Api("/weapp")
@Controller
@RequestMapping("/weapp")
public class WeappController {
	@Resource
	MessageService messageService;
	Logger LOG = LoggerFactory.getLogger(getClass());
	String appId = "wxd1405e5c40ff05db";
	String appSecret = "2c34b95ab3a5ff4f77763ba7931dcc4d";
//	String openId = "oK0VK5FPNwYVy_UjdV63hRj67PpY"; // 加林微信
	//
	String openId ="oK0VK5Ef3lcQMkbfxEc5LD1dBEQk"; //王波
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

	@RequestMapping(value = "/sendMessage.action", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String sendMessage(@RequestBody String jsonBody) {
		try {
			LOG.info("小程序模板消息发送formId = " + jsonBody);
			// 获取 access_token
			HashMap<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("appid", appId);
			paramMap.put("secret", appSecret);
			paramMap.put("grant_type", "client_credential");
			String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
			Response execute = OkHttpUtils.get().headers(null).url(tokenUrl).params(paramMap).build()
					// 3小时过期
					.connTimeOut(1000L * 60L * 60L * 3).readTimeOut(1000L * 60L * 60L * 3).writeTimeOut(1000L * 60L * 60L * 3).execute();
			String tokenResponse = execute.body().string();
			LOG.info("token>" + tokenResponse);
			JSONObject tokenObj = JSONObject.parseObject(tokenResponse);
			String accessToken = tokenObj.getString("access_token");

			// 发送模板消息
			String templateId = "uBWlGrCDLb9pRXmpozxPdKPet0bkrMm8YEtjLHzf5t4"; // 取货消息
			String sendMessageUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + accessToken;
			HashMap<String, Object> sendParam = new HashMap<String, Object>();
			sendParam.put("access_token", accessToken);
			sendParam.put("touser", openId); // 接收者（用户）的 openid
			sendParam.put("template_id", templateId); // 所需下发的模板消息的id
			JSONObject reqParamJsonObj=JSONObject.parseObject(jsonBody);
			sendParam.put("form_id", reqParamJsonObj.getString("formId")); // 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的
			sendParam.put("page", "pages/home/home");
			
			Map<String,Map<String, String>> dataMap=new HashMap<String,Map<String,String>>();
			Map<String, String> wordMap = new HashMap<String, String>();
			wordMap.put("value", "339208499");
			dataMap.put("keyword1", wordMap);
			wordMap.put("value", "2015年01月05日 12:30");
			dataMap.put("keyword2",wordMap);
			sendParam.put("data", dataMap);
			LOG.info("发送消息请求参数>" + JSONObject.toJSONString(sendParam,SerializerFeature.DisableCircularReferenceDetect));
			// prepay_id
			Response sendResponse = OkHttpUtils.postString().content(JSONObject.toJSONString(sendParam,SerializerFeature.DisableCircularReferenceDetect)).url(sendMessageUrl)
					// .mediaType(MediaType.parse("application/json; charset=utf-8"))
					// .mediaType(MediaType.parse("application/x-www-form-urlencoded;
					// charset=UTF-8"))
					.build()
					// 3小时过期
					.connTimeOut(1000L * 60L * 60L * 3).readTimeOut(1000L * 60L * 60L * 3).writeTimeOut(1000L * 60L * 60L * 3).execute();
			String sendResponseString = sendResponse.body().string();
			LOG.info("token>" + sendResponseString);
			return "sucess";
		} catch (Exception e) {
			LOG.error("小程序登录异常！", e);
		}
		return null;
	}
	
	/**
	 * 同时推送小程序和公众号
	 */
	@RequestMapping(value = "/sendAllMessage.action", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String sendAllMessage(@RequestBody String jsonBody) {
		try {
			LOG.info("小程序模板消息发送formId = " + jsonBody);
			String formId=JSONObject.parseObject(jsonBody).getString("formId");
			goodsWarning(formId);
			return "sucess";
		} catch (Exception e) {
			LOG.error("小程序登录异常！", e);
		}
		return null;
	}
	private void goodsWarning(String formId) {
		GoodsWarningParam param=new GoodsWarningParam();
		param.setAmount(53+"");
		param.setArea("成都区域");;
		param.setGoods("苹果");;
		param.setMobile("18981883712");
		messageService.goodsWarning(param, MessageType.WEAPP,MessageType.MP,MessageType.SMS);
	}
	private void sendDelivery(String formId) {
		DeliveryParam param=new DeliveryParam();
		param.setBusinessHours("8:00-20:00");
		param.setDeliveryAddress("人民大道东6号");
		param.setDeliveryCode("665752");
		param.setDeliveryTime("2018.11.13下午14：30");
		param.setFromId(formId);
		param.setMobile("18981883712");
		param.setOpenId("oK0VK5Ef3lcQMkbfxEc5LD1dBEQk");
		param.setOrderNo("XX36045872");
		param.setProductItemInfo("鱼、人参、苹果");
		param.setShopName("天安门一号店");
		messageService.delivery(param, MessageType.WEAPP,MessageType.MP);
	}
//	/**
//	 * 同时推送小程序和公众号
//	 */
//	@RequestMapping(value = "/sendAllMessage.action", method = { RequestMethod.GET, RequestMethod.POST })
//	@ResponseBody
//	public String sendAllMessage(@RequestBody String jsonBody) {
//		try {
//			LOG.info("小程序模板消息发送formId = " + jsonBody);
//			// 获取 access_token
//			HashMap<String, String> paramMap = new HashMap<String, String>();
//			paramMap.put("appid", appId);
//			paramMap.put("secret", appSecret);
//			paramMap.put("grant_type", "client_credential");
//			String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
//			Response execute = OkHttpUtils.get().headers(null).url(tokenUrl).params(paramMap).build()
//					// 3小时过期
//					.connTimeOut(1000L * 60L * 60L * 3).readTimeOut(1000L * 60L * 60L * 3).writeTimeOut(1000L * 60L * 60L * 3).execute();
//			String tokenResponse = execute.body().string();
//			LOG.info("token>" + tokenResponse);
//			JSONObject tokenObj = JSONObject.parseObject(tokenResponse);
//			String accessToken = tokenObj.getString("access_token");
//
//			// 发送模板消息
//			String templateId = "uBWlGrCDLb9pRXmpozxPdKPet0bkrMm8YEtjLHzf5t4"; // 取货消息
//			String sendMessageUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=" + accessToken;
//			HashMap<String, Object> sendParam = new HashMap<String, Object>();
////			sendParam.put("access_token", accessToken);
//			sendParam.put("template_id", templateId); // 所需下发的模板消息的id
//			JSONObject reqParamJsonObj=JSONObject.parseObject(jsonBody);
//			sendParam.put("form_id", reqParamJsonObj.getString("formId")); // 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的
//			sendParam.put("page", "pages/home/home");
//			Map<String,Map<String, String>> dataMap=new HashMap<String,Map<String,String>>();
//			Map<String, String> wordMap = new HashMap<String, String>();
//			wordMap.put("value", "339208499");
//			dataMap.put("keyword1", wordMap);
//			wordMap.put("value", "2015年01月05日 12:30");
//			dataMap.put("keyword2",wordMap);
//			sendParam.put("data", dataMap);
//			
//			/**
//			 * 公众号参数
//			 */
//			JSONObject mpParam=new JSONObject();
////			mpParam.put("access_token", accessToken);
//			mpParam.put("appid", "wx9fb157a2c5c18c81");
//			mpParam.put("template_id", "P_1fRKmePFWiXcFg9rn7-hEXCwGWmbu7gdoxIP4TEUM");
//			mpParam.put("url", "http://weixin.qq.com/download");
//			JSONObject miniprogram=new JSONObject();
//			miniprogram.put("appid", appId);
//			miniprogram.put("path", "pages/welcome/welcome");
//			mpParam.put("miniprogram", miniprogram);
////			dataMap.put("first", wordMap);
//			mpParam.put("data", dataMap);
//			JSONObject param=new JSONObject();
//			param.put("touser", openId); // 接收者（用户）的 openid
//			param.put("weapp_template_msg", sendParam);
////			param.put("mp_template_msg", mpParam);
//			LOG.info("发送消息请求参数>" + JSONObject.toJSONString(param,SerializerFeature.DisableCircularReferenceDetect));
//			
//			// prepay_id
//			Response sendResponse = OkHttpUtils.postString().content(JSONObject.toJSONString(param,SerializerFeature.DisableCircularReferenceDetect)).url(sendMessageUrl)
//					// .mediaType(MediaType.parse("application/json; charset=utf-8"))
//					// .mediaType(MediaType.parse("application/x-www-form-urlencoded;
//					// charset=UTF-8"))
//					.build()
//					// 3小时过期
//					.connTimeOut(1000L * 60L * 60L * 3).readTimeOut(1000L * 60L * 60L * 3).writeTimeOut(1000L * 60L * 60L * 3).execute();
//			String sendResponseString = sendResponse.body().string();
//			LOG.info("token>" + sendResponseString);
//			return "sucess";
//		} catch (Exception e) {
//			LOG.error("小程序登录异常！", e);
//		}
//		return null;
//	}
	
	public static void main(String[] args) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		JSONObject wordMap = new JSONObject();
		wordMap.put("value", "339208499");
		dataMap.put("keyword1", wordMap.toString());
		wordMap.put("value", "2015年01月05日 12:30");
		dataMap.put("keyword2",wordMap.toString());
		System.out.println(JSONObject.toJSONString(dataMap,SerializerFeature.DisableCircularReferenceDetect));
	}
}
