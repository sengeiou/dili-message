package com.dili.message.sdk.domain;

/**
 * @description： 消息推送-退款通知
 * 
 * @author ：WangBo
 * @time ：2018年11月9日下午3:57:48
 */
public class RefundParam extends BaseParam {
	/** 退款时间 */
	private String time;
	/** 退款金额 */
	private String amount;
	/** 订单号 */
	private String orderNo;
	/** 退款状态 */
	private String refundState;

	/** 退款时间 */
	public String getTime() {
		return time;
	}

	/** 退款时间 */
	public void setTime(String time) {
		this.time = time;
	}

	/** 退款金额 */
	public String getAmount() {
		return amount;
	}

	/** 退款金额 */
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

	/** 退款状态 */
	public String getRefundState() {
		return refundState;
	}

	/** 退款状态 */
	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}

}