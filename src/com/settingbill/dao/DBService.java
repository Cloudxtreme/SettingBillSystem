package com.settingbill.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.settingbill.bean.Bill;
import com.settingbill.bean.BillDetail;
import com.settingbill.bean.Template;
import com.settingbill.bean.TemplateDetail;
import com.settingbill.bean.User;
import com.settingbill.util.BillStatus;

public class DBService
{
	/**
	 *  Create Account
	 * @param user the user you want to add
	 */
	public void addUser(User user) {
		Connection conn = DBConnection.getConnection();
		if(conn != null) {
			System.out.println("[DB] DB Connectted on adding user");
		}
		PreparedStatement ps = null;
		String sql = "INSERT INTO Users(Username, DisplayName, Authority, Company) VALUES(?, ?, ?, ?)";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getDisplayName());
			ps.setInt(3, user.getAuthority());
			ps.setInt(4, user.getCompanyID());
			ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(ps, conn);
		}
	}
	
	/**
	 *  Validate Login
	 * @param username username
	 * @param password password
	 * @return if success on validation, return the user. otherwise return null
	 */
	public User validateLogin(String username, String password) {
		Connection conn = DBConnection.getConnection();
		if(conn != null) {
			System.out.println("[DB] DB Connectted on validating login");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT u.ID, u.UserName, u.DisplayName, u.Password, u.Authority, u.CompanyID, "
				+ "(SELECT c.CompanyName FROM company c WHERE u.CompanyID = c.ID ) AS 'CompanyName', "
				+ "u.`Status`, u.AddTime, u.LastUpdateTime "
				+ "FROM Users u WHERE Username = ? AND Password = PASSWORD(?) AND `Status`>0";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if(rs.next()) {
				User user = new User();
				user.setID(rs.getInt("ID"));
				user.setUsername(rs.getString("Username"));
				user.setDisplayName(rs.getString("DisplayName"));
				user.setPassword(rs.getString("Password"));
				user.setAuthority(rs.getInt("Authority"));
				user.setCompanyID(rs.getInt("CompanyID"));
				user.setCompanyName(rs.getString("CompanyName"));
				user.setStatus(rs.getInt("Status"));
				user.setAddTime(rs.getDate("AddTime"));
				user.setLastUpdateTime(rs.getDate("LastUpdateTime"));
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(rs, ps, conn);
		}
		return null;
	}
	
	/**
	 *  check if user exist, or multiple users share the same name
	 *  @param username keyword to check
	 *  @return amount of matched users
	 */
	public int hasSameName(String username) {
		Connection conn = DBConnection.getConnection();
		if(conn != null) {
			System.out.println("[DB] DB Connectted on checking same name");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM Users WHERE Username = ? AND `Status`>0";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			rs.last();
			int rows = rs.getRow();
			System.out.println("[DB] " + rows + " user(s) found");
			return rows;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(rs, ps, conn);
		}
		System.out.println("[Info] No user found");
		return 0;
	}
	
	/**
	 * Load bills by company and authority of current user
	 * @param authority the authority of the user
	 * @param companyID the company which the user belongs to
	 * @param uid current login user
	 * @return a JSONObject which contains infos about bills
	 */
	public JSONObject loadBills(int authority, int companyID, String username) {
		System.out.println("\n========== Loading bills ==========");
		String sql = "";
		switch(authority) {
			case 1:
				sql = "SELECT b.ID, "
						+ "b.BillCode, "
						+ "(SELECT CompanyName FROM Company c WHERE c.ID = b.LocationToID) AS 'LocationTo', "
						+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.CreaterID) AS 'Creater', "
						+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.LastEditorID) AS 'LastEditor', "
						+ "b.CreateTime, "
						+ "b.LastEditTime, "
						+ "(SELECT `Name` FROM BillStatus s WHERE s.`ID` = b.`Status`) AS 'Status' "
						+ "FROM settingbill b "
						+ "WHERE b.LocationFromID = '" + companyID + "' AND b.`Status` IN(100, 150, -100, 200, -200, 300, -300) ORDER BY b.CreateTime DESC";
				break;
			case 2:
				sql = "SELECT b.ID, "
						+ "b.BillCode, "
						+ "(SELECT CompanyName FROM Company c WHERE c.ID = b.LocationFromID) AS 'LocationTo', "
						+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.CreaterID) AS 'Creater', "
						+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.LastEditorID) AS 'LastEditor', "
						+ "b.CreateTime, "
						+ "b.LastEditTime, "
						+ "(SELECT `Name` FROM BillStatus s WHERE s.`ID` = b.`Status`) AS 'Status' "
						+ "FROM settingbill b "
						+ "WHERE b.LocationFromID = '" + companyID + "' AND b.`Status` IN(150, 200, -200, 300, -300) ORDER BY b.CheckTime DESC";
				break;
			case 3:
				sql = "SELECT b.ID, "
						+ "b.BillCode, "
						+ "(SELECT CompanyName FROM Company c WHERE c.ID = b.LocationFromID) AS 'LocationFrom', "
						+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.CreaterID) AS 'Creater', "
						+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.LastEditorID) AS 'LastEditor', "
						+ "b.CreateTime, "
						+ "b.LastEditTime, "
						+ "(SELECT `Name` FROM BillStatus s WHERE s.`ID` = b.`Status`) AS 'Status' "
						+ "FROM settingbill b "
						+ "WHERE b.LocationToID = '" + companyID + "' AND b.`Status` IN(200, 300, 400) ORDER BY b.CheckTime DESC";
				break;
			case 4:
				sql = "SELECT b.ID, "
						+ "b.BillCode, "
						+ "(SELECT CompanyName FROM Company c WHERE c.ID = b.LocationFromID) AS 'LocationFrom', "
						+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.CreaterID) AS 'Creater', "
						+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.LastEditorID) AS 'LastEditor', "
						+ "b.CreateTime, "
						+ "b.LastEditTime, "
						+ "(SELECT `Name` FROM BillStatus s WHERE s.`ID` = b.`Status`) AS 'Status' "
						+ "FROM settingbill b "
						+ "WHERE b.LocationToID = '" + companyID + "' AND b.`Status`=300 AND ExecuterID=" + username + " ORDER BY b.ReceiveTime DESC";
				break;
			default:
				return null;
		}
		System.out.println("[SQL] " + sql);
		
		Connection conn = DBConnection.getConnection();
		if(conn != null) {
			System.out.println("[DB] DB Connectted on retriving bills");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.last();
			int rows = rs.getRow();
			System.out.println("[Info] " + rows + " found");
			JSONObject bills = new JSONObject();
			bills.put("authority", authority);
			if(rows>0) {
				ArrayList<Bill> list = new ArrayList<Bill>();
				rs.beforeFirst();
				if(authority == 1 || authority == 2 || authority == 5) {
					while(rs.next()) {
						Bill bill = new Bill();
						bill.setID(rs.getInt("ID"));
						bill.setBillCode(rs.getString("BillCode"));
						bill.setLocationToName(rs.getString("LocationTo"));
						bill.setCreaterName(rs.getString("Creater"));
						bill.setLastEditorName(rs.getString("LastEditor"));
						bill.setCreateTime(rs.getDate("CreateTime"));
						bill.setLastEditTime(rs.getDate("LastEditTime"));
						bill.setStatusName(rs.getString("Status"));
//						String txt = JSON.toJSONString(bill);
//						System.out.println("[JSON]" + txt);
						list.add(bill);
					}
				} else if(authority == 3 || authority == 4){
					while(rs.next()) {
						Bill bill = new Bill();
						bill.setID(rs.getInt("ID"));
						bill.setBillCode(rs.getString("BillCode"));
						bill.setLocationFromName(rs.getString("LocationFrom"));
						bill.setCreaterName(rs.getString("Creater"));
						bill.setLastEditorName(rs.getString("LastEditor"));
						bill.setCreateTime(rs.getDate("CreateTime"));
						bill.setLastEditTime(rs.getDate("LastEditTime"));
						bill.setStatusName(rs.getString("Status"));
//						String txt = JSON.toJSONString(bill);
//						System.out.println("[JSON]" + txt);
						list.add(bill);
					}
				}
				bills.put("success", true);
				bills.put("data", list);
				return bills;
			} else {
				bills.put("success", false);
				System.out.println("[Info] No records found");
				return bills;
			}
		} catch (SQLException e) {
			System.err.println("[Error]SQL Exception occured");
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(rs, ps, conn);
		}
		return null;
	}
	
	/**
	 * Load done bills by company and authority of current user
	 * @param authority the authority of the user
	 * @param companyID the company which the user belongs to
	 * @param uid current login user
	 * @return a JSONObject which contains infos about bills
	 */
	public JSONObject loadSuccessBills(int authority, int companyID, String username) {
		System.out.println("\n========== Loading success bills ==========");
		String sql = "";
		if(authority == 1 || authority == 2) {
			sql = "SELECT b.ID, "
					+ "b.BillCode, "
					+ "(SELECT CompanyName FROM Company c WHERE c.ID = b.LocationToID) AS 'LocationTo', "
					+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.CreaterID) AS 'Creater', "
					+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.LastEditorID) AS 'LastEditor', "
					+ "b.CreateTime, "
					+ "b.LastEditTime, "
					+ "(SELECT `Name` FROM BillStatus s WHERE s.`ID` = b.`Status`) AS 'Status' "
					+ "FROM settingbill b "
					+ "WHERE b.LocationFromID = '" + companyID + "' AND b.`Status` IN(400, 500) ORDER BY b.ReportFeedbackTime, b.ExecuteFeedbackTime, b.AuditTime DESC";
		} else if(authority == 3) {
			sql = "SELECT b.ID, "
					+ "b.BillCode, "
					+ "(SELECT CompanyName FROM Company c WHERE c.ID = b.LocationFromID) AS 'LocationFrom', "
					+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.CreaterID) AS 'Creater', "
					+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.LastEditorID) AS 'LastEditor', "
					+ "b.CreateTime, "
					+ "b.LastEditTime, "
					+ "(SELECT `Name` FROM BillStatus s WHERE s.`ID` = b.`Status`) AS 'Status' "
					+ "FROM settingbill b "
					+ "WHERE b.LocationToID = '" + companyID + "' AND b.`Status`=500 ORDER BY b.ReportFeedbackTime DESC";
		} else if(authority == 4) {
			sql = "SELECT b.ID, "
					+ "b.BillCode, "
					+ "(SELECT CompanyName FROM Company c WHERE c.ID = b.LocationFromID) AS 'LocationFrom', "
					+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.CreaterID) AS 'Creater', "
					+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.LastEditorID) AS 'LastEditor', "
					+ "b.CreateTime, "
					+ "b.LastEditTime, "
					+ "(SELECT `Name` FROM BillStatus s WHERE s.`ID` = b.`Status`) AS 'Status' "
					+ "FROM settingbill b "
					+ "WHERE b.LocationToID = '" + companyID + "' AND b.`Status`=400 AND ExecuterID=" + username + " ORDER BY b.ExecuteFeedbackTime DESC";
		} else {
			return null;
		}
		System.out.println("[SQL] " + sql);
		
		Connection conn = DBConnection.getConnection();
		if(conn != null) {
			System.out.println("[DB] DB Connectted on retriving finished bills");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.last();
			int rows = rs.getRow();
			JSONObject bills = new JSONObject();
			bills.put("authority", authority);
			if(rows>0) {
				ArrayList<Bill> list = new ArrayList<Bill>();
				rs.beforeFirst();
				if(authority == 1 || authority == 2 || authority == 5) {
					while(rs.next()) {
						Bill bill = new Bill();
						bill.setID(rs.getInt("ID"));
						bill.setBillCode(rs.getString("BillCode"));
						bill.setLocationToName(rs.getString("LocationTo"));
						bill.setCreaterName(rs.getString("Creater"));
						bill.setLastEditorName(rs.getString("LastEditor"));
						bill.setCreateTime(rs.getDate("CreateTime"));
						bill.setLastEditTime(rs.getDate("LastEditTime"));
						bill.setStatusName(rs.getString("Status"));
//						String txt = JSON.toJSONString(bill);
//						System.out.println("[JSON] " + txt);
						list.add(bill);
					}
				} else if(authority == 3 || authority == 4){
					while(rs.next()) {
						Bill bill = new Bill();
						bill.setID(rs.getInt("ID"));
						bill.setBillCode(rs.getString("BillCode"));
						bill.setLocationFromName(rs.getString("LocationFrom"));
						bill.setCreaterName(rs.getString("Creater"));
						bill.setLastEditorName(rs.getString("LastEditor"));
						bill.setCreateTime(rs.getDate("CreateTime"));
						bill.setLastEditTime(rs.getDate("LastEditTime"));
						bill.setStatusName(rs.getString("Status"));
//						String txt = JSON.toJSONString(bill);
//						System.out.println("[JSON] " + txt);
						list.add(bill);
					}
				}
				bills.put("success", true);
				bills.put("data", list);
				return bills;
			} else {
				bills.put("success", false);
				System.out.println("[Info] No records found");
				return bills;
			}
		} catch (SQLException e) {
			System.err.println("[Error] SQL Exception occured");
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(rs, ps, conn);
		}
		return null;
	}
	
	/**
	 * Load failed bills by company and authority of current user
	 * @param authority the authority of the user
	 * @param companyID the company which the user belongs to
	 * @param uid current login user
	 * @return a JSONObject which contains infos about bills
	 */
	public JSONObject loadFailBills(int authority, int companyID) {
		System.out.println("\n========== Loading fail bills ==========");
		String sql = "";
		if(authority == 1 || authority == 2) {
			sql = "SELECT b.ID, "
					+ "b.BillCode, "
					+ "(SELECT CompanyName FROM Company c WHERE c.ID = b.LocationToID) AS 'LocationTo', "
					+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.CreaterID) AS 'Creater', "
					+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.LastEditorID) AS 'LastEditor', "
					+ "b.CreateTime, "
					+ "b.LastEditTime, "
					+ "(SELECT `Name` FROM BillStatus s WHERE s.`ID` = b.`Status`) AS 'Status' "
					+ "FROM settingbill b "
					+ "WHERE b.LocationFromID = '" + companyID + "' AND b.`Status`=-500 ORDER BY b.TrashTime DESC";
		} else if(authority == 3) {
			sql = "SELECT b.ID, "
					+ "b.BillCode, "
					+ "(SELECT CompanyName FROM Company c WHERE c.ID = b.LocationFromID) AS 'LocationFrom', "
					+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.CreaterID) AS 'Creater', "
					+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.LastEditorID) AS 'LastEditor', "
					+ "b.CreateTime, "
					+ "b.LastEditTime, "
					+ "(SELECT `Name` FROM BillStatus s WHERE s.`ID` = b.`Status`) AS 'Status' "
					+ "FROM settingbill b "
					+ "WHERE b.LocationToID = '" + companyID + "' AND b.`Status`=-300 ORDER BY b.ReceiveTime DESC";
		} else {
			return null;
		}
		System.out.println("[SQL] " + sql);
		
		Connection conn = DBConnection.getConnection();
		if(conn != null) {
			System.out.println("[DB] DB Connectted on retriving bills");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.last();
			int rows = rs.getRow();
			JSONObject bills = new JSONObject();
			bills.put("authority", authority);
			if(rows>0) {
				ArrayList<Bill> list = new ArrayList<Bill>();
				rs.beforeFirst();
				if(authority == 1 || authority == 2 || authority == 5) {
					while(rs.next()) {
						Bill bill = new Bill();
						bill.setID(rs.getInt("ID"));
						bill.setBillCode(rs.getString("BillCode"));
						bill.setLocationToName(rs.getString("LocationTo"));
						bill.setCreaterName(rs.getString("Creater"));
						bill.setLastEditorName(rs.getString("LastEditor"));
						bill.setCreateTime(rs.getDate("CreateTime"));
						bill.setLastEditTime(rs.getDate("LastEditTime"));
						bill.setStatusName(rs.getString("Status"));
//						String txt = JSON.toJSONString(bill);
//						System.out.println("[JSON] " + txt);
						list.add(bill);
					}
				} else if(authority == 3){
					while(rs.next()) {
						Bill bill = new Bill();
						bill.setID(rs.getInt("ID"));
						bill.setBillCode(rs.getString("BillCode"));
						bill.setLocationFromName(rs.getString("LocationFrom"));
						bill.setCreaterName(rs.getString("Creater"));
						bill.setLastEditorName(rs.getString("LastEditor"));
						bill.setCreateTime(rs.getDate("CreateTime"));
						bill.setLastEditTime(rs.getDate("LastEditTime"));
						bill.setStatusName(rs.getString("Status"));
//						String txt = JSON.toJSONString(bill);
//						System.out.println("[JSON] " + txt);
						list.add(bill);
					}
				}
				bills.put("success", true);
				bills.put("data", list);
				return bills;
			} else {
				bills.put("success", false);
				System.out.println("[Info] No records found");
				return bills;
			}
		} catch (SQLException e) {
			System.err.println("[Error] SQL Exception occured");
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(rs, ps, conn);
		}
		return null;
	}
	
	/**
	 * load details of a certain bill
	 * @param billcode as a reference to the bill
	 * @return the details of the bill
	 */
	@SuppressWarnings("resource")
	public JSONObject loadDetails(String billcode, int authority) {
		System.out.println("\n========== Loading bill details ==========");
		JSONObject details = new JSONObject();
		System.out.println("[Info] BillCode: " + billcode);
		if(billcode != null) {
			String sql1 = "SELECT b.ID, "
					+ "b.BillCode, "
					+ "(SELECT c.CompanyName FROM Company c WHERE c.ID = b.LocationFromID) AS 'LocationFrom', "
					+ "(SELECT c.CompanyName FROM Company c WHERE c.ID = b.LocationToID) AS 'LocationTo', "
					+ "(SELECT t.TName FROM TransformerStation t WHERE t.ID = b.DeviceBelongingID) AS 'DeviceBelonging', "
					+ "(SELECT l.LineName FROM Line l WHERE l.ID = b.LineID) AS 'Line', "
					+ "(SELECT d.DeviceName FROM Device d WHERE d.ID = b.DeviceID) AS 'Device', "
					+ "(SELECT d.DeviceModel FROM Device d WHERE d.ID = b.DeviceID) AS 'Model', "
					+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.CreaterID) AS 'Creater', "
					+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.LastEditorID) AS 'LastEditor', "
					+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.CheckerID) AS 'Checker', "
					+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.RecovererID) AS 'Recoverer', "
					+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.TrasherID) AS 'Trasher', "
					+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.AuditorID) AS 'Auditor', "
					+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.ReceiverID) AS 'Receiver', "
					+ "(SELECT DisplayName FROM users u WHERE u.UserName = b.ExecuterID) AS 'Executer', "
					+ "b.CreateTime, "
					+ "b.LastEditTime, "
					+ "b.CheckTime, "
					+ "b.RecoverTime, "
					+ "b.TrashTime, "
					+ "b.AuditTime, "
					+ "b.ReceiveTime, "
					+ "b.ReportFeedbackTime, "
					+ "b.ExecuteFeedbackTime, "
					+ "b.Remark, "
					+ "b.RecoverRemark, "
					+ "b.TrashRemark, "
					+ "b.AuditCancelRemark, "
					+ "b.ReceiveCancelRemark, "
					+ "b.ReportFeedbackRemark, "
					+ "b.ExecuteFeedbackRemark, "
					+ "b.`Status`, "
					+ "b.TemplateCode, "
					+ "(SELECT t.TemplateName FROM template t WHERE t.TemplateCode = b.TemplateCode) AS 'TemplateName' "
					+ "FROM settingbill b "
					+ "WHERE b.BillCode = '" + billcode + "'";
			
			Connection conn = DBConnection.getConnection();
			if(conn != null) {
				System.out.println("[DB] DB Connectted on retriving bill details");
			}
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			
			try {
				// get basic infos
				ps = conn.prepareStatement(sql1);
				rs = ps.executeQuery();
				if(rs.next()) {
					Bill bill = new Bill();
					bill.setID(rs.getInt("ID"));
					bill.setBillCode(rs.getString("BillCode"));
					bill.setLocationFromName(rs.getString("LocationFrom"));
					bill.setLocationToName(rs.getString("LocationTo"));
					bill.setDeviceBelongingName(rs.getString("DeviceBelonging"));
					bill.setLineName(rs.getString("Line"));
					bill.setDeviceName(rs.getString("Device"));
					bill.setDeviceModelName(rs.getString("Model"));
					bill.setCreaterName(rs.getString("Creater"));
					bill.setLastEditorName(rs.getString("LastEditor"));
					bill.setCheckerName(rs.getString("Checker"));
					bill.setRecovererName(rs.getString("Recoverer"));
					bill.setTrasherName(rs.getString("Trasher"));
					bill.setAuditorName(rs.getString("Auditor"));
					bill.setReceiverName(rs.getString("Receiver"));
					bill.setExecuterName(rs.getString("Executer"));
					bill.setCreateTime(rs.getTimestamp("CreateTime"));
					bill.setLastEditTime(rs.getTimestamp("LastEditTime"));
					bill.setCheckTime(rs.getTimestamp("CheckTime"));
					bill.setRecoverTime(rs.getTimestamp("RecoverTime"));
					bill.setTrashTime(rs.getTimestamp("TrashTime"));
					bill.setAuditTime(rs.getTimestamp("AuditTime"));
					bill.setReceiveTime(rs.getTimestamp("ReceiveTime"));
					bill.setReportFeedbackTime(rs.getTimestamp("ReportFeedbackTime"));
					bill.setExecuteFeedbackTime(rs.getTimestamp("ExecuteFeedbackTime"));
					bill.setRemark(rs.getString("Remark"));
					bill.setRecoverRemark(rs.getString("RecoverRemark"));
					bill.setTrashRemark(rs.getString("TrashRemark"));
					bill.setAuditCancelRemark(rs.getString("AuditCancelRemark"));
					bill.setReceiveCancelRemark(rs.getString("ReceiveCancelRemark"));
					bill.setReportFeedbackRemark(rs.getString("ReportFeedbackRemark"));
					bill.setExecuteFeedbackRemark(rs.getString("ExecuteFeedbackRemark"));
					bill.setStatus(rs.getInt("Status"));
					bill.setTemplateCode(rs.getString("TemplateCode"));
					bill.setTemplateName(rs.getString("TemplateName"));
					System.out.println("tcode: " + bill.getTemplateCode() + "  " + rs.getString("TemplateCode"));
					System.out.println("tname: " + bill.getTemplateName() + "  " + rs.getString("TemplateName"));
//					String basic_txt = JSON.toJSONString(bill);
//					System.out.println("[JSON] Basic: " + basic_txt);
					details.put("basic", bill);
					
					// get details
					String sql2 = "SELECT * FROM settingbilldetail WHERE BillCode = '" + billcode + "'";
					ps = conn.prepareStatement(sql2);
					rs = ps.executeQuery();
					rs.last();
					int rows = rs.getRow();
					System.out.println("[DB] " + rows + " details found");
					if(rows>0) {
						ArrayList<BillDetail> list = new ArrayList<BillDetail>();
						rs.beforeFirst();
						while(rs.next()) {
							BillDetail detail = new BillDetail();
							detail.setName(rs.getString("Name"));
							detail.setBefore1(rs.getString("Before1"));
							detail.setBefore2(rs.getString("Before2"));
							detail.setAfter1(rs.getString("After1"));
							detail.setAfter2(rs.getString("After2"));
							list.add(detail);
						}
						details.put("details", list);
					}
					details.put("authority", authority);
					details.put("success", true);
				} else {
					// if no record found in the database
					details.put("success", false);
					System.out.println("[Info] No records found");
				}
			} catch (SQLException e) {
				System.err.println("[SQL] SQLException occured");
				e.printStackTrace();
			} finally {
				DBConnection.closeConnection(rs, ps, conn);
			}
		} else {
			// if billcode is null
			details.put("success", false);
		}
		return details;
	}
	
	/**
	 * Update the status of certain bill
	 * @param billcode billcode of the bill
	 * @param action action to take
	 * @param username for executer
	 * @return rows affected, return -1 if error occured
	 */
	public int updateBillStatus(String billcode, String action, String username) {
		System.out.println("\n========== updateBillStatus ==========");
		System.out.println("[Request] action: " + action);
		System.out.println(action == "pass");
		Connection conn = DBConnection.getConnection();
		if(conn != null) {
			System.out.println("[DB] DB Connectted on updating bill status");
		}
		PreparedStatement ps = null;
		int status = BillStatus.initStatus().get(action);
		
		String who = "";
		String when = "";
		switch(action) {
			case "check": 		who = "CheckerID";		when = "CheckTime";		break;
			case "recover":		who = "RecovererID";	when = "RecoverTime";	break;
			case "pass":		who = "AuditorID";		when = "AuditTime";		break;
			case "receive":		who = "ReceiverID";		when = "ReceiveTime";	break;
		}
		
		String sql = "UPDATE settingbill SET `Status` = " + status
				+ ", " + who + " = " + username
				+ ", " + when + " = NOW()"
				+ " WHERE BillCode = '" + billcode + "'";
		if(action.equals("pass")) {
			sql = "UPDATE settingbill SET `Status` = " + status
				+ ", " + who + " = " + username
				+ ", " + when + " = NOW()"
				+ ", AuditCancelRemark=null"
				+ " WHERE BillCode = '" + billcode + "'";
			System.out.println("sql for pass changed");
		} else if(action.equals("receive")) {
			sql = "UPDATE settingbill SET `Status` = " + status
				+ ", " + who + " = " + username
				+ ", " + when + " = NOW()"
				+ ", ReceiveCancelRemark=null"
				+ " WHERE BillCode = '" + billcode + "'";
			System.out.println("sql for receive changed");
		}
		System.out.println("[SQL] " + sql);

		try {
			ps = conn.prepareStatement(sql);
			int affected = ps.executeUpdate();
			System.out.println("[DB] " + affected + " rows affected");
			return affected;
		} catch (SQLException e) {
			System.err.println("[Error] SQLException occured while updating bill status");
			e.printStackTrace();
			return -1;
		} finally {
			DBConnection.closeConnection(ps, conn);
		}
	}
	
	/**
	 * Update the status of certain bill
	 * @param billcode billcode of the bill
	 * @param action action to take
	 * @param username for executer
	 * @param remarkType remark type
	 * @param remark remark content 
	 * @return rows affected, return -1 if error occured
	 */
	public int updateBillStatus(String billcode, String action, String username, String remarkType, String remark) {
		System.out.println("\n========== updateBillStatus(with remark) ==========");
		Connection conn = DBConnection.getConnection();
		if(conn != null) {
			System.out.println("[DB] DB Connectted on updating bill status");
		}
		PreparedStatement ps = null;
		int status = BillStatus.initStatus().get(action);
		
		String who = "";
		String when = "";
		switch(action) {
			case "trash":		who = "TrasherID";		when = "TrashTime";				break;
			case "refuse":		who = "AuditorID";		when = "AuditTime";				break;
			case "reject":		who = "ReceiverID";		when = "ReceiveTime";			break;
			case "report":		who = "ReporterID";		when = "ReportFeedbackTime";	break;
			case "finish":		who = "ExecuterID";		when = "ExecuteFeedbackTime";	break;
		}
		
		String sql = "UPDATE settingbill SET `Status` = " + status 
				+ ", " + who + " = " + username
				+ ", " + when + " = NOW()"
				+ ", " + remarkType + " = '" + remark + "'" 
				+ " WHERE BillCode = '" + billcode + "'";
		System.out.println("[SQL] " + sql);
		try {
			ps = conn.prepareStatement(sql);
			int affected = ps.executeUpdate();
			System.out.println("[DB] " + affected + " rows affected");
			return affected;
		} catch (SQLException e) {
			System.err.println("[Error] SQLException occured while updating bill status with remark");
			e.printStackTrace();
			return -1;
		} finally {
			DBConnection.closeConnection(ps, conn);
		}
	}

	/**
	 * assign bill to executer
	 * @param billcode bill to assign
	 * @param assignTo executer to be assigned to
	 * @return rows affected
	 */
	public int assignTo(String billcode, String assignTo) {
		System.out.println("\n========== assignTo ==========");
		Connection conn = DBConnection.getConnection();
		if(conn != null) {
			System.out.println("[DB] DB Connectted on updating bill status");
		}
		PreparedStatement ps = null;
		
		String sql = "UPDATE settingbill SET `ExecuterID` = " + assignTo	+ " WHERE BillCode = '" + billcode + "'";
		System.out.println("[SQL] " + sql);
		try {
			ps = conn.prepareStatement(sql);
			int affected = ps.executeUpdate();
			System.out.println("[DB] " + affected + " rows affected");
			return affected;
		} catch (SQLException e) {
			System.err.println("[Error] SQLException occured while assigning");
			e.printStackTrace();
			return -1;
		} finally {
			DBConnection.closeConnection(ps, conn);
		}
	}

	/**
	 * load details for edit
	 * @param billcode billcode
	 * @return bill detail in JSON format
	 */
	@SuppressWarnings("resource")
	public JSONObject loadDetailsForEdit(String billcode) {
		System.out.println("\n========== Loading bill details for edit ==========");
		System.out.println("[Info] BillCode: " + billcode);
		JSONObject obj = new JSONObject();

		Connection conn = DBConnection.getConnection();
		if(conn != null) {
			System.out.println("[DB] DB Connectted on retriving bill details");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		// Query brief
		try {
			String sql_brief = "SELECT b.locationFromID, (SELECT c.CompanyName FROM company c WHERE c.ID = b.locationFromID) AS 'locationFromName', b.locationToID, b.DeviceBelongingID, b.LineID, b.DeviceID, (SELECT d.DeviceModel FROM device d WHERE d.ID = b.DeviceID ) AS 'DeviceModelName', b.Remark, b.TemplateCode, (SELECT t.TemplateName FROM template t WHERE t.TemplateCode = b.TemplateCode) AS 'TemplateName' FROM settingbill b WHERE BillCode='" + billcode + "'";
			ps = conn.prepareStatement(sql_brief);
			rs = ps.executeQuery();
			if(rs.next()) {
				/*Bill bill = new Bill();
				bill.setBillCode(billcode);
				bill.setLocationFromName(rs.getString("locationFromName"));
				bill.setLocationToID(rs.getInt("locationToID"));
				bill.setDeviceBelongingID(rs.getInt("DeviceBelongingID"));
				bill.setLineID(rs.getInt("LineID"));
				bill.setDeviceID(rs.getInt("DeviceID"));
				bill.setDeviceModelName(rs.getString("DeviceModelName"));*/
				JSONObject brief = new JSONObject();
				brief.put("billcode", billcode);
				brief.put("locationFromID", rs.getInt("locationFromID"));
				brief.put("locationFromName", rs.getString("locationFromName"));
				brief.put("locationToID", rs.getInt("locationToID"));
				brief.put("deviceBelongingID", rs.getInt("DeviceBelongingID"));
				brief.put("lineID", rs.getInt("LineID"));
				brief.put("deviceID", rs.getInt("DeviceID"));
				brief.put("deviceModelName", rs.getString("DeviceModelName"));
				brief.put("remark", rs.getString("Remark"));
				brief.put("templateCode", rs.getString("TemplateCode"));
				brief.put("templateName", rs.getString("TemplateName"));
				obj.put("brief", brief);
			}
		
			// 	Query company
			String sql_company = "SELECT ID, CompanyName FROM company WHERE Belong=" + obj.getJSONObject("brief").getIntValue("locationFromID");
			ps = conn.prepareStatement(sql_company);
			rs = ps.executeQuery();
			JSONObject company = new JSONObject();
			while(rs.next()) {
				company.put(String.valueOf(rs.getInt("ID")), rs.getString("CompanyName"));
			}
			obj.put("company", company);
		
			// Query devicebelonging
			String sql_devicebelonging = "SELECT ID, TName FROM transformerstation WHERE Belong=" + obj.getJSONObject("brief").getIntValue("locationToID") + " ORDER BY TName";
			ps = conn.prepareStatement(sql_devicebelonging);
			rs = ps.executeQuery();
			JSONObject devicebelonging = new JSONObject();
			while(rs.next()) {
				devicebelonging.put(String.valueOf(rs.getInt("ID")), rs.getString("TName"));
			}
			obj.put("devicebelonging", devicebelonging);
		
			// Query line
			String sql_line = "SELECT t.LineID, (SELECT l.LineName FROM line l WHERE l.ID = t.LineID) AS 'LineName' FROM transformerstation t WHERE t.ID=" + obj.getJSONObject("brief").getIntValue("deviceBelongingID");
			ps = conn.prepareStatement(sql_line);
			rs = ps.executeQuery();
//			JSONObject line = new JSONObject();
//			System.out.println("obj.getJSONObject(\"brief\").getIntValue(\"deviceBelongingID\"): " + obj.getJSONObject("brief").getIntValue("deviceBelongingID"));
			while(rs.next()) {
//				line.put(String.valueOf(rs.getInt("LineID")), rs.getString("LineName"));
				obj.put("lineID", rs.getString("LineID"));
				obj.put("lineName", rs.getString("LineName"));
			}
		
			// Query device
			String sql_device = "SELECT ID, DeviceName FROM device WHERE Belong=" + obj.getJSONObject("brief").getIntValue("deviceBelongingID") + " GROUP BY DeviceName ORDER BY DeviceName";
			ps = conn.prepareStatement(sql_device);
			rs = ps.executeQuery();
			JSONObject device = new JSONObject();
			while(rs.next()) {
				device.put(String.valueOf(rs.getInt("ID")), rs.getString("DeviceName"));
			}
			obj.put("device", device);
		
			// Query model
			String sql_model = "SELECT ID, DeviceModel FROM device WHERE DeviceName = '" + obj.getJSONObject("device").getString( obj.getJSONObject("brief").getString("deviceID") ) + "' AND Belong = " + obj.getJSONObject("brief").getString("deviceBelongingID") + " ORDER BY DeviceModel";
//			System.out.println("[DB] " + sql_model);
			ps = conn.prepareStatement(sql_model);
			rs = ps.executeQuery();
			JSONObject model = new JSONObject();
			while(rs.next()) {
				model.put(String.valueOf(rs.getInt("ID")), rs.getString("DeviceModel"));
			}
			obj.put("model", model);
		
			// Query detail
			String sql_detail = "SELECT * FROM settingbilldetail WHERE BillCode = '" + billcode + "'";
			ps = conn.prepareStatement(sql_detail);
			rs = ps.executeQuery();
			rs.last();
			int rows = rs.getRow();
			System.out.println("[DB] " + rows + " details found");
			if(rows>0) {
				ArrayList<BillDetail> list = new ArrayList<BillDetail>();
				rs.beforeFirst();
				while(rs.next()) {
					BillDetail detail = new BillDetail();
					detail.setName(rs.getString("Name"));
					detail.setBefore1(rs.getString("Before1"));
					detail.setBefore2(rs.getString("Before2"));
					detail.setAfter1(rs.getString("After1"));
					detail.setAfter2(rs.getString("After2"));
					list.add(detail);
				}
				obj.put("details", list);
			}
			obj.put("success", true);
		} catch (SQLException e) {
			System.err.println("[SQL] SQLException occured");
			e.printStackTrace();
			obj.put("success", false);
		} finally {
			DBConnection.closeConnection(rs, ps, conn);
		}
		return obj;
	}
	
	/**
	 * load locationTo for new bill
	 * @param companyID companyID
	 * @return locationTo info in JSON format
	 */
	public JSONObject queryLocationTo(String companyID) {
		System.out.println("\n========== queryLocationTo ==========");
		JSONObject obj = null;
		
		Connection conn = DBConnection.getConnection();
		if(conn != null) {
			System.out.println("[DB] DB Connectted on querying LocationTo");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			// Query LocationTo
			String sql = "SELECT ID, CompanyName FROM company WHERE Belong='" + companyID + "' ORDER BY CompanyName";
			System.out.println("[DB] " + sql);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			obj = new JSONObject();
			while(rs.next()) {
				obj.put(String.valueOf(rs.getInt("ID")), rs.getString("CompanyName"));
			}
		} catch (SQLException e) {
			System.err.println("[ERROR] error occured while querying locationTo");
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(rs, ps, conn);
		}
		System.out.println("[INFO] " + obj);
		return obj;
	}
	
	/**
	 * load template list for new bill
	 * @param companyID companyID
	 * @return template list in JSON format
	 */
	public JSONObject queryTemplate(String companyID) {
		System.out.println("\n========== queryLocationTo ==========");
		JSONObject obj = null;
		
		Connection conn = DBConnection.getConnection();
		if(conn != null) {
			System.out.println("[DB] DB Connectted on querying LocationTo");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			// Query LocationTo
			String sql = "SELECT TemplateCode, TemplateName FROM template WHERE BelongID='" + companyID + "' AND `Status`>0 ORDER BY TemplateName";
			System.out.println("[DB] " + sql);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			obj = new JSONObject();
			while(rs.next()) {
				obj.put(String.valueOf(rs.getString("TemplateCode")), rs.getString("TemplateName"));
			}
		} catch (SQLException e) {
			System.err.println("[ERROR] error occured while querying template");
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(rs, ps, conn);
		}
		System.out.println("[INFO] " + obj);
		return obj;
	}
	
	/**
	 * Query DeviceBelonging by given key
	 * @param key search by locationTo
	 * @return results found
	 */
	public JSONObject queryDeviceBelonging(String key) {
		System.out.println("\n========== queryDeviceBelonging ==========");
		System.out.println("[Info] Key: " + key);
		JSONObject obj = null;
		
		Connection conn = DBConnection.getConnection();
		if(conn != null) {
			System.out.println("[DB] DB Connectted on querying DeviceBelonging");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			// Query devicebelonging
			String sql = "SELECT ID, TName FROM transformerstation WHERE Belong=" + key + " ORDER BY TName";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			obj = new JSONObject();
			while(rs.next()) {
				obj.put(String.valueOf(rs.getInt("ID")), rs.getString("TName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(rs, ps, conn);
		}
		return obj;
	
	}
	
	/**
	 * Query line by given key
	 * @param key search by transform station
	 * @return results found
	 */
	public JSONObject queryLine(String key) {
		System.out.println("\n========== queryLine ==========");
		System.out.println("[Info] Key: " + key);
		JSONObject obj = null;
		
		Connection conn = DBConnection.getConnection();
		if(conn != null) {
			System.out.println("[DB] DB Connectted on querying Line");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		// Query line
		try {
			String sql = "SELECT t.LineID, (SELECT l.LineName FROM line l WHERE l.ID = t.LineID) AS 'LineName' FROM transformerstation t WHERE t.ID=" + key;
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			obj = new JSONObject();
			if(rs.next()) {
				obj.put("LineID",   rs.getString("LineID"));
				obj.put("LineName", rs.getString("LineName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(rs, ps, conn);
		}
		return obj;
	}

	/**
	 * Query Device by given key
	 * @param key search by transform station
	 * @return results found
	 */
	public JSONObject queryDevice(String key) {
		System.out.println("\n========== queryDevice ==========");
		System.out.println("[Info] Key: " + key);
		JSONObject obj = null;
		
		Connection conn = DBConnection.getConnection();
		if(conn != null) {
			System.out.println("[DB] DB Connectted on querying Device");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		// Query device
		try {
			String sql_device = "SELECT ID, DeviceName FROM device WHERE Belong=" + key + " GROUP BY DeviceName ORDER BY DeviceName";
			ps = conn.prepareStatement(sql_device);
			rs = ps.executeQuery();
			obj = new JSONObject();
			while(rs.next()) {
				obj.put(String.valueOf(rs.getInt("ID")), rs.getString("DeviceName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(rs, ps, conn);
		}
		return obj;
	}
	
	/**
	 * Query Model by given key
	 * @param key search by device name and belonging
	 * @return results found
	 */
	public JSONObject queryModel(String key) {
		System.out.println("\n========== queryModel ==========");
		System.out.println("[Info] Key: " + key);
		JSONObject obj = null;
		
		String[] params = key.split(",");
		System.out.println("params.length: " + params.length);
		
		Connection conn = DBConnection.getConnection();
		if(conn != null) {
			System.out.println("[DB] DB Connectted on querying Model");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		// Query model
		try {
			String sql = "SELECT ID, DeviceModel FROM device WHERE DeviceName = '" + params[0] + "' AND Belong = " + params[1] + " ORDER BY DeviceModel";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			obj = new JSONObject();
			while(rs.next()) {
				obj.put(String.valueOf(rs.getInt("ID")), rs.getString("DeviceModel"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(rs, ps, conn);
		}
		return obj;
	}

	/**
	 * add new bill by given data
	 * @param data new data to update
	 * @return rows affected
	 */
	@SuppressWarnings("resource")
	public JSONObject insertBill(JSONObject data, String username, String companyID) {
		System.out.println("\n========== insert bill ==========");
		
		int result_record = -1;
//		int result_item = -1;
		JSONObject obj = new JSONObject();
		Connection conn = DBConnection.getConnection();
		if(conn != null) {
			System.out.println("[DB] DB Connectted on updating bill");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		// get companycode
		String sql_ccode = "SELECT CompanyCode FROM company WHERE ID='" + companyID + "'";
		System.out.println("[DB] " + sql_ccode);
		String ccode = "";
		try {
			ps = conn.prepareStatement(sql_ccode);
			rs = ps.executeQuery();
			if(rs.next()) {
				ccode = rs.getString("CompanyCode");
			}
			System.out.println("[INFO] ccode: " + ccode);
		
			// calculate billcode
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String today = sdf.format(new Date());
			System.out.println("[INFO] today: " + today);
			String sql_billcode = "SELECT COUNT(ID) AS 'count' FROM settingbill WHERE BillCode LIKE '" + today + ccode + "%'";
			System.out.println("[DB] " + sql_billcode);
			int amount = 0;
			String billcode = "";
			ps = conn.prepareStatement(sql_billcode);
			rs = ps.executeQuery();
			if(rs.next()){
				amount = rs.getInt("count");
				System.out.println("[DB] COUNT(ID) = " + amount);
			}
			amount += 1;
			if(amount>=1 && amount<10){
				billcode = today + ccode + "000" + amount;
			} else if(amount>=10 && amount<100) {
				billcode = today + ccode + "00" + amount;
			} else if(amount>=100 && amount<1000) {
				billcode = today + ccode + "0" + amount;
			} else if(amount>=1000 && amount<10000) {
				billcode = today + ccode + amount;
			}
			System.out.println("[INFO] billcode: " + billcode);
		
			// insert record.
			String locationToID = data.getJSONObject("brief").getString("locationTo");
			String deviceBelongingID = data.getJSONObject("brief").getString("devicebelonging");
			String lineID = data.getJSONObject("brief").getString("line");
			String deviceID = data.getJSONObject("brief").getString("device");
			String remark = data.getJSONObject("brief").getString("remark");
			String sql_record = "INSERT INTO settingbill(BillCode, LocationFromID, LocationToID, DeviceBelongingID, LineID, DeviceID, Remark, CreaterID, CreateTime, LastEditorID, LastEditTime) VALUES('" 
					+ billcode + "', '" 
					+ companyID + "', '" 
					+ locationToID + "', '" 
					+ deviceBelongingID + "', '" 
					+ lineID + "', '" 
					+ deviceID + "', '" 
					+ remark + "', '" 
					+ username + "', NOW(), '" 
					+ username + "', NOW() )";
					System.out.println("[DB] " + sql_record);
			ps = conn.prepareStatement(sql_record);
			result_record = ps.executeUpdate();
			System.out.println("[INFO] " + result_record + " bill(s) stored");
		
			// insert items
			JSONArray details = data.getJSONArray("details");
			System.out.println("found " + details.size() + " detail(s): ");
			for(int i=0, len=details.size(); i<len; i++) {
				String item    = details.getJSONObject(i).getString("item");
				String before1 = details.getJSONObject(i).getString("before1");
				String before2 = details.getJSONObject(i).getString("before2");
				String after1  = details.getJSONObject(i).getString("after1");
				String after2  = details.getJSONObject(i).getString("after2");
				
				String sql_detail = "INSERT INTO settingbilldetail(BillCode, Name, Before1, Before2, After1, After2) VALUES('" 
						+ billcode + "', '"
						+ item + "', '"
						+ before1 + "', '"
						+ before2 + "', '"
						+ after1 + "', '"
						+ after2 + "')";
				ps = conn.prepareStatement(sql_detail);
//						result_item = ps.executeUpdate();
				ps.executeUpdate();
				System.out.println("[INFO] item " + (i+1) + " stored");
			}
			obj.put("billcode", billcode);
			obj.put("success", true);
		} catch (SQLException e) {
			System.err.println("[ERROR] error occured while storing template");
			obj.put("success", false);
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(ps, conn);
		}

		return obj;
	}

	/**
	 * update bill by given data
	 * @param data new data to update
	 * @return rows affected
	 * @throws SQLException 
	 */
	public int updateBill(String billcode, JSONObject data, String username, Boolean isBriefEdited, Boolean isDetailEdited) {
		System.out.println("\n========== update bill ==========");
		
		int result_editinfo = -1;
		int result_brief = -1;
		int result_detail = 0;

		Connection conn = DBConnection.getConnection();
		if(conn != null) {
			System.out.println("[DB] DB Connectted on updating bill");
		}
		PreparedStatement ps = null;
		
		String sql_editinfo = "UPDATE settingbill SET LastEditorID='" + username + "', LastEditTime=NOW() WHERE BillCode='" + billcode + "'";
		try {
			ps = conn.prepareStatement(sql_editinfo);
			result_editinfo = ps.executeUpdate();
			System.out.println("[DB] result(editinfo): " + result_editinfo);
		} catch (SQLException e) {
			System.out.println("[ERROR] Error occured while updating edit info");
			e.printStackTrace();
		}
		
		if(isBriefEdited && data.getJSONObject("brief") != null) {
			String locationTo = data.getJSONObject("brief").getString("locationTo");
			String deviceBelonging = data.getJSONObject("brief").getString("devicebelonging");
			String line = data.getJSONObject("brief").getString("line");
			String device = data.getJSONObject("brief").getString("device");
			String remark = data.getJSONObject("brief").getString("remark");
			
			String sql_brief = "UPDATE settingbill SET LocationToID='" + locationTo
					+ "', DeviceBelongingID='" + deviceBelonging
					+ "', LineID='" + line
					+ "', DeviceID='" + device
					+ "', Remark='" + remark 
					+ "' WHERE BillCode='" + billcode + "'";
			System.out.println("[DB] sql_brief: " + sql_brief);
			
			try {
				ps = conn.prepareStatement(sql_brief);
				result_brief = ps.executeUpdate();
				System.out.println("[DB] result(brief): " + result_brief);
			} catch (SQLException e) {
				System.out.println("[ERROR] Error occured while updating bill brief");
				e.printStackTrace();
			}
		} else {
			result_brief = 1;
		}
				
		if(isDetailEdited && data.getJSONArray("details") != null) {
			JSONArray details = data.getJSONArray("details");
			System.out.println("found " + details.size() + " dateil(s): " + details);
			for(int i=0, len=details.size(); i<len; i++) {
				String item    = details.getJSONObject(i).getString("item");
				String before1 = details.getJSONObject(i).getString("before1");
				String before2 = details.getJSONObject(i).getString("before2");
				String after1  = details.getJSONObject(i).getString("after1");
				String after2  = details.getJSONObject(i).getString("after2");
				
				String sql_detail = "UPDATE settingbilldetail SET Before1='" + before1
						+ "', Before2='" + before2
						+ "', After1='" + after1
						+ "', After2='" + after2
						+ "' WHERE BillCode='" + billcode + "' AND Name='" + item + "'";
				try {
					System.out.println("[DB] sql_brief: " + sql_detail);
					ps = conn.prepareStatement(sql_detail);
					result_detail += ps.executeUpdate();
					System.out.println("[DB] result(2): " + result_detail);
				} catch (SQLException e) {
					System.out.println("[ERROR] Error occured while updating bill detail");
					e.printStackTrace();
				}
			}
		} else {
			result_detail = 1;
		}
		
		DBConnection.closeConnection(ps, conn);
		
		if(result_editinfo>0 && result_brief>0 && result_detail>0) {
			return 1; 
		} else {
			return -1;
		}
	}

	/**
	 * load templates according to companyID
	 * @param company companyID
	 * @return template records in JSON format
	 */
	public JSONObject loadTemplates(int company) {
		System.out.println("\n========== Loading templates ==========");
		String sql = "SELECT t.TemplateCode, t.TemplateName, t.`Status`, (SELECT s.Name FROM templatestatus s WHERE s.ID = t.`Status`) AS 'StatusName', (SELECT u.DisplayName FROM users u WHERE u.UserName = t.CreaterID) AS 'CreaterName', t.CreateTime FROM template t WHERE t.BelongID='" + company + "' AND t.Status IN('1', '-1') ORDER BY t.Status DESC, t.CreateTime DESC";
		System.out.println("[SQL] " + sql);
		JSONObject templates = null;
		
		Connection conn = DBConnection.getConnection();
		if(conn != null) {
			System.out.println("[DB] DB Connectted on retriving templates");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			templates = new JSONObject(); 
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.last();
			System.out.println("[DB] " + rs.getRow() + " templates found");
			rs.beforeFirst();
			ArrayList<Template> list = new ArrayList<Template>();
			while(rs.next()) {
				Template template = new Template();
				template.setTemplateCode(rs.getString("TemplateCode"));
				template.setTemplateName(rs.getString("TemplateName"));
				template.setStatus(rs.getInt("Status"));
				template.setStatusName(rs.getString("StatusName"));
				template.setCreaterName(rs.getString("CreaterName"));
				template.setCreateTime(rs.getDate("CreateTime"));
				list.add(template);
			}
			templates.put("data", list);
			templates.put("success", true);
		} catch (SQLException e) {
			System.out.println("[ERROR] error occured on retriving templates");
			e.printStackTrace();
			templates.put("success", false);
		} finally {
			DBConnection.closeConnection(rs, ps, conn);
		}
		
		return templates;
	}

	/**
	 * update template status using given status
	 * @param templateCode template to update
	 * @param status new status
	 * @return rows affected
	 */
	public int updateTemplateStatus(String templateCode, String status) {
		System.out.println("\n========== Loading templates ==========");
		String sql = "UPDATE template SET `Status`='" + status + "' WHERE TemplateCode='" + templateCode + "'";
		System.out.println("[SQL] " + sql);
		
		Connection conn = DBConnection.getConnection();
		if(conn != null) {
			System.out.println("[DB] DB Connectted on updating template status");
		}
		PreparedStatement ps = null;
		
		int affected = -1;
		try {
			ps = conn.prepareStatement(sql);
			 affected = ps.executeUpdate();
		} catch (SQLException e) {
			 System.out.println("[ERROR] error occured while updating template status");
			 e.printStackTrace();
		} finally {
			DBConnection.closeConnection(ps, conn);
		}
		
		return affected;
	}

	/**
	 * load details of a certain template
	 * @param tcode template code
	 * @return the details of the template
	 */
	public JSONObject loadTemplateDetails(String tcode) {
		System.out.println("\n========== Loading bill details ==========");
		System.out.println("[Info] tcode: " + tcode);

		JSONObject details = new JSONObject();
		if(tcode != null) {
			Connection conn = DBConnection.getConnection();
			if(conn != null) {
				System.out.println("[DB] DB Connectted on retriving template details");
			}
			PreparedStatement ps = null;
			ResultSet rs = null;
					
			// get details
			String sql = "SELECT * FROM templatedetail WHERE TemplateCode = '" + tcode + "'";
			try {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				rs.last();
				int rows = rs.getRow();
				System.out.println("[DB] " + rows + " details found");
				if(rows>0) {
					ArrayList<TemplateDetail> list = new ArrayList<TemplateDetail>();
					rs.beforeFirst();
					while(rs.next()) {
						TemplateDetail detail = new TemplateDetail();
						detail.setName(rs.getString("Name"));
						list.add(detail);
					}
					details.put("details", list);
				}
				details.put("success", true);
			} catch (SQLException e) {
				System.err.println("[SQL] SQLException occured");
				e.printStackTrace();
			} finally {
				DBConnection.closeConnection(rs, ps, conn);
			}
		} else {
			// if no record found in the database
			details.put("success", false);
			System.out.println("[Info] No records found");
		}
		return details;
	}

	/**
	 * add new template by given data
	 * @param data data to insert
	 * @return rows affected
	 */
	@SuppressWarnings("resource")
	public JSONObject insertTemplate(JSONObject data, String username, String companyID) {
		System.out.println("\n========== storing template ==========");
		
		int result_record = -1;
//		int result_item = -1;
		JSONObject obj = new JSONObject();
		
		Connection conn = DBConnection.getConnection();
		if(conn != null) {
			System.out.println("[DB] DB Connectted on inserting new template");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		// get companycode
		String sql_ccode = "SELECT CompanyCode FROM company WHERE ID='" + companyID + "'";
//		System.out.println("[DB] " + sql_ccode);
		String ccode = "";
		try {
			ps = conn.prepareStatement(sql_ccode);
			rs = ps.executeQuery();
			if(rs.next()) {
				ccode = rs.getString("CompanyCode");
			}
//			System.out.println("[INFO] ccode: " + ccode);
		
			// calculate tcode
			String sql_tcode = "SELECT COUNT(ID) AS 'count' FROM template WHERE BelongID='" + companyID + "'";
//			System.out.println("[DB] " + sql_tcode);
			int amount = 0;
			String tcode = "";
			ps = conn.prepareStatement(sql_tcode);
			rs = ps.executeQuery();
			if(rs.next()){
				amount = rs.getInt("count");
//				System.out.println("[DB] COUNT(ID) = " + amount);
			}
			amount += 1;
			if(amount>=1 && amount<10){
				tcode = "T" + ccode + "000" + amount;
			} else if(amount>=10 && amount<100) {
				tcode = "T" + ccode + "00" + amount;
			} else if(amount>=100 && amount<1000) {
				tcode = "T" + ccode + "0" + amount;
			} else if(amount>=1000 && amount<10000) {
				tcode = "T" + ccode + amount;
			}
			System.out.println("[INFO] tcode: " + tcode);
		
			// insert record.
			String tname = data.getString("tname");
			String sql_record = "INSERT INTO template(TemplateCode, TemplateName, CreaterID, BelongID) VALUES('" + tcode + "', '" + tname + "', '" + username + "', '" + companyID + "')";
//			System.out.println("[DB] " + sql_record);
			ps = conn.prepareStatement(sql_record);
			result_record = ps.executeUpdate();
			System.out.println("[INFO] " + result_record + " template(s) stored");
		
			// insert items
			JSONArray items = data.getJSONArray("items");
//			System.out.println("found " + items.size() + " item(s): " + items);
			for(int i=0, len=items.size(); i<len; i++) {
				String name = items.getJSONObject(i).getString("name");
				String sql_item = "INSERT INTO templatedetail(TemplateCode, Name) VALUES('" + tcode + "', '" + name + "')";
//				System.out.println("[DB] " + sql_item);
				ps = conn.prepareStatement(sql_item);
//				result_item = ps.executeUpdate();
				ps.executeUpdate();
				System.out.println("[INFO] item " + (i+1) + " stored");
			}
			obj.put("tcode", tcode);
			obj.put("success", true);
		} catch (SQLException e) {
			System.err.println("[ERROR] error occured while storing template");
			obj.put("success", false);
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(ps, conn);
		}

		return obj;
	}

	/**
	 * validate if tname already exist
	 * @param tname tname to validate
	 * @param companyID current login company
	 * @return how many match found
	 */
	public int validateTname(String tname, String companyID) {
		System.out.println("\n========== validating tname ==========");
		
		Connection conn = DBConnection.getConnection();
		if(conn != null) {
			System.out.println("[DB] DB Connectted on validating tname");
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = -1;
		
		String sql = "SELECT COUNT(ID) AS 'count' FROM template WHERE BelongID='" + companyID + "' AND Templatename='" + tname + "'";
//		System.out.println("[DB] " + sql);
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
//				result = Integer.valueOf(rs.getString("count"));
				result = rs.getInt("count");
				System.out.println("[INFO] " + result + " rows like this exist");
			}
		} catch (SQLException e) {
			System.err.println("[ERROR] error occured while validating tname");
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(rs, ps, conn);
		}
		
		return result;
	}
}
