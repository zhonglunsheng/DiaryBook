package com.lipop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.lipop.model.User;
import com.lipop.util.DbUtil;
import com.lipop.util.PropertiesUtil;

public class UserDao {
		DbUtil dbUtil=new DbUtil();
		public User userLogin(Connection conn,User user) throws Exception
		{
			User user2=null;
			conn=dbUtil.getCon();
			String sql="select * from tb_user where userName=? and password=?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getPassword());
			ResultSet rs = statement.executeQuery();
			while(rs.next())
			{
				user2=new User();
				user2.setUserId(rs.getInt("userId"));
				user2.setUserName(rs.getString("userName"));
				user2.setPassword(rs.getString("password"));
				user2.setNickName(rs.getString("nickName"));
				user2.setImageName(PropertiesUtil.getValue("imageUrl")+rs.getString("imageName"));
				user2.setMood(rs.getString("mood"));
			}
			return user2;
		}
		
		public int updateUser (Connection conn,User user)throws Exception
		{
			String sql ="update tb_user set nickName=? , imageName=? , mood=? where userId=1";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, user.getNickName());
			statement.setString(2, user.getImageName());
			statement.setString(3, user.getMood());
			return statement.executeUpdate();
		}
		
		public int psaveUser (Connection conn,User user)throws Exception
		{
			String sql ="update tb_user set nickName=? , mood=? where userId=1";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, user.getNickName());
			statement.setString(2, user.getMood());
			return statement.executeUpdate();
		}
		
		public User showUser(Connection conn)throws Exception
		{
			String sql = "select * from tb_user";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			User user = new User();
			if (rs.next()) {
				user.setNickName(rs.getString("nickName"));
				user.setMood(rs.getString("mood"));
				user.setImageName(PropertiesUtil.getValue("imageUrl")+rs.getString("imageName"));
			}
			return user;
		}
		
		
}
