package com.bridgelabz.userapis.loginregistration.services;

import java.util.ArrayList;
import java.util.List;

import javax.json.bind.JsonbException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bridgelabz.userapis.loginregistration.database.DAOImplementation;
import com.bridgelabz.userapis.loginregistration.database.DatabaseConnectivity;
import com.bridgelabz.userapis.loginregistration.database.IUserDao;
import com.bridgelabz.userapis.loginregistration.model.Users;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
	@Produces({MediaType.APPLICATION_JSON}) //will produce JSON type
//	@Produces("text/html")
	public Response registerUser(
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
		{
			System.out.println("Successfully Inserted");
//			String output = "<font face='verdana' size='2'>" +
//	                "Web Service has added your Customer information with Name - <u>"+firstname+"</u>, Country - <u>"+state+"</u></font>";
//	        return Response.status(200).entity(output).build();
		}
		usersList.add(userInfo);	
		return Response.status(200).entity(usersList).build();
	}

	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public String indexes() 
	{
		JsonArray userList = daoObj.getAll();
		System.out.println("List :"+userList);
		return userList.toString();
		//new Viewable("/usersData.jsp", userList);
	}
	
	@GET
    @Path("{mail}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCustomer(@PathParam("mail") String mail) throws JsonbException
	{ 
        return daoObj.getUserByName(mail);
    }
	
}
