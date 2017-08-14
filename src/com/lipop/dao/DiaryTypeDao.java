package com.lipop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.lipop.model.DiaryType;

public class DiaryTypeDao {
		public List<DiaryType> diaryTypes(Connection conn)throws Exception
		{
			List<DiaryType> diaryTypes = new ArrayList<DiaryType>();
			String sql="SELECT diaryTypeId,typeName,COUNT(diaryId) as total FROM tb_diary RIGHT JOIN tb_diaryType ON tb_diary.typeId=tb_diaryType.diaryTypeId GROUP BY typeName";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while(rs.next())
			{
				DiaryType diaryType = new DiaryType();
				diaryType.setTypeName(rs.getString("typeName"));
				diaryType.setTotal(rs.getInt("total"));
				diaryType.setDiaryTypeId(rs.getInt("diaryTypeId"));
				diaryTypes.add(diaryType);
			}
			return diaryTypes;
		}
		
		public List<DiaryType> diaryTypeList(Connection conn)throws Exception
		{
			List<DiaryType>diaryTypeList = new ArrayList<DiaryType>();
			String sql = "select * from tb_diaryType";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs= statement.executeQuery();
			while(rs.next()){
				DiaryType diaryType = new DiaryType();
				diaryType.setDiaryTypeId(rs.getInt("diaryTypeId"));
				diaryType.setTypeName(rs.getString("typeName"));
				diaryTypeList.add(diaryType);
			}
			return diaryTypeList;
		}
		
		public int diaryTypeDelet(Connection conn,int diaryTypeId) throws Exception
		{
			String sql ="delete from tb_diaryType where diaryTypeId=?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, diaryTypeId);
			return statement.executeUpdate();
		}
		
		public boolean existDiary(Connection conn,int diaryTypeId) throws Exception
		{
			String sql = "select count(*) as total from tb_diary where typeId=?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, diaryTypeId);
			ResultSet rs = statement.executeQuery();
			int i=0;
			if (rs.next()) {
				i=rs.getInt("total");
			}
			if (i>0) {
				return true;
			}else{
				return false;
			}	
		}
		
		public int diaryTypeAdd(Connection conn,String diaryTypeName)throws Exception
		{
			String sql ="insert into tb_diaryType values(null,?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, diaryTypeName);
			return statement.executeUpdate();
		}
		
		public int diaryTypeUpdate(Connection conn,DiaryType diaryType)throws Exception
		{
			String sql ="update tb_diaryType set typeName=? where diaryTypeId=? ";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, diaryType.getTypeName());
			statement.setInt(2, diaryType.getDiaryTypeId());
			return statement.executeUpdate();
		}
		
		public DiaryType diaryTypeShow(Connection conn,int diaryTypeId)throws Exception
		{
			String sql="select * from tb_diaryType where diaryTypeId=?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, diaryTypeId);
			ResultSet rs= statement.executeQuery();
			DiaryType diaryType = new DiaryType();
			while(rs.next()){
				diaryType.setDiaryTypeId(rs.getInt("diaryTypeId"));
				diaryType.setTypeName(rs.getString("typeName"));
			}
			return diaryType;
		}
}
