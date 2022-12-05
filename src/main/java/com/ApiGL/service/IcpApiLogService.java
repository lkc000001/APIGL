package com.ApiGL.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ApiGL.entity.IcpApiLog;
import com.ApiGL.entity.request.ConditionsRequest;

public interface IcpApiLogService {
	
	public ResponseEntity<?> queryIcpApiLog(ConditionsRequest conditionsRequest) throws ParseException;

	public IcpApiLog findByicpId(Long icpId);
	
}
