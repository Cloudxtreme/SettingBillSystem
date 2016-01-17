package com.settingbill.bean;

import java.util.Date;

public class User
{
	private int ID;
	private String username;
	private String displayName;
	private String password;
	private int authority;
	private int companyID;
	private String companyName;
	private int status;
	private Date addTime;
	private Date lastUpdateTime;
	
	public User() {
		super();
	}
	
	public User(String username, String password, int authority, int companyID) {
		this.username = username;
		this.password = password;
		this.authority = authority;
		this.companyID = companyID;
	}

	public int getID()	{
		return ID;
	}

	public void setID(int iD)	{
		ID = iD;
	}

	public String getUsername()	{
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword()	{
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAuthority() {
		return authority;
	}

	public void setAuthority(int authority) {
		this.authority = authority;
	}

	public int getCompanyID()	{
		return companyID;
	}

	public void setCompanyID(int companyID)	{
		this.companyID = companyID;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getDisplayName()	{
		return displayName;
	}

	public void setDisplayName(String displayName)	{
		this.displayName = displayName;
	}

	public int getStatus()	{
		return status;
	}

	public void setStatus(int status)	{
		this.status = status;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime){
		this.addTime = addTime;
	}

	public Date getLastUpdateTime()	{
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime)	{
		this.lastUpdateTime = lastUpdateTime;
	}
	
}