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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.facebook.model.FriendRequest;
import com.zaxxer.hikari.HikariDataSource;

@Service
public class FriendRequestPersistenceJdbi {
	
	FriendDto friendDto;
	@Autowired
	public FriendRequestPersistenceJdbi(DataSource dataSource) {
		HikariDataSource hikariDataSource = dataSource.getHikariDataSource();
		DBI dbi =  new DBI(hikariDataSource);
		friendDto = dbi.onDemand(FriendDto.class);
	}
	public void acceptFriendreq(int userId, int secondUserId) {
		friendDto.acceptReq(userId, secondUserId);
		friendDto.deleteAccepted(secondUserId,userId);
		System.out.println("Successfully Added");
	}
	public List<FriendRequest> getPendingReq(int userId){
		return friendDto.getAllfriendReq(userId);
	}
	
	public void sendReq(int userId,int secondUserId) {
		if(friendDto.checkforRequest(userId, secondUserId)!=0) {
			System.out.println("Already sent Before");
			return;
		}
		friendDto.sendReq(userId, secondUserId);
		System.out.println("Request Sent");
	}
	public void deleteReq(int userId, int secondUserId) {
		friendDto.deleteAccepted(secondUserId,userId);
		System.out.println("Request Deleted");
	}
	
	public int getReqCount(int userId) {
		return friendDto.countOfPendingReq(userId);
	}
	
	public static class FriendMapper implements ResultSetMapper<FriendRequest>{
		public FriendRequest map(int ids, ResultSet rs, StatementContext context) throws SQLException {
			FriendRequest friendRequest = new FriendRequest();
			friendRequest.setDateOfReq(rs.getTimestamp("date_of_request"));
			friendRequest.setSecondUserId(rs.getInt("secondUserId"));
			friendRequest.setUserId(rs.getInt("userId"));
			return friendRequest;
		}
	}
	public interface FriendDto{
			@SqlUpdate("insert into friend_request (userId,secondUserId) values (:userId,:secondUserId)")
			void sendReq(@Bind("userId") int userId, @Bind("secondUserId") int secondUserId);
			
			@SqlUpdate("delete from friend_request where userId = :userId and secondUserId = :secondUserId")
			void deleteAccepted(@Bind("userId") int userId,@Bind("secondUserId") int  secondUserId);
			
			@SqlQuery("select * from friend_request where secondUserId = :userId ")//order by req_id desc
			@Mapper(FriendMapper.class)
			List<FriendRequest> getAllfriendReq(@Bind("userId") int userId);
			
			@SqlQuery("select count(*) from friend_request where secondUserId = :userId")
			int countOfPendingReq(@Bind("userId") int userId);
			
			@SqlUpdate("insert into friend (firstUserId,secondUserId) values (:firstUserId, :secondUserId)")
			void acceptReq(@Bind("firstUserId") int firstUserId, @Bind("secondUserId") int secondUserId);
			
			@SqlQuery("select count(*) from friend_request where userId = :userId and secondUserId = :secondUserId")
			int checkforRequest(@Bind("userId") int firstUserId,@Bind("secondUserId") int secondUserId);
	}
	

}
