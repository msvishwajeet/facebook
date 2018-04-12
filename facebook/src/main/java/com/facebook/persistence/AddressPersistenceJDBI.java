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

import com.facebook.model.Address;
import com.zaxxer.hikari.HikariDataSource;
@Service
public class AddressPersistenceJDBI {
	AddressDTO addressDTO;
	@Autowired
	public AddressPersistenceJDBI(DataSource dataSource) {
		HikariDataSource hikariDataSource = dataSource.getHikariDataSource();
		DBI dbi =  new DBI(hikariDataSource);
		addressDTO = dbi.onDemand(AddressDTO.class);
	}

	public Address getAddress(int userId) {
		return addressDTO.getAddress(userId);//.get(0);
	}
	public List<Address> getAllAddress(){
		return addressDTO.getAll();
	}
	public void insertAddress(Address address) {
		addressDTO.insertAddress(address.getCityName(), address.getPin(), address.getStateName(), address.getUserId());
	}
	
	public int getCount() {
		return (int)addressDTO.getCount();
	}
	
	public int getCount(int userId) {
		return addressDTO.getCount(userId);
	}
	public void updateAddress(int userId,int pin,String cityName,String stateName) {
		addressDTO.updateAddress(pin, cityName, stateName, userId);
	}
	public static class AddressMapper implements ResultSetMapper<Address>{
		public Address map(int ids, ResultSet rs, StatementContext context) throws SQLException {
			Address address = new Address();
			address.setAddressId(rs.getInt("addressId"));
			address.setCityName(rs.getString("cityName"));
			address.setPin(rs.getInt("pin"));
			address.setStateName(rs.getString("stateName"));
			address.setUserId(rs.getInt("userId"));
			return address;
		}
	}
	public static interface AddressDTO{
		@SqlQuery("select * from address where userId = :userId")
		@Mapper(AddressMapper.class)
		Address getAddress(@Bind("userId") int userId);

		@SqlUpdate("insert into address (cityName,pin,stateName,userId) values(:cityName,:pin,"
				+ ":stateName,:userId)")
		void insertAddress(@Bind("cityName") String cityName, @Bind("pin") int pin, 
				@Bind("stateName")String stateName,@Bind("userId") int userId);

		@SqlQuery("select count(addressId) from address")
		int getCount();
		@SqlQuery("select * from address")
		@Mapper(AddressMapper.class)
		List<Address> getAll();
		
		@SqlUpdate("update address set pin = :pin,cityName = :cityName ,stateName = :stateName where userId = :userId")
		void updateAddress(@Bind("pin") int pin,@Bind("cityName") String cityName,@Bind("stateName") String stateName,
				@Bind("userId") int userId);
		
		
		@SqlQuery("select count(addressId) from address where userId= :userId")
		int getCount(@Bind("userId") int userId);
	}

}
