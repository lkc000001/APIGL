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

@Table("LPMAPILog")
public @Data class LpmApiLog extends JSGridFilter implements Serializable {
	
	@Id
	@Column("LPMId")
	private Integer lpmId;
	
	@Column("LPMTxSeq")
	private String lpmTxSeq;
	
	@Column("LPMTxCode")
	private String lpmTxCode;
	
	@Column("LPMUserId")
	private String lpmUserId;
	
	@Column("LPMUserCode")
	private String lpmUserCode;
	
	@Column("LPMData")
	private String lpmData;
	
	@Column("LPMSendStatus")
	private String lpmSendStatus;
	
	@Column("LPMMsgCode")
	private String lpmMsgCode;
	
	@Column("LPMURL")
	private String lpmUrl;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
	@Column("CREATETIME")
	private Date createtime;
	
	@Column("LPMCipher")
	private String lpmCipher;
}
