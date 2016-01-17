package com.settingbill.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.settingbill.dao.DBService;

public class ValidateTname extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("\n========== ValidateTname.java ==========");
		String tname = req.getParameter("tname");
		System.out.println("[INFO] tname: " + tname);
		DBService dbservice = new DBService();
		HttpSession session = req.getSession();
		String companyID = String.valueOf(session.getAttribute("companyID"));

		int result = dbservice.validateTname(tname, companyID);
			
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		out.write(String.valueOf(result));
	}
}
