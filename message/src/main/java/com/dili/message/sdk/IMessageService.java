package com.dili.message.sdk;

import com.dili.message.sdk.domain.CampaignFailureParam;
import com.dili.message.sdk.domain.CampaignSuccessParam;
import com.dili.message.sdk.domain.CloseOrderParam;
import com.dili.message.sdk.domain.DeliveryParam;
import com.dili.message.sdk.domain.DeliverySuccessParam;
import com.dili.message.sdk.domain.OrderPaySuccessParam;
import com.dili.message.sdk.domain.RefundsParam;

/**
 * @description：
 * 
 * @author ：WangBo
 * @time ：2018年11月9日上午11:09:52
 */
public interface IMessageService {
	/**
	 * 取货通知
	 */
	public boolean delivery(DeliveryParam param);


	/**
	 * 开团成功提醒
	 */
	public boolean campaignSuccess(CampaignSuccessParam param);



	/**
	 * 退款通知
	 */
	public boolean refunds(RefundsParam param);


	/**
	 * 订单关闭提醒
	 */
	public boolean closeOrder(CloseOrderParam param);


	/**
	 * 团购失败通知
	 */
	public boolean campaignFailure(CampaignFailureParam param);



	/**
	 * 取货成功 
	 */
	public boolean deliverySuccess(DeliverySuccessParam parameterObject);


	/**
	 * 订单支付成功通知
	 */
	public boolean orderPaySuccess(OrderPaySuccessParam param);
}
