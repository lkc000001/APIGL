package com.ApiGL.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ApiGL.entity.ApiGuiLog;
import com.ApiGL.entity.LpmApiLog;
import com.ApiGL.entity.request.ConditionsRequest;

public interface ApiGuiLogService {

	public ResponseEntity<?> queryApiGuiLog(ConditionsRequest conditionsRequest) throws ParseException;

	public ApiGuiLog findByApiGuiId(Long lpmid);
	
	public void save(ApiGuiLog apiGuiLog);
}
