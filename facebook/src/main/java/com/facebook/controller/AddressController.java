package com.facebook.controller;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facebook.model.Address;
import com.facebook.persistence.AddressPersistenceJDBI;

@Service
public class AddressController {
	public static final Scanner scanner = new Scanner(System.in);
	@Autowired
	ValidationController validationController;
	@Autowired
	AddressPersistenceJDBI addressPersistenceJDBI;
	
	public void insertAddress(int userId) {
		Address address = new Address();
		System.out.println("Enter your pin");
		address.setPin(validationController.validInt());
		System.out.println("Enter your city Name");
		address.setCityName(scanner.nextLine());
		System.out.println("Enter State Name");
		address.setStateName(scanner.nextLine());
		address.setUserId(userId);
		if (addressPersistenceJDBI.getCount(userId)!=0) {
			System.out.println("Already Record Exist for the given user!");
			return;
		}
		addressPersistenceJDBI.insertAddress(address);
		System.out.println("Successfully Added");
	}
	public void updateAddress(int userId) {
		System.out.println("Enter Your Pin");
		int pin = validationController.validInt();
		System.out.println("Enter Your City Name");
		String cityName = scanner.nextLine();
		System.out.println("Enter your State Name");
		String stateName = scanner.nextLine();
		addressPersistenceJDBI.updateAddress(userId, pin, cityName, stateName);
	}
	
	public void insertBlankAddress(int userId) {
		Address address = new Address();
		address.setCityName("");
		address.setPin(-1);
		address.setStateName("");
		address.setUserId(userId);
		addressPersistenceJDBI.insertAddress(address);
	}
	public void getYourAddress(int userId) {

		if(addressPersistenceJDBI.getCount(userId)==0) {
			System.out.println("No match found!!!");
		}
		else {
			Address address =addressPersistenceJDBI.getAddress(userId);
			System.out.println("Pin: "+address.getPin());
			System.out.println("City : "+address.getCityName());
			System.out.println("State: "+address.getStateName().toUpperCase());
			
		}
		}
	public  void getAddressCount() {
		int s = addressPersistenceJDBI.getCount();
		System.out.println(s);
	}

}
