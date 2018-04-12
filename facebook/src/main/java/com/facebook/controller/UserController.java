package com.facebook.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facebook.model.Address;
import com.facebook.model.Post;
import com.facebook.model.User;
import com.facebook.persistence.AddressPersistenceJDBI;
import com.facebook.persistence.FollowPersistenceJDBI;
import com.facebook.persistence.FriendPersistenceJDBI;
import com.facebook.persistence.FriendRequestPersistenceJdbi;
import com.facebook.persistence.LikePersistenceJDBI;
import com.facebook.persistence.PostPersistenceJDBI;
import com.facebook.persistence.SharePersistenceJDBI;
import com.facebook.persistence.UserPersistenceJDBI;
@Service
public class UserController {
	public static final Scanner scanner = new Scanner(System.in);
	@Autowired
	SharePersistenceJDBI sharePersistenceJDBI;
	@Autowired
	LikePersistenceJDBI likePersistenceJDBI;
	@Autowired
	ValidationController validationController;
	@Autowired
	UserPersistenceJDBI userPersistenceJDBI;
	@Autowired
	AddressPersistenceJDBI addressPersistenceJDBI;
	@Autowired
	AddressController addressController;
	@Autowired
	FriendController friendController;
	@Autowired
	FriendPersistenceJDBI friendPersistenceJDBI;
	@Autowired
	FollowPersistenceJDBI followPersistenceJDBI;
	@Autowired
	PostPersistenceJDBI postPersistenceJDBI;
	@Autowired
	TimelineContoller timelineContoller;
	@Autowired
	FriendReqRespController friendReqRespController;
	@Autowired
	FriendRequestPersistenceJdbi friendRequestPersistenceJdbi;

	public void register() {
		System.out.println("Enter Your Gmail");
		String email =validationController.validEmail();
		int available=userPersistenceJDBI.emailExistence(email);
		if (available>0) {
			System.out.println("Email Already Exist");
			System.out.println("Please Register with your Unique Gmail Id");
			System.out.println();
			return;
		}
		User user = new User();
		user.setEmail(email);
		System.out.println("Enter your First Name");
		user.setFirstName(scanner.nextLine());
		System.out.println("Enter your Last Name");
		user.setLastName(scanner.nextLine());
		System.out.println("Slect Your Gender");
		String gender="";
		System.out.println("press 1. for Male");
		System.out.println("Press 2. for Female");
		int tempInput = validationController.validInt();
		while(tempInput > 2 || tempInput < 1) {
			System.out.println("Please press 1 or 2 Only....");
			tempInput = validationController.validInt();
		}
		switch (tempInput) {
		case 1:
			gender = "Male";
			break;
		case 2:
			gender = "Female";
			break;

		}
		user.setGender(gender);


		System.out.println("Enter your password ");
		String password = scanner.nextLine();
		while(password.length()<6) {
			System.out.println("Please Enter atleast 6 character Password");
			password = scanner.nextLine();
		}
		user.setPassword(password);
		System.out.println("Enter Your Mobile number");
		user.setMobileNumber(validationController.validMobileNumber());
		Date date =new Date();
		Timestamp timeStamp=new Timestamp(date.getTime());  
		user.setTimeStamp(timeStamp);
		int newUserId = userPersistenceJDBI.insertUser(user);
		System.out.println("Successfully Added");
		System.out.println("press 1. to add your Address");
		System.out.println("press any other to skip....");
		int input = validationController.validInt();
		switch (input) {
		case 1:
			addressController.insertAddress(newUserId);
			break;

		default:
			addressController.insertBlankAddress(newUserId);
			break;
		}
		
		System.out.println("You have been Registered Successfully....");
		System.out.println("_______________________________________________________");
		System.out.println();
	}

