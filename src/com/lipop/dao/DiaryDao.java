package com.lipop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.lipop.model.Diary;
import com.lipop.model.PageBean;
import com.lipop.util.DateUtil;
import com.lipop.util.StringUtil;

public class DiaryDao {
		public List<Diary> diaryList(Connection conn,PageBean pageBean,Diary tb_diary)throws Exception{
			List<Diary> diaryList = new ArrayList<Diary>();
			StringBuffer sbBuffer = new StringBuffer("select * from tb_diary t1,tb_diaryType t2 where t1.typeId=t2.diaryTypeId");
			if (StringUtil.IsNotEmpty(tb_diary.getTitle())) {
				sbBuffer.append(" and t1.title like '%"+tb_diary.getTitle()+"%'");
			}
			if (tb_diary.getTypeId()!=-1) {
				sbBuffer.append(" and t1.typeId="+tb_diary.getTypeId());
			}
			if (StringUtil.IsNotEmpty(tb_diary.getReleaseDateStr())) {
				sbBuffer.append(" AND DATE_FORMAT(t1.releaseDate,'%Y年%m月')='"+tb_diary.getReleaseDateStr()+"'");
				System.out.println(tb_diary.getReleaseDateStr());
			}
			sbBuffer.append(" order by t1.releaseDate desc");
			if (pageBean!=null) {
				sbBuffer.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
			}
			PreparedStatement statement = conn.prepareStatement(sbBuffer.toString());
			ResultSet rs=statement.executeQuery();
			while(rs.next())
			{
				Diary diary = new Diary();
				diary.setDiaryId(rs.getInt("diaryId"));
				diary.setTitle(rs.getString("title"));
				diary.setContent(rs.getString("content"));
				diary.setDiaryId(rs.getInt("diaryId"));
				diary.setTypeId(rs.getInt("typeId"));
				diary.setReleaseDate(DateUtil.formatString(rs.getString("releaseDate"), "yyyy-MM-dd HH:mm:ss"));
				diaryList.add(diary);
			}
			return diaryList;	
		}
		
		public int diaryCount(Connection conn,Diary diary) throws Exception
		{
			StringBuffer sbBuffer = new StringBuffer("select count(*) as total from tb_diary t1,tb_diaryType t2 where t1.typeId=t2.diaryTypeId");
			if (StringUtil.IsNotEmpty(diary.getTitle())) {
				sbBuffer.append(" and t1.title like '%"+diary.getTitle()+"%'");
			}
			if (diary.getTypeId()!=-1) {
				sbBuffer.append(" and t1.typeId="+diary.getTypeId());
			}
			if (StringUtil.IsNotEmpty(diary.getReleaseDateStr())) {
				sbBuffer.append(" AND DATE_FORMAT(t1.releaseDate,'%Y年%m月')='"+diary.getReleaseDateStr()+"'");
			}
			PreparedStatement statement = conn.prepareStatement(sbBuffer.toString());
			ResultSet rs=statement.executeQuery();
				if (rs.next()) {
					return rs.getInt("total");
				}else{
					return 0;
				}
		}
		
		public List<Diary> diaryDate(Connection conn)throws Exception
		{
			List<Diary>diaryDate = new ArrayList<Diary>();
			String sql="SELECT DATE_FORMAT(releaseDate,'%Y年%m月') AS diaryTime,COUNT(DATE_FORMAT(releaseDate,'%Y年%m月')) AS total FROM tb_diary GROUP BY DATE_FORMAT(releaseDate,'%Y年%m月') ORDER BY DATE_FORMAT(releaseDate,'%Y年%m月') DESC ";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while(rs.next())
			{
				Diary diary = new Diary();
				diary.setReleaseDateStr(rs.getString("diaryTime"));
				diary.setDateTotal(rs.getInt("total"));
				diaryDate.add(diary);
			}
			return diaryDate;
		}
		
		public Diary diaryShow(Connection conn,int diaryId) throws Exception
		{
			String sql="select * from tb_diary t1,tb_diaryType t2 where t1.typeId=t2.diaryTypeId and t1.diaryId=?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, diaryId);
			ResultSet rs = statement.executeQuery();
			Diary diary =null;
			if (rs.next()) {
				diary = new Diary();
				diary.setDiaryId(rs.getInt("diaryId"));
				diary.setTitle(rs.getString("title"));
				diary.setContent(rs.getString("content"));
				diary.setTypeName(rs.getString("typeName"));
				diary.setTypeId(rs.getInt("typeId"));
				diary.setReleaseDate(DateUtil.formatString(rs.getString("releaseDate"), "yy-MM-HH HH:mm:ss"));
			}
			return diary;
		}
		
		public int diarySave(Connection conn,Diary diaryInsert)throws Exception
		{
			String sql = "insert into tb_diary values(NULL,?,?,?,now())";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, diaryInsert.getTitle());
			statement.setString(2, diaryInsert.getContent());
			statement.setInt(3, diaryInsert.getTypeId());
			return statement.executeUpdate();
		}
		
		public int diaryDelet(Connection conn,int diaryId) throws Exception
		{
			String sql ="delete from tb_diary where diaryId=?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, diaryId);
			return statement.executeUpdate();
		}
		
		public int diaryUpdate(Connection conn,Diary diary) throws Exception
		{
			String sql = "update tb_diary set title=? , content=? , typeId=? where diaryId="+diary.getDiaryId();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, diary.getTitle());
			statement.setString(2, diary.getContent());
			statement.setInt(3, diary.getTypeId());
			return statement.executeUpdate();
		}
}
