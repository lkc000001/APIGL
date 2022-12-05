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

@Table("ICPAPILog")
public @Data class IcpApiLog extends JSGridFilter implements Serializable {
	
	@Id
	@Column("ICPId")
	private Long icpId;
	
	@Column("ICPType")
	private Integer icpType;
	
	@Column("ICPCustId")
	private String icpCustId;
	
	@Column("ICPData")
	private String icpData;
	
	@Column("ICPURL")
	private String icpUrl;
	
	@Column("CREATETIME")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
	private Date createtime;
}
