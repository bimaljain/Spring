package com.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDAO {
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void createUser(User user){
		int count = jdbcTemplate.update("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(?,?)", new Object[]{user.getUserid(), user.getPassword()});
		System.out.println("# of user created: " + count);
	}

	public List<User> getUsers(){
		List<User> user = jdbcTemplate.query("SELECT * FROM USER_DETAILS ORDER BY ID DESC", new rowMapper());
		return user;
	}	

	public User getUserById(int id){
		User user = jdbcTemplate.queryForObject("SELECT * FROM USER_DETAILS WHERE ID=?" , new Object[]{id}, new rowMapper());
		return user;
	}
	
	public void updateUser(User user){
		jdbcTemplate.update("UPDATE USER_DETAILS SET USER_ID=?, PASSWORD=? WHERE ID=?" , 
				new Object[]{user.getUserid(), user.getPassword(), new Integer(user.getId())});
	}
	
	public void deleteUser(int id){
		jdbcTemplate.update("DELETE FROM USER_DETAILS WHERE ID=?" , new Object[]{id});
	}
	
	public List<User> getUserByPage(int limit, int total){
		List<User> user = jdbcTemplate.query("SELECT * FROM USER_DETAILS LIMIT " + limit +" , " + total, new rowMapper());
		return user;
	}
}


class rowMapper implements RowMapper<User>{
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getInt(1));
		user.setUserid(rs.getString(2));
		user.setPassword(rs.getString(3));
		return user;				
	}
}
