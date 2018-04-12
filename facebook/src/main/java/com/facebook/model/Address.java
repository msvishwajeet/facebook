package com.facebook.model;

public class Address {
private int addressId;
private int userId;
private int pin;
private String cityName;
private String StateName;
public int getAddressId() {
	return addressId;
}
public void setAddressId(int addressId) {
	this.addressId = addressId;
}
public int getUserId() {
	return userId;
}
public void setUserId(int userId) {
	this.userId = userId;
}
public int getPin() {
	return pin;
}
public void setPin(int pin) {
	this.pin = pin;
}
public String getCityName() {
	return cityName;
}
public void setCityName(String cityName) {
	this.cityName = cityName;
}
public String getStateName() {
	return StateName;
}
public void setStateName(String stateName) {
	StateName = stateName;
}
}
