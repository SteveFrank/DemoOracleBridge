package com.oracle.bridge;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class DemoOraBridge {
	public static void main(String[] args){
		//本案例利用的是ODBC
		try{
			//1、加载驱动sun.jdbc.odbc.JdbcOdbcDriver
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			//2、得到连接
			System.out.println("开始连接");
			Connection ct = DriverManager.getConnection("jdbc:odbc:DEMOORACLE","scott","tiger");
			//3、从下面开始正常创建
			Statement stmt = ct.createStatement();
			ResultSet rs = stmt.executeQuery("select * from EMP");
			while(rs.next()){
				//结果集
				 System.out.println("工号:" + rs.getInt("EMPNO") + "  " + "姓名:" + rs.getString("ENAME"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
