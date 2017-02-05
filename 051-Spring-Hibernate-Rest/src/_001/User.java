package _001;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER_DETAILS")
public class User {
	
	@Id
	@Column(name="ID")
	private int id;
	
	@Column(name="USER_ID")
	private String userid;
	
	@Column(name="PASSWORD")
	private String password;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public User(int id, String userid, String password) {
		super();
		this.id = id;
		this.userid = userid;
		this.password = password;
	}
	public User() {
	}	
	public String toString(){
		return id + " : " + userid + " : " + password;
	}
}

