package _002_helper;

public class User {
	private int id;
	private String userId;
	private String password;
	
	public User(int id, String name, String password) {
		this.id = id;
		this.userId = name;
		this.password = password;
	}
	public User(String name, String password) {
		this.userId = name;
		this.password = password;
	}	
	public User() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String toString(){
		return id + ": " + userId + ": " + password;
	}
}
