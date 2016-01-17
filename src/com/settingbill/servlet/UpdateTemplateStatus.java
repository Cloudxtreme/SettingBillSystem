package com.settingbill.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.settingbill.dao.DBService;

public class UpdateTemplateStatus extends HttpServlet {
	
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
		System.out.println("\n========== UpdateTemplateStatus.java ==========");
		DBService dbservice = new DBService();
		String tcode = req.getParameter("tcode");
		String status = req.getParameter("status");
		System.out.println(" tcode: " + tcode);
		System.out.println("status: " + status);
		
		int result = dbservice.updateTemplateStatus(tcode, status);
		System.out.println("[Response] " + String.valueOf(result));
		
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
	    out.write(String.valueOf(result));
	}
}
