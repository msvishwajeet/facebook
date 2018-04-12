package com.facebook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facebook.model.Post;
import com.facebook.model.User;
import com.facebook.persistence.LikePersistenceJDBI;
import com.facebook.persistence.PostPersistenceJDBI;
import com.facebook.persistence.SharePersistenceJDBI;

@Service
public class TimelineContoller {
	@Autowired
	ValidationController validationContoller;
	@Autowired
	PostPersistenceJDBI postPersistenceJDBI;
	@Autowired
	UserController userController;
	@Autowired
	LikePersistenceJDBI likePersistenceJDBI;
	@Autowired
	SharePersistenceJDBI sharePersistenceJDBI;

	public void seeTimeLine(User user) {
		List<Post> listOfPost = postPersistenceJDBI.getAllPosts();
		int temp = 0;
		boolean readUserMind = true;
		while(temp < listOfPost.size() && readUserMind) {
			userController.printPost(listOfPost.get(temp));

			int response = activityOption(listOfPost.get(temp), user);
			switch (response) {
			case 1:
				break;
			case 2:
				temp++;
				break;
			case 0:
				return;
			}
			if (temp==listOfPost.size()) {
				System.err.println("No More post On time Line");
			}
		}
	}

	public int activityOption(Post post,User user) {
		System.out.println("press 1. to like the post");
		System.out.println("Press 2. Share the Post");
		System.out.println("Press 3. to see next post");
		System.out.println("press 0. for Home......");
		int input = validationContoller.validInt();
		switch (input) {
		case 1:
			if(!likePersistenceJDBI.likeAPost(user.getUserId(),post.getPostId())) {
				System.out.println("Already liked");	
			}
			else {
				likePersistenceJDBI.likeAPost(user.getUserId(), post.getPostId());
				System.out.println("success");
			}

			return 1;
		case 2:
			if(!sharePersistenceJDBI.shareAPost(user.getUserId(),post.getPostId())) {
				System.out.println("Already Shared");	
			}
			else {
				sharePersistenceJDBI.shareAPost(user.getUserId(), post.getPostId());
				System.out.println("Success");
			}
			return 1;
		case 3:
			return 2;
		default:
			System.out.println("Wrong Input");
			return 1;
		case 0:
			return 0;
		}
	}
}
