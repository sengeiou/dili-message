package com.dili.message.sdk.domain;

/**
 * @author ：WangBo
 * @time ：2018年11月9日下午4:05:00
 */
public class DeliverySuccessParam {
	/** 商品信息 */
	private String productInfo;
	/** 订单号 */
	private String orderNo;
	/** 取件时间 */
	private String deliveryTime;
	/** 取货店铺 */
	private String deliveryShop;


	/** 商品信息 */
	public String getProductInfo() {
		return productInfo;
	}

	/** 商品信息 */
	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}

	/** 订单号 */
	public String getOrderNo() {
		return orderNo;
	}

	/** 订单号 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/** 取件时间 */
	public String getDeliveryTime() {
		return deliveryTime;
	}

	/** 取件时间 */
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	/** 取货店铺 */
	public String getDeliveryShop() {
		return deliveryShop;
	}

	/** 取货店铺 */
	public void setDeliveryShop(String deliveryShop) {
		this.deliveryShop = deliveryShop;
	}

}