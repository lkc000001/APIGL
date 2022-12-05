package com.ApiGL.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ApiGL.entity.TwPayLineBindLog;
import com.ApiGL.entity.request.ConditionsRequest;

public interface TwPayLineBindLogService {
	
	public ResponseEntity<?> querytwPayLineBindLog(ConditionsRequest conditionsRequest) throws ParseException;

	public TwPayLineBindLog findBytwPayLineBindLogId(Long twPayLineBindLogid);

}
