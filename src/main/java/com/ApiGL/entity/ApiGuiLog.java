package com.ApiGL.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.ApiGL.entity.request.JSGridFilter;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Table("APIGUILog")
public @Data class ApiGuiLog extends JSGridFilter implements Serializable {
	@Id
	@Column("ApiGuiId")
	private Long apiGuiId;
	
	@Column("ApiGuiUserId")
	private String apiGuiUserId;
	
	@Column("ApiGuiData")
	private String apiGuiData;
	
	@Column("ApiGuiSendStatus")
	private String apiGuiSendStatus;
	
	@Column("ApiGuinReturnCode")
	private String apiGuinReturnCode;
	
	@Column("ApiGuiUrl")
	private String apiGuiUrl;
	
	@Column("ApiGuiLogType")
	private String apiGuiLogType;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
	@Column("CREATETIME")
	private Date createtime;
	
	public ApiGuiLog() {
	}

	public ApiGuiLog(String apiGuiUserId, String apiGuiData, String apiGuiSendStatus, String apiGuinReturnCode,
			String apiGuiUrl, String apiGuiLogType, Date createtime) {
		super();
		this.apiGuiUserId = apiGuiUserId;
		this.apiGuiData = apiGuiData;
		this.apiGuiSendStatus = apiGuiSendStatus;
		this.apiGuinReturnCode = apiGuinReturnCode;
		this.apiGuiUrl = apiGuiUrl;
		this.apiGuiLogType = apiGuiLogType;
		this.createtime = createtime;
	}
}
