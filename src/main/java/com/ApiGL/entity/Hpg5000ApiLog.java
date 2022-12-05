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

@Table("HPG5000APILog")
public @Data class Hpg5000ApiLog extends JSGridFilter implements Serializable {
	
	@Id
	@Column("HPGLogId")
	private Long hpgLogId;
	
	@Column("HPGLogType")
	private Integer hpgLogType;
	
	@Column("HPGLogIdnTxn")
	private String hpgLogIdnTxn;
	
	@Column("HPGLogSeqNo")
	private String hpgLogSeqNo;
	
	@Column("HPGLogTxnTime")
	private String hpgLogTxnTime;
	
	@Column("HPGData")
	private String hpgData;
	
	@Column("HPGSendStatus")
	private String hpgSendStatus;
	
	@Column("HPGReturnStatus")
	private String hpgReturnStatus;
	
	@Column("HPGReturnCode")
	private String hpgReturnCode;
	
	@Column("HPGLogURL")
	private String hpgLogUrl;
	
	@Column("CREATETIME")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
	private Date createtime;
}
