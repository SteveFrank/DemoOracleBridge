package com.demo.procedure1;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

/**
 * 调用一个无返回值的过程
 * @author 杨谦
 * @date 2015-8-25 下午9:34:20
 *
 */
public class DemoProcedure {
	@Test
	public void DemoTest() throws ClassNotFoundException, SQLException{
		//加载驱动
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection ct = null;
		//创建CallableStatement
		CallableStatement cs = null;
		String call_sql = "{call sp_pro7(?,?,?)}";
		try {
			ct = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "yq", "123");
			cs = ct.prepareCall(call_sql);
			//给？赋值
			cs.setInt(1, 1);
			cs.setString(2, "笑傲江湖");
			cs.setString(3, "人民出版社");
			//执行
			cs.execute();
			cs.close();
			ct.close();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
		}
	}
}
