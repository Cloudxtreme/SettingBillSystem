package com.settingbill.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Logout extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("\n========== Logout.java ==========");
		HttpSession session = request.getSession();
		String displayName = (String) session.getAttribute("displayName");
		String username = (String) session.getAttribute("username");
		session.invalidate();
		response.sendRedirect("/SettingBillSystem/login.jsp");
		System.out.println("[INFO] user " + displayName + "(" + username + ") logout");
	}

	
}
