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
		//��ȡС����Ų�ת��Ϊint����
		int commno = Integer.parseInt(request.getParameter("commno"));
		//�޸ĵ�����ID
		String regionid = request.getParameter("regionid");
		//��ȡ�޸ĵ�������ת��Ϊint����
		int pknumber;
		try{
			pknumber = Integer.parseInt(request.getParameter("pknumber"));
		}catch(Exception e){
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return;
		}
		//�������ݿ��޸�����
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
		//�޸ĳɹ�ˢ��ҳ��
		if(count == 1){
			count = 0;
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
	}
}
