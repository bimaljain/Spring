package _001.Jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "user")
public class User {
	@XmlElement(name = "id")
	private int id;
	@XmlElement(name = "userid")
	private String userid;
	@XmlElement(name = "password")
	private String password;
	
	public User() {
	}
	public User(int id, String userid, String password) {
		super();
		this.id = id;
		this.userid = userid;
		this.password = password;
	}
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
}

