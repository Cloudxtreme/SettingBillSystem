package com.settingbill.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.settingbill.bean.User;
import com.settingbill.dao.DBService;

public class Login extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("\n========== Login.java ==========");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		DBService dbservice = new DBService();
		User user = new User();
		user = dbservice.validateLogin(username, password);
		if(user == null) {
			int userAmount = dbservice.hasSameName(username);
			if(userAmount<1) {
				request.setAttribute("error", "该账号不可用");
				request.getRequestDispatcher("login.jsp").forward(request, response);
				System.err.println("[ERROR] username not available");
			} else {
				request.setAttribute("error", "账号或密码错误");
				request.getRequestDispatcher("login.jsp").forward(request, response);
				System.err.println("[ERROR] wrong username or password");
			}
		} else {
			System.out.println("     User: " + " - " + user.getDisplayName() + "(" + user.getUsername() + ")" + " UID: " + user.getID());
			System.out.println(" Password: " + user.getPassword());
			System.out.println("Authority: " + user.getAuthority());
			System.out.println("  Company: " + user.getCompanyName() + "(" + user.getCompanyID() + ")");
			HttpSession session = request.getSession();
			session.setAttribute("uid", user.getID());
			session.setAttribute("username", user.getUsername());
			session.setAttribute("displayName", user.getDisplayName());
			session.setAttribute("companyID", user.getCompanyID());
			session.setAttribute("companyName", user.getCompanyName());
			session.setAttribute("authority", user.getAuthority());
			session.setAttribute("status", user.getStatus());
//			Cookie c_uid = new Cookie("uid", String.valueOf(user.getID()));
//			Cookie c_authority = new Cookie("authority", String.valueOf(user.getAuthority()));
//			Cookie c_companyID = new Cookie("companyID", String.valueOf(user.getCompanyID()));
			
			response.setCharacterEncoding("utf-8");
			response.setHeader("Cache-Control", "no-cache");	// 不对页面进行缓存，再次访问时将从服务器重新获取最新版本
			response.setHeader("Cache-Control", "no-store");	// 任何情况下都不缓存页面
			response.setDateHeader("Expires", 0);
			response.setHeader("Pragma", "no-cache");
//			response.addCookie(c_uid);
//			response.addCookie(c_authority);
//			response.addCookie(c_companyID);
			if(user.getAuthority() == 5) {
				response.sendRedirect("system.jsp");
			} else {
				response.sendRedirect("index.jsp");
			}
			System.out.println("[INFO] user " + (String)session.getAttribute("displayName") + "(" + (String)session.getAttribute("username") + ") login");
		}
	}
}
