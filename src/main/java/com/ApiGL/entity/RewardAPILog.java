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

@Table("RewardAPILog")
public @Data class RewardAPILog extends JSGridFilter implements Serializable {
	
	@Id
	@Column("RewardId")
	private Long rewardId;
	
	@Column("RewardType")
	private Integer rewardType;
	
	@Column("RewardIdnTpan")
	private String rewardIdnTpan;
	
	@Column("RewardSir")
	private String rewardSir;
	
	@Column("RewardSeqNo")
	private String rewardSeqNo;
	
	@Column("RewardTxnTime")
	private String rewardTxnTime;
	
	@Column("RewardData")
	private String rewardData;
	
	@Column("RewardSendStatus")
	private String rewardSendStatus;
	
	@Column("RewardStatusCode")
	private String rewardStatusCode;
	
	@Column("RewardReturnCode")
	private String rewardReturnCode;
	
	@Column("RewardVerifyCode")
	private String rewardVerifyCode;
	
	@Column("RewardURL")
	private String rewardUrl;
	
	@Column("CREATETIME")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
	private Date createtime;
}
