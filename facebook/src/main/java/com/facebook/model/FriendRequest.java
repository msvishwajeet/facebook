package com.facebook.model;

import java.sql.Timestamp;

public class FriendRequest {
	private int userId;
	private int secondUserId;
	private int id;
	private Timestamp dateOfReq;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getSecondUserId() {
		return secondUserId;
	}
	public void setSecondUserId(int secondUserId) {
		this.secondUserId = secondUserId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Timestamp getDateOfReq() {
		return dateOfReq;
	}
	public void setDateOfReq(Timestamp dateOfReq) {
		this.dateOfReq = dateOfReq;
	}

}
