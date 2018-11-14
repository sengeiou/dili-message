package com.dili.message.sdk.type;

/**
 * @description： 模板类型
 * 
 * @author ：WangBo
 * @time ：2018年11月12日上午11:06:11
 */
public enum TemplateType {
	/** 订单创建 */
	PAY(1, "订单支付"),
	/** 开团成功提醒 */
	CAMPAIGN_SUCCESS(2, "拼团成功"),
	/** 退款通知 */
	DELIVERY(3, "取货通知"),
	/** 取货成功  */
	DELIVERY_SUCCESS(4, "取货成功 "),
	/** 团购失败通知 */
	CAMPAIGN_FAILURE(5, "团购失败"),
	/** 团购失败通知 */
	REFUND(6, "订单退款"),
	/** 订单关闭提醒 */
	CLOSE_ORDER(7, "订单关闭");

	private String name;
	private int code;

	TemplateType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}