package com.ApiGL.entity;

import lombok.Data;

public @Data class PermissionResponse {
	
	private Long apiglroleid;
	
	private Long apiglfunctionid;
	
	private String apiglfunctionname;
	
	private String apiglfunctionshowname;
	
	private String apiglfunctionurl;
	
	private Integer apiglfunctionsort;
	
	private String enabled;
	
	private String type;

	private String permissionenabled;

	public PermissionResponse() {
		
	}
	
	public PermissionResponse(String apiglfunctionname, String apiglfunctionshowname,
			 String apiglfunctionurl, Integer apiglfunctionsort, String enabled,
			String type, String permissionenabled) {

		this.apiglfunctionname = apiglfunctionname;
		this.apiglfunctionshowname = apiglfunctionshowname;
		this.apiglfunctionurl = apiglfunctionurl;
		this.apiglfunctionsort = apiglfunctionsort;
		this.enabled = enabled;
		this.type = type;
		this.permissionenabled = permissionenabled;
	}

	
	
}
