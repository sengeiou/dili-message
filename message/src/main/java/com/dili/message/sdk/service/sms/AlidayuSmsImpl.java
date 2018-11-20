package com.dili.message.sdk.service.sms;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
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

/**
 * @description： 阿里大鱼短信发送
 * 
 * @author ：WangBo
 * @time ：2018年11月9日上午10:50:06
 */
@Component
public class AlidayuSmsImpl implements IMessageService {
	static Logger log = LoggerFactory.getLogger(AlidayuSmsImpl.class);
	private static String messagetype = "短信";

	@Value("${alidayu.sms.app.key}")
	public String app_key;
	@Value("${alidayu.sms.secret}")
	public String secret;
	@Value("${alidayu.sms.signname}")
	public String sms_sign_name;

	/**
	 * 支持的模板
	 */
	@Value("${alidayu.sms.templateid.delivery}")
	public String template_delivery;
	@Value("${alidayu.sms.templateid.refund}")
	public String template_refund;
	@Value("${alidayu.sms.templateid.goodsWarning}")
	public String template_goodsWarning;

	private IClientProfile profile = null;

	@PostConstruct
	private void init() {
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		try {
			profile = DefaultProfile.getProfile("cn-hangzhou", app_key, secret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
		} catch (ClientException e1) {
			log.error("初始化addEndpoint出错!", e1);
		}
	}

	@Override
	public boolean delivery(List<DeliveryParam> params) {
		if (profile == null) {
			log.error("初始化addEndpoint出错!");
		}
		for (DeliveryParam param : params) {
			try {
				IAcsClient acsClient = new DefaultAcsClient(profile);
				
				JSONObject json = new JSONObject();
				json.put("order", param.getOrderNo());
				json.put("date", param.getDeliveryTime());
				json.put("code", param.getDeliveryCode());
				json.put("Shop", param.getDeliveryAddress());
				SendSmsRequest request = buildData(template_delivery, param.getMobile(), json.toJSONString());

				log.info(messagetype+"sendSmsRequest>" + JSONObject.toJSONString(request));
				SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
				log.info(messagetype+"sendSmsResponse>" + JSONObject.toJSONString(sendSmsResponse));
				if (sendSmsResponse != null) {
					return sendSmsResponse.getCode().equals("OK");
				}
			} catch (Exception e) {
				log.error("用户[" + param.getMobile() + "]推送[" + TemplateType.DELIVERY + "]消息失败！", e);
			}
		}
		return false;
	}

	@Override
	public boolean refund(List<RefundParam> params) {
		if (profile == null) {
			log.error("初始化addEndpoint出错!");
		}
		for (RefundParam param : params) {
			try {
				IAcsClient acsClient = new DefaultAcsClient(profile);
				
				JSONObject json = new JSONObject();
				json.put("order", param.getOrderNo());
				json.put("amount", param.getAmount());
				SendSmsRequest request = buildData(template_delivery, param.getMobile(), json.toJSONString());

				log.info(messagetype+"sendSmsRequest>" + JSONObject.toJSONString(request));
				SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
				log.info(messagetype+"sendSmsResponse>" + JSONObject.toJSONString(sendSmsResponse));
				if (sendSmsResponse != null) {
					return sendSmsResponse.getCode().equals("OK");
				}
			} catch (Exception e) {
				log.error("用户[" + param.getMobile() + "]推送[" + TemplateType.DELIVERY + "]消息失败！", e);
			}
		}
		return false;
	}

	public boolean goodsWarning(List<GoodsWarningParam> params) {
		if (profile == null) {
			log.error("初始化addEndpoint出错!");
		}
		for (GoodsWarningParam param : params) {
			try {
				IAcsClient acsClient = new DefaultAcsClient(profile);
				
				JSONObject json = new JSONObject();
				json.put("area", param.getArea());
				json.put("goods", param.getGoods());
				json.put("amount", param.getAmount());
				SendSmsRequest request = buildData(template_delivery, param.getMobile(), json.toJSONString());

				log.info(messagetype+"sendSmsRequest>" + JSONObject.toJSONString(request));
				SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
				log.info(messagetype+"sendSmsResponse>" + JSONObject.toJSONString(sendSmsResponse));
				if (sendSmsResponse != null) {
					return sendSmsResponse.getCode().equals("OK");
				}
			} catch (Exception e) {
				log.error("用户[" + param.getMobile() + "]推送[" + TemplateType.DELIVERY + "]消息失败！", e);
			}
		}
		return false;
	}

	@Override
	public boolean campaignSuccess(List<CampaignSuccessParam> param) {
		log.info(messagetype + "推送[" + TemplateType.CAMPAIGN_SUCCESS + "]暂未实现！");
		return false;
	}

	@Override
	public boolean closeOrder(List<CloseOrderParam> param) {
		log.info(messagetype + "推送[" + TemplateType.CLOSE_ORDER + "]暂未实现！");
		return false;
	}

	@Override
	public boolean campaignFailure(List<CampaignFailureParam> params) {
		log.info(messagetype + "推送[" + TemplateType.CAMPAIGN_FAILURE + "]暂未实现！");
		return false;
	}

	@Override
	public boolean deliverySuccess(List<DeliverySuccessParam> parames) {
		log.info(messagetype + "推送[" + TemplateType.DELIVERY_SUCCESS + "]暂未实现！");
		return false;
	}

	@Override
	public boolean orderPaySuccess(List<OrderPaySuccessParam> param) {
		log.info(messagetype + "推送[" + TemplateType.PAY_SUCCESS + "]暂未实现！");
		return false;
	}

	/**
	 * 构造阿里大鱼指定格式请求对象
	 */
	private SendSmsRequest buildData(String TemplateCode, String mobile, String jsonData) {
		SendSmsRequest request = new SendSmsRequest();
		request.setSignName(sms_sign_name);
		request.setTemplateCode(TemplateCode);
		request.setPhoneNumbers(mobile);
		request.setTemplateParam(jsonData);
		return request;
	}
}
