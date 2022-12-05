package com.ApiGL.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.ApiGL.entity.request.JSGridFilter;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Table("APIGLROLE")	
public @Data class ApiGLRole extends JSGridFilter implements Serializable {

	@Id
	@Column("apiglroleid")
	private Long apiglRoleId;
	
	@Column("apiglrolenumber")
	private String apiglRoleNumber;
	
	@Column("apiglrolename")
	private String apiglRoleName;
	
	@Column("apiglroledirections")
	private String apiglRoleDirections;
	
	@Column("apiglroletype")
	private String apiglRoleType;
	
	@Column("enabled")
	private String enabled;
	
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
