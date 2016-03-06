package com.demo.procedure1;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import org.junit.Test;

public class DemoPageProcedure {
	@Test
	public void DemoPageTest() throws ClassNotFoundException, SQLException{
		//加载驱动
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection ct = null;
		//创建CallableStatement
		CallableStatement cs = null;
		String call_sql = "{call sp_pageSplit_pro11(?,?,?,?,?,?)}";
		try {
			ct = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "yq", "123");
			cs = ct.prepareCall(call_sql);
			//给？赋值
			cs.setString(1, "EMP");
			cs.setInt(2, 2);
			cs.setInt(3, 2);
			//输出的参数
			//注册总记录数
			cs.registerOutParameter(4, OracleTypes.INTEGER);
			//注册总页数
			cs.registerOutParameter(5, OracleTypes.INTEGER);
			//注册返回的结果集
			cs.registerOutParameter(6, OracleTypes.CURSOR);
			
			//执行
			cs.execute();
			
			//现在取出返回的值
			//这里的数字完全取决于问号是第几个，也就是数据的所在的位置
			int rowNum = cs.getInt(4);
			int pageCount = cs.getInt(5);
			ResultSet rs = (ResultSet)cs.getObject(6);
			
			//测试结果是否正确
			System.out.println("RowNum = " + rowNum);
			System.out.println("pageCount = " + pageCount);
			int i = 1;
			while(rs.next()){
				System.out.println("序列号："+(i++)+"\t"+" 编号："+rs.getInt("EMPNO")+"   姓名："+rs.getString("ENAME"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			cs.close();
			ct.close();
		}
	}
}
