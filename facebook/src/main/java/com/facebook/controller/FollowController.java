package com.facebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facebook.model.Follow;
import com.facebook.persistence.FollowPersistenceJDBI;

@Service
public class FollowController {
@Autowired
FollowPersistenceJDBI followPersistenceJDBI;
 

public void satrtFollow(int userId,int friendsId) {
	Follow follow = new Follow();
	follow.setFriendsId(friendsId);
	follow.setUserId(userId);
}


public void getTotalFollowers(int userId) {
	int count = followPersistenceJDBI.myTotalFoolowers(userId);
	System.out.println("Your Total Number of Followers is :"+ count);
}
public void youFollow(int userId) {
	int count = followPersistenceJDBI.youFollow(userId);
	System.out.println("You are following "+count+" people");
}

public void startFollow(int userId,int secondUserId) {
	
}



}
