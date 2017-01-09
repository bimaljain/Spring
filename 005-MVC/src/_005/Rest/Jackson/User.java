package _005.Rest.Jackson;

public class User {
	private int id;
	private String userid;
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

