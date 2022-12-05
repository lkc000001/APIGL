package com.ApiGL.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ApiGL.entity.IcpApiLog;
import com.ApiGL.entity.MtpApiLog;
import com.ApiGL.entity.request.ConditionsRequest;

public interface MtpApiLogService {
	
	public ResponseEntity<?> queryMtpApiLog(ConditionsRequest conditionsRequest) throws ParseException;

	public MtpApiLog findBymtpLId(Long mtpLId);
}
