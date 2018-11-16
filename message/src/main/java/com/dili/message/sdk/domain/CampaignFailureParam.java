package com.dili.message.sdk.domain;

/**
 * @author ：WangBo
 * @time ：2018年11月9日下午4:03:17
 */
public class CampaignFailureParam extends BaseParam{
	/** 订单号 */
	private String orderNo;
	/** 处理结果 */
	private String processingResults;
	/** 团购项目 */
	private String campaignName;
	/** 支付金额 */
	private String amount;
	/** 失败原因 */
	private String failureCause;
	/** 团购商品 */
	private String campaignProduct;

	/** 订单号 */
	public String getOrderNo() {
		return orderNo;
	}

	/** 订单号 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/** 处理结果 */
	public String getProcessingResults() {
		return processingResults;
	}

	/** 处理结果 */
	public void setProcessingResults(String processingResults) {
		this.processingResults = processingResults;
	}

	/** 团购项目 */
	public String getCampaignName() {
		return campaignName;
	}

	/** 团购项目 */
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	/** 支付金额 */
	public String getAmount() {
		return amount;
	}

	/** 支付金额 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/** 失败原因 */
	public String getFailureCause() {
		return failureCause;
	}

	/** 失败原因 */
	public void setFailureCause(String failureCause) {
		this.failureCause = failureCause;
	}

	/** 团购商品 */
	public String getCampaignProduct() {
		return campaignProduct;
	}

	/** 团购商品 */
	public void setCampaignProduct(String campaignProduct) {
		this.campaignProduct = campaignProduct;
	}

}