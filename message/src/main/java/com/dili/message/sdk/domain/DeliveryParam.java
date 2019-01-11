package com.dili.message.sdk.domain;

/**
 * @description： 消息推送-取货参数（包括短信、公众号、小程序）
 * 
 * @author ：WangBo
 * @time ：2018年11月9日下午3:49:39
 */
public class DeliveryParam extends BaseParam {
	/** 订单编号 */
	private String orderNo;
	/** 购买时间 */
	private String createOrderTime;
	/** 取货码 */
	private String orderState;
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

	/** 购买时间 */
	public String getCreateOrderTime() {
		return createOrderTime;
	}

	/** 购买时间 */
	public void setCreateOrderTime(String createOrderTime) {
		this.createOrderTime = createOrderTime;
	}

	/** 取货码 */
	public String getOrderState() {
		return orderState;
	}

	/** 取货码 */
	public void setOrderState(String orderState) {
		this.orderState = orderState;
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
