package com.facebook.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.springframework.stereotype.Service;

import com.facebook.model.Friend;
import com.facebook.model.User;
import com.facebook.persistence.UserPersistenceJDBI.UserMapper;
import com.zaxxer.hikari.HikariDataSource;
@Service
public class FriendPersistenceJDBI {
	FriendDto friendDto;
	public FriendPersistenceJDBI(DataSource dataSource) {
		HikariDataSource hikariDataSource = dataSource.getHikariDataSource();
		DBI dbi =  new DBI(hikariDataSource);
		friendDto = dbi.onDemand(FriendDto.class);
	}
	public void addFriend(int firstUserId, int secondUserId) {
		friendDto.addFriend(firstUserId, secondUserId);
	}
	public List<User> getUserByState(String state){
		return friendDto.getUsernearByState(state);
	}
	public List<User> getAllUser(int userId){
		return friendDto.getAllUser(userId);
	}
	public List<User> getNewUser(int userId){
		return friendDto.getNewUser(userId,userId,userId);
	}
	public int getFriendCount(int userId) {
		return friendDto.getCountOfFriend(userId,userId);
	}



	public static class FriendMapper implements ResultSetMapper<Friend>{
		public Friend map(int ids, ResultSet rs, StatementContext context) throws SQLException {
			Friend friend = new Friend();
			friend.setDate(rs.getTimestamp("dateOfFriendship"));
			friend.setSecondUserId(rs.getInt("secondUserId"));
			friend.setUserId(rs.getInt("firstUserId"));
			return friend;
		}
	}

	public static interface FriendDto{
		//@SqlQuery("select count(firstUserId) from friend where firstUserId = :firstUserId")

		//Proper-way**************************
		@SqlQuery("select count(*) from users where UserId in (select firstUserId from friend where secondUserId = "
				+ ":userId) or userId in ( select secondUserId from friend where firstUserId = :userId1)")
		int getCountOfFriend(@Bind("userId") int userId,@Bind("userId1") int userId1);

		//New way**************************
		@SqlQuery("select * from users where UserId not in (select firstUserId from friend where secondUserId=:userId) and"
				+ " userId not in ( select secondUserId from friend where firstUserId=:userId1) and userId !=:userId2;")
		@Mapper(UserMapper.class)
		List<User> getNewUser(@Bind("userId")int userId,@Bind("userId1")int userId1,@Bind("userId2")int userId2);

		@SqlQuery("select secondUserId from friend where firstUserId = :uid")
		List<Integer> getfriendsId(@Bind ("uid") int uid);

		@SqlQuery("select * from users where userId in (select userId from address where stateName = :stateName)")
		@Mapper(UserMapper.class)
		List<User> getUsernearByState(@Bind ("stateName") String stateName);

		@SqlQuery("select * from users where userId <> :userId")
		@Mapper(UserMapper.class)
		List<User> getAllUser(@Bind("userId") int userID);

		@SqlUpdate("insert into friend (firstUserId,secondUserId) "
				+ "values (:firstUserId, :secondUserId)")
		void addFriend(@Bind("firstUserId") int firstUserId, @Bind("secondUserId") int secondUserId);

	}

}
