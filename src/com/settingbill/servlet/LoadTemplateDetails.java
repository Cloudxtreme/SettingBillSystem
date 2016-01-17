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

public class LoadTemplateDetails extends HttpServlet {
	
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
		System.out.println("\n========== LoadTemplateDetails.java ==========");
		String tcode = req.getParameter("tcode");
		DBService dbservice = new DBService();
		System.out.println("tcode: " + tcode);
		
		JSONObject obj = dbservice.loadTemplateDetails(tcode);
		String data = JSON.toJSONString(obj);
		System.out.println("[Response] " + data);
		
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
	    out.write(data);
	}
}
