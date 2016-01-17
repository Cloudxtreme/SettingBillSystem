package com.settingbill.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.settingbill.dao.DBService;


public class QueryBrief extends HttpServlet {
	
	private static final long	serialVersionUID	= 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\n========== QueryBrief.java ==========");
		DBService dbservice = new DBService();
		String trigger = req.getParameter("trigger");
		String val = req.getParameter("val");
		System.out.println("[Request] trigger: " + trigger + ", val: " + val);
		
		JSONObject obj = null;
		switch (trigger) {
			case "locationTo":
				obj = new JSONObject();
				obj.put("devicebelonging", dbservice.queryDeviceBelonging(val));
				break;
			case "deviceBelonging": 
				obj = new JSONObject();
				obj.put("device", dbservice.queryDevice(val));
				obj.put("line", dbservice.queryLine(val));
				break;
			case "device": 
				obj = new JSONObject();
				obj.put("model", dbservice.queryModel(val));
				break;
			default:
				break;
		}

		String data = JSON.toJSONString(obj);
		System.out.println("[Response] " + data);
		
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
	    out.write(data);
	}
}
