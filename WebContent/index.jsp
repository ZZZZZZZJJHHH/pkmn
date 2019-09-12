<%@page import="java.util.List"%>
<%@page import="com.zjh.pkm.util.DBUtil"%>
<%@page import="com.zjh.pkm.entity.Community"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.zjh.pkm.entity.User"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%--验证用户是否登录，若未登录则跳转到登陆页面，已登录的标志：session对象中保存了User对象--%>
<%  
	if(session != null && session.getAttribute("user") != null){
		//已经登陆，可以展示页面
		//1.查询数据库信息
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = (User)session.getAttribute("user");
		List<Community> cummunityList = new ArrayList<Community>();
		try {
			conn = DBUtil.getConnection();
			DBUtil.beginTransaction(conn);
			String sql = "select * from t_comm where userid=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getId()+"");
			rs = ps.executeQuery();
			while(rs.next()){
				int commno= rs.getInt("commno");
				String commname = rs.getString("commname");
				String filepath = rs.getString("filepath");
				String region1 = rs.getString("region1");
				String region2 = rs.getString("region2");
				String region3 = rs.getString("region3");
				String region4 = rs.getString("region4");
				String region5 = rs.getString("region5");
				String region6 = rs.getString("region6");
				String region7 = rs.getString("region7");
				String region8 = rs.getString("region8");
				String region9 = rs.getString("region9");
				String region10 = rs.getString("region10");
				
				Community comm = new Community();
				comm.setCommno(commno);
				comm.setCommname(commname);
				comm.setFilepath(filepath);
				comm.setRegion(1,region1);
				comm.setRegion(2,region2);
				comm.setRegion(3,region3);
				comm.setRegion(4,region4);
				comm.setRegion(5,region5);
				comm.setRegion(6,region6);
				comm.setRegion(7,region7);
				comm.setRegion(8,region8);
				comm.setRegion(9,region9);
				comm.setRegion(10,region10);
				
				cummunityList.add(comm);
			}
			DBUtil.commitTransaction(conn);
		} catch (Exception e) {
			DBUtil.rollbackTransaction(conn);
			e.printStackTrace();
		} finally{
			DBUtil.close(conn, ps, rs);
		}
		//2.展示页面
		if(cummunityList.size() > 0){
			Community c = cummunityList.get(0);
			//该小区Region中非空的个数
			int count = c.getNotNull(c);
%>
		<html>
			<head>
				<title>后台管理</title>
			</head>
			<body>
				<center>
					<img alt="<%=c.getCommname() %>" src="<%=request.getContextPath() %><%=c.getFilepath() %>"></img>
					<table border="1px" width="50%">
						<tr align="center">
							<th>区域</th>
							<th>车辆数</th>
							<th>操作</th>
						</tr>
						<% 
							for(int i=1;i<=count;i++){
						%>
								<tr align="center">
									<td>区域<%=i %></td>
									<td><%=c.getRegion(i) %></td>
									<td>
										<form  action="<%=request.getContextPath() %>/modify?commno=<%=c.getCommno() %>&regionid=<%=i %>" method="post">
											<input type="number" min="0" name="pknumber"/>
											<input type="submit"  value="修改"/>
										</form>
									</td>
								</tr>
						<%
							}
						%>
					</table>
				</center>
			</body>
		</html>
<%
		}else{
			out.print("该账号没有所管理的小区，请联系管理员！");
		}
	}else{
		response.sendRedirect(request.getContextPath() + "/login.html");
	}	
%>

