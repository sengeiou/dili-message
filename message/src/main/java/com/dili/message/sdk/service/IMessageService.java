package com.dili.message.sdk.service;

import java.util.List;

import com.dili.message.sdk.domain.*;

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
	default public boolean delivery(List<DeliveryParam> params) {
		return false;
	}

	/**
	 * 退款通知
	 * 
	 * @return 单条数据可以使用，多条数据意义不大
	 */
	default public boolean refund(List<RefundParam> params) {
		return false;
	}


	/**
	 * 订单支付成功通知
	 * 
	 * @return 单条数据可以使用，多条数据意义不大
	 */
	default public boolean orderPaySuccess(List<OrderPaySuccessParam> params) {
		return false;
	}

	/**
	 * 商品可用量告警
	 * 
	 * @return 单条数据可以使用，多条数据意义不大
	 */
	default public boolean goodsWarning(List<GoodsWarningParam> params) {
		return false;
	}
	
	/**
	 * 短信验证码
	 * 
	 * @return 单条数据可以使用，多条数据意义不大
	 */
	default public boolean verificationCode(List<VerificationCodeParam> params) {
		return false;
	}
	/**
	 * 退货申请
	 * 
	 * @return 单条数据可以使用，多条数据意义不大
	 */
	default public boolean returnApply(List<ReturnApplyParam> params) {
		return false;
	}
	/**
	 * 审核结果通知
	 *
	 * @return 单条数据可以使用，多条数据意义不大
	 */
	default public boolean auditResultNotice(List<AuditResultNoticeParam> params) {
		return false;
	}
}
