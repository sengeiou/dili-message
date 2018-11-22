package com.dili.message.sdk.common;

/**
 * @description： 公众号或小程序消息模板参数
 * 
 * @author ：WangBo
 * @time ：2018年11月22日下午2:39:02
 */
public class TemplateParam {
	/** 参数名称 */
	private String name;
	/** 参数值 */
	private String value;
	/** 颜色 */
	private String color;

	public TemplateParam(String name, String value, String color) {
		this.name = name;
		this.value = value;
		this.color = color;
	}

	public TemplateParam(String value) {
		this.value = value;
	}

	/** 参数名称 */
	public String getName() {
		return name;
	}

	/** 参数名称 */
	public void setName(String name) {
		this.name = name;
	}

	/** 参数值 */
	public String getValue() {
		return value;
	}

	/** 参数值 */
	public void setValue(String value) {
		this.value = value;
	}

	/** 颜色 */
	public String getColor() {
		return color;
	}

	/** 颜色 */
	public void setColor(String color) {
		this.color = color;
	}

}
