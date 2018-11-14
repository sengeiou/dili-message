package com.dili.demo.webapp;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class AlidayuRpc2 {
	
	public static String HRB_APP_KEY="LTAImzT8nS0nCKy2";
	public static String HRB_SECRET="Msz8vY30Fl8cspVBzuXVSTi5LMzAnw";
	public static String HRB_SMS_SIGN_NAME="地利生鲜";
	public static String HRB_SMS_TEMPLATE_CODE="SMS_150172881";//取货通知
//	public static String HRB_SMS_TEMPLATE_CODE="SMS_150172896";//退款通知
	/**
	 * 阿里大于短信发送接口 无特殊市场
	 * @param code 短信验证码
	 * @param mobile 电话号码(多个电话号码逗号分隔)
	 * @param desc 公共回传参数(调用应用/接口名称)
	 * @return 
	 */
	public static Boolean sendSms(String order,String amount, String mobile, String desc) {
		//可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", HRB_APP_KEY, HRB_SECRET);
        try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
		} catch (ClientException e1) {
			e1.printStackTrace();
		}
        IAcsClient acsClient = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();
        request.setSignName(HRB_SMS_SIGN_NAME);
		request.setTemplateCode(HRB_SMS_TEMPLATE_CODE);
		request.setPhoneNumbers(mobile);
		JSONObject json=new JSONObject();
//		json.put("order", order);
//		json.put("amount", amount);
		json.put("date", "2018-03-12");
		json.put("code", "666666");
		json.put("Shop", "人民东路1号店");
		json.put("order", "XXXX-XXXX");
	    request.setTemplateParam(json.toJSONString());
        
        SendSmsResponse sendSmsResponse = null;
		try {
			System.out.println("sendSmsRequest>"+json.toJSONString());
			sendSmsResponse = acsClient.getAcsResponse(request);
			System.out.println("sendSmsResponse>"+JSONObject.toJSONString(sendSmsResponse));
			if (sendSmsResponse != null) {
		        return sendSmsResponse.getCode().equals("OK");
			}else {
				return false;
			}
		} catch (ClientException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static void main(String[] args) {
		System.out.println(sendSms("定单", "5000000.6", "18981883712", ""));
	}
}
