package helper;

public class _010_CustomException extends RuntimeException{
	private String exceptionMsg;
	public _010_CustomException(String exceptionMsg) { 
		this.exceptionMsg = exceptionMsg; 
	}
	public String getExceptionMsg(){ 
		return this.exceptionMsg; 
	}
}
