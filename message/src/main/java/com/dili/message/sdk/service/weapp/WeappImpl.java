package com.dili.message.sdk.service.weapp;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import com.dili.message.sdk.type.TemplateType;

import okhttp3.Response;

/**
 * @description： 小程序消息推送实现
 * 
 * @author ：WangBo
 * @time ：2018年11月9日上午10:50:06
 */
@Component
public class WeappImpl implements IMessageService {
	Logger log = LoggerFactory.getLogger(WeappImpl.class);
	private static String messagetype = "小程序";
	@Value("${weapp.appId}")
	public String appId;
	@Value("${weapp.appsecret}")
	public String appsecret;

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
	@Resource
	private AccessTokenUtil accessTokenUtil;

	@Override
	public boolean delivery(List<DeliveryParam> params) {
		log.info(messagetype + "推送[" + TemplateType.DELIVERY + "].");
		boolean ret = false;
		try {
			String accessToken = accessTokenUtil.getToken(appId, appsecret);
			for (DeliveryParam param : params) {
				Map<String, TemplateParam> dataMap = new HashMap<String, TemplateParam>();
				dataMap.put("keyword1", new TemplateParam(param.getDeliveryCode()));
				dataMap.put("keyword2", new TemplateParam(param.getProductItemInfo()));
				dataMap.put("keyword3", new TemplateParam(param.getDeliveryAddress()));
				dataMap.put("keyword4", new TemplateParam(param.getShopName()));
				dataMap.put("keyword5", new TemplateParam(param.getBusinessHours()));
				dataMap.put("keyword6", new TemplateParam(param.getOrderNo()));
				dataMap.put("keyword7", new TemplateParam(param.getDeliveryTime()));
				HashMap<String, Object> weappParam = buildParam(template_delivery, param.getPage(), param.getFormOrPayId(), dataMap);

				HashMap<String, Object> sendParam = new HashMap<String, Object>();
				sendParam.put("touser", param.getOpenId());
				sendParam.put("weapp_template_msg", weappParam);
				ret = sendParam(accessToken, sendParam);
			}
		} catch (Exception e) {
			log.error(messagetype + "推送[" + TemplateType.DELIVERY + "]异常！", e);
		}
		return ret;
	}

	@Override
	public boolean refund(List<RefundParam> params) {
		log.info(messagetype + "推送[" + TemplateType.REFUND + "].");
		boolean ret = false;
		try {
			String accessToken = accessTokenUtil.getToken(appId, appsecret);
			for (RefundParam param : params) {
				Map<String, TemplateParam> dataMap = new HashMap<String, TemplateParam>();
				dataMap.put("keyword1", new TemplateParam(param.getAmount()));
				dataMap.put("keyword2", new TemplateParam(param.getCause()));
				dataMap.put("keyword3", new TemplateParam(param.getTime()));
				dataMap.put("keyword4", new TemplateParam(param.getRefundMode()));
				dataMap.put("keyword5", new TemplateParam(param.getOrderNo()));
				dataMap.put("keyword6", new TemplateParam(param.getRemark()));
				HashMap<String, Object> weappParam = buildParam(template_refunds, param.getPage(), param.getFormOrPayId(), dataMap);

				HashMap<String, Object> sendParam = new HashMap<String, Object>();
				sendParam.put("touser", param.getOpenId());
				sendParam.put("weapp_template_msg", weappParam);
				ret = sendParam(accessToken, sendParam);
			}
		} catch (Exception e) {
			log.error(messagetype + "推送[" + TemplateType.REFUND + "]异常！", e);
		}
		return ret;
	}

