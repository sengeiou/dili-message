package com.dili.message.sdk.service.mp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.dili.message.sdk.domain.DeliveryParam;
import com.dili.message.sdk.domain.OrderPaySuccessParam;
import com.dili.message.sdk.domain.RefundParam;
import com.dili.message.sdk.domain.ReturnApplyParam;
import com.dili.message.sdk.service.IMessageService;
import com.dili.message.sdk.service.weapp.WeappImpl;
import com.dili.message.sdk.type.TemplateType;

import okhttp3.Response;

/**
 * @description： 公众号消息推送实现发送 <br>
 * <i>keywaord类变量名由公众号模板定义，其它变量名由推送消息接口定义
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

	public String mpAppId = "wx9fb157a2c5c18c81";
	public String appsecret ="0e94c851320ec3a17cd19ac009dec280";
	
///支持的模板
	/** 取货通知 */
	public String template_delivery ="UIODWv8AOhuZCKM_dQWfT35Su0-96XYEvykAbEHMcOQ";
	/** 退款通知*/
	public String template_refund = "d6Dnq4j0qWrIJePX8_3ChOrThQvmEYfVy4oNHImCITM";
	/** 支付成功*/
	public String template_orderPaySuccess="EYRqYct2xYs_MQXhPDhvTPU-smeEjsW7ZxdNl3sN2Ko";
	/** 退款申请*/
	public String template_returnApply = "r71u8jR4qnr-cPo2E5jrjuQWQC-pShUb_09NlHQ-Y0E";
	
	@Override
	public boolean delivery(List<DeliveryParam> params) {
		boolean ret = false;
		try {
			for (DeliveryParam param : params) {
				Map<String, TemplateParam> dataMap = new HashMap<String, TemplateParam>();
				dataMap.put("first", new TemplateParam(param.getFirst()));
				dataMap.put("keyword1", new TemplateParam(param.getOrderNo()));
				dataMap.put("keyword2", new TemplateParam(param.getCreateOrderTime()));
				dataMap.put("keyword3", new TemplateParam(param.getOrderState()));
				dataMap.put("keyword4", new TemplateParam(param.getDeliveryAddress()));
				dataMap.put("remark", new TemplateParam(param.getRemark()));

				HashMap<String, Object> sendParam = new HashMap<String, Object>();
				sendParam.put("touser", param.getOpenId());
				sendParam.put("mp_template_msg", buildParam(template_delivery, param.getPage(), dataMap));
				ret = sendParam(sendParam,TemplateType.DELIVERY);
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
				dataMap.put("first", new TemplateParam(param.getFirst()));
				dataMap.put("keyword1", new TemplateParam(param.getOrderNo()));
				dataMap.put("keyword2", new TemplateParam(param.getAmount()));
				dataMap.put("keyword3", new TemplateParam(param.getRefundState()));
				dataMap.put("remark", new TemplateParam(param.getRemark()));

				HashMap<String, Object> sendParam = new HashMap<String, Object>();
				sendParam.put("touser", param.getOpenId());
				sendParam.put("mp_template_msg", buildParam(template_refund, param.getPage(), dataMap));
				ret = sendParam(sendParam,TemplateType.REFUND);
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
				dataMap.put("first", new TemplateParam(param.getFirst()));
				dataMap.put("keyword1", new TemplateParam(param.getOrderNo()));
				dataMap.put("keyword2", new TemplateParam(param.getOrderAmount()));
				dataMap.put("remark", new TemplateParam(param.getRemark()));

				HashMap<String, Object> sendParam = new HashMap<String, Object>();
				sendParam.put("touser", param.getOpenId());
				sendParam.put("mp_template_msg", buildParam(template_orderPaySuccess, param.getPage(), dataMap));
				ret = sendParam(sendParam,TemplateType.PAY_SUCCESS);
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
				dataMap.put("first", new TemplateParam(param.getFirst()));
				dataMap.put("keyword1", new TemplateParam(param.getOrderNo()));
				dataMap.put("keyword2", new TemplateParam(param.getProductQuantity()));
				dataMap.put("keyword3", new TemplateParam(param.getAmount()));
				dataMap.put("remark", new TemplateParam(param.getRemark()));

				HashMap<String, Object> sendParam = new HashMap<String, Object>();
				sendParam.put("touser", param.getOpenId());
				sendParam.put("mp_template_msg", buildParam(template_returnApply, param.getPage(), dataMap));
				ret = sendParam(sendParam,TemplateType.RETURN_APPLY);
			}
		} catch (Exception e) {
			log.error(messagetype + "推送[" + TemplateType.RETURN_APPLY + "]异常！", e);
		}
		return ret;
	}

	/**
	 * 构造参数 <br>
	 * <i> 按小程序和公众号统一的服务消息接口参数格式构造
	 * 
	 * @param templateId
	 * @param page
	 *            点击时需要跳转的页面
	 * @param data
	 *            模板中的参数(keyword1,keyworkd2...)
	 * @return
	 */
	public HashMap<String, Object> buildParam(String templateId, String page, Map<String, TemplateParam> data) {
		HashMap<String, Object> mpParam = new HashMap<String, Object>();
		mpParam.put("template_id", templateId);
		mpParam.put("appid", mpAppId);
		mpParam.put("url", "http://weixin.qq.com/download");
		mpParam.put("data", data);

		JSONObject miniprogram = new JSONObject();
		miniprogram.put("appid", WeappImpl.appId);
		miniprogram.put("path", page);
		mpParam.put("miniprogram", miniprogram);
		return mpParam;
	}

	/**
	 * 向小程序和公众号统一的服务消息接口推送消息 <br>
	 * <i>API地址：https://developers.weixin.qq.com/miniprogram/dev/api/open-api/uniform-message/sendUniformMessage.html
	 * 
	 * @param data
	 *            统一服务消息接口参数
	 * @return
	 */
	private boolean sendParam(HashMap<String, Object> data,TemplateType templateType) {
		// 发送模板消息
		String sendMessageUrl = UrlConstants.SEND_UNIFORM_MESSAGE + accessTokenUtil.getToken();
		String jsonData = JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect);
		log.info(messagetype + "推送[" + templateType + "]access_token[" + accessTokenUtil.getToken() + "]requestData>" + jsonData);
		Response sendResponse;
		try {
			sendResponse = OkHttpUtils.postString().content(jsonData).url(sendMessageUrl).build().connTimeOut(1000L * 60L * 60L * 3)
					.readTimeOut(1000L * 60L * 60L * 3).writeTimeOut(1000L * 60L * 60L * 3).execute();
			String sendResponseString = sendResponse.body().string();
			log.info(messagetype+"推送[" +templateType+ "]response>" + sendResponseString);
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
