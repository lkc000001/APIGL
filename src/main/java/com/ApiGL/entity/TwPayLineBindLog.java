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

@Table("TWPayLineBindLog")
public @Data class TwPayLineBindLog extends JSGridFilter implements Serializable {
	
	@Id
	@Column("TWPayLineBindLogId")
	private Long twPayLineBindLogId;
	
	@Column("TWPayLineBindLogLineUid")
	private String twPayLineBindLogLineUid;
	
	@Column("TWPayLineBindLogTxnNo")
	private String twPayLineBindLogTxnNo;
	
	@Column("TWPayLineBindLogTxnDateTime")
	private String twPayLineBindLogTxnDateTime;
	
	@Column("TWPayLineBindLogData")
	private String twPayLineBindLogData;
	
	@Column("TWPayLineBindLogSendStatus")
	private String twPayLineBindLogSendStatus;
	
	@Column("TWPayLineBindLogRCode")
	private String twPayLineBindLogRCode;
	
	@Column("TWPayLineBindLogURL")
	private String twPayLineBindLogUrl;
	
	@Column("CREATETIME")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
	private Date createtime;
}