	@Override
	public boolean campaignSuccess(List<CampaignSuccessParam> params) {
		log.info(messagetype + "推送[" + TemplateType.CAMPAIGN_SUCCESS + "].");
		boolean ret = false;
		try {
			String accessToken = accessTokenUtil.getToken(appId, appsecret);
			for (CampaignSuccessParam param : params) {
				Map<String, TemplateParam> dataMap = new HashMap<String, TemplateParam>();
				dataMap.put("keyword1", new TemplateParam(param.getProductName()));
				dataMap.put("keyword2", new TemplateParam(param.getPeoples()));
				dataMap.put("keyword3", new TemplateParam(param.getPrice()));
				dataMap.put("keyword4", new TemplateParam(param.getTimeRemaining()));
				HashMap<String, Object> weappParam = buildParam(template_campaignSuccess, param.getPage(), param.getFormOrPayId(), dataMap);

				HashMap<String, Object> sendParam = new HashMap<String, Object>();
				sendParam.put("touser", param.getOpenId());
				sendParam.put("weapp_template_msg", weappParam);
				ret = sendParam(accessToken, sendParam);
			}
		} catch (Exception e) {
			log.error(messagetype + "推送[" + TemplateType.CAMPAIGN_SUCCESS + "]异常！", e);
		}
		return ret;
	}

	@Override
	public boolean closeOrder(List<CloseOrderParam> params) {
		log.info(messagetype + "推送[" + TemplateType.CLOSE_ORDER + "].");
		boolean ret = false;
		try {
			String accessToken = accessTokenUtil.getToken(appId, appsecret);
			for (CloseOrderParam param : params) {
				Map<String, TemplateParam> dataMap = new HashMap<String, TemplateParam>();
				dataMap.put("keyword1", new TemplateParam(param.getProductName()));
				dataMap.put("keyword2", new TemplateParam(param.getOrderNo()));
				dataMap.put("keyword3", new TemplateParam(param.getAmount()));
				dataMap.put("keyword4", new TemplateParam(param.getCreateOrderTime()));
				dataMap.put("keyword5", new TemplateParam(param.getKindlyReminder()));
				HashMap<String, Object> weappParam = buildParam(template_closeOrder, param.getPage(), param.getFormOrPayId(), dataMap);

				HashMap<String, Object> sendParam = new HashMap<String, Object>();
				sendParam.put("touser", param.getOpenId());
				sendParam.put("weapp_template_msg", weappParam);
				ret = sendParam(accessToken, sendParam);
			}
		} catch (Exception e) {
			log.error(messagetype + "推送[" + TemplateType.CLOSE_ORDER + "]异常！", e);
		}
		return ret;
	}

	@Override
	public boolean campaignFailure(List<CampaignFailureParam> params) {
		log.info(messagetype + "推送[" + TemplateType.CAMPAIGN_FAILURE + "].");
		boolean ret = false;
		try {
			String accessToken = accessTokenUtil.getToken(appId, appsecret);
			for (CampaignFailureParam param : params) {
				Map<String, TemplateParam> dataMap = new HashMap<String, TemplateParam>();
				dataMap.put("keyword1", new TemplateParam(param.getOrderNo()));
				dataMap.put("keyword2", new TemplateParam(param.getProcessingResults()));
				dataMap.put("keyword3", new TemplateParam(param.getCampaignName()));
				dataMap.put("keyword4", new TemplateParam(param.getAmount()));
				dataMap.put("keyword5", new TemplateParam(param.getFailureCause()));
				dataMap.put("keyword6", new TemplateParam(param.getCampaignProduct()));
				HashMap<String, Object> weappParam = buildParam(template_campaignFailure, param.getPage(), param.getFormOrPayId(), dataMap);

				HashMap<String, Object> sendParam = new HashMap<String, Object>();
				sendParam.put("touser", param.getOpenId());
				sendParam.put("weapp_template_msg", weappParam);
				ret = sendParam(accessToken, sendParam);
			}
		} catch (Exception e) {
			log.error(messagetype + "推送[" + TemplateType.CAMPAIGN_FAILURE + "]异常！", e);
		}
		return ret;
	}

	@Override
	public boolean deliverySuccess(List<DeliverySuccessParam> params) {
		log.info(messagetype + "推送[" + TemplateType.DELIVERY_SUCCESS + "].");
		boolean ret = false;
		try {
			String accessToken = accessTokenUtil.getToken(appId, appsecret);
			for (DeliverySuccessParam param : params) {
				Map<String, TemplateParam> dataMap = new HashMap<String, TemplateParam>();
				dataMap.put("keyword1", new TemplateParam(param.getProductInfo()));
				dataMap.put("keyword2", new TemplateParam(param.getOrderNo()));
				dataMap.put("keyword3", new TemplateParam(param.getDeliveryTime()));
				dataMap.put("keyword4", new TemplateParam(param.getDeliveryShop()));
				HashMap<String, Object> weappParam = buildParam(template_deliverySuccess, param.getPage(), param.getFormOrPayId(), dataMap);

				HashMap<String, Object> sendParam = new HashMap<String, Object>();
				sendParam.put("touser", param.getOpenId());
				sendParam.put("weapp_template_msg", weappParam);
				ret = sendParam(accessToken, sendParam);
			}
		} catch (Exception e) {
			log.error(messagetype + "推送[" + TemplateType.DELIVERY_SUCCESS + "]异常！", e);
		}
		return ret;
	}

