package com.dili.message.sdk.task;

import com.alibaba.fastjson.JSONObject;
import com.dili.message.sdk.domain.DeliveryParam;
import com.dili.message.sdk.domain.GoodsWarningParam;
import com.dili.message.sdk.domain.OrderPaySuccessParam;
import com.dili.message.sdk.domain.RefundParam;
import com.dili.message.sdk.domain.ReturnApplyParam;
import com.dili.message.sdk.domain.VerificationCodeParam;
import com.dili.message.sdk.service.IMessageService;
import com.dili.message.sdk.type.TemplateType;

/**
 * @description： 消息类型分发工作线程
 * 
 * @author ：WangBo
 * @time ：2018年11月12日上午10:33:39
 */
public class SendMessageWork implements Runnable {
	private String param;
	private TemplateType templateType;
	private IMessageService messageService;

	/**
	 * @param param
	 *            消息参数
	 * @param templateType
	 *            消息类型
	 * @param messageService
	 *            推送方式
	 */
	public SendMessageWork(String param, TemplateType templateType, IMessageService messageService) {
		this.param = param;
		this.templateType = templateType;
		this.messageService = messageService;
	}

	@Override
	public void run() {
		if (templateType == TemplateType.DELIVERY) {// 取货
			messageService.delivery(JSONObject.parseArray(param, DeliveryParam.class));

		} else if (templateType == TemplateType.PAY_SUCCESS) {// 支付成功
			messageService.orderPaySuccess(JSONObject.parseArray(param, OrderPaySuccessParam.class));

		} else if (templateType == TemplateType.REFUND) {// 退款
			messageService.refund(JSONObject.parseArray(param, RefundParam.class));

		} else if (templateType == TemplateType.GOODS_WARNING) {// 商品可用量告警
			messageService.goodsWarning(JSONObject.parseArray(param, GoodsWarningParam.class));
			
		} else if (templateType == TemplateType.VERIFICATION_CODE) {// 短信验证码
			messageService.verificationCode(JSONObject.parseArray(param, VerificationCodeParam.class));
			
		}else if (templateType == TemplateType.RETURN_APPLY) {// 退货申请
			messageService.returnApply(JSONObject.parseArray(param, ReturnApplyParam.class));
		}
	}
}
