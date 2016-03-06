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
public class DemoProcedureOut {
	@Test
	public void DemoTest() throws ClassNotFoundException, SQLException{
		//加载驱动
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection ct = null;
		//创建CallableStatement
		CallableStatement cs = null;
		String call_sql = "{call sp_pro9(?,?,?,?)}";
		try {
			ct = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "yq", "123");
			cs = ct.prepareCall(call_sql);
			//给？赋值
			cs.setString(1, "7788");
			cs.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
			cs.registerOutParameter(3,oracle.jdbc.OracleTypes.DOUBLE);
			cs.registerOutParameter(4,oracle.jdbc.OracleTypes.VARCHAR);
			//执行
			cs.execute();
			//取出返回的值,要注意问号的顺序
			System.out.println("利用存储过程返回多个的值");
			String name = cs.getString(2);
			System.out.println("7788的姓名的值："+name);
			String sal = cs.getString(3);
			System.out.println("7788的工资的值："+sal);
			String job = cs.getString(4);
			System.out.println("7788的工作的值："+job);
			cs.close();
			ct.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
		}
	}
}