package com.dili.message.sdk.service.mp;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dili.http.okhttp.OkHttpUtils;
import com.dili.message.sdk.common.AccessTokenUtil;
import com.dili.message.sdk.common.TemplateParam;
import com.dili.message.sdk.domain.CampaignFailureParam;
import com.dili.message.sdk.domain.CampaignSuccessParam;
import com.dili.message.sdk.domain.CloseOrderParam;
import com.dili.message.sdk.domain.DeliveryParam;
import com.dili.message.sdk.domain.DeliverySuccessParam;
import com.dili.message.sdk.domain.GoodsWarningParam;
import com.dili.message.sdk.domain.OrderPaySuccessParam;
import com.dili.message.sdk.domain.RefundParam;
import com.dili.message.sdk.service.IMessageService;
import com.dili.message.sdk.type.MessageType;
import com.dili.message.sdk.type.TemplateType;
import com.dili.ss.util.RedisUtil;
import com.dili.ss.util.SpringUtil;

import okhttp3.Response;

/**
 * @description： 公众号消息推送实现发送
 * 
 * @author ：WangBo
 * @time ：2018年11月9日上午10:50:06
 */
@Component
public class MpImpl implements IMessageService {
	Logger log = LoggerFactory.getLogger(MpImpl.class);

	private static String messagetype = "公众号";

	@Resource
	private AccessTokenUtil accessTokenUtil;

