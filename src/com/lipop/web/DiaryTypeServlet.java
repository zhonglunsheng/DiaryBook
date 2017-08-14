package com.lipop.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lipop.dao.DiaryTypeDao;
import com.lipop.model.DiaryType;
import com.lipop.util.DbUtil;
import com.lipop.util.StringUtil;

public class DiaryTypeServlet extends HttpServlet{
	Connection conn = null;
	DiaryTypeDao diaryTypeDao = new DiaryTypeDao();
	DbUtil dbUtil = new DbUtil();
	DiaryType diaryType = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			request.setCharacterEncoding("UTF-8");
			String action = request.getParameter("action");
			try {
				conn=dbUtil.getCon();
				if ("list".equals(action)) {
					listType(request, response);
				}else if ("delete".equals(action)) {
					deleteType(request, response);
				}else if ("write".equals(action)) {
					request.setAttribute("mainPage", "diaryType/diaryTypeWrite.jsp");
					request.getRequestDispatcher("mainTemp.jsp").forward(request, response);	
				}else if ("save".equals(action)) {
					saveType(request,response);
				}else if ("update".equals(action)) {
					Presave(request,response);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}
	
	/**
	 * 分类类别
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void listType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<DiaryType> diaryTypeList;
		try {
			diaryTypeList = diaryTypeDao.diaryTypeList(conn);
			session.setAttribute("diaryTypeList", diaryTypeList);
			request.setAttribute("mainPage", "diaryType/diaryTypeList.jsp");
			request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 分类名删除
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void deleteType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String diaryTypeId = request.getParameter("diaryTypeId");
		try {
			if (diaryTypeDao.existDiary(conn, Integer.parseInt(diaryTypeId))) {
				request.setAttribute("error", "改分类下有日志，不能删除");
				request.getRequestDispatcher("diaryType?action=list").forward(request, response);
			}else{
				diaryTypeDao.diaryTypeDelet(conn, Integer.parseInt(diaryTypeId));
				request.getRequestDispatcher("diaryType?action=list").forward(request, response);
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * 日志类别添加
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void saveType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String diaryTypeName = request.getParameter("diaryTypeName");
		String diaryTypeId = request.getParameter("diaryTypeId");
		try {
			if (StringUtil.IsNotEmpty(diaryTypeId)) {
				diaryType.setDiaryTypeId(Integer.parseInt(diaryTypeId));
				diaryType.setTypeName(diaryTypeName);
				diaryTypeDao.diaryTypeUpdate(conn, diaryType);
				response.sendRedirect("diaryType?action=list");
			}else{
				diaryTypeDao.diaryTypeAdd(conn, diaryTypeName);
				response.sendRedirect("diaryType?action=list");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 分类修改
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void Presave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String diaryTypeId = request.getParameter("diaryTypeId");
		try {
			diaryType=diaryTypeDao.diaryTypeShow(conn, Integer.parseInt(diaryTypeId));
			request.setAttribute("diaryType", diaryType);
			request.getRequestDispatcher("diaryType?action=write").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