	public void login() {
		System.out.println("Enter you Email-ID");
		String email = validationController.validEmail();
		System.out.println("Enter your password");
		String password = scanner.nextLine();
		while(userPersistenceJDBI.getuser(email, password).size()==0) {
			System.out.println("Wrong Email Or Password");
			System.out.println("Press  1. to Re-enter");
			System.out.println("Press any other to go to main menu");
			int input = validationController.validInt();
			switch (input) {
			case 1:
				System.out.println("Enter Your Email");
				email = validationController.validEmail();
				System.out.println("Eeter Your Paasword");
				password = scanner.nextLine();
				break;
			default:
				return;
			}
		}
		User user = userPersistenceJDBI.getuser(email, password).get(0);
		String title = "";
		if (user.getGender().equals("Male")) {
			title = "Mr. ";
		}
		else {
			title = "Ms. ";
		}
		System.out.println("_________________________________________________");
		System.out.println("Welcome "+title+user.getFirstName()+ " "+user.getLastName());
		System.out.println("_________________________________________________");
		System.out.println();
		start(user);
	}
	public void start(User user) {
		System.out.println("Press 1. to see your Profile");
		System.out.println("Press 2. to add or Folow friend");
		System.out.println("press 3. to Accept Pending Requests");
		System.out.println("press 4. to post Some thing");
		System.out.println("press 5. to See Time-Line");
		System.out.println("press 0. to Log Out....");
		int input = validationController.validInt();

		switch (input) {
		case 1:
			userProfile(user);
			start(user);
			break;
		case 2:
			friendReqRespController.addFriendOrFollow(user);
			start(user);
			break;
		case 3:
			friendReqRespController.acceptRequest(user);

			start(user);
			break;

		case 4:
			System.out.println("What's on Your Mind");
			String content = scanner.nextLine();
			postPersistenceJDBI.insertPost(content, user.getUserId());
			System.out.println("Suucessfully Posted");
			System.out.println();
			start(user);
			break;


		case 5:
			System.out.println("----->> TIME-LINE <<------");
			timelineContoller.seeTimeLine(user);
			start(user);
			break;

		case 0:

			return;

		default:
			System.out.println("Wrong input");
			start(user);
			break;
		}
	}

	public void userProfile(User user) {
		System.out.println("_______________________________________________");
		System.out.format("%-20s%-23s\n", "Name:","|     "+user.getFirstName()+" "+user.getLastName());
		System.out.println("_______________________________________________|");
		System.out.format("%-20s%-23s\n", "Contact Number:","|     "+user.getMobileNumber());
		System.out.println("_______________________________________________|");
		System.out.format("%-20s%-23s\n", "Date of creation:","|     "+user.getTimeStamp().toString().substring(0, 10));
		System.out.println("_______________________________________________|");
		int followers;
		if (followPersistenceJDBI.myTotalFoolowers(user.getUserId())==-1) {
			followers = 0;
		}
		else {
			followers = followPersistenceJDBI.myTotalFoolowers(user.getUserId());
		}
		int followCount;
		if (followPersistenceJDBI.youFollow(user.getUserId())==-1) {
			followCount = 0;
		}
		else {
			followCount = followPersistenceJDBI.youFollow(user.getUserId());
		}
		System.out.format("%-20s%-23s\n", "Followers: ("+followers+")","| Following: ("+followCount+")");
		System.out.println("_______________________________________________|");
		int friendCount;
		if (friendPersistenceJDBI.getFriendCount(user.getUserId())==-1) {
			friendCount = 0;
		}
		else {
			friendCount = friendPersistenceJDBI.getFriendCount(user.getUserId());
		}
		int request;
		if (friendRequestPersistenceJdbi.getReqCount(user.getUserId())==-1) {
			request = 0;
		}
		else {
			request = friendRequestPersistenceJdbi.getReqCount(user.getUserId());
		}
		System.out.format("%-20s%-23s\n", "Friends: ("+friendCount+")","| Pending Request: ("+request+")");
		System.out.println("_______________________________________________|");
		System.out.println();
		System.out.format("%40s\n", "------------------Address------------------");
		System.out.println("_______________________________________________");
		Address address = addressPersistenceJDBI.getAddress(user.getUserId());
		if (addressPersistenceJDBI.getAddress(user.getUserId())!=null && addressPersistenceJDBI.getAddress(user.getUserId()).getCityName().length()>0) {
			System.out.format("%-20s%-30s\n", "Zip Code: -"+address.getPin()+"-","| City: -"+address.getCityName().toUpperCase()+"-");
			System.out.println("_______________________________________________|");
			System.out.format("%-20s%-30s\n", "State: -"+address.getStateName()+"-","| Country: -INDIA-");
		}
		else {
			System.out.println("--------------No Address Saved-------------");
		}
		System.out.println("_______________________________________________|");
		printMyLastPost(user);


		System.out.println("press 1. to update your Address");
		System.out.println("Press any other for HOME.....");
		int inputs = validationController.validInt();
		switch(inputs) {
		case 1:
			addressController.updateAddress(user.getUserId());
			break;
		default:
			break;
		}
	}

