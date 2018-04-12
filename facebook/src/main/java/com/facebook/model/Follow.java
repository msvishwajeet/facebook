package com.facebook.model;

import java.sql.Timestamp;

public class Follow {
	private int followId;
	private int userId;
	private int friendsId;
	private Timestamp dateOfFollow;
	
	public int getFollowId() {
		return followId;
	}
	public void setFollowId(int followId) {
		this.followId = followId;
	}
	public int getFriendsId() {
		return friendsId;
	}
	public void setFriendsId(int friendsId) {
		this.friendsId = friendsId;
	}
	public Timestamp getDateOfFollow() {
		return dateOfFollow;
	}
	public void setDateOfFollow(Timestamp dateOfFollow) {
		this.dateOfFollow = dateOfFollow;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

}
