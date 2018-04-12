package com.facebook.controller;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class ValidationController {
	public static final Scanner scanner = new Scanner(System.in);
	
	//************INPUT VALIDATOR********************
	public int validInt() {
		String s = scanner.nextLine();
		while (!isInteger(s)) {
			System.out.println("Please Enter Digits only...");
			 s = scanner.nextLine();
		}
		return Integer.parseInt(s);
	}
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	//************MOBILE NUMBER VALIDATOR********************
	public String validMobileNumber() {
		String mobileNumber = scanner.nextLine();
		while (!mobileValidator(mobileNumber)) {
			System.out.println("Please Enter a valid Mobile Number...");
			mobileNumber = scanner.nextLine();
		}
		return mobileNumber;
	}
	public boolean mobileValidator(String mobileNumber) {
		if (mobileNumber.length()!=10) {
			return false;
		}
		Pattern pattern = Pattern.compile("[7-9][0-9]{9}");
		Matcher matcher = pattern.matcher(mobileNumber);
		if(matcher.find()&&matcher.group().equals(mobileNumber)) {
			return true;
		}
		return false;
	}
	//************E-MAIL VALIDATOR********************
	public String validEmail() {
		String email = scanner.nextLine();
		while (!emailValidator(email)) {
			System.out.println("Please Enter a valid email Id...");
			email = scanner.nextLine();
		}
		return email;
	}
	public boolean emailValidator(String email) {
		Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9-.]*@gmail[.]com");
		Matcher matcher = pattern.matcher(email);
		if(matcher.find()&& matcher.group().equals(email)) {
			return true;
		}
		return false;
	}
	
	
}
