package com.dili.message.sdk.domain;

/**
 * @description：
 * 消息推送-商品可用量告警通知参数
 * @author ：WangBo
 * @time ：2018年11月9日下午3:57:48
 */
public class GoodsWarningParam extends BaseParam {
	/** 区域 */
	private String area;
	/** 商品 */
	private String goods;
	/** 可用量 */
	private String amount;

	/** 区域 */
	public String getArea() {
		return area;
	}

	/** 区域 */
	public void setArea(String area) {
		this.area = area;
	}

	/** 商品 */
	public String getGoods() {
		return goods;
	}

	/** 商品 */
	public void setGoods(String goods) {
		this.goods = goods;
	}

	/** 可用量 */
	public String getAmount() {
		return amount;
	}

	/** 可用量 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

}