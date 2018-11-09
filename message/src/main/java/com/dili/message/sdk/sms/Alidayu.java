package com.dili.message.sdk.sms;

import com.dili.message.sdk.IMessageService;
import com.dili.message.sdk.domain.CampaignFailureParam;
import com.dili.message.sdk.domain.CampaignSuccessParam;
import com.dili.message.sdk.domain.CloseOrderParam;
import com.dili.message.sdk.domain.DeliveryParam;
import com.dili.message.sdk.domain.DeliverySuccessParam;
import com.dili.message.sdk.domain.OrderPaySuccessParam;
import com.dili.message.sdk.domain.RefundsParam;

/**
 * @description：
 *               阿里大鱼短信发送
 * @author     ：WangBo
 * @time       ：2018年11月9日上午10:50:06
 */

public class Alidayu implements IMessageService {

	@Override
	public boolean delivery(DeliveryParam param) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean refunds(RefundsParam param) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	@Override
	public boolean campaignSuccess(CampaignSuccessParam param) {
		return false;
	}


	@Override
	public boolean closeOrder(CloseOrderParam param) {
		return false;
	}

	@Override
	public boolean campaignFailure(CampaignFailureParam param) {
		return false;
	}

	@Override
	public boolean deliverySuccess(DeliverySuccessParam parameterObject) {
		return false;
	}

	@Override
	public boolean orderPaySuccess(OrderPaySuccessParam param) {
		return false;
	}
	
	
}
