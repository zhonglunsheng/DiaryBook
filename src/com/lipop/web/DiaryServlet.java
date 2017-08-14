package com.lipop.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lipop.dao.DiaryDao;
import com.lipop.model.Diary;
import com.lipop.util.DbUtil;
import com.lipop.util.StringUtil;

public class DiaryServlet extends HttpServlet{

	/**
	 * 
	 */
	DiaryDao diaryDao = new DiaryDao();
	DbUtil dbUtil = new DbUtil();
	Diary diary = new Diary();
	Connection conn=null;
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
			try {
				conn = dbUtil.getCon();
				if ("show".equals(action)) {
					diaryShow(request,response);
				}else if ("write".equals(action)) {
					request.setAttribute("mainPage", "diary/diaryWrite.jsp");
					request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
				}else if ("save".equals(action)) {
					diarySave(request, response);
				}else if ("delete".equals(action)) {
					diaryDelete(request,response);
				}else if ("update".equals(action)) {
					diaryPresave(request, response);
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
	/**
	 * 日志显示
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void diaryShow(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String diaryId = request.getParameter("diaryId");
		try {
			diary=diaryDao.diaryShow(conn, Integer.parseInt(diaryId));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("diaryShow", diary);
		request.setAttribute("mainPage","diary/diaryShow.jsp");
		request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
	}
	
	/**
	 * 插入日志
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void diarySave(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException,Exception {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String typeId = request.getParameter("typeId");
		String diaryId = request.getParameter("diaryId");
		diary=new Diary(title,content,Integer.parseInt(typeId));
		if (StringUtil.IsNotEmpty(diaryId)) {
			diary.setDiaryId(Integer.parseInt(diaryId));
			diaryDao.diaryUpdate(conn, diary);
			response.sendRedirect("user?all=true");
		}else{
			int i=diaryDao.diarySave(conn, diary);
			if (i>0) {
				response.sendRedirect("user?all=true");
			}
		}
	
	}
	
	private void diaryDelete(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException,Exception {
		String diaryId = request.getParameter("diaryId");
		int i=diaryDao.diaryDelet(conn, Integer.parseInt(diaryId));
		if (i>0) {
			response.sendRedirect("user?all=true");
		}
	}
	
	private void diaryPresave(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException,Exception {
		String diaryId = request.getParameter("diaryId");
		diary=diaryDao.diaryShow(conn, Integer.parseInt(diaryId));
		request.setAttribute("diaryUpdate", diary);
		request.setAttribute("mainPage", "diary/diaryWrite.jsp");
		request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
	}
	
}