	@Override
	public boolean orderPaySuccess(List<OrderPaySuccessParam> params) {
		log.info(messagetype + "推送[" + TemplateType.PAY_SUCCESS + "].");
		boolean ret = false;
		try {
			String accessToken = accessTokenUtil.getToken(appId, appsecret);
			for (OrderPaySuccessParam param : params) {
				Map<String, TemplateParam> dataMap = new HashMap<String, TemplateParam>();
				dataMap.put("keyword1", new TemplateParam(param.getOrderNo()));
				dataMap.put("keyword2", new TemplateParam(param.getProductName()));
				dataMap.put("keyword3", new TemplateParam(param.getAmount()));
				dataMap.put("keyword4", new TemplateParam(param.getCreateOrderTime()));
				HashMap<String, Object> weappParam = buildParam(template_delivery, param.getPage(), param.getFormOrPayId(), dataMap);

				HashMap<String, Object> sendParam = new HashMap<String, Object>();
				sendParam.put("touser", param.getOpenId());
				sendParam.put("weapp_template_msg", weappParam);
				ret = sendParam(accessToken, sendParam);
			}
		} catch (Exception e) {
			log.error(messagetype + "推送[" + TemplateType.PAY_SUCCESS + "]异常！", e);
		}
		return ret;
	}

	@Override
	public boolean goodsWarning(List<GoodsWarningParam> param) {
		log.info(messagetype + "推送[" + TemplateType.GOODS_WARNING + "]暂未实现！");
		return false;
	}

	/**
	 * 构造参数 <br>
	 * <i> 按小程序和公众号统一的服务消息接口参数格式构造
	 * 
	 * @param templateId
	 *            模板ID
	 * @param page
	 *            点击时需要跳转的页面
	 * @param fromOrPayId
	 *            小程序表单ID或预支付ID
	 * @param data
	 *            模板中的参数(keyword1,keyworkd2...)
	 * @return
	 */
	public HashMap<String, Object> buildParam(String templateId, String page, String fromOrPayId, Map<String, TemplateParam> data) {
		HashMap<String, Object> weappParam = new HashMap<String, Object>();
		weappParam.put("template_id", template_delivery);
		weappParam.put("form_id", fromOrPayId);
		weappParam.put("page", page);
		weappParam.put("data", data);
		return weappParam;
	}

	/**
	 * 向小程序和公众号统一的服务消息接口推送消息 <br>
	 * <i>API地址：https://developers.weixin.qq.com/miniprogram/dev/api/open-api/uniform-message/sendUniformMessage.html
	 * 
	 * @param accessToken
	 *            每次请求的token
	 * @param data
	 *            统一服务消息接口参数
	 * @return
	 */
	private boolean sendParam(String accessToken, HashMap<String, Object> data) {
		// 发送模板消息
		String sendMessageUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=" + accessToken;
		String jsonData = JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect);
		log.info(messagetype+"requestData>" + jsonData);
		Response sendResponse;
		try {
			sendResponse = OkHttpUtils.postString().content(jsonData).url(sendMessageUrl).build().connTimeOut(1000L * 60L * 60L * 3)
					.readTimeOut(1000L * 60L * 60L * 3).writeTimeOut(1000L * 60L * 60L * 3).execute();
			String sendResponseString = sendResponse.body().string();
			log.info(messagetype+"response>" + sendResponseString);
			if (StringUtils.isNotBlank(sendResponseString)) {
				int errorCode = JSONObject.parseObject(sendResponseString).getIntValue("errcode");
				if (errorCode == 0) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			log.error("小程序推送异常!", e);
		}
		return false;
	}
}
