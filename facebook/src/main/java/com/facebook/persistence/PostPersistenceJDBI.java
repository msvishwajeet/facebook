package com.facebook.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.springframework.stereotype.Service;

import com.facebook.model.Post;
import com.zaxxer.hikari.HikariDataSource;
@Service
public class PostPersistenceJDBI {
	
	PostDto postDto;
	public PostPersistenceJDBI(DataSource dataSource) {
		HikariDataSource hikariDataSource = dataSource.getHikariDataSource();
		DBI dbi =  new DBI(hikariDataSource);
		postDto = dbi.onDemand(PostDto.class);
	}
	//TimeLine Method
	public List<Post> getAllPosts(){
		return postDto.getAllPost();
	}
	public void insertPost(String content, int userId) {
		postDto.insertPost(content, userId);
	}
	public List<Post> getLastPost(int userId){
		return postDto.getPost(userId);
	}
	public Map<Integer,Post> seeLastTenPost(){
		Map<Integer,Post> map = new HashMap<Integer, Post>();
		List<Post>  list =postDto.getLastTenPost();
		for (Post post : list) {
			map.put(post.getPostId(), post);
		}
		return map;
	}
	public static class PostMapper implements ResultSetMapper<Post>{
		public Post map(int ids, ResultSet rs, StatementContext context) throws SQLException {
			Post post = new Post();
			post.setContent(rs.getString("content"));
			post.setDateOfPost(rs.getTimestamp("dateOfPOST"));
			post.setPostId(rs.getInt("postId"));
			post.setUserId(rs.getInt("userId"));
			return post;
		}
	}
	
	public static interface PostDto{
		@SqlQuery("select * from post where userId = :userId")
		@Mapper(PostMapper.class)
		List<Post> getPost(@Bind("userId") int userId);
		
		@SqlUpdate("insert into post (content,userId) values (:content,:userId)")
		void insertPost(@Bind("content") String content, @Bind("userId")int userId);
		
		@SqlQuery("(select * from post order by postId desc limit 10) order by postid;")
		@Mapper(PostMapper.class)
		List<Post> getLastTenPost();
		
		//For TimeLine
		@SqlQuery("select * from post order by postId desc")
		@Mapper(PostMapper.class)
		List<Post> getAllPost();
	}
	
}
