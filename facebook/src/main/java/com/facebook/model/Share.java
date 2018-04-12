package com.facebook.model;

import java.sql.Timestamp;

public class Share {
	private int postId;
	private Timestamp dateOfShare;
	private int shareId;
	private int userId;
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public Timestamp getDateOfShare() {
		return dateOfShare;
	}
	public void setDateOfShare(Timestamp dateOfShare) {
		this.dateOfShare = dateOfShare;
	}
	public int getShareId() {
		return shareId;
	}
	public void setShareId(int shareId) {
		this.shareId = shareId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

}
