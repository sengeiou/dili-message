package com.dili.message.sdk.service.sms;

import java.util.List;

import javax.annotation.PostConstruct;

import com.dili.message.sdk.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.dili.message.sdk.service.IMessageService;
import com.dili.message.sdk.type.TemplateType;

/**
 * @description： 阿里大鱼短信发送
 * <br><i>变量名由阿里大鱼模板定义
 * @author ：WangBo
 * @time ：2018年11月9日上午10:50:06
 */
@Component
public class AlidayuSmsImpl implements IMessageService {
	static Logger log = LoggerFactory.getLogger(AlidayuSmsImpl.class);
	private static String messagetype = "短信";

	private String app_key="LTAImzT8nS0nCKy2";
	private String secret ="Msz8vY30Fl8cspVBzuXVSTi5LMzAnw";
	private String sms_sign_name="地利生鲜";

	///支持的模板ID
	/** 取货通知 */
	private String template_delivery="SMS_150172881";
	/** 退款通知*/
	private String template_refund="SMS_150172896";
	/** 退款申请*/
	private String template_returnApply = "SMS_158051414";
	/** 商品可用量告警*/
	private String template_goodsWarning="SMS_150866557";
	/** 短信验证码*/
	private String template_verificationCode="SMS_154594680";
	/** 审核结果通知*/
	private String template_auditResultNotice="SMS_160270506";

	
	/** 阿里大鱼客户端配置*/
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
	public boolean refund(List<RefundParam> params) {
		if (profile == null) {
			log.error("初始化addEndpoint出错!");
			return false;
		}
		for (RefundParam param : params) {
			try {
				IAcsClient acsClient = new DefaultAcsClient(profile);
				
				JSONObject json = new JSONObject();
				json.put("order", param.getOrderNo());
				json.put("amount", param.getAmount());
				SendSmsRequest request = buildData(template_refund, param.getMobile(), json.toJSONString());

				log.info(messagetype+"推送["+TemplateType.REFUND+"]sendSmsRequest>" + JSONObject.toJSONString(request));
				SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
				log.info(messagetype+"推送["+TemplateType.REFUND+"]sendSmsResponse>" + JSONObject.toJSONString(sendSmsResponse));
				if (sendSmsResponse != null) {
					return sendSmsResponse.getCode().equals("OK");
				}
			} catch (Exception e) {
				log.error("用户[" + param.getMobile() + "]推送[" + TemplateType.REFUND + "]消息失败！", e);
			}
		}
		return false;
	}

	public boolean goodsWarning(List<GoodsWarningParam> params) {
		if (profile == null) {
			log.error("初始化addEndpoint出错!");
			return false;
		}
		for (GoodsWarningParam param : params) {
			try {
				IAcsClient acsClient = new DefaultAcsClient(profile);
				
				JSONObject json = new JSONObject();
				json.put("area", param.getArea());
				json.put("goods", param.getGoods());
				json.put("amount", param.getAmount());
				SendSmsRequest request = buildData(template_goodsWarning, param.getMobile(), json.toJSONString());

				log.info(messagetype+"推送["+TemplateType.GOODS_WARNING+"]sendSmsRequest>" + JSONObject.toJSONString(request));
				SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
				log.info(messagetype+"推送["+TemplateType.GOODS_WARNING+"]sendSmsResponse>" + JSONObject.toJSONString(sendSmsResponse));
				if (sendSmsResponse != null) {
					return sendSmsResponse.getCode().equals("OK");
				}
			} catch (Exception e) {
				log.error("用户[" + param.getMobile() + "]推送[" + TemplateType.GOODS_WARNING + "]消息失败！", e);
			}
		}
		return false;
	}


	@Override
	public boolean orderPaySuccess(List<OrderPaySuccessParam> param) {
		log.info(messagetype + "推送[" + TemplateType.PAY_SUCCESS + "]暂未实现！");
		return false;
	}

	@Override
	public boolean verificationCode(List<VerificationCodeParam> params) {
		if (profile == null) {
			log.error("初始化addEndpoint出错!");
			return false;
		}
		for (VerificationCodeParam param : params) {
			try {
				IAcsClient acsClient = new DefaultAcsClient(profile);
				
				JSONObject json = new JSONObject();
				json.put("code", param.getCode());
				json.put("expireTime", param.getExpireTime());
				SendSmsRequest request = buildData(template_verificationCode, param.getMobile(), json.toJSONString());

				log.info(messagetype+"推送["+TemplateType.VERIFICATION_CODE+"]sendSmsRequest>" + JSONObject.toJSONString(request));
				SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
				log.info(messagetype+"推送["+TemplateType.VERIFICATION_CODE+"]sendSmsResponse>" + JSONObject.toJSONString(sendSmsResponse));
				if (sendSmsResponse != null) {
					return sendSmsResponse.getCode().equals("OK");
				}
			} catch (Exception e) {
				log.error("用户[" + param.getMobile() + "]推送[" + TemplateType.VERIFICATION_CODE + "]消息失败！", e);
			}
		}
		return false;
	}
	@Override
	public boolean returnApply(List<ReturnApplyParam> params) {
		if (profile == null) {
			log.error("初始化addEndpoint出错!");
			return false;
		}
		for (ReturnApplyParam param : params) {
			try {
				IAcsClient acsClient = new DefaultAcsClient(profile);
				
				JSONObject json = new JSONObject();
				json.put("order", param.getOrderNo());
				SendSmsRequest request = buildData(template_returnApply, param.getMobile(), json.toJSONString());

				log.info(messagetype+"推送["+TemplateType.RETURN_APPLY+"]sendSmsRequest>" + JSONObject.toJSONString(request));
				SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
				log.info(messagetype+"推送["+TemplateType.RETURN_APPLY+"]sendSmsResponse>" + JSONObject.toJSONString(sendSmsResponse));
				if (sendSmsResponse != null) {
					return sendSmsResponse.getCode().equals("OK");
				}
			} catch (Exception e) {
				log.error("用户[" + param.getMobile() + "]推送[" + TemplateType.RETURN_APPLY + "]消息失败！", e);
			}
		}
		return false;
	}

	@Override
	public boolean auditResultNotice(List<AuditResultNoticeParam> params) {
		if (profile == null) {
			log.error("初始化addEndpoint出错!");
			return false;
		}
		for (AuditResultNoticeParam param : params) {
			try {
				IAcsClient acsClient = new DefaultAcsClient(profile);

				JSONObject json = new JSONObject();
				json.put("applyCode", param.getApplyNumber());
				SendSmsRequest request = buildData(template_auditResultNotice, param.getMobile(), json.toJSONString());

				log.info(messagetype+"推送["+TemplateType.AUDIT_RESULT_NOTICE+"]sendSmsRequest>" + JSONObject.toJSONString(request));
				SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
				log.info(messagetype+"推送["+TemplateType.AUDIT_RESULT_NOTICE+"]sendSmsResponse>" + JSONObject.toJSONString(sendSmsResponse));
				if (sendSmsResponse != null) {
					return sendSmsResponse.getCode().equals("OK");
				}
			} catch (Exception e) {
				log.error("用户[" + param.getMobile() + "]推送[" + TemplateType.AUDIT_RESULT_NOTICE + "]消息失败！", e);
			}
		}
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
