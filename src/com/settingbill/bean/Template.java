package com.settingbill.bean;

import java.util.Date;

public class Template {

	private int	ID;
	private String	templateCode;
	private String	templateName;
	private int	status;
	private String	statusName;
	private int	createrID;
	private String	createrName;
	private Date	createTime;
	private int    belongID;

	public Template() {
		super();
	}

	public Template(String templateCode, String templateName, int createrID,
			int belongID) {
		this.templateCode = templateCode;
		this.templateName = templateName;
		this.createrID = createrID;
		this.belongID = belongID;
	}

	public int getID() {
		return ID;
	}
	
	public void setID(int iD) {
		ID = iD;
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
	
	public int getBelongID() {
		return belongID;
	}

	public void setBelongID(int belongID) {
		this.belongID = belongID;
	}

}
