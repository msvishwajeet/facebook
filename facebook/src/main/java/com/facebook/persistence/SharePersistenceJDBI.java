package com.facebook.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.unstable.BindIn;
import org.springframework.stereotype.Service;

import com.facebook.model.Share;
import com.zaxxer.hikari.HikariDataSource;

@Service
public class SharePersistenceJDBI {
	ShareDto shareDto;
	public SharePersistenceJDBI(DataSource dataSource) {
		HikariDataSource hikariDataSource = dataSource.getHikariDataSource();
		DBI dbi = new DBI(hikariDataSource);
		shareDto = dbi.onDemand(ShareDto.class);
	}
	
//	public void sharePost(int postId,int userId ) {
//		shareDto.shareStaus(userId, postId);
//	}
	public int totalShare(int postId) {
		return shareDto.shareCount(postId);
	}
	
	public boolean shareAPost(int userId,int postId) {
		if (shareDto.checkShare(userId, postId)!=0) {
			return false;
		}
		shareDto.share(userId, postId);
		return true;
	}
	public static class ShareMapper implements ResultSetMapper<Share>{

		public Share map(int idx, ResultSet rs, StatementContext context) throws SQLException {
			Share share = new Share();
			share.setDateOfShare(rs.getTimestamp("dateOfShare"));
			share.setPostId(rs.getInt("postId"));
			share.setShareId(rs.getInt("shareID"));
			return share;
		}
		
	}
	
	public static interface ShareDto{
//		@SqlUpdate ("insert into share (userId,postId) vlaues (:userId,:postId)")
//		@Mapper(ShareMapper.class)
//		void shareStaus(@Bind("userId") int userID, @Bind("postId") int postId);
////		
//		@SqlUpdate("insert into share (userId,postId) values (:userId,postId)")
//		void share(@Bind("userId") int userId,@Bind("postId") int postId);
//		
		@SqlUpdate("insert into share (userId,postId) values (:userId,:postId)")
		void share(@Bind("userId") int userId, @Bind("postId") int postId);
		
		@SqlQuery("select count(postId) from share where postId=:postId")
		int shareCount(@Bind("postId") int postId);
		
		@SqlQuery("select count(*) from share where userId = :userId and postId = :postId")
		int checkShare(@Bind("userId") int userId, @Bind("postId")int postId);
	}

	
}
