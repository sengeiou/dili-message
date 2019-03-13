package com.dili.message.sdk.service.weapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.dili.message.sdk.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dili.http.okhttp.OkHttpUtils;
import com.dili.message.sdk.common.AccessTokenUtil;
import com.dili.message.sdk.common.TemplateParam;
import com.dili.message.sdk.constants.UrlConstants;
import com.dili.message.sdk.service.IMessageService;
import com.dili.message.sdk.type.TemplateType;

import okhttp3.Response;

/**
 * @description： 小程序消息推送实现 <br>
 * <i>keywaord类变量名由小程序模板定义，其它变量名由推送消息接口定义
 * 
 * @author ：WangBo
 * @time ：2018年11月9日上午10:50:06
 */
@Component
public class WeappImpl implements IMessageService {
	Logger log = LoggerFactory.getLogger(WeappImpl.class);
	private static String messagetype = "小程序";

	public static final String appId = "wxd1405e5c40ff05db";
	public static final String appsecret = "2c34b95ab3a5ff4f77763ba7931dcc4d";

	//////支持的模板
	/** 支付成功  */
	private static final String template_orderPaySuccess = "NnIfV5AxznoLITNCa-XgJUQvojINXY3Jt0SswVUojjg";
	/** 取货通知 */
	private static final String template_delivery = "Ma---ZWwg-5ch18I3WcCCqYm-Cgpa3AP3PIZvGzwsuE";
	/** 退款通知 */
	private static final String template_refund = "w7v4dZl2gD1fI_0nEeJNFlU_tg62Q7lsmGWsZouDbbc";
	/** 退款申请*/
	private static final String template_returnApply = "iZvXIS3pePWp8-lCvdY6siHrN95YPabvwXDbcYX2otI";
	/** 审核结果通知*/
	private static final String template_auditResultNotice = "2tigCO3h9xQNnrfW2ShbxmOlKn_R-nUg2ZOEk19UoPw";

	@Resource
	private AccessTokenUtil accessTokenUtil;

	@Override
	public boolean delivery(List<DeliveryParam> params) {
		boolean ret = false;
		try {
			for (DeliveryParam param : params) {
				Map<String, TemplateParam> dataMap = new HashMap<String, TemplateParam>();
				dataMap.put("keyword1", new TemplateParam(param.getOrderNo()));
				dataMap.put("keyword2", new TemplateParam(param.getDeliveryAddress()));
				dataMap.put("keyword3", new TemplateParam(param.getRemark()));
				HashMap<String, Object> weappParam = buildParam(template_delivery, param.getPage(), param.getFormOrPayId(), dataMap);

				HashMap<String, Object> sendParam = new HashMap<String, Object>();
				sendParam.put("touser", param.getOpenId());
				sendParam.put("weapp_template_msg", weappParam);
				ret = sendParam(sendParam, TemplateType.DELIVERY);
			}
		} catch (Exception e) {
			log.error(messagetype + "推送[" + TemplateType.DELIVERY + "]异常！", e);
		}
		return ret;
	}

	@Override
	public boolean refund(List<RefundParam> params) {
		boolean ret = false;
		try {
			for (RefundParam param : params) {
				Map<String, TemplateParam> dataMap = new HashMap<String, TemplateParam>();
				dataMap.put("keyword1", new TemplateParam(param.getOrderNo()));
				dataMap.put("keyword2", new TemplateParam(param.getTime()));
				dataMap.put("keyword3", new TemplateParam(param.getAmount()));
				dataMap.put("keyword4", new TemplateParam(param.getRemark()));
				HashMap<String, Object> weappParam = buildParam(template_refund, param.getPage(), param.getFormOrPayId(), dataMap);

				HashMap<String, Object> sendParam = new HashMap<String, Object>();
				sendParam.put("touser", param.getOpenId());
				sendParam.put("weapp_template_msg", weappParam);
				ret = sendParam(sendParam, TemplateType.REFUND);
			}
		} catch (Exception e) {
			log.error(messagetype + "推送[" + TemplateType.REFUND + "]异常！", e);
		}
		return ret;
	}

