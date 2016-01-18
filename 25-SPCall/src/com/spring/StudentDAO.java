package com.spring;

import javax.sql.DataSource;

public interface StudentDAO {
   public void setDataSource(DataSource ds);
   public Student getStudent(Integer id);
}