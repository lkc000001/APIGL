package com.ApiGL.entity.response;

import lombok.Data;

public @Data class ErrorMsg {
	private int code;
	private String message;
	private int errorcount;
	
	public ErrorMsg() {
	}
	
	
	public ErrorMsg(int code, String message) {
		this.code = code;
	    this.message = message;
	}
	
	public ErrorMsg(int code, String message, int errorcount) {
		this.code = code;
	    this.message = message;
	    this.errorcount = errorcount;
	}
	
}
