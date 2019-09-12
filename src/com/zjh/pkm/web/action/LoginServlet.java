package com.zjh.pkm.web.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zjh.pkm.entity.User;
import com.zjh.pkm.util.DBUtil;

public class LoginServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡ�û���������
		request.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String userpwd = request.getParameter("userpwd");
		//�������ݿ���֤�û���������
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		try {
			conn = DBUtil.getConnection();
			DBUtil.beginTransaction(conn);
			String sql = "select id,username,userpwd from t_user where username=? and userpwd=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, userpwd);
			//System.out.println(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				//��������ݣ�˵����½�ɹ������û���Ϣ��װ��ʵ�������
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setUserpwd(rs.getString("userpwd"));
			}
			DBUtil.commitTransaction(conn);
		} catch (Exception e) {
			DBUtil.rollbackTransaction(conn);
			e.printStackTrace();
		} finally{
			DBUtil.close(conn, ps, rs);
		}
		
		if(user != null){
			//��½�ɹ��������û���Ϣ��session����ת��index.jspҳ��
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}else{
			//��½ʧ�ܣ���ת��ʧ��ҳ��
			response.sendRedirect(request.getContextPath() + "/login_error.jsp");
		}
	}

}
