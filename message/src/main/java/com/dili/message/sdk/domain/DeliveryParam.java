package com.dili.message.sdk.domain;

/**
 * @description：
 * 			消息推送-取货参数
 * @author ：WangBo
 * @time ：2018年11月9日下午3:49:39
 */
public class DeliveryParam {
	/** 取货码 */
	private String deliveryCode;
	/** 商品详情 */
	private String productItemInfo;
	/** 取货地点 */
	private String deliveryAddress;
	/** 门店 */
	private String shopName;
	/** 营业时间 */
	private String businessHours;
	/** 订单号 */
	private String orderNo;
	/** 取货时间 */
	private String deliveryTime;

	/** 取货码 */
	public String getDeliveryCode() {
		return deliveryCode;
	}

	/** 取货码 */
	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	/** 商品详情 */
	public String getProductItemInfo() {
		return productItemInfo;
	}

	/** 商品详情 */
	public void setProductItemInfo(String productItemInfo) {
		this.productItemInfo = productItemInfo;
	}

	/** 取货地点 */
	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	/** 取货地点 */
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	/** 门店 */
	public String getShopName() {
		return shopName;
	}

	/** 门店 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	/** 营业时间 */
	public String getBusinessHours() {
		return businessHours;
	}

	/** 营业时间 */
	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}

	/** 订单号 */
	public String getOrderNo() {
		return orderNo;
	}

	/** 订单号 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/** 取货时间 */
	public String getDeliveryTime() {
		return deliveryTime;
	}

	/** 取货时间 */
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

}
