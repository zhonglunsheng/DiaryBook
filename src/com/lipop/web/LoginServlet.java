package com.lipop.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lipop.dao.UserDao;
import com.lipop.model.User;
import com.lipop.util.DbUtil;
import com.lipop.util.Md5Util;

public class LoginServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO 自动生成的方法存根
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		String remember=request.getParameter("remember");
		HttpSession session = request.getSession();
		DbUtil dbUtil = new DbUtil();
		Connection conn=null;
		try {
			conn = dbUtil.getCon();
			User user = new User(userName,Md5Util.EncoderPwdByMd5(password));
			UserDao userDao = new UserDao();
			User user2=userDao.userLogin(conn, user);
			if(user2!=null)
			{
				  if ("remember-me".equals(remember)) {
						rememberMe(userName, password, response);
					}
					session.setAttribute("user2",user2);
					session.setAttribute("myuser", user2);
					request.getRequestDispatcher("user").forward(request, response);
			}else{		
				request.setAttribute("user", user);
				request.setAttribute("error","用户名或密码错误");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void rememberMe(String userName,String password,HttpServletResponse response)
	{
		Cookie user = new Cookie("user", userName+"-"+password);
		user.setMaxAge(1*60*60*24);
		response.addCookie(user);
	}
}

