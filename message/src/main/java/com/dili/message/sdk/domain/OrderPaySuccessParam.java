package com.dili.message.sdk.domain;

/**
 * 消息推送-订单支付成功参数
 * 
 * @author ：WangBo
 * @time ：2018年11月9日下午4:06:11
 */
public class OrderPaySuccessParam extends BaseParam {
	/** 订单编号 */
	private String orderNo;
	/** 提货地点 */
	private String deliveryAddress;
	/** 订单金额 */
	private String orderAmount;

	/** 订单编号 */
	public String getOrderNo() {
		return orderNo;
	}

	/** 订单编号 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/** 提货地点 */
	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	/** 提货地点 */
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	/** 订单金额 */
	public String getOrderAmount() {
		return orderAmount;
	}

	/** 订单金额 */
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

}