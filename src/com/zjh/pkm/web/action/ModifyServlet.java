package com.zjh.pkm.web.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zjh.pkm.entity.User;
import com.zjh.pkm.util.DBUtil;

public class ModifyServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//获取小区编号并转换为int类型
		int commno = Integer.parseInt(request.getParameter("commno"));
		//修改的区域ID
		String regionid = request.getParameter("regionid");
		//获取修改的数量并转换为int类型
		int pknumber;
		try{
			pknumber = Integer.parseInt(request.getParameter("pknumber"));
		}catch(Exception e){
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return;
		}
		//连接数据库修改数据
		Connection conn = null;
		PreparedStatement ps = null;
		int count = 0;
		try {
			System.out.println("commno="+commno+"regionid="+regionid+"pknumber="+pknumber);
			conn = DBUtil.getConnection();
			DBUtil.beginTransaction(conn);
			String sql = "update t_comm set region"+regionid+"=? where commno=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, pknumber);
			ps.setInt(2, commno);
			//System.out.println(sql);
			count = ps.executeUpdate();
			DBUtil.commitTransaction(conn);
		} catch (Exception e) {
			DBUtil.rollbackTransaction(conn);
			e.printStackTrace();
		} finally{
			DBUtil.close(conn, ps, null);
		}
		//修改成功刷新页面
		if(count == 1){
			count = 0;
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
	}
}
