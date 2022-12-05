package com.ApiGL.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.ApiGL.entity.request.JSGridFilter;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Table("USERFUNCTION")	
public @Data class UserFunction implements Serializable {

	@Id
	@Column("userfunctionid")
	private Long userFunctionId;
	
	@Column("userid")
	private Long userId;
	
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
