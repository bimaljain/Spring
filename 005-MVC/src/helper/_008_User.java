package helper;

import java.util.ArrayList;
import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class _008_User {

	@NotBlank // this will display default error message
	private Long phone;
	
	@Size(min=2, max=30, message="userid cannot be less than {min} and more than {max}") // with custom message in pojo class itself
	@Pattern(regexp="[^0-9]*")
	private String userid="bimal";
	
	@Size(max=30, min=2)// custom message from property file
	/*
	 In this case, how Spring MVC search for property key? If follows below hierarchy
	 1. [Validation Annotation Name].[Object Reference Name].[Field Name] , eg, Size.user.userid
	 2. [Validation Annotation Name].[Field Name] , eg, Size.userid
	 3. [Validation Annotation Name].[Field Type] , eg, Size.String 
	 4. [Validation Annotation Name] , eg, Size
	 5. Default error message
	 
	 1. Placeholder {0} is always replaced dynamically by Spring MVC with the name of the field for which violation occurs.
	 2. Placeholders {1},{2}, and so on would be replaced with the arguments values passed to the constraint annotation. Remember that Spring MVC uses
	 	alphabetical order of the arguments to decide upon which placeholder to be replaced with which argument value. so {1} was replaced with the 
	 	max value and {2} was replaced with the min value.
	 */
	private String password;
	
	@Past
	private Date dob; // dob cannot be in future
	private ArrayList<String> skills;
	private Address userAddress;

	public Address getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(Address userAddress) {
		this.userAddress = userAddress;
	}
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public ArrayList<String> getSkills() {
		return skills;
	}
	public void setSkills(ArrayList<String> skills) {
		this.skills = skills;
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

