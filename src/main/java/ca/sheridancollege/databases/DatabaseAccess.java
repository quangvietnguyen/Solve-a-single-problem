package ca.sheridancollege.databases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cs.sheridancollege.beans.Users;

@Repository
public class DatabaseAccess {
	@Autowired
	NamedParameterJdbcTemplate jdbc;
	
	public void addUser(Users user) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query="INSERT INTO Users VALUES " + 
		"(:username, :password, :fullname, :age, :email)";
		parameters.addValue("username", user.getUsername());
		parameters.addValue("password", user.getPassword());
		parameters.addValue("fullname", user.getFullname());
		parameters.addValue("age", user.getAge());
		parameters.addValue("email", user.getEmail());
		jdbc.update(query, parameters);
		
	}
	
	/*public boolean checkUser(Users user) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query="SELECT 1 FROM Users WHERE " + "user_name = :username AND password = :password";
		parameters.addValue("username", user.getUsername());
		parameters.addValue("password", user.getPassword());
		if (jdbc.update(query, parameters) != 0) return true;
		else return false;
	}*/
}
