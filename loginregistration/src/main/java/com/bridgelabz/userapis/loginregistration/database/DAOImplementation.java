package com.bridgelabz.userapis.loginregistration.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bridgelabz.userapis.loginregistration.model.Users;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * 
 * @author Bikash Mohanty
 * @since 25Th Jan 2020
 * @version 1.0 
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

	@Override
	public String getUserByName(String mail) 
	{
		Users userDetails = new Users();
		String query = "SELECT * FROM users_api WHERE email = ?";
		try(PreparedStatement preparedStatementMail = connection.prepareStatement(query)) 
		{
			preparedStatementMail.setString(1, mail);
			ResultSet resultset = preparedStatementMail.executeQuery();
			if(resultset.next())
			{
				userDetails.setFirstname(resultset.getString(2));
				userDetails.setLastname(resultset.getString(3));
				userDetails.setUsername(resultset.getString(4));
				userDetails.setCity(resultset.getString(5));
				userDetails.setState(resultset.getString(6));
				userDetails.setZip(resultset.getString(7));
				userDetails.setEmail(resultset.getString(8));
				userDetails.setPassword(resultset.getString(9));
				
				Gson gson = new Gson();
				
				return gson.toJson(userDetails);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String updateUsersByMail(String mail, Users user) 
	{
		String updateQuery = "UPDATE users_api SET firstname = ?, lastname = ?, username = ?, city = ?,"
				+ "state = ?, zip = ?, email = ?, password = ? WHERE email = '"+mail+"'";
		
		try(PreparedStatement preparedStatementUpdate = connection.prepareStatement(updateQuery)) 
		{
			
			preparedStatementUpdate.setString(1, user.getFirstname());
			preparedStatementUpdate.setString(2, user.getLastname());
			preparedStatementUpdate.setString(3, user.getUsername());
			preparedStatementUpdate.setString(4, user.getCity());
			preparedStatementUpdate.setString(5, user.getState());
			preparedStatementUpdate.setString(6, user.getZip());
			preparedStatementUpdate.setString(7, user.getEmail());
			preparedStatementUpdate.setString(8, user.getPassword());
			
			int result = preparedStatementUpdate.executeUpdate();
			if(result > 0)
				return "User Updated Successfully";
			else
				return "Internal Error While Updating record";
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String deleteUser(String mail) 
	{
		String deleteQuery = "DELETE FROM users_api WHERE email = '"+mail+"'";
		try(PreparedStatement preparedStatementDelete = connection.prepareStatement(deleteQuery)) 
		{
			int deleteResult = preparedStatementDelete.executeUpdate();
			if(deleteResult > 0)
				return "User Deleted Successfully...";
			else
				return "Error While Deleting Record...";
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
