package com.dili.message.sdk.domain;

/**
 * 消息推送-取货成功参数（包括短信、公众号、小程序）
 * 
 * @author ：WangBo
 * @time ：2018年11月9日下午4:05:00
 */
public class DeliverySuccessParam extends BaseParam {
	/** 订单编号 */
	private String orderNo;
	/** 提货地点 */
	private String deliveryAddress;

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

}