package com.ApiGL.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.ApiGL.entity.request.JSGridFilter;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Table("APIGLFUNCTION")
public @Data class ApiGLFunction extends JSGridFilter implements Serializable {

	@Id
	@Column("apiglfunctionid")
	private Long apiglFunctionId;
	
	@Column("apiglfunctionname")
	private String apiglFunctionName;
	
	@Column("apiglfunctionshowname")
	private String apiglFunctionShowName;
	
	@Column("apiglfunctionurl")
	private String apiglFunctionUrl;
	
	@Column("apiglfunctionsort")
	private Integer apiglFunctionSort;
	
	@Column("enabled")
	private String enabled;
	
	@Column("type")
	private String type;
	
	@Column("updatetime")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
	private Date updateTime;
	
	@Column("updateuser")
	private String updateUser;
	
	@Column("createtime")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
	private Date createTime;
	
	@Column("createuser")
	private String createUser;
}
