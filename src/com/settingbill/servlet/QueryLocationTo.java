package com.settingbill.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.serializer.SerializerFeature;
import com.settingbill.dao.DBService;


public class QueryLocationTo extends HttpServlet {
	
	private static final long	serialVersionUID	= 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\n========== QueryLocationTo.java ==========");
		HttpSession session = req.getSession();
		String companyID = String.valueOf(session.getAttribute("companyID"));
		DBService dbservice = new DBService();
		
		JSONObject obj = new JSONObject();
		obj.put("locationto", dbservice.queryLocationTo(companyID));
		String data = JSON.toJSONString(obj);
		
		System.out.println("[Response] " + data);
		
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
	    out.write(data);
	}
}
