<%@ page language="java" import="java.util.*,java.sql.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'OraclePage.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <h2>Oracle分页案例演示</h2>

    <table align="left" border="1">
    	<tr align="center">
    		<td>用户号</td>
    		<td>用户名</td>
    		<td>工  &nbsp;种</td>
    		<td>薪  &nbsp;水</td>
    	</tr>
    <%
    	Connection conn = null;// 创建一个数据库连接
	    PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
	    ResultSet result = null;// 创建一个结果集对象
	    String sql = null;
	    try{
	        Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
	        System.out.println("------开始尝试连接数据库！------");
	        String url = "jdbc:oracle:" + "thin:@127.0.0.1:1521:orcl";// 127.0.0.1是本机地址
	        String user = "scott";// 用户名,系统默认的账户名
	        String password = "tiger";// 安装时选设置的密码
	        conn = DriverManager.getConnection(url, user, password);// 获取连接
	        Statement stmt = conn.createStatement();
	        System.out.println("---------连接成功！----------");
	        //以上内容作为数据库的连接的使用
	        int pageCount=0;//查询总页数
	        int rowCount=0;//共有几条记录
	        int pageSize=3;//每页显示几条记录
	        //接受PageNum
	        String s_pageNum = (String)request.getParameter("pageNum");
	        int PageNow = 1;
	        if(s_pageNum!=null){
	        	PageNow = Integer.parseInt(s_pageNum);
	        }
	       	ResultSet rs = stmt.executeQuery("select count(*) count from EMP");
	       
	       	if(rs.next()){
       			String rowCountStr = rs.getString("count");
	       		rowCount = Integer.parseInt(rowCountStr);
	       		if(rowCount%pageSize == 0){
	       			pageCount = rowCount / 3;
	       		}else{
	       			pageCount = rowCount / 3 + 1;
	       		}
	       	}
	       
	  
	        sql = "select * from (select a1.*,rownum rn from (select * from EMP)a1 where rownum<="
	        			+PageNow*pageSize+")where rn >="+((PageNow-1)*3+1);// 预编译语句，“？”代表参数
	        pre = conn.prepareStatement(sql);// 实例化预编译语句
	        //pre.setString(1, "CLERK");// 设置参数，前面的1表示参数的索引，而不是表中列名的索引
	        result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
	        
	        while (result.next()){    // 当结果集不为空时
	        	out.print("<tr align=\"center\">");
	        	out.println("<td>" + result.getString("EMPNO") + "</td>");
	            out.println("<td>" + result.getString("ENAME") + "</td>");
	            out.println("<td>" + result.getString("JOB") + "</td>");
	            out.println("<td>" + result.getString("SAL") + "</td>");
	            out.print("</tr>");   
	        } 
	        out.print("<tr>");
	        for(int i=1;i<=pageCount;i++){
	        	out.print(" &nbsp;<a href=OraclePage.jsp?pageNum="+i+">"+"["+i+"]</a> &nbsp;&nbsp;&nbsp;&nbsp;");
	        }
	        out.print("</tr>"); 
	    }
	    catch (Exception e){
	        e.printStackTrace();
	    } finally{
	        try {
	            // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
	            // 注意关闭的顺序，最后使用的最先关闭
	            if (result != null)
	                result.close();
	            if (pre != null)
	                pre.close();
	            if (conn != null)
	                conn.close();
	            System.out.println("-------数据库连接已关闭！-------");
	        } catch (Exception e){
	            e.printStackTrace();
	        }
	    }
     %>
    	
    </table>
  </body>
</html>
