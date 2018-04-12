package com.facebook.controller;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facebook.model.Address;
import com.zaxxer.hikari.HikariDataSource;
import com.facebook.model.User;
import com.facebook.persistence.AddressPersistenceJDBI;
import com.facebook.persistence.FriendPersistenceJDBI;
import com.facebook.persistence.UserPersistenceJDBI;
@Service
public class FriendController {
	public static final Scanner scanner = new Scanner(System.in);
	@Autowired
	ValidationController validationController;
	@Autowired
	FriendPersistenceJDBI friendPersistenceJDBI;
	@Autowired
	UserPersistenceJDBI userPersistenceJDBI;
	@Autowired
	AddressPersistenceJDBI addressPersistenceJDBI;
	
	public void getfriendCount(int userId) {
		int count = friendPersistenceJDBI.getFriendCount(userId);
		System.out.println("Your total Number of friend: "+count);
	}
	
	public void addFriend(int firstUserId) {
		System.out.println("Enter Your Friends Id");
		int secondUserId = validationController.validInt();
		friendPersistenceJDBI.addFriend(firstUserId, secondUserId);
		System.out.println("SuccessFully Added");
	}
	
	public void addFriend(int userId,int secondUserId) {
		friendPersistenceJDBI.addFriend(userId, secondUserId);
		System.out.println("SuccessFully Added");
	}
	
	public void getAllUserInState(String stateName) {
		System.out.println("_______________________________________________");
		System.out.format("%-20s%-13s%-22s\n", "User Name", "User Id", "Address");
		System.out.println("_______________________________________________");
		System.out.println("-----------------------------------------------");
		
		List<User> list = friendPersistenceJDBI.getUserByState(stateName);
		
		for (User user : list) {
			
			System.out.format("%-20s%-13s%-22s\n", user.getFirstName()+" "+user.getLastName(),user.getUserId(), addressPersistenceJDBI.getAddress(user.getUserId()).getCityName());
			System.out.println("_______________________________________________");
		}
		System.out.println();
	}
	
}
