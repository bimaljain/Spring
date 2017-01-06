package helper;

public class _009_User {

	@ValidHobby1
	private String hobby1;
	
	@ValidHobby2(listofHobbies = "football|cricket")
	private String hobby2;
	
	@ValidHobby3() // here we can either provide a list of hobbies else Spring will consider the  default list of hobbies
	private String hobby3;
	
	public String getHobby1() {
		return hobby1;
	}
	public void setHobby1(String hobby1) {
		this.hobby1 = hobby1;
	}
	public String getHobby2() {
		return hobby2;
	}
	public void setHobby2(String hobby2) {
		this.hobby2 = hobby2;
	}
	public String getHobby3() {
		return hobby3;
	}
	public void setHobby3(String hobby3) {
		this.hobby3 = hobby3;
	}
}

