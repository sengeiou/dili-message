package com.dili.message.sdk.exception;

/**
 * @description：
 *               程序业务异常
 * @author     ：WangBo
 * @time       ：2018年11月9日下午2:08:08
 */
public class ApplicationException  extends RuntimeException{

	private static final long serialVersionUID = 1L;
	public ApplicationException(String msg) {
		super(msg);
	}
}