	@Value("${mp.appId}")
	public String mpAppId;
	@Value("${weapp.appId}")
	public String weappAppId;
	@Value("${mp.appsecret}")
	public String appsecret;
	// public static String
	// uniformMessageUrl="https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token="
	// + accessToken;
	/**
	 * 支持的模板
	 */
	@Value("${weapp.templateid.delivery}")
	public String template_delivery;
	@Value("${weapp.templateid.refund}")
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
		log.error(messagetype + "推送[" + TemplateType.DELIVERY + "]暂未实现！");
		return false;
	}

	@Override
	public boolean refund(RefundParam param) {
		log.error(messagetype + "推送[" + TemplateType.REFUND + "]暂未实现！");
		return false;
	}

	@Override
	public boolean campaignSuccess(CampaignSuccessParam param) {
		log.error(messagetype + "推送[" + TemplateType.CAMPAIGN_SUCCESS + "]暂未实现！");
		return false;
	}

	@Override
	public boolean closeOrder(CloseOrderParam param) {
		try {
			log.info(messagetype + "[" + TemplateType.CLOSE_ORDER + "]消息推送");
			// 获取 access_token
			String accessToken = accessTokenUtil.getToken(weappAppId, appsecret);

			// 发送模板消息
			String sendMessageUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=" + accessToken;
			HashMap<String, Object> sendParam = new HashMap<String, Object>();
			sendParam.put("touser", param.getOpenId());
			HashMap<String, Object> mpParam = new HashMap<String, Object>();
			sendParam.put("mp_template_msg", mpParam);

			mpParam.put("template_id", template_delivery);
			mpParam.put("appid", mpAppId);
			mpParam.put("url", "http://weixin.qq.com/download");
			JSONObject miniprogram = new JSONObject();
			miniprogram.put("appid", weappAppId);
			miniprogram.put("path", "pages/welcome/welcome");
			mpParam.put("miniprogram", miniprogram);
			Map<String, TemplateParam> dataMap = new HashMap<String, TemplateParam>();
			dataMap.put("first", new TemplateParam(TemplateType.CLOSE_ORDER.getName()));
			dataMap.put("keyword1", new TemplateParam(param.getProductName()));
			dataMap.put("keyword2", new TemplateParam(param.getOrderNo()));
			dataMap.put("keyword3", new TemplateParam(param.getCreateOrderTime()));
			dataMap.put("keyword4", new TemplateParam(param.getAmount()));
			dataMap.put("keyword5", new TemplateParam(param.getCloseOrderTime()));
			dataMap.put("remark", new TemplateParam("备注"));
			mpParam.put("data", dataMap);
			log.info("发送消息请求参数>" + JSONObject.toJSONString(sendParam, SerializerFeature.DisableCircularReferenceDetect));
			// prepay_id
			Response sendResponse = OkHttpUtils.postString()
					.content(JSONObject.toJSONString(sendParam, SerializerFeature.DisableCircularReferenceDetect)).url(sendMessageUrl).build()
					.connTimeOut(1000L * 60L * 60L * 3).readTimeOut(1000L * 60L * 60L * 3).writeTimeOut(1000L * 60L * 60L * 3).execute();
			String sendResponseString = sendResponse.body().string();
			log.info("Response" + sendResponseString);
			return true;
		} catch (Exception e) {
			log.error(messagetype + "推送[" + TemplateType.CLOSE_ORDER + "]异常！", e);
		}
		return false;
	}

	@Override
	public boolean campaignFailure(CampaignFailureParam param) {
		log.error(messagetype + "推送[" + TemplateType.CAMPAIGN_FAILURE + "]暂未实现！");
		return false;
	}

	@Override
	public boolean deliverySuccess(DeliverySuccessParam param) {
		try {
			log.info(messagetype + "[" + TemplateType.DELIVERY_SUCCESS + "]消息推送");
			// 获取 access_token
			String accessToken = accessTokenUtil.getToken(weappAppId, appsecret);

			// 发送模板消息
			String sendMessageUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=" + accessToken;
			HashMap<String, Object> sendParam = new HashMap<String, Object>();
			sendParam.put("touser", param.getOpenId());
			HashMap<String, Object> mpParam = new HashMap<String, Object>();
			sendParam.put("mp_template_msg", mpParam);

			mpParam.put("template_id", template_delivery);
			mpParam.put("appid", mpAppId);
			mpParam.put("url", "http://weixin.qq.com/download");
			JSONObject miniprogram = new JSONObject();
			miniprogram.put("appid", weappAppId);
			miniprogram.put("path", "pages/welcome/welcome");
			mpParam.put("miniprogram", miniprogram);
			Map<String, TemplateParam> dataMap = new HashMap<String, TemplateParam>();
			dataMap.put("first", new TemplateParam(TemplateType.DELIVERY_SUCCESS.getName()));
			dataMap.put("keyword1", new TemplateParam(param.getOrderNo()));
			dataMap.put("keyword2", new TemplateParam(param.getDeliveryTime()));
			dataMap.put("remark", new TemplateParam("备注"));
			mpParam.put("data", dataMap);
			log.info("发送消息请求参数>" + JSONObject.toJSONString(sendParam, SerializerFeature.DisableCircularReferenceDetect));
			// prepay_id
			Response sendResponse = OkHttpUtils.postString()
					.content(JSONObject.toJSONString(sendParam, SerializerFeature.DisableCircularReferenceDetect)).url(sendMessageUrl).build()
					.connTimeOut(1000L * 60L * 60L * 3).readTimeOut(1000L * 60L * 60L * 3).writeTimeOut(1000L * 60L * 60L * 3).execute();
			String sendResponseString = sendResponse.body().string();
			log.info("Response" + sendResponseString);
			return true;
		} catch (Exception e) {
			log.error(messagetype + "推送[" + TemplateType.DELIVERY_SUCCESS + "]异常！", e);
		}
		return false;
	}

	@Override
	public boolean orderPaySuccess(OrderPaySuccessParam param) {
		try {
			log.info(messagetype + "[" + TemplateType.PAY_SUCCESS + "]消息推送");
			// 获取 access_token
			String accessToken = accessTokenUtil.getToken(weappAppId, appsecret);

			// 发送模板消息
			String sendMessageUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=" + accessToken;
			HashMap<String, Object> sendParam = new HashMap<String, Object>();
			sendParam.put("touser", param.getOpenId());
			HashMap<String, Object> mpParam = new HashMap<String, Object>();
			sendParam.put("mp_template_msg", mpParam);

			mpParam.put("template_id", template_delivery);
			mpParam.put("appid", mpAppId);
			mpParam.put("url", "http://weixin.qq.com/download");
			JSONObject miniprogram = new JSONObject();
			miniprogram.put("appid", weappAppId);
			miniprogram.put("path", "pages/welcome/welcome");
			mpParam.put("miniprogram", miniprogram);
			Map<String, TemplateParam> dataMap = new HashMap<String, TemplateParam>();
			dataMap.put("first", new TemplateParam(TemplateType.DELIVERY_SUCCESS.getName()));
			dataMap.put("keyword1", new TemplateParam(param.getOrderNo()));
			dataMap.put("keyword2", new TemplateParam(param.getAmount()));
			dataMap.put("remark", new TemplateParam("备注"));
			mpParam.put("data", dataMap);
			log.info("发送消息请求参数>" + JSONObject.toJSONString(sendParam, SerializerFeature.DisableCircularReferenceDetect));
			// prepay_id
			Response sendResponse = OkHttpUtils.postString()
					.content(JSONObject.toJSONString(sendParam, SerializerFeature.DisableCircularReferenceDetect)).url(sendMessageUrl).build()
					.connTimeOut(1000L * 60L * 60L * 3).readTimeOut(1000L * 60L * 60L * 3).writeTimeOut(1000L * 60L * 60L * 3).execute();
			String sendResponseString = sendResponse.body().string();
			log.info("Response" + sendResponseString);
			return true;
		} catch (Exception e) {
			log.error(messagetype + "推送[" + TemplateType.PAY_SUCCESS + "]异常！", e);
		}
		return false;
	}

	@Override
	public boolean goodsWarning(GoodsWarningParam param) {
		log.info(messagetype+"推送["+TemplateType.GOODS_WARNING+"]暂未实现！");
		return false;
	}

}
