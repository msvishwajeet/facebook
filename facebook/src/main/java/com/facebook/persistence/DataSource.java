package com.facebook.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import org.springframework.stereotype.Service;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
@Service
public class DataSource {
	private final HikariDataSource hikariDatasource;
	
	public DataSource() throws ClassNotFoundException, SQLException {
		HikariConfig jdbcConfig = new HikariConfig();
		jdbcConfig.setPoolName("hikaripool");
		jdbcConfig.setMaximumPoolSize(5);
		jdbcConfig.setJdbcUrl("jdbc:mysql://localhost:3306/facebook?useSSL=false");
		jdbcConfig.setUsername("root");
		jdbcConfig.setPassword("dinga");
		
		hikariDatasource = new HikariDataSource(jdbcConfig);
	}
	
	public Connection getConnection() throws SQLException {
		return hikariDatasource.getConnection();
	}
	public HikariDataSource getHikariDataSource() {
		return hikariDatasource;
	}
}
