package com.spring;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class StudentDAOImpl implements StudentDAO {
    private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
   
   public void setDataSource(DataSource dataSource) {
       this.dataSource = dataSource;
	   jdbcTemplateObject = new JdbcTemplate(dataSource);
   }
   public void create(String name, Integer age) {
	  String SQL0 = "select max(id) from Stud";
      int id = jdbcTemplateObject.queryForInt( SQL0 );
      String SQL = "insert into Stud (id, name, age) values (?, ?, ?)";
      jdbcTemplateObject.update( SQL, ++id, name, age);
      System.out.println("Created Record Name = " + name + " Age = " + age);
      return;
   }
   public Student getStudent(Integer id) {
      String SQL = "select * from Stud where id = ?";
      Student student = jdbcTemplateObject.queryForObject(SQL, new Object[]{id}, new StudentMapper());
      return student;
   }
   public List<Student> listStudents() {
      String SQL = "select * from Stud";
      List <Student> students = jdbcTemplateObject.query(SQL, new StudentMapper());
      return students;
   }
   public void delete(Integer id){
      String SQL = "delete from Stud where id = ?";
      jdbcTemplateObject.update(SQL, id);
      System.out.println("Deleted Record with ID = " + id );
      return;
   }
   public void update(Integer id, Integer age){
      String SQL = "update Stud set age = ? where id = ?";
      jdbcTemplateObject.update(SQL, age, id);
      System.out.println("Updated Record with ID = " + id );
      return;
   }
}