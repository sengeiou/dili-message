package com.dili.message.sdk;

import com.alibaba.fastjson.JSONObject;
import com.dili.message.sdk.domain.DeliveryParam;
import com.dili.message.sdk.service.IMessageService;
import com.dili.message.sdk.type.TemplateType;

/**
 * @description：
 * 
 * @author ：WangBo
 * @time ：2018年11月12日上午10:33:39
 */
public class SendMessageWork implements Runnable {
	private String param;
	private TemplateType templateType;
	private IMessageService messageService;

	public SendMessageWork(String param, TemplateType templateType, IMessageService messageService) {
		this.param = param;
		this.templateType = templateType;
		this.messageService = messageService;
	}

	@Override
	public void run() {
		if (templateType == TemplateType.DELIVERY) {
			DeliveryParam sendParam = JSONObject.parseObject(param, DeliveryParam.class);
			messageService.delivery(sendParam);
		}
	}

}
