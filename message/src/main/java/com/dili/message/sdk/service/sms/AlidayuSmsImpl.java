package com.dili.message.sdk.service.sms;

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
	Logger log = LoggerFactory.getLogger(AlidayuSmsImpl.class);
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
	@Value("${alidayu.sms.templateid.refunds}")
	public String template_refunds;

	@Override
	public boolean delivery(DeliveryParam param) {
		try {
			// 可自助调整超时时间
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");

			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", app_key, secret);

			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
			IAcsClient acsClient = new DefaultAcsClient(profile);

			SendSmsRequest request = new SendSmsRequest();
			request.setSignName(sms_sign_name);
			request.setTemplateCode(template_delivery);
			request.setPhoneNumbers(param.getMobile());
			JSONObject json = new JSONObject();
			json.put("order", param.getOrderNo());
			json.put("date", param.getDeliveryTime());
			json.put("code", param.getDeliveryCode());
			json.put("Shop", param.getDeliveryAddress());
			request.setTemplateParam(json.toJSONString());

			SendSmsResponse sendSmsResponse = null;
			log.info("sendSmsRequest>" + JSONObject.toJSONString(request));
			sendSmsResponse = acsClient.getAcsResponse(request);
			log.info("sendSmsResponse>" + JSONObject.toJSONString(sendSmsResponse));
			if (sendSmsResponse != null) {
				return sendSmsResponse.getCode().equals("OK");
			} else {
				return false;
			}
		} catch (ClientException e) {
			log.error("用户[" + param.getMobile() + "]推送[" + TemplateType.DELIVERY + "]消息失败！", e);
			return false;
		}
	}

	@Override
	public boolean refund(RefundParam param) {
		try {
			// 可自助调整超时时间
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");

			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", app_key, secret);

			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
			IAcsClient acsClient = new DefaultAcsClient(profile);

			SendSmsRequest request = new SendSmsRequest();
			request.setSignName(sms_sign_name);
			request.setTemplateCode(app_key);
			request.setPhoneNumbers(param.getMobile());
			JSONObject json = new JSONObject();
			json.put("order", param.getRefundsNo());
			json.put("amount", param.getAmount());
			request.setTemplateParam(json.toJSONString());

			SendSmsResponse sendSmsResponse = null;
			sendSmsResponse = acsClient.getAcsResponse(request);
			System.out.println("sendSmsResponse>" + JSONObject.toJSONString(sendSmsResponse));
			if (sendSmsResponse != null) {
				return sendSmsResponse.getCode().equals("OK");
			} else {
				return false;
			}
		} catch (ClientException e) {                                    
			log.error("用户[" + param.getMobile() + "]推送[" + TemplateType.REFUND + "]消息失败！", e);
			return false;
		}
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
