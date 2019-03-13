package com.dili.message.sdk.domain;

import java.util.Date;

/**
 * @description： 消息推送-审核结果通知
 * 
 * @author ：jcy
 * @time ：2018年11月9日下午3:57:48
 */
public class AuditResultNoticeParam extends BaseParam {
	/** 审核结果 */
	private String auditResult;
	/** 申请编号 */
	private String applyNumber;
	/** 审核时间*/
	private String auditTime;
	/** 申请时间*/
	private String applyTime;

	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	public String getApplyNumber() {
		return applyNumber;
	}

	public void setApplyNumber(String applyNumber) {
		this.applyNumber = applyNumber;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
}