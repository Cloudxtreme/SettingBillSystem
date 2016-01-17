package com.settingbill.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.settingbill.dao.DBService;

public class LoadTemplates extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
		super.doGet(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\n========== LoadTemplates.java ==========");
		HttpSession session = req.getSession();
		DBService dbservice = new DBService();
		int companyID = (int)session.getAttribute("companyID");
		System.out.println("companyID: " + companyID);
		
		JSONObject obj = new JSONObject();
		obj = dbservice.loadTemplates(companyID);
		
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
		String data = JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat);
		
		System.out.println("[Response] " + data);
		
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
	    out.write(data);
	}
}
