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

@Table("SmsOtpLog")
public @Data class SmsOtpLog extends JSGridFilter implements Serializable {
	
	@Id
	@Column("SOLID")
	private Long solId;
	
	@Column("PHONE")
	private String phone;
	
	@Column("SYSFLAG")
	private String sysFlag;
	
	@Column("MSG")
	private String msg;
	
	@Column("MEMO")
	private String memo;
	
	@Column("STATUSCODE")
	private String statusSCode;
	
	@Column("MSGID")
	private String msgId;
	
	@Column("USERID")
	private String userId;

	@Column("CREATETIME")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
	private Date createtime;
}
