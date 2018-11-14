package com.dili.message.sdk.domain;

/**
 * @description： 退款通知
 * 
 * @author ：WangBo
 * @time ：2018年11月9日下午3:57:48
 */
public class RefundParam extends BaseParam{
	/** 退款时间 */
	private String time;
	/** 退款说明 */
	private String description;
	/** 退款金额 */
	private String amount;
	/** 退费原因 */
	private String cause;
	/** 退款单号 */
	private String refundsNo;
	/** 商品详情 */
	private String productItemInfo;

	/**
	 * 
	 */
	public RefundParam(String time, String description, String amount, String cause, String refundsNo, String productItemInfo) {
		this.time = time;
		this.description = description;
		this.amount = amount;
		this.cause = cause;
		this.refundsNo = refundsNo;
		this.productItemInfo = productItemInfo;
	}

	/** 退款时间 */
	public String getTime() {
		return time;
	}

	/** 退款时间 */
	public void setTime(String time) {
		this.time = time;
	}

	/** 退款说明 */
	public String getDescription() {
		return description;
	}

	/** 退款说明 */
	public void setDescription(String description) {
		this.description = description;
	}

	/** 退款金额 */
	public String getAmount() {
		return amount;
	}

	/** 退款金额 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/** 退费原因 */
	public String getCause() {
		return cause;
	}

	/** 退费原因 */
	public void setCause(String cause) {
		this.cause = cause;
	}

	/** 退款单号 */
	public String getRefundsNo() {
		return refundsNo;
	}

	/** 退款单号 */
	public void setRefundsNo(String refundsNo) {
		this.refundsNo = refundsNo;
	}

	/** 商品详情 */
	public String getProductItemInfo() {
		return productItemInfo;
	}

	/** 商品详情 */
	public void setProductItemInfo(String productItemInfo) {
		this.productItemInfo = productItemInfo;
	}

}