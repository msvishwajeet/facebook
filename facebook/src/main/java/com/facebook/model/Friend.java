package com.facebook.model;

import java.sql.Timestamp;

public class Friend {
	private int userId;
	private int secondUserId;
	private int id;
	private Timestamp date;
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
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}

}
