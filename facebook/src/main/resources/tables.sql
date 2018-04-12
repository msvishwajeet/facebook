/******Database Design***********/

CREATE DATABASE `facebook` 

  CREATE TABLE `users` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(40) NOT NULL,
  `email` varchar(60) NOT NULL,
  `password` varchar(20) NOT NULL,
  `mobileNumber` varchar(13) NOT NULL,
  `dateOfJoin` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `gender` varchar(8) NOT NULL,
  PRIMARY KEY (`userId`)
)
 
 CREATE TABLE `address` (
  `addressId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `pin` int(11) NOT NULL,
  `cityName` varchar(40) NOT NULL,
  `stateName` varchar(30) NOT NULL,
  PRIMARY KEY (`addressId`)
)
 
 CREATE TABLE `share` (
  `shareId` int(11) NOT NULL AUTO_INCREMENT,
  `dateOfShare` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userId` int(11) NOT NULL,
  `postId` int(11) NOT NULL,
  PRIMARY KEY (`shareId`)
) 
 
CREATE TABLE `post` (
  `postId` int(11) NOT NULL AUTO_INCREMENT,
  `dateOfPOST` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userId` int(11) NOT NULL,
  `content` varchar(255) NOT NULL,
  PRIMARY KEY (`postId`)
)
  CREATE TABLE `likes` (
  `likeId` int(11) NOT NULL AUTO_INCREMENT,
  `dateOfLike` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userId` int(11) NOT NULL,
  `postId` int(11) NOT NULL,
  PRIMARY KEY (`likeId`)
)
 
CREATE TABLE `friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateOfFriendship` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `firstUserId` int(11) NOT NULL,
  `secondUserId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
)

CREATE TABLE `friend_request` (
  `req_id` int(11) NOT NULL AUTO_INCREMENT,
  `date_of_request` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userId` int(11) NOT NULL,
  `secondUserId` int(11) NOT NULL,
  PRIMARY KEY (`req_id`)
)

CREATE TABLE `follow` (
  `followId` int(11) NOT NULL AUTO_INCREMENT,
  `dateOfFollow` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userId` int(11) NOT NULL,
  `friendsId` int(11) NOT NULL,
  PRIMARY KEY (`followId`)
)