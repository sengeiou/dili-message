package com.dili.demo.webapp;

//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
//import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.profile.DefaultProfile;
//import com.aliyuncs.profile.IClientProfile;

public class AlidayuRpc {
	
	public static String HRB_APP_KEY="LTAIYYN6H84VrryU";
	public static String HRB_SECRET="eVmakQ1udym1BcMNhe4K5KjTHeLpwH";
	public static String HRB_SMS_SIGN_NAME="农丰时代";
	public static String HRB_SMS_TEMPLATE_CODE="SMS_145920640";
	
	/**
	 * 阿里大于短信发送接口 无特殊市场
	 * @param code 短信验证码
	 * @param mobile 电话号码(多个电话号码逗号分隔)
	 * @param desc 公共回传参数(调用应用/接口名称)
	 * @return 
	 */
//	public static Boolean sendSms(String code, String mobile, String desc) {
//		//可自助调整超时时间
//        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
//        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
//
//        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", HRB_APP_KEY, HRB_SECRET);
//        try {
//			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
//		} catch (ClientException e1) {
//			e1.printStackTrace();
//		}
//        IAcsClient acsClient = new DefaultAcsClient(profile);
//
//        SendSmsRequest request = new SendSmsRequest();
//        request.setSignName(HRB_SMS_SIGN_NAME);
//		request.setTemplateCode(HRB_SMS_TEMPLATE_CODE);
//		request.setPhoneNumbers(mobile);
//	    request.setTemplateParam("{\"code\":\"" + code + "\"}");
//        
//        SendSmsResponse sendSmsResponse = null;
//		try {
//			sendSmsResponse = acsClient.getAcsResponse(request);
//			if (sendSmsResponse != null) {
//		        return sendSmsResponse.getCode().equals("OK");
//			}else {
//				return false;
//			}
//		} catch (ClientException e) {
//			e.printStackTrace();
//			return false;
//		}
//      
//	}

}
