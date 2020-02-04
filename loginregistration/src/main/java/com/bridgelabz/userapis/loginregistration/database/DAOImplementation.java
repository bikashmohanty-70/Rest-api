package com.bridgelabz.userapis.loginregistration.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bridgelabz.userapis.loginregistration.model.Users;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * 
 * @author Bikash Mohanty
 * @since 25Th Jan 2020
 * @version 1.0
 * 
 * Purpose: 
 *
 */
public class DAOImplementation implements IUserDao 
{
	Users usersBean = new Users();
	static Connection connection = DatabaseConnectivity.getConnection(); 
	static PreparedStatement preparedStatement = null;
	Statement statement = null;
	ResultSet resultSet = null;
	JsonArray userArrayData = new JsonArray();
	@Override
	public boolean insertIntoUserApiTable(Users usersBean)
	{
		String insertQuery = "INSERT INTO users_api (firstname, lastname, username, password, email, city, state, zip) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try(PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) 
		{
			System.out.println("Connection "+connection);
			preparedStatement.setString(1, usersBean.getFirstname());
			preparedStatement.setString(2, usersBean.getLastname());
			preparedStatement.setString(3, usersBean.getUsername());
			preparedStatement.setString(4, usersBean.getPassword());
			preparedStatement.setString(5, usersBean.getEmail());
			preparedStatement.setString(6, usersBean.getCity());
			preparedStatement.setString(7, usersBean.getState());
			preparedStatement.setString(8, usersBean.getZip());
			
			if(preparedStatement.executeUpdate() > 0)
				return true;
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	@Override
	public boolean updateUserApiTable()
	{
		return false;
	}
	
	@Override
	public boolean deleteRecordsInUserApiTable()
	{
		return false;
	}

	@Override
	public JsonArray getAll() 
	{
		

		String userList = "select * from users_api";
		try(PreparedStatement preparedStatement = connection.prepareStatement(userList))
		{
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) 
			{		
				JsonObject usersData = new JsonObject();
				
				usersData.addProperty("Id",resultSet.getString(1));
				usersData.addProperty("firstname",resultSet.getString(2));
				usersData.addProperty("lastname", resultSet.getString(3));
				usersData.addProperty("username", resultSet.getString(4));
				usersData.addProperty("email", resultSet.getString(8));
				usersData.addProperty("password", resultSet.getString(9));
				usersData.addProperty("city", resultSet.getString(5));
				usersData.addProperty("state", resultSet.getString(6));
				usersData.addProperty("zip", resultSet.getString(7));
				
				userArrayData.add(usersData);
			}
			return userArrayData;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