	@Override
	public boolean orderPaySuccess(List<OrderPaySuccessParam> params) {
		boolean ret = false;
		try {
			for (OrderPaySuccessParam param : params) {
				Map<String, TemplateParam> dataMap = new HashMap<String, TemplateParam>();
				dataMap.put("keyword1", new TemplateParam(param.getOrderNo()));
				dataMap.put("keyword2", new TemplateParam(param.getOrderAmount()));
				dataMap.put("keyword3", new TemplateParam(param.getDeliveryAddress()));
				dataMap.put("keyword4", new TemplateParam(param.getRemark()));
				HashMap<String, Object> weappParam = buildParam(template_orderPaySuccess, param.getPage(), param.getFormOrPayId(), dataMap);

				HashMap<String, Object> sendParam = new HashMap<String, Object>();
				sendParam.put("touser", param.getOpenId());
				sendParam.put("weapp_template_msg", weappParam);
				ret = sendParam(sendParam, TemplateType.PAY_SUCCESS);
			}
		} catch (Exception e) {
			log.error(messagetype + "推送[" + TemplateType.PAY_SUCCESS + "]异常！", e);
		}
		return ret;
	}

	@Override
	public boolean returnApply(List<ReturnApplyParam> params) {
		boolean ret = false;
		try {
			for (ReturnApplyParam param : params) {
				Map<String, TemplateParam> dataMap = new HashMap<String, TemplateParam>();
				dataMap.put("keyword1", new TemplateParam(param.getOrderNo()));
				dataMap.put("keyword2", new TemplateParam(param.getAmount()));
				dataMap.put("keyword3", new TemplateParam(param.getTime()));
				dataMap.put("keyword4", new TemplateParam(param.getRefundDesc()));
				HashMap<String, Object> weappParam = buildParam(template_returnApply, param.getPage(), param.getFormOrPayId(), dataMap);

				HashMap<String, Object> sendParam = new HashMap<String, Object>();
				sendParam.put("touser", param.getOpenId());
				sendParam.put("weapp_template_msg", weappParam);
				ret = sendParam(sendParam, TemplateType.RETURN_APPLY);
			}
		} catch (Exception e) {
			log.error(messagetype + "推送[" + TemplateType.RETURN_APPLY + "]异常！", e);
		}
		return ret;
	}

	@Override
	public boolean auditResultNotice(List<AuditResultNoticeParam> params) {
		boolean ret = false;
		try {
			for (AuditResultNoticeParam param : params) {
				Map<String, TemplateParam> dataMap = new HashMap<String, TemplateParam>();
				dataMap.put("keyword1", new TemplateParam(param.getAuditResult()));
				dataMap.put("keyword2", new TemplateParam(param.getAuditTime()));
				dataMap.put("keyword3", new TemplateParam(param.getApplyNumber()));
				dataMap.put("keyword4", new TemplateParam(param.getApplyTime()));
				HashMap<String, Object> weappParam = buildParam(template_auditResultNotice, param.getPage(), param.getFormOrPayId(), dataMap);

				HashMap<String, Object> sendParam = new HashMap<String, Object>();
				sendParam.put("touser", param.getOpenId());
				sendParam.put("weapp_template_msg", weappParam);
				ret = sendParam(sendParam, TemplateType.AUDIT_RESULT_NOTICE);
			}
		} catch (Exception e) {
			log.error(messagetype + "推送[" + TemplateType.AUDIT_RESULT_NOTICE + "]异常！", e);
		}
		return ret;
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
		weappParam.put("template_id", templateId);
		weappParam.put("form_id", fromOrPayId);
		weappParam.put("page", page);
		weappParam.put("data", data);
		return weappParam;
	}

	/**
	 * 向小程序和公众号统一的服务消息接口推送消息 <br>
	 * <i>API地址：https://developers.weixin.qq.com/miniprogram/dev/api/open-api/uniform-message/sendUniformMessage.html
	 * 
	 * @param data
	 *            统一服务消息接口参数
	 * @return
	 */
	private boolean sendParam(HashMap<String, Object> data, TemplateType templateType) {
		// 发送模板消息
		String sendMessageUrl = UrlConstants.SEND_UNIFORM_MESSAGE + accessTokenUtil.getToken();
		String jsonData = JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect);
		log.info(messagetype + "推送[" + templateType + "]access_token[" + accessTokenUtil.getToken() + "]requestData>" + jsonData);
		Response sendResponse;
		try {
			sendResponse = OkHttpUtils.postString().content(jsonData).url(sendMessageUrl).build().connTimeOut(1000L * 60L * 60L * 3)
					.readTimeOut(1000L * 60L * 60L * 3).writeTimeOut(1000L * 60L * 60L * 3).execute();
			String sendResponseString = sendResponse.body().string();
			log.info(messagetype + "推送[" + templateType + "]response>" + sendResponseString);
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
