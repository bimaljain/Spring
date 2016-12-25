package com.spring;

public class SpringException extends RuntimeException{
	private String exceptionMsg;
	private String exCode;
	public SpringException(String exceptionMsg, String exCode) { 
		this.exceptionMsg = exceptionMsg; 
		this.exCode = exCode;
	}
	public String getExceptionMsg(){ 
		return this.exceptionMsg; 
	}
	public void setExceptionMsg(String exceptionMsg) { 
		this.exceptionMsg = exceptionMsg;
	}
	public String getExCode() {
		return exCode;
	}
	public void setExCode(String exCode) {
		this.exCode = exCode;
	}
	
}