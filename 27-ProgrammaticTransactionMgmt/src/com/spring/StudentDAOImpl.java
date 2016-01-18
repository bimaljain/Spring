package com.spring;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class StudentDAOImpl implements StudentDAO{
	private JdbcTemplate jdbcTemplateObject;
	private PlatformTransactionManager transactionManager;

	public void setDataSource(DataSource dataSource) { this.jdbcTemplateObject = new JdbcTemplate(dataSource); }
	public void setTransactionManager(PlatformTransactionManager transactionManager) { this.transactionManager = transactionManager; }

	public void create(String name, Integer age, Integer marks, Integer year){
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);

		try {
			String SQL0 = "select max(id) from Student1";
			int id = jdbcTemplateObject.queryForInt( SQL0 );
			String SQL1 = "insert into Student1 (id, name, age) values (?, ?, ?)";
			jdbcTemplateObject.update( SQL1, ++id, name, age);

			// Get the latest student id to be used in Marks table
			String SQL2 = "select max(id) from Student1";
			int sid = jdbcTemplateObject.queryForInt( SQL2 );
			String SQL3 = "insert into Marks1(sid, marks, year) values (?, ?, ?)";
			jdbcTemplateObject.update( SQL3, sid, marks, year);

			System.out.println("Created Name = " + name + ", Age = " + age);
			transactionManager.commit(status);
		} catch (DataAccessException e) {
			System.out.println("Error in creating record, rolling back");
			transactionManager.rollback(status);
			throw e;
		}
		return;
	}
	public List<StudentMarks> listStudents() {
		String SQL = "select * from Student1, Marks1 where Student1.id=Marks1.sid";
		List <StudentMarks> studentMarks = jdbcTemplateObject.query(SQL, new StudentMarksMapper());
		return studentMarks;
	}
}