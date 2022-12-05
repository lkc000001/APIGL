package com.ApiGL.exception;

public class AesException extends RuntimeException {
	
	private Integer code;
	
	public AesException(String message, Integer code) {
		super(message);
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
}
