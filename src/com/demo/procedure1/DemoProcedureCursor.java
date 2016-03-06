package com.demo.procedure1;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class DemoProcedureCursor {
	@Test
	public void DemoTest(){
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String call_sql = "{call sp_pro10(?,?)}";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl","yq","123");
			
			cs = conn.prepareCall(call_sql);
			cs.setInt(1, 20);
			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			
			cs.execute();
			
			rs = (ResultSet) cs.getObject(2);
			while(rs.next()){
				System.out.println("===================================================");
				System.out.print("工号："+rs.getString("EMPNO")+"\t");
				System.out.println("姓名："+rs.getString("ENAME"));
				System.out.println("===================================================");
			}
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
		} catch (SQLException e) {
			// TODO: handle exception
		}
	}
}	
