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
import com.settingbill.dao.DBService;

public class StoreTemplates extends HttpServlet {
	
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
		System.out.println("\n========== StoreTemplates.java ==========");
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("username");
		String companyID = String.valueOf(session.getAttribute("companyID"));
		String data = req.getParameter("data");
		System.out.println("[INFO] data: " + data);
		DBService dbservice = new DBService();
		
		JSONObject obj = (JSONObject) JSON.parse(data);

		JSONObject result = dbservice.insertTemplate(obj, username, companyID);
//		String out = JSON.toJSONString(result);
		System.out.println("[Response] " + result);
		
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		out.write(result.toJSONString());
	}
}
