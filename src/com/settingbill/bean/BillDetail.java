package com.settingbill.bean;


public class BillDetail {
	private String name;
	private String before1;
	private String before2;
	private String after1;
	private String after2;
	
	public BillDetail() {
		super();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getBefore1() {
		return before1;
	}
	
	public void setBefore1(String before1) {
		this.before1 = before1;
	}
	
	public String getBefore2() {
		return before2;
	}
	
	public void setBefore2(String before2) {
		this.before2 = before2;
	}

	public String getAfter1() {
		return after1;
	}
	
	public void setAfter1(String after1) {
		this.after1 = after1;
	}

	public String getAfter2() {
		return after2;
	}

	public void setAfter2(String after2) {
		this.after2 = after2;
	}
	
	
}
