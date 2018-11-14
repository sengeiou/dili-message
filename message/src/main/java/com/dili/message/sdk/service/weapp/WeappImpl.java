package com.dili.message.sdk.service.weapp;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dili.http.okhttp.OkHttpUtils;
import com.dili.message.sdk.common.TemplateParam;
import com.dili.message.sdk.domain.CampaignFailureParam;
import com.dili.message.sdk.domain.CampaignSuccessParam;
import com.dili.message.sdk.domain.CloseOrderParam;
import com.dili.message.sdk.domain.DeliveryParam;
import com.dili.message.sdk.domain.DeliverySuccessParam;
import com.dili.message.sdk.domain.OrderPaySuccessParam;
import com.dili.message.sdk.domain.RefundParam;
import com.dili.message.sdk.service.IMessageService;
import com.dili.ss.util.RedisUtil;
import com.dili.ss.util.SpringUtil;

import okhttp3.Response;

/**
 * @description： 阿里大鱼短信发送
 * 
 * @author ：WangBo
 * @time ：2018年11月9日上午10:50:06
 */
@Component
public class WeappImpl implements IMessageService {
	Logger log = LoggerFactory.getLogger(WeappImpl.class);
	@Value("${weapp.appId}")
	public String appId;
	@Value("${weapp.appsecret}")
	public String appsecret;

//	public static String uniformMessageUrl="https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=" + accessToken;
	/**
	 * 支持的模板
	 */
	@Value("${weapp.templateid.delivery}")
	public String template_delivery;
	@Value("${weapp.templateid.refunds}")
	public String template_refunds;
	@Value("${weapp.templateid.campaignSuccess}")
	public String template_campaignSuccess;
	@Value("${weapp.templateid.closeOrder}")
	public String template_closeOrder;
	@Value("${weapp.templateid.campaignFailure}")
	public String template_campaignFailure;
	@Value("${weapp.templateid.deliverySuccess}")
	public String template_deliverySuccess;
	@Value("${weapp.templateid.orderPaySuccess}")
	public String template_orderPaySuccess;

	@Override
	public boolean delivery(DeliveryParam param) {
		try {
			log.info("小程序取货消息推送");
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
			String accessToken = tokenObj.getString("access_token");

			// 发送模板消息
			String sendMessageUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=" + accessToken;
			HashMap<String, Object> sendParam = new HashMap<String, Object>();
			sendParam.put("touser", param.getOpenId());
			HashMap<String, Object> weappParam = new HashMap<String, Object>();
			sendParam.put("weapp_template_msg", weappParam);
			
			weappParam.put("template_id", template_delivery); // 所需下发的模板消息的id
			weappParam.put("form_id", param.getFromId()); // 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的
			weappParam.put("page", "pages/home/home");
			Map<String,TemplateParam> dataMap=new HashMap<String,TemplateParam>();
			dataMap.put("keyword1", new TemplateParam(param.getDeliveryCode()));
			dataMap.put("keyword2", new TemplateParam(param.getProductItemInfo()));
			dataMap.put("keyword3", new TemplateParam(param.getDeliveryAddress()));
			dataMap.put("keyword4", new TemplateParam(param.getShopName()));
			dataMap.put("keyword5", new TemplateParam(param.getBusinessHours()));
			dataMap.put("keyword6", new TemplateParam(param.getOrderNo()));
			dataMap.put("keyword7", new TemplateParam(param.getDeliveryTime()));
			weappParam.put("data", dataMap);
			log.info("发送消息请求参数>" + JSONObject.toJSONString(sendParam,SerializerFeature.DisableCircularReferenceDetect));
			// prepay_id
			Response sendResponse = OkHttpUtils.postString().content(JSONObject.toJSONString(sendParam,SerializerFeature.DisableCircularReferenceDetect)).url(sendMessageUrl)
					.build()
					.connTimeOut(1000L * 60L * 60L * 3).readTimeOut(1000L * 60L * 60L * 3).writeTimeOut(1000L * 60L * 60L * 3).execute();
			String sendResponseString = sendResponse.body().string();
			log.info("token>" + sendResponseString);
			return true;
		} catch (Exception e) {
			log.error("小程序推送取货通知异常！", e);
		}
		return false;
	}

	@Override
	public boolean refund(RefundParam param) {
		return false;
	}

	@Override
	public boolean campaignSuccess(CampaignSuccessParam param) {
		return false;
	}

	@Override
	public boolean closeOrder(CloseOrderParam param) {
		return false;
	}

	@Override
	public boolean campaignFailure(CampaignFailureParam param) {
		return false;
	}

	@Override
	public boolean deliverySuccess(DeliverySuccessParam parameterObject) {
		return false;
	}

	@Override
	public boolean orderPaySuccess(OrderPaySuccessParam param) {
		return false;
	}

}
