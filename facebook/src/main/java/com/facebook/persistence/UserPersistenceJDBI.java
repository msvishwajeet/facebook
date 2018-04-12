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

import com.facebook.model.User;
import com.zaxxer.hikari.HikariDataSource;
@Service
public class UserPersistenceJDBI {
	UserDto userDto;
	public UserPersistenceJDBI(DataSource dataSource) {
		HikariDataSource hikariDataSource = dataSource.getHikariDataSource();
		DBI dbi =  new DBI(hikariDataSource);
		userDto = dbi.onDemand(UserDto.class);
	}
	public List<User> getUserByState(String state){
		return userDto.getUsernearByState(state);
	}
	public List<User> getuser(String email, String password) {
		return userDto.getUser(email, password);//.get(0);
	}
	public User getuser(int userId) {
		return userDto.getUser(userId);
	}
	public int insertUser(User user) {
		userDto.insertUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(),user.getMobileNumber(),user.getGender());
		return userDto.countOfUser();
	}
	public int emailExistence(String email) {
		return userDto.emailVerifier(email);
	}
	public List<User> getAllUsers(){
		return userDto.getAllUsers();
	}

	public static class UserMapper implements ResultSetMapper<User>{
		public User map(int ids, ResultSet rs, StatementContext context) throws SQLException {
			User user = new User();
			user.setEmail(rs.getString("email"));
			user.setFirstName(rs.getString("firstName"));
			user.setLastName(rs.getString("lastName"));
			user.setPassword(rs.getString("password"));
			user.setMobileNumber(rs.getString("mobileNumber"));
			user.setTimeStamp(rs.getTimestamp("dateOfJoin"));
			user.setUserId(rs.getInt("userId"));
			user.setGender(rs.getString("gender"));
			return user;
		}
	}

	public static interface UserDto{
		@SqlQuery("select * from users where userId = :userId")
		@Mapper(UserMapper.class)
		User getUser(@Bind("userId") int userId);
		
		@SqlQuery("select * from users where email = :email and password = :password")
		@Mapper(UserMapper.class)
		List<User> getUser(@Bind("email") String email, @Bind("password") String password);

		@SqlUpdate("insert into users (firstName,lastName,email,password,mobileNumber,gender) values"
				+ "(:firstName,:lastName,:email,:password,:mobileNumber,:gender)")
		void insertUser(@Bind("firstName") String firstName, @Bind("lastName")  String lastName, 
				@Bind("email")String email,@Bind("password") String password,@Bind("mobileNumber") 
		String mobileNumber,@Bind("gender") String gender);



		@SqlQuery("select count(email) from users where email= :email")
		int emailVerifier(@Bind("email") String email);

		@SqlQuery("select * from users where userId in (select userId from address where stateName = :stateName) ")
		@Mapper(UserMapper.class)
		List<User> getUsernearByState(@Bind ("stateName") String stateName);

		@SqlQuery("select secondUserId from friend where firstUserId = :uid")
		List<Integer> getfriendsId(@Bind ("uid") int uid);

		@SqlQuery("select count(*) from users")
		int countOfUser();
		
		@SqlQuery("select * from users")
		@Mapper(UserMapper.class)
		List<User> getAllUsers();

	}


}
