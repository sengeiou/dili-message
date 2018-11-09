package com.dili.message.sdk.domain;

/**
 * @author ：WangBo
 * @time ：2018年11月9日下午4:06:11
 */
public class OrderPaySuccessParam {
	/** 订单编号 */
	private String orderNo;
	/** 商品名称 */
	private String productName;
	/** 支付金额 */
	private String amount;
	/** 下单时间 */
	private String createOrderTime;

	/** 订单编号 */
	public String getOrderNo() {
		return orderNo;
	}

	/** 订单编号 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/** 商品名称 */
	public String getProductName() {
		return productName;
	}

	/** 商品名称 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/** 支付金额 */
	public String getAmount() {
		return amount;
	}

	/** 支付金额 */
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

}