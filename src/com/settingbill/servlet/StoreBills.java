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

public class StoreBills extends HttpServlet {
	
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
		System.out.println("\n========== StoreBills.java ==========");
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("username");
		DBService dbservice = new DBService();
		String billcode = req.getParameter("billcode");
		String data = req.getParameter("data");
		Boolean isBriefEdited = Boolean.valueOf(req.getParameter("isBriefEdited"));
		Boolean isDetailEdited = Boolean.valueOf(req.getParameter("isDetailEdited"));
		
		JSONObject obj = (JSONObject) JSON.parse(data);
		System.out.println(obj);

		int result = dbservice.updateBill(billcode, obj, username, isBriefEdited, isDetailEdited);
		System.out.println("[Response] " + result);
		
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		out.write(String.valueOf(result));
	}
}
