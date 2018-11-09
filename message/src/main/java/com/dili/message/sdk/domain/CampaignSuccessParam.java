package com.dili.message.sdk.domain;

/**
 * 
 * @author ：WangBo
 * @time ：2018年11月9日下午3:53:35
 */
/**
 * @description：
 *               消息推送-开团成功提醒参数
 * @author     ：WangBo
 * @time       ：2018年11月9日下午3:54:39
 */
public class CampaignSuccessParam {
	/** 开团时间 */
	private String campaignTimeStart;
	/** 截至时间 */
	private String campaignTimeEnd;
	/** 参团人数 */
	private String peoples;
	/** 拼团价 */
	private String price;
	/** 商品名称 */
	private String productName;
	/** 剩余时间 */
	private String timeRemaining;

	/** 开团时间 */
	public String getCampaignTimeStart() {
		return campaignTimeStart;
	}

	/** 开团时间 */
	public void setCampaignTimeStart(String campaignTimeStart) {
		this.campaignTimeStart = campaignTimeStart;
	}

	/** 截至时间 */
	public String getCampaignTimeEnd() {
		return campaignTimeEnd;
	}

	/** 截至时间 */
	public void setCampaignTimeEnd(String campaignTimeEnd) {
		this.campaignTimeEnd = campaignTimeEnd;
	}

	/** 参团人数 */
	public String getPeoples() {
		return peoples;
	}

	/** 参团人数 */
	public void setPeoples(String peoples) {
		this.peoples = peoples;
	}

	/** 拼团价 */
	public String getPrice() {
		return price;
	}

	/** 拼团价 */
	public void setPrice(String price) {
		this.price = price;
	}

	/** 商品名称 */
	public String getProductName() {
		return productName;
	}

	/** 商品名称 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/** 剩余时间 */
	public String getTimeRemaining() {
		return timeRemaining;
	}

	/** 剩余时间 */
	public void setTimeRemaining(String timeRemaining) {
		this.timeRemaining = timeRemaining;
	}

}