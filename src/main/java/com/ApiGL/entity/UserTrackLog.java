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

@Table("USERTRACKLog")
public @Data class UserTrackLog extends JSGridFilter implements Serializable {
	@Id
	@Column("UserTrackId")
	private Long userTrackId;
	
	@Column("UserTrackAccountID")
	private Integer userTrackAccountID;
	
	@Column("UserTrackAccount")
	private String userTrackAccount;
	
	@Column("UserTrackName")
	private String userTrackName;
	
	@Column("UserTrackData")
	private String userTrackData;
	
	@Column("UserTrackUrl")
	private String userTrackUrl;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
	@Column("CREATETIME")
	private Date createtime;
	
	public UserTrackLog() {
	}

	public UserTrackLog(Integer userTrackAccountID, String userTrackAccount, String userTrackName, String userTrackData,
			String userTrackUrl, Date createtime) {
		super();
		this.userTrackAccountID = userTrackAccountID;
		this.userTrackAccount = userTrackAccount;
		this.userTrackName = userTrackName;
		this.userTrackData = userTrackData;
		this.userTrackUrl = userTrackUrl;
		this.createtime = createtime;
	}

}
