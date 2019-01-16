package com.dili.message.sdk.domain;

/**
 * @description： 消息推送-退货申请
 * 
 * @author ：WangBo
 * @time ：2018年11月9日下午3:57:48
 */
public class ReturnApplyParam extends BaseParam {
	/** 申请时间 */
	private String time;
	/** 金额 */
	private String amount;
	/** 订单号 */
	private String orderNo;
	/** 退款说明 */
	private String refundDesc;
	/** 商品数量 */
	private String productQuantity;

	/** 退款时间 */
	public String getTime() {
		return time;
	}

	/** 退款时间 */
	public void setTime(String time) {
		this.time = time;
	}

	/** 金额 */
	public String getAmount() {
		return amount;
	}

	/** 金额 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/** 退款单号 */
	public String getOrderNo() {
		return orderNo;
	}

	/** 退款单号 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/** 退款说明 */
	public String getRefundDesc() {
		return refundDesc;
	}

	/** 退款说明 */
	public void setRefundDesc(String refundDesc) {
		this.refundDesc = refundDesc;
	}

	/** 商品数量 */
	public String getProductQuantity() {
		return productQuantity;
	}

	/** 商品数量 */
	public void setProductQuantity(String productQuantity) {
		this.productQuantity = productQuantity;
	}

}