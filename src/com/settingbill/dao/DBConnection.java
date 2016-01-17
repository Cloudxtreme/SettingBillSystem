package com.settingbill.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Scanner;

import com.alibaba.fastjson.JSON;

public class DBConnection
{
	/**
	 * Get JDBC driver ready
	 */
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("MySQL JDBC Driver not found. Please check your build path.\nOr maybe the Driver file is missing in server.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Open Connection
	 * @return instance of Connection
	 */
	public static Connection getConnection() {
		Connection conn = null;
		String DB_URL = "";
		String DB_USERNAME = "";
		String DB_PASSWORD = "";
		
		String location = DBConnection.class.getResource("/").toString().replace("%20", " ");
		if(location.indexOf("file:/")==0) {
			location = location.substring(6, location.length()-16);
		} else {
			location = location.substring(0, location.length()-16);
		}
		System.out.println("location: " + location);
		String fullFileName = location+"db.json";
//		String fullFileName = "D:/Program Files/Tomcat/webapps/SettingBillSystem/db.json";
        File file = new File(fullFileName);
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            } 
        } catch (FileNotFoundException e) {
            System.err.println("File " + fullFileName + " not found."); 
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        
        String jsontext = buffer.toString().replaceAll(" ", "").replaceAll("\t", "");
        System.out.println("");
        
        /*String buffer = "{\"DB_HOST\":\"jdbc:mysql://127.0.0.1:3306/\",\"DB_NAME\":\"SettingBill\",\"DB_USERNAME\":\"root\",\"DB_PASSWORD\":\"111111\"}";
        System.out.println("text: " + buffer);*/
        try {
            Map<String, Object> map = JSON.parseObject(jsontext);
            DB_URL = map.get("DB_HOST").toString() + map.get("DB_NAME").toString();
            DB_USERNAME = map.get("DB_USERNAME").toString();
            DB_PASSWORD = map.get("DB_PASSWORD").toString();
            
        } catch (Exception e) {
            System.out.println("JSON parsing failed.");
        }
		
        System.out.println("DB_URL: " + DB_URL);
        System.out.println("DB_Username: " + DB_USERNAME);
        System.out.println("DB_Password: " + DB_PASSWORD);
//		String DB_URL = "jdbc:mysql://127.0.0.1:3306/SettingBill";
//		String DB_Username = "root";
//		String DB_Password = "111111";
		try	{
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		} catch (Exception e) {
			System.out.println("Connection to " + DB_URL + " has failed ...");
			e.printStackTrace();
		}
		return conn;
	}
	
	/** 
	 * Close Connection for insert and update (resultset not used)
	 * @param stmt stmt to be close
	 * @param conn conn to be close
	 */
	public static void closeConnection( Statement stmt, Connection conn ) {
		
		if(stmt != null) {
			try {
				stmt.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(conn != null) {
			try {
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/** 
	 * Close Connection for query
	 * @param rs rs to be close
	 * @param stmt stmt to be close
	 * @param conn conn to be close
	 */
	public static void closeConnection( ResultSet rs, Statement stmt, Connection conn ) {
		if(rs != null) {
			try {
				rs.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(stmt != null) {
			try {
				stmt.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(conn != null) {
			try {
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
