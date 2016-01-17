package com.settingbill.bean;

import java.util.Date;

public class Bill {

	private int	ID;
	private String	billCode;
	private int	locationFromID;
	private String	locationFromName;
	private int	locationToID;
	private String	locationToName;
	private int	deviceBelongingID;
	private String	deviceBelongingName;
	private int	lineID;
	private String	lineName;
	private int	deviceID;
	private String	deviceName;
	private String	deviceModelName;
	private String	remark;
	private int	status;
	private String	statusName;
	private String	templateCode;
	private String	templateName;
	private int	createrID;
	private String	createrName;
	private Date	createTime;
	private int	lastEditorID;
	private String	lastEditorName;
	private Date	lastEditTime;
	private int	checkerID;
	private String	checkerName;
	private Date	checkTime;
	private int	recovererID;
	private String	recovererName;
	private Date	recoverTime;
	private String	recoverRemark;
	private int	trasherID;
	private String	trasherName;
	private Date	trashTime;
	private String	trashRemark;
	private int	auditorID;
	private String	auditorName;
	private Date	auditTime;
	private String	auditCancelRemark;
	private int	receiverID;
	private String	receiverName;
	private Date	receiveTime;
	private String	receiveCancelRemark;
	private int	reporterID;
	private String	reporterName;
	private Date	reportFeedbackTime;
	private String	reportFeedbackRemark;
	private int	executerID;
	private String	executerName;
	private Date	executeFeedbackTime;
	private String	executeFeedbackRemark;

	public Bill() {
		super();
	}

	public Bill(String billCode, int locationFromID, int locationToID,
			int deviceBelongingID, int lineID, int deviceID, int createrID) {
		this.billCode = billCode;
		this.locationFromID = locationFromID;
		this.locationToID = locationToID;
		this.deviceBelongingID = deviceBelongingID;
		this.lineID = lineID;
		this.deviceID = deviceID;
		this.createrID = createrID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public int getLocationFromID() {
		return locationFromID;
	}

	public void setLocationFromID(int locationFromID) {
		this.locationFromID = locationFromID;
	}

	public String getLocationFromName() {
		return locationFromName;
	}

	public void setLocationFromName(String locationFromName) {
		this.locationFromName = locationFromName;
	}

	public int getLocationToID() {
		return locationToID;
	}

	public void setLocationToID(int locationToID) {
		this.locationToID = locationToID;
	}

	public String getLocationToName() {
		return locationToName;
	}

	public void setLocationToName(String locationToName) {
		this.locationToName = locationToName;
	}

	public int getDeviceBelongingID() {
		return deviceBelongingID;
	}

	public void setDeviceBelongingID(int deviceBelongingID) {
		this.deviceBelongingID = deviceBelongingID;
	}

	public String getDeviceBelongingName() {
		return deviceBelongingName;
	}

	public void setDeviceBelongingName(String deviceBelongingName) {
		this.deviceBelongingName = deviceBelongingName;
	}

	public int getLineID() {
		return lineID;
	}

	public void setLineID(int lineID) {
		this.lineID = lineID;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public int getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(int deviceID) {
		this.deviceID = deviceID;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceModelName() {
		return deviceModelName;
	}

	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	public String getTemplateCode() {
		return templateCode;
	}
	
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
	
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public int getCreaterID() {
		return createrID;
	}

	public void setCreaterID(int createrID) {
		this.createrID = createrID;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getLastEditorID() {
		return lastEditorID;
	}

	public void setLastEditorID(int lastEditorID) {
		this.lastEditorID = lastEditorID;
	}

	public String getLastEditorName() {
		return lastEditorName;
	}

	public void setLastEditorName(String lastEditorName) {
		this.lastEditorName = lastEditorName;
	}

	public Date getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public int getCheckerID() {
		return checkerID;
	}

	public void setCheckerID(int checkerID) {
		this.checkerID = checkerID;
	}

	public String getCheckerName() {
		return checkerName;
	}

	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public int getRecovererID() {
		return recovererID;
	}

	public void setRecovererID(int recovererID) {
		this.recovererID = recovererID;
	}

	
	public String getRecovererName() {
		return recovererName;
	}

	
	public void setRecovererName(String recovererName) {
		this.recovererName = recovererName;
	}

	public Date getRecoverTime() {
		return recoverTime;
	}

	public void setRecoverTime(Date recoverTime) {
		this.recoverTime = recoverTime;
	}

	public String getRecoverRemark() {
		return recoverRemark;
	}

	public void setRecoverRemark(String recoverRemark) {
		this.recoverRemark = recoverRemark;
	}

	public int getTrasherID() {
		return trasherID;
	}

	public void setTrasherID(int trasherID) {
		this.trasherID = trasherID;
	}

	
	public String getTrasherName() {
		return trasherName;
	}

	
	public void setTrasherName(String trasherName) {
		this.trasherName = trasherName;
	}

	public Date getTrashTime() {
		return trashTime;
	}

	public void setTrashTime(Date trashTime) {
		this.trashTime = trashTime;
	}

	public String getTrashRemark() {
		return trashRemark;
	}

	public void setTrashRemark(String trashRemark) {
		this.trashRemark = trashRemark;
	}

	public int getAuditorID() {
		return auditorID;
	}

	public void setAuditorID(int auditorID) {
		this.auditorID = auditorID;
	}

	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditCancelRemark() {
		return auditCancelRemark;
	}

	public void setAuditCancelRemark(String auditCancelRemark) {
		this.auditCancelRemark = auditCancelRemark;
	}

	public int getReceiverID() {
		return receiverID;
	}

	public void setReceiverID(int receiverID) {
		this.receiverID = receiverID;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getReceiveCancelRemark() {
		return receiveCancelRemark;
	}

	public void setReceiveCancelRemark(String receiveCancelRemark) {
		this.receiveCancelRemark = receiveCancelRemark;
	}

	public int getReporterID() {
		return reporterID;
	}

	public void setReporterID(int reporterID) {
		this.reporterID = reporterID;
	}

	
	public String getReporterName() {
		return reporterName;
	}

	
	public void setReporterName(String reporterName) {
		this.reporterName = reporterName;
	}

	public Date getReportFeedbackTime() {
		return reportFeedbackTime;
	}

	public void setReportFeedbackTime(Date reportFeedbackTime) {
		this.reportFeedbackTime = reportFeedbackTime;
	}

	public String getReportFeedbackRemark() {
		return reportFeedbackRemark;
	}

	public void setReportFeedbackRemark(String reportFeedbackRemark) {
		this.reportFeedbackRemark = reportFeedbackRemark;
	}

	public int getExecuterID() {
		return executerID;
	}

	public void setExecuterID(int executerID) {
		this.executerID = executerID;
	}

	public String getExecuterName() {
		return executerName;
	}

	public void setExecuterName(String executerName) {
		this.executerName = executerName;
	}

	public Date getExecuteFeedbackTime() {
		return executeFeedbackTime;
	}

	public void setExecuteFeedbackTime(Date executeFeedbackTime) {
		this.executeFeedbackTime = executeFeedbackTime;
	}

	public String getExecuteFeedbackRemark() {
		return executeFeedbackRemark;
	}

	public void setExecuteFeedbackRemark(String executeFeedbackRemark) {
		this.executeFeedbackRemark = executeFeedbackRemark;
	}

}