	public void getAllUser() {
		System.out.println("__________________________________________________________________|");
		System.out.format("%-20s%-13s%-22s\n", "User Name", "User Id", "Address");
		System.out.println("__________________________________________________________________|");
		System.out.println("------------------------------------------------------------------");

		List<User> list = userPersistenceJDBI.getAllUsers();

		for (User user : list) {
			if(addressPersistenceJDBI.getCount(user.getUserId())!=0) {
				System.out.format("%-20s%-13s%-22s\n", user.getFirstName()+" "+user.getLastName(),user.getUserId(), addressPersistenceJDBI.getAddress(user.getUserId()).getCityName()
						+" ("+addressPersistenceJDBI.getAddress(user.getUserId()).getStateName()+" )");
				System.out.println("__________________________________________________________________|");
			}
			else {
				System.out.format("%-20s%-13s%-22s\n", user.getFirstName()+" "+user.getLastName(),user.getUserId(), "");
				System.out.println("__________________________________________________________________|");
			}
		}
		System.out.println();
	}

	public void printPost(Post post) {
		String[] arr =  post.getContent().split(" ");
		String s = "";
		for (int i = 0; i < arr.length; i++) {
			if (i%5==0 && i > 0) {
				s+="\n";
			}
			s+=arr[i]+" ";
		}
		System.out.println("_________________________________");
		System.out.format("%-25s\n", s);
		User tempUser = userPersistenceJDBI.getuser(post.getUserId());
		String title;
		if (tempUser.getGender().equals("Male")) {
			title = "Mr. ";
		}
		else {
			title = "Ms. ";
		}
		System.out.println("_________________________________");
		System.out.format("%-21s\n","Posted By: "+title+tempUser.getFirstName()+" "+tempUser.getLastName());
		System.out.println("_________________________________");
		System.out.format("%-21s\n", "Posted On:  "+post.getDateOfPost().toString().substring(0, 11));
		System.out.println("_________________________________");
		System.out.format("%-21s%-21s\n", "Likes:  ("+likePersistenceJDBI.totalLikes(post.getPostId())+")",
				"Share:  ("+sharePersistenceJDBI.totalShare(post.getPostId())+")");
		System.out.println("_________________________________");
	}
	public void printUser(User user) {
		System.out.println("_______________________________________________");
		System.out.format("%-25s%-23s\n", "Name: "+user.getFirstName()+" "+user.getLastName(),"|  (-"+user.getGender()+"-)");

		if (addressPersistenceJDBI.getAddress(user.getUserId())!=null && addressPersistenceJDBI.getAddress(user.getUserId()).getCityName().length()>0) {
			Address address = addressPersistenceJDBI.getAddress(user.getUserId());
			System.out.println("_______________________________________________|");
			System.out.format("%-25s%-23s\n", "City: -"+address.getCityName()+"-","| State: -"+address.getStateName()+"-");
		}
		System.out.println("_______________________________________________|");
		System.out.println();
	}
	public void printMyLastPost(User user) {
		if (postPersistenceJDBI.getLastPost(user.getUserId()).isEmpty()) {
			System.out.println("---------------Nothing posted----------------- ");
			System.out.println("_______________________________________________");
			System.out.println();
			return;
		}
		System.out.println();
		System.out.println("***** -Your Last Post Details are- ******");
		Post post = postPersistenceJDBI.getLastPost(user.getUserId()).get(postPersistenceJDBI.getLastPost(user.getUserId()).size()-1);

		System.out.println("_______________________________________________");
		String[]arr = post.getContent().split(" ");
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]+" ");
			if (i % 5 == 0 && i > 0) {
				System.out.println();
			}
		}
		System.out.println();
		System.out.println("_______________________________________________|");
		System.out.format("%-21s%-25s\n","Likes: ("+ likePersistenceJDBI.totalLikes(post.getPostId())+")",
				"| Share: ("+sharePersistenceJDBI.totalShare(post.getPostId())+")");
		System.out.println("_______________________________________________|");
		System.out.format("%-21s%-25s\n","DateOfPost", post.getDateOfPost().toString().substring(0, 11));
		System.out.println("_______________________________________________|");
	}
}
