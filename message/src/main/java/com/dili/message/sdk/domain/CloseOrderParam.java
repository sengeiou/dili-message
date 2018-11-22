package com.dili.message.sdk.domain;

/**
 * 消息推送-关闭订单参数（包括短信、公众号、小程序）
 * @author ：WangBo
 * @time ：2018年11月9日下午4:01:23
 */
public class CloseOrderParam extends BaseParam {
	/** 商品名称 */
	private String productName;
	/** 订单编号 */
	private String orderNo;
	/** 订单金额 */
	private String amount;
	/** 下单时间 */
	private String createOrderTime;
	/** 订单关闭时间 */
	private String closeOrderTime;
	/** 温馨提示 */
	private String kindlyReminder;

	/** 商品名称 */
	public String getProductName() {
		return productName;
	}

	/** 商品名称 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/** 订单编号 */
	public String getOrderNo() {
		return orderNo;
	}

	/** 订单编号 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/** 订单金额 */
	public String getAmount() {
		return amount;
	}

	/** 订单金额 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/** 下单时间 */
	public String getCreateOrderTime() {
		return createOrderTime;
	}

	/** 下单时间 */
	public void setCreateOrderTime(String createOrderTime) {
		this.createOrderTime = createOrderTime;
	}

	/** 温馨提示 */
	public String getKindlyReminder() {
		return kindlyReminder;
	}

	/** 温馨提示 */
	public void setKindlyReminder(String kindlyReminder) {
		this.kindlyReminder = kindlyReminder;
	}

	/** 订单关闭时间 */
	public String getCloseOrderTime() {
		return closeOrderTime;
	}

	/** 订单关闭时间 */
	public void setCloseOrderTime(String closeOrderTime) {
		this.closeOrderTime = closeOrderTime;
	}

}