package com.lipop.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lipop.dao.DiaryDao;
import com.lipop.dao.DiaryTypeDao;
import com.lipop.model.Diary;
import com.lipop.model.DiaryType;
import com.lipop.model.PageBean;
import com.lipop.util.DbUtil;
import com.lipop.util.PropertiesUtil;
import com.lipop.util.StringUtil;

public class MainServlet extends HttpServlet{
	Connection conn = null;
	DbUtil dbUtil = new DbUtil();
	DiaryDao diaryDao = new DiaryDao();
	DiaryTypeDao diaryTypeDao = new DiaryTypeDao();
	Diary diary = new Diary();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		try {
			conn=dbUtil.getCon();
			String diary_time = request.getParameter("date_time");
			String type_Id = request.getParameter("type_id");
			String diary_title=request.getParameter("s_title");
			if ("search".equals(request.getParameter("all"))) {
				if (StringUtil.IsNotEmpty(diary_title)) {
					diary.setTitle(diary_title);
				}
				diary.setTypeId(-1);
				diary.setReleaseDateStr(null);
			}else{
				if (type_Id!=null) {
					diary.setTypeId(Integer.parseInt(type_Id));
					diary.setReleaseDateStr(null);
					diary.setTitle(null);
				}
				if (diary_time!=null) {
					diary_time=new String(diary_time.getBytes("ISO-8859-1"),"UTF-8");
					diary.setReleaseDateStr(diary_time);
					diary.setTypeId(-1);
					diary.setTitle(null);
				}
			}
			
			if ("true".equals(request.getParameter("head"))) {
				diary.setTitle(null);
				diary.setReleaseDateStr(null);
				diary.setTypeId(-1);
			}
			
			paging(request, response); //分页功能
			List<DiaryType>types =diaryTypeDao.diaryTypes(conn);//日志类别分类显示
			List<Diary>diaryDate = diaryDao.diaryDate(conn); //时间日期分类显示
			session.setAttribute("diaryDate", diaryDate);
			session.setAttribute("diaryTypes", types);
			/*if (total!=0) {
				
			}else{
				request.setAttribute("error", "没有查询到此记录");
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				request.setAttribute("mainPage", "diary/diaryList.jsp");
				request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
				dbUtil.closeCon(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 分页功能
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws Exception
	 */
	public void paging(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException,Exception
	{
		String page=request.getParameter("page");
		if (StringUtil.IsEmpty(page)) {
			page="1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(PropertiesUtil.getValue("pageSize")));
		List<Diary>diaryList=diaryDao.diaryList(conn,pageBean,diary);//分页功能
		request.setAttribute("diaryList", diaryList);//日志和分页的显示
		int total = diaryDao.diaryCount(conn,diary);
		String pageCode=null;
		if (total!=0) {
			pageCode= getPageCode(total,Integer.parseInt(page),Integer.parseInt(PropertiesUtil.getValue("pageSize")));
		}else{
			request.setAttribute("error", "抱歉，没有记录");
		}
		
		request.setAttribute("pageCode", pageCode);
	}
	/**
	 * 分页代码拼接
	 * @param total
	 * @param page
	 * @param pageSize
	 * @return
	 */
	private String getPageCode(int total, int page, int pageSize) {
		int endPage = total%pageSize==0?total/pageSize:total/pageSize+1;
		StringBuffer pageCode = new StringBuffer();
		pageCode.append("<li><a href='user?page=1'>首页</li>");
		if(page==1)
		{
			pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
		}else{
			pageCode.append("<li><a href='user?page="+(page-1)+"'=>上一页</a></li>");
		}
		for (int i=page-2;i<page+2;i++) {
			if (i<1 || i>endPage) {
				continue;
			}
			if (i==page) {
				pageCode.append("<li class='disabled'><a href='#'>"+i+"</a></li>");
			}else{
				pageCode.append("<li><a href='user?page="+i+"'>"+i+"</a></li>");
			}
		}
		if (page==endPage) {
			pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
		}else{
			pageCode.append("<li><a href='user?page="+(page+1)+"'>下一页</a></li>");
		}
		pageCode.append("<li><a href='user?page="+endPage+"'>尾页</a></li>");
		return pageCode.toString();
	}
	


}
