package com.ApiGL.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.ApiGL.entity.request.JSGridFilter;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Table("ROLEFUNCTION")	
public @Data class RoleFunction implements Serializable {

	@Id
	@Column("rolefunctionid")
	private Long roleFunctionId;
	
	@Column("apiglroleid")
	private Long apiglRoleId;
	
	@Column("apiglFunctionid")
	private Long apiglFunctionId;
	
	@Column("enabled")
	private String enabled;
	
	@Column("createtime")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
	private Date createTime;
	
	@Column("createuser")
	private String createUser;
}
