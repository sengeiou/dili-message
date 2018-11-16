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
		if (templateType == TemplateType.DELIVERY) {
			//取货
			DeliveryParam sendParam = JSONObject.parseObject(param, DeliveryParam.class);
			messageService.delivery(sendParam);
		} else if (templateType == TemplateType.CAMPAIGN_FAILURE) {
			//团购失败
			CampaignFailureParam sendParam = JSONObject.parseObject(param, CampaignFailureParam.class);
			messageService.campaignFailure(sendParam);
		} else if (templateType == TemplateType.CAMPAIGN_SUCCESS) {
			//团购成功
			CampaignSuccessParam sendParam = JSONObject.parseObject(param, CampaignSuccessParam.class);
			messageService.campaignSuccess(sendParam);
		} else if (templateType == TemplateType.CLOSE_ORDER) {
			//关闭订单
			CloseOrderParam sendParam = JSONObject.parseObject(param, CloseOrderParam.class);
			messageService.closeOrder(sendParam);
		} else if (templateType == TemplateType.DELIVERY_SUCCESS) {
			//取货成功
			DeliverySuccessParam sendParam = JSONObject.parseObject(param, DeliverySuccessParam.class);
			messageService.deliverySuccess(sendParam);
		} else if (templateType == TemplateType.PAY_SUCCESS) {
			//支付成功
			OrderPaySuccessParam sendParam = JSONObject.parseObject(param, OrderPaySuccessParam.class);
			messageService.orderPaySuccess(sendParam);
		} else if (templateType == TemplateType.REFUND) {
			//退款
			RefundParam sendParam = JSONObject.parseObject(param, RefundParam.class);
			messageService.refund(sendParam);
		} else if (templateType == TemplateType.GOODS_WARNING) {
			//商品可用量告警
			GoodsWarningParam sendParam = JSONObject.parseObject(param, GoodsWarningParam.class);
			messageService.goodsWarning(sendParam);
		}
	}

}
