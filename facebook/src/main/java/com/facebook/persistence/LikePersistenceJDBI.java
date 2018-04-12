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

import com.facebook.model.Follow;
import com.facebook.model.Like;
import com.zaxxer.hikari.HikariDataSource;
@Service
public class LikePersistenceJDBI {
LikeDto likeDto;
	
	@Autowired
	public LikePersistenceJDBI(DataSource dataSource) {
		HikariDataSource hikariDataSource = dataSource.getHikariDataSource();
		DBI dbi = new DBI(hikariDataSource);
		likeDto = dbi.onDemand(LikeDto.class);
	}
	
	public int totalLikes(int postId) {
		return likeDto.numberOfLikes(postId);
	}
	public boolean likeAPost(int userId,int postId) {
		if (likeDto.checkLike(userId, postId)!=0) {
			return false;
		}
		likeDto.like(userId, postId);
		return true;
	}
	public static class LikeMapper implements ResultSetMapper<Like>{

		public Like map(int index, ResultSet rs, StatementContext context) throws SQLException {
			Like like = new Like();
			like.setDate(rs.getTimestamp("dateOfLike"));
			like.setId(rs.getInt("likeId"));
			like.setPostId(rs.getInt("postId"));
			like.setUserId(rs.getInt("userId"));
			return like;
		}
		
	}
	
	
	public static interface LikeDto{
		@SqlUpdate("insert into likes (userId,postId) values (:userId,:postId)")
		void like(@Bind("userId") int userId, @Bind("postId") int postId);

	@SqlQuery("Select count(postId) from likes where postId = :postId")
	int numberOfLikes(@Bind("postId") int postId);
	
	@SqlQuery("select * from likes where postId = :postId")
	@Mapper(LikeMapper.class)
	List<Follow> totalLikesObj(@Bind("postId") int postId);
	
	@SqlQuery("select count(likeId) from likes where userId = :userId and postId = :postId")
	int checkLike(@Bind("userId") int userId, @Bind("postId")int postId);
	
	}
	
	
	
}
