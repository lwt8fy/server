package net.jeeshop.chat.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.logicalcobwebs.proxool.ProxoolDataSource;

public class DBCon {
	
    private static ProxoolDataSource dataSource;

	public static Connection getConnect(){
			
			Connection con=null;
			try {
				con = dataSource.getConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("获取连接失败!!!");
			}
			return con;
	}
    
    
    
    
    /*////////////////////////////////////////////////////////////////////
    private static final String url = "jdbc:mysql://localhost";
    private static final String user = "root";
    private static final String password = "root";

	public static Connection getConnect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return null;
		}
	
		Connection con = null;
		try {
		    con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		if(con == null) {
			System.out.println("数据库连接失败!!!");
		}

		return con;
	}
	*/ ///////////////////////////////////////////////////////////////////////////
	
	
	

	public ProxoolDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(ProxoolDataSource dataSource) {
		DBCon.dataSource = dataSource;
	}


}
