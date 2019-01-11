package com.dili.message.sdk;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.dili.message.sdk.common.AccessTokenUtil;
import com.dili.message.sdk.domain.CampaignFailureParam;
import com.dili.message.sdk.domain.CampaignSuccessParam;
import com.dili.message.sdk.domain.CloseOrderParam;
import com.dili.message.sdk.domain.DeliveryParam;
import com.dili.message.sdk.domain.DeliverySuccessParam;
import com.dili.message.sdk.domain.GoodsWarningParam;
import com.dili.message.sdk.domain.OrderPaySuccessParam;
import com.dili.message.sdk.domain.RefundParam;
import com.dili.message.sdk.domain.VerificationCodeParam;
import com.dili.message.sdk.service.mp.MpImpl;
import com.dili.message.sdk.service.sms.AlidayuSmsImpl;
import com.dili.message.sdk.service.weapp.WeappImpl;
import com.dili.message.sdk.task.SendMessageWork;
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
	@Resource
	AccessTokenUtil accessTokenUtil;
	Logger log = LoggerFactory.getLogger(MessageService.class);

	@PostConstruct
	private void init() {
		// 初始化消息推送线程池
		try {
			int processors = Runtime.getRuntime().availableProcessors();
			pool = new ThreadPoolExecutor(processors, 100, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
		} catch (Exception e) {
			log.error("初始化推送线程池出错.", e);
		}
	}
	/**
	 * 短信验证码
	 */
	public void verificationCode(VerificationCodeParam param, MessageType... type) {
		List<VerificationCodeParam> list = new ArrayList<VerificationCodeParam>();
		list.add(param);
		executeWork(JSONObject.toJSONString(list), TemplateType.VERIFICATION_CODE, type);
	}
	/**
	 * 取货通知
	 */
	public void delivery(DeliveryParam param, MessageType... type) {
		List<DeliveryParam> list = new ArrayList<DeliveryParam>();
		list.add(param);
		executeWork(JSONObject.toJSONString(list), TemplateType.DELIVERY, type);

	}


	/**
	 * 退款通知
	 */
	public void refund(RefundParam param, MessageType... type) {
		List<RefundParam> list = new ArrayList<RefundParam>();
		list.add(param);
		executeWork(JSONObject.toJSONString(list), TemplateType.REFUND, type);
	}



	/**
	 * 订单支付成功通知
	 */
	public void orderPaySuccess(OrderPaySuccessParam param, MessageType... type) {
		List<OrderPaySuccessParam> list = new ArrayList<OrderPaySuccessParam>();
		list.add(param);
		executeWork(JSONObject.toJSONString(list), TemplateType.PAY_SUCCESS, type);
	}

	/**
	 * 商品告警通知
	 */
	public void goodsWarning(GoodsWarningParam param, MessageType... type) {
		List<GoodsWarningParam> list = new ArrayList<GoodsWarningParam>();
		list.add(param);
		executeWork(JSONObject.toJSONString(list), TemplateType.GOODS_WARNING, type);
	}

	/**
	 * 取货通知
	 */
	public void delivery(List<DeliveryParam> param, MessageType... type) {
		executeWork(JSONObject.toJSONString(param), TemplateType.DELIVERY, type);

	}

	/**
	 * 退款通知
	 */
	public void refund(List<RefundParam> param, MessageType... type) {
		executeWork(JSONObject.toJSONString(param), TemplateType.REFUND, type);
	}


	/**
	 * 订单支付成功通知
	 */
	public void orderPaySuccess(List<OrderPaySuccessParam> param, MessageType... type) {
		executeWork(JSONObject.toJSONString(param), TemplateType.PAY_SUCCESS, type);
	}

	/**
	 * 商品告警通知
	 */
	public void goodsWarning(List<GoodsWarningParam> param, MessageType... type) {
		executeWork(JSONObject.toJSONString(param), TemplateType.GOODS_WARNING, type);
	}
	/**
	 * 短信验证码
	 */
	public void verificationCode(List<VerificationCodeParam> param, MessageType... type) {
		executeWork(JSONObject.toJSONString(param), TemplateType.VERIFICATION_CODE, type);
	}
	
	
	/**
	 * 异步推送消息
	 * 
	 * @param paramJsonStr
	 *            消息模板参数
	 * @param templateType
	 *            消息类型
	 * @param type
	 *            推送方式
	 */
	private void executeWork(String paramJsonStr, TemplateType templateType, MessageType... type) {
		if (StringUtils.isBlank(paramJsonStr) || "[null]".equals(paramJsonStr)) {
			log.error("[" + templateType.getName() + "]推送失败，参数为null");
			return;
		}

		for (MessageType messageType : type) {
			if (messageType == MessageType.WEAPP || messageType == MessageType.MP) {
				// 初始化获取token线程池
				accessTokenUtil.initGetTokenWork();
				break;
			}
		}
		if (pool != null) {
			for (MessageType messageType : type) {
				if (messageType == MessageType.SMS) {
					pool.execute(new SendMessageWork(paramJsonStr, templateType, alidayuSmsImpl));
				} else if (messageType == MessageType.WEAPP) {
					pool.execute(new SendMessageWork(paramJsonStr, templateType, weappImpl));
				} else if (messageType == MessageType.MP) {
					pool.execute(new SendMessageWork(paramJsonStr, templateType, mpImpl));
				}
			}
		}
	}
}
