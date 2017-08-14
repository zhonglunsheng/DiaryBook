package com.lipop.web;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.*;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.lipop.dao.UserDao;
import com.lipop.model.User;
import com.lipop.util.DbUtil;
import com.lipop.util.StringUtil;

import org.apache.commons.fileupload.FileItem;



public class UserServlet extends HttpServlet{
	DbUtil dbUtil = new DbUtil();
	Connection conn = null;
	UserDao userDao = new UserDao();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String action = request.getParameter("action");
			if ("user".equals(action)) {
				request.setAttribute("mainPage", "user/userWrite.jsp");
				request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
			}else if ("save".equals(action)) {
				userSave(request,response);
			}
	}
	
	
	protected void userSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			request.setCharacterEncoding("UTF-8");
			HttpSession session = request.getSession();
			//  创建基于磁盘的文件工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			String nickName=null;
			String mood=null;
			String imageName=null;
			try {
				//解析请求，返回一个存储了所有表单元素的集合
				List items = upload.parseRequest(request);
				Iterator itr = items.iterator();
				while(itr.hasNext())
				{
					FileItem item = (FileItem)itr.next();
					if (item.isFormField()) {   //如果是普通的文本信息，返回true
						if ("nickName".equals(item.getFieldName())) {
							nickName=item.getString("UTF-8");
						}else{
							mood=item.getString("UTF-8");
						}
					}else{
						if (StringUtil.IsNotEmpty(item.getName())) {
							if ("image/png".equals(item.getContentType())||"image/jpeg".equals(item.getContentType())) {
								File tempFile = new File(item.getName());
								String realPath = getServletContext().getRealPath("/") + "headImage";
								File file = new File(realPath, tempFile.getName());
								item.write(file);
								imageName=item.getName();
							}else{
								request.setAttribute("error", "只能上传jpg或png格式的图片");
								request.setAttribute("mainPage", "user/userWrite.jsp");
								request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
							}
						}
					}
				}
				//更新个人中心信息
				User user = null;
				int i=0;
				conn=dbUtil.getCon();
				
				if (StringUtil.IsNotEmpty(imageName)) {
					user = new User(nickName,imageName,mood);
					i=userDao.updateUser(conn, user);
				}else {
					user=new User();
					user.setNickName(nickName);
					user.setMood(mood);
					i=userDao.psaveUser(conn, user);
				}
				try {
					if (i>0) {
						session.removeAttribute("myuser");
						User user2 = userDao.showUser(conn);
						session.setAttribute("myuser", user2);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					dbUtil.closeCon(conn);
				}
				request.getRequestDispatcher("user?all=true").forward(request, response);
		}catch (Exception e2) {
				e2.printStackTrace();
			}
	}
}
