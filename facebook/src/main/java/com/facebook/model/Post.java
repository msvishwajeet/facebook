package com.facebook.model;

import java.sql.Timestamp;

public class Post {
	private String content;
	private Timestamp dateOfPost;
	private int postId;
	private int userId;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getDateOfPost() {
		return dateOfPost;
	}
	public void setDateOfPost(Timestamp dateOfPost) {
		this.dateOfPost = dateOfPost;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}


}
