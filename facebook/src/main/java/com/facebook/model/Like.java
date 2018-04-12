package com.facebook.model;

import java.sql.Timestamp;

public class Like {
	private int postId;
	private Timestamp dateOfLike;
	private int userId;
	private int id;
	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public Timestamp getDate() {
		return dateOfLike;
	}
	public void setDate(Timestamp dateOfLike) {
		this.dateOfLike = dateOfLike;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
