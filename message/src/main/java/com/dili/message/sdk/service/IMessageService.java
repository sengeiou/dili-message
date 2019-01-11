package com.dili.message.sdk.service;

import java.util.List;

import com.dili.message.sdk.domain.DeliveryParam;
import com.dili.message.sdk.domain.GoodsWarningParam;
import com.dili.message.sdk.domain.OrderPaySuccessParam;
import com.dili.message.sdk.domain.RefundParam;
import com.dili.message.sdk.domain.VerificationCodeParam;

/**
 * @description： 消息类型接口
 * 
 * @author ：WangBo
 * @time ：2018年11月9日上午11:09:52
 */
public interface IMessageService {
	/**
	 * 取货通知
	 * 
	 * @return 单条数据可以使用，多条数据意义不大
	 */
	default public boolean delivery(List<DeliveryParam> param) {
		return false;
	}

	/**
	 * 退款通知
	 * 
	 * @return 单条数据可以使用，多条数据意义不大
	 */
	default public boolean refund(List<RefundParam> param) {
		return false;
	}


	/**
	 * 订单支付成功通知
	 * 
	 * @return 单条数据可以使用，多条数据意义不大
	 */
	default public boolean orderPaySuccess(List<OrderPaySuccessParam> param) {
		return false;
	}

	/**
	 * 商品可用量告警
	 * 
	 * @return 单条数据可以使用，多条数据意义不大
	 */
	default public boolean goodsWarning(List<GoodsWarningParam> param) {
		return false;
	}
	
	/**
	 * 短信验证码
	 * 
	 * @return 单条数据可以使用，多条数据意义不大
	 */
	default public boolean verificationCode(List<VerificationCodeParam> param) {
		return false;
	}

}
