package com.bridgelabz.userapis.loginregistration.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.server.mvc.Viewable;

import com.bridgelabz.userapis.loginregistration.database.DAOImplementation;
import com.bridgelabz.userapis.loginregistration.database.DatabaseConnectivity;
import com.bridgelabz.userapis.loginregistration.database.IUserDao;
import com.bridgelabz.userapis.loginregistration.model.Users;
import com.google.gson.JsonArray;

/**
 * 
 * @author Bikash Mohanty
 * @since 25th jan 2020
 * @version 1.0
 * 
 * purpose:
 *
 */

@Path("/usersRegistration")
public class UsersRegistration 
{
	
	public UsersRegistration() {
	}

	DatabaseConnectivity dtabaseConnectivity = new DatabaseConnectivity();
	IUserDao daoObj = new DAOImplementation();
	

	@POST
	@Path("/addUsers")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void registerUser(
			@FormParam("fname") String firstname,
			@FormParam("lname") String lastname,
			@FormParam("uname") String username,
			@FormParam("password") String password,
			@FormParam("mail") String email,
			@FormParam("city_name") String city,
			@FormParam("state_name") String state,
			@FormParam("pincode") String zip)
	{
		
		List<Users> usersList = new ArrayList<Users>();
		Users userInfo = new Users();
		userInfo.setFirstname(firstname);
		userInfo.setLastname(lastname);
		userInfo.setUsername(username);
		userInfo.setPassword(password);
		userInfo.setEmail(email);
		userInfo.setCity(city);
		userInfo.setState(state);
		userInfo.setZip(zip);
		
		
		if(daoObj.insertIntoUserApiTable(userInfo))
			System.out.println("Successfully Inserted");
		usersList.add(userInfo);		
	}
	

//	@GET
//	@Path("/getAll")
//	//@Consumes(MediaType.APPLICATION_JSON)
////	@Produces(MediaType.TEXT_HTML)
//	public JsonArray display() 
//	{
//		JsonArray userList = daoObj.getAll();
//		System.out.println("List :"+userList);
//		
////		return new Viewable("/feed", model);
//		return userList;
//	}
	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
    public String indexes() {
//        Map<String, String> model = new HashMap<>();
		JsonArray userList = daoObj.getAll();
		System.out.println("List :"+userList);
        return userList.toString();
        		//new Viewable("/usersData.jsp", userList);
    }
}
