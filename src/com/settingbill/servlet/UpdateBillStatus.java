package com.settingbill.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.settingbill.dao.DBService;

public class UpdateBillStatus extends HttpServlet {
	
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
		System.out.println("\n========== UpdateBillStatus.java ==========");
		DBService dbservice = new DBService();
		HttpSession session = req.getSession();
		String billcode = req.getParameter("billcode");
		String action = req.getParameter("action");
		String remarkType = req.getParameter("remarkType");
		String remark = req.getParameter("remark");
		String assignTo = req.getParameter("assignTo");
		String username = (String) session.getAttribute("username");
		System.out.println("  billcode: " + billcode);
		System.out.println("    action: " + action);
		System.out.println("remarkType: " + remarkType);
		System.out.println("    remark: " + remark);
		System.out.println("  assignTo: " + assignTo);
		System.out.println("  username: " + username);
		
		int result = 0;
		if(remark != null && remarkType != null) {
			result = dbservice.updateBillStatus(billcode, action, username, remarkType, remark);
		} else {
			result = dbservice.updateBillStatus(billcode, action, username);
		}
		
		if(assignTo != null) {
			result = dbservice.assignTo(billcode, assignTo);
		}
		System.out.println("[Response] " + String.valueOf(result));
		
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
	    out.write(String.valueOf(result));
	}
}
