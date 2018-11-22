package com.dili.message.sdk.service;

import java.util.List;

import com.dili.message.sdk.domain.CampaignFailureParam;
import com.dili.message.sdk.domain.CampaignSuccessParam;
import com.dili.message.sdk.domain.CloseOrderParam;
import com.dili.message.sdk.domain.DeliveryParam;
import com.dili.message.sdk.domain.DeliverySuccessParam;
import com.dili.message.sdk.domain.GoodsWarningParam;
import com.dili.message.sdk.domain.OrderPaySuccessParam;
import com.dili.message.sdk.domain.RefundParam;

/**
 * @description： 消息类型接口
 * 
 * @author ：WangBo
 * @time ：2018年11月9日上午11:09:52
 */
public interface IMessageService {
	/**
	 * 取货通知
	 * 
	 * @return 单条数据可以使用，多条数据意义不大
	 */
	public boolean delivery(List<DeliveryParam> param);

	/**
	 * 开团成功提醒
	 * 
	 * @return 单条数据可以使用，多条数据意义不大
	 */
	public boolean campaignSuccess(List<CampaignSuccessParam> param);

	/**
	 * 退款通知
	 * 
	 * @return 单条数据可以使用，多条数据意义不大
	 */
	public boolean refund(List<RefundParam> param);

	/**
	 * 订单关闭提醒
	 * 
	 * @return 单条数据可以使用，多条数据意义不大
	 */
	public boolean closeOrder(List<CloseOrderParam> param);

	/**
	 * 团购失败通知
	 * 
	 * @return 单条数据可以使用，多条数据意义不大
	 */
	public boolean campaignFailure(List<CampaignFailureParam> param);

	/**
	 * 取货成功
	 * 
	 * @return 单条数据可以使用，多条数据意义不大
	 */
	public boolean deliverySuccess(List<DeliverySuccessParam> param);

	/**
	 * 订单支付成功通知
	 * 
	 * @return 单条数据可以使用，多条数据意义不大
	 */
	public boolean orderPaySuccess(List<OrderPaySuccessParam> param);

	/**
	 * 商品可用量告警
	 * 
	 * @return 单条数据可以使用，多条数据意义不大
	 */
	public boolean goodsWarning(List<GoodsWarningParam> param);

}
