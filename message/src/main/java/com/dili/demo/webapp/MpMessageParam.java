package com.dili.demo.webapp;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * @description： 小程序和公众号统一的服务消息接口 公众号模板消息参数
 * 
 * @author ：WangBo
 * @time ：2018年11月7日上午9:24:13
 */
public class MpMessageParam {

	/**
	 * 
	 * @param accessToken
	 * @param openId
	 * @param templateId
	 * @param fromId
	 * @param page
	 * @param datas
	 * @return
	 */
	public static String buildSendParam(String accessToken, String openId, String templateId, String fromId, String page,
			Map<String, String>... datas) {
		JSONObject json = new JSONObject();
		json.put("access_token", accessToken);
		json.put("touser", openId); // 接收者（用户）的 openid
		json.put("template_id", templateId); // 所需下发的模板消息的id
		json.put("form_id", fromId); // 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的
		json.put("page", page);
		JSONObject dataParam = new JSONObject();
		for (int i = 0; i < datas.length; i++) {
			Map<String, String> map = datas[i];
			int keywordIndex = i + 1;
			dataParam.put("keyword" + keywordIndex, map);
		}
		return json.toJSONString();
	}
}
