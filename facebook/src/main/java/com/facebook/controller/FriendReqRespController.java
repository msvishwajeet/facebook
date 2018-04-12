package com.facebook.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facebook.model.FriendRequest;
import com.facebook.model.User;
import com.facebook.persistence.FollowPersistenceJDBI;
import com.facebook.persistence.FriendPersistenceJDBI;
import com.facebook.persistence.FriendRequestPersistenceJdbi;
import com.facebook.persistence.PostPersistenceJDBI;
import com.facebook.persistence.UserPersistenceJDBI;

@Service
public class FriendReqRespController {
	@Autowired
	ValidationController validationContoller;
	@Autowired
	PostPersistenceJDBI postPersistenceJDBI;
	@Autowired
	UserController userController;
	@Autowired
	FriendPersistenceJDBI friendPersistenceJDBI;
	@Autowired
	FollowPersistenceJDBI followPersistenceJDBI;
	@Autowired
	FriendRequestPersistenceJdbi friendReuestPersistenceJdbi;
	@Autowired
	UserPersistenceJDBI userPersistenceJDBI;

	public void addFriendOrFollow(User user) {
		List<User> userList = friendPersistenceJDBI.getNewUser(user.getUserId());
		int temp = 0;
		boolean readUserMind = true;
		while(temp < userList.size() && readUserMind) {
			userController.printUser(userList.get(temp));

			int response = activityOption(user.getUserId(), userList.get(temp).getUserId());
			switch (response) {
			case 1:
				break;
			case 2:
				temp++;
				break;
			case 0:
				return;
			}
			if (temp==userList.size()) {
				System.err.println("No More Users");
			}
		}
	}

	public int activityOption(int userId,int secondUserId) {
		System.out.println("press 1. To Send Request");
		System.out.println("Press 2. Follow");
		System.out.println("Press 3. To see next");
		System.out.println("press 0. for Home......");
		int input = validationContoller.validInt();
		switch (input) {
		case 1:
			friendReuestPersistenceJdbi.sendReq(userId, secondUserId);
			return 1;
		case 2:
			followPersistenceJDBI.startFollow(userId, secondUserId);
			return 1;
		case 3:
			return 2;
		default:
			System.out.println("Wrong Input");
			return 1;
		case 0:
			return 0;
		}
	}

	public void acceptRequest(User user) {
		List<FriendRequest> friendListObj = friendReuestPersistenceJdbi.getPendingReq(user.getUserId());
		if(friendListObj.isEmpty()) {
			System.out.println("No pending Request");
			return;
		}
		List<User> listOfRequest = new ArrayList<User>();
		for (FriendRequest friendRequest : friendListObj) {
			User tempUser = userPersistenceJDBI.getuser(friendRequest.getUserId());
					listOfRequest.add(tempUser);
		}
		int temp = 0;
		boolean readUserMind = true;
		while(temp < listOfRequest.size() && readUserMind) {
			userController.printUser(listOfRequest.get(temp));

			int response = operationOnRequest(user.getUserId(), listOfRequest.get(temp).getUserId());
			switch (response) {
			case 1:
				break;
			case 2:
				temp++;
				break;
			case 0:
				return;
			}
			if (temp==listOfRequest.size()) {
				System.out.println("No More Request pending.....");
			}
		}
	}
	public int operationOnRequest(int userId,int secondUserId) {
		System.out.println("press 1. Accept");
		System.out.println("Press 2. Reject");
		System.out.println("Press 3. to see next");
		System.out.println("press 0. for Home......");
		int input = validationContoller.validInt();
		switch (input) {
		case 1:
			friendReuestPersistenceJdbi.acceptFriendreq(userId, secondUserId);
			followPersistenceJDBI.startFollow(userId, secondUserId);
			return 1;
		case 2:
			friendReuestPersistenceJdbi.deleteReq(userId, secondUserId);
			return 1;
		case 3:
			return 2;
		default:
			System.out.println("Wrong Input");
			return 1;
		case 0:
			return 0;
		}
	}
}



