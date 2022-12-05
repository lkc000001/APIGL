package com.ApiGL.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ApiGL.entity.Hpg5000ApiLog;
import com.ApiGL.entity.IcpApiLog;
import com.ApiGL.entity.MtpApiLog;
import com.ApiGL.entity.request.ConditionsRequest;

public interface Hpg5000ApiLogService {
	
	public ResponseEntity<?> queryHpg5000ApiLog(ConditionsRequest conditionsRequest) throws ParseException;

	public Hpg5000ApiLog findByhpgLogId(Long hpgLogId);
}
