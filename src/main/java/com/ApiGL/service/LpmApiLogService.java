package com.ApiGL.service;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;

import com.ApiGL.entity.LpmApiLog;
import com.ApiGL.entity.request.ConditionsRequest;

public interface LpmApiLogService {

	public ResponseEntity<?> queryLpmApiLog(ConditionsRequest conditionsRequest) throws ParseException;

	public LpmApiLog findByLpmid(Long lpmid);
	
}
