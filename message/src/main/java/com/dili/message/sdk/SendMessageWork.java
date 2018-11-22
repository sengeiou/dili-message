package com.dili.message.sdk;

import com.alibaba.fastjson.JSONObject;
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
 * @description： 消息类型分发工作线程
 * 
 * @author ：WangBo
 * @time ：2018年11月12日上午10:33:39
 */
public class SendMessageWork implements Runnable {
	private String param;
	private TemplateType templateType;
	private IMessageService messageService;

	/**
	 * @param param
	 *            消息参数
	 * @param templateType
	 *            消息类型
	 * @param messageService
	 *            推送方式
	 */
	public SendMessageWork(String param, TemplateType templateType, IMessageService messageService) {
		this.param = param;
		this.templateType = templateType;
		this.messageService = messageService;
	}

	@Override
	public void run() {
		if (templateType == TemplateType.DELIVERY) {// 取货
			messageService.delivery(JSONObject.parseArray(param, DeliveryParam.class));

		} else if (templateType == TemplateType.CAMPAIGN_FAILURE) {// 团购失败
			messageService.campaignFailure(JSONObject.parseArray(param, CampaignFailureParam.class));

		} else if (templateType == TemplateType.CAMPAIGN_SUCCESS) {// 团购成功
			messageService.campaignSuccess(JSONObject.parseArray(param, CampaignSuccessParam.class));

		} else if (templateType == TemplateType.CLOSE_ORDER) {// 关闭订单
			messageService.closeOrder(JSONObject.parseArray(param, CloseOrderParam.class));

		} else if (templateType == TemplateType.DELIVERY_SUCCESS) {// 取货成功
			messageService.deliverySuccess(JSONObject.parseArray(param, DeliverySuccessParam.class));

		} else if (templateType == TemplateType.PAY_SUCCESS) {// 支付成功
			messageService.orderPaySuccess(JSONObject.parseArray(param, OrderPaySuccessParam.class));

		} else if (templateType == TemplateType.REFUND) {// 退款
			messageService.refund(JSONObject.parseArray(param, RefundParam.class));

		} else if (templateType == TemplateType.GOODS_WARNING) {// 商品可用量告警
			messageService.goodsWarning(JSONObject.parseArray(param, GoodsWarningParam.class));
		}
	}
}
