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
	/** 退款方式 */
	private String refundMode;
	/** 退款金额 */
	private String amount;
	/** 退款原因 */
	private String cause;
	/** 退款单号 */
	private String orderNo;
	/** 备注 */
	private String remark;

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

	/** 退款原因 */
	public String getCause() {
		return cause;
	}

	/** 退款原因 */
	public void setCause(String cause) {
		this.cause = cause;
	}

	/** 退款单号 */
	public String getOrderNo() {
		return orderNo;
	}

	/** 退款单号 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/** 备注 */
	public String getRemark() {
		return remark;
	}

	/** 备注 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/** 退款方式 */
	public String getRefundMode() {
		return refundMode;
	}

	/** 退款方式 */
	public void setRefundMode(String refundMode) {
		this.refundMode = refundMode;
	}

}