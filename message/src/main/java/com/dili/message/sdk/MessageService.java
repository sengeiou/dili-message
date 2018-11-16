package com.dili.message.sdk;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.dili.message.sdk.domain.CampaignFailureParam;
import com.dili.message.sdk.domain.CampaignSuccessParam;
import com.dili.message.sdk.domain.CloseOrderParam;
import com.dili.message.sdk.domain.DeliveryParam;
import com.dili.message.sdk.domain.DeliverySuccessParam;
import com.dili.message.sdk.domain.GoodsWarningParam;
import com.dili.message.sdk.domain.OrderPaySuccessParam;
import com.dili.message.sdk.domain.RefundParam;
import com.dili.message.sdk.service.mp.MpImpl;
import com.dili.message.sdk.service.sms.AlidayuSmsImpl;
import com.dili.message.sdk.service.weapp.WeappImpl;
import com.dili.message.sdk.type.MessageType;
import com.dili.message.sdk.type.TemplateType;

/**
 * @description： 消息推送
 * 
 * @author ：WangBo
 * @time ：2018年11月9日下午4:29:53
 */
@Component
@Scope("singleton")
public class MessageService {
	private static ExecutorService pool;
	@Resource
	AlidayuSmsImpl alidayuSmsImpl;
	@Resource
	WeappImpl weappImpl;
	@Resource
	MpImpl mpImpl;

	@PostConstruct
	private void init() {
		int processors = Runtime.getRuntime().availableProcessors();
		pool = new ThreadPoolExecutor(processors, 100, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
	}

	/**
	 * 取货通知
	 */
	public void delivery(DeliveryParam param, MessageType... type) {
		executeWork(JSONObject.toJSONString(param), TemplateType.DELIVERY, type);

	}

	/**
	 * 开团成功提醒
	 */
	public void campaignSuccess(CampaignSuccessParam param, MessageType... type) {
		executeWork(JSONObject.toJSONString(param), TemplateType.CAMPAIGN_SUCCESS, type);
	}

	/**
	 * 退款通知
	 */
	public void refund(RefundParam param, MessageType... type) {
		executeWork(JSONObject.toJSONString(param), TemplateType.REFUND, type);
	}

	/**
	 * 订单关闭提醒
	 */
	public void closeOrder(CloseOrderParam param, MessageType... type) {
		executeWork(JSONObject.toJSONString(param), TemplateType.CLOSE_ORDER, type);
	}

	/**
	 * 团购失败通知
	 */
	public void campaignFailure(CampaignFailureParam param, MessageType... type) {
		executeWork(JSONObject.toJSONString(param), TemplateType.CAMPAIGN_FAILURE, type);
	}

	/**
	 * 取货成功
	 */
	public void deliverySuccess(DeliverySuccessParam param, MessageType... type) {
		executeWork(JSONObject.toJSONString(param), TemplateType.DELIVERY_SUCCESS, type);
	}

	/**
	 * 订单支付成功通知
	 */
	public void orderPaySuccess(OrderPaySuccessParam param, MessageType... type) {
		executeWork(JSONObject.toJSONString(param), TemplateType.PAY_SUCCESS, type);
	}

	/**
	 * 商品告警通知
	 */
	public void goodsWarning(GoodsWarningParam param, MessageType... type) {
		executeWork(JSONObject.toJSONString(param), TemplateType.GOODS_WARNING, type);
	}

	private void executeWork(String paramJsonStr, TemplateType templateType, MessageType... type) {
		for (MessageType messageType : type) {
			if (messageType == MessageType.SMS) {
				SendMessageWork sendWork = new SendMessageWork(paramJsonStr, templateType, alidayuSmsImpl);
				pool.execute(sendWork);
			} else if (messageType == MessageType.WEAPP) {
				SendMessageWork sendWork = new SendMessageWork(paramJsonStr, templateType, weappImpl);
				pool.execute(sendWork);
			} else if (messageType == MessageType.MP) {
				SendMessageWork sendWork = new SendMessageWork(paramJsonStr, templateType, mpImpl);
				pool.execute(sendWork);
			}
		}
	}
}
