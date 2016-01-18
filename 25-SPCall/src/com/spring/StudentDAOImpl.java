package com.spring;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

public class StudentDAOImpl implements StudentDAO {
	   private DataSource dataSource;
	   private SimpleJdbcCall jdbcCall;

	   public void setDataSource(DataSource dataSource) {
		  this.dataSource = dataSource;
	      this.jdbcCall =  new SimpleJdbcCall(dataSource).withProcedureName("getStudByID");
	   }
	   public Student getStudent(Integer id) {
	      SqlParameterSource in = new MapSqlParameterSource().addValue("p_id", id);
	      Map<String, Object> out = jdbcCall.execute(in);
	      Student student = new Student();
	      student.setId(id);
	      student.setName((String) out.get("o_name"));
	      student.setAge((Integer) out.get("o_age"));
	      return student;
	   }
}