package com.ApiGL.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ApiGL.entity.Hpg5000ApiLog;
import com.ApiGL.entity.IcpApiLog;
import com.ApiGL.entity.MtpApiLog;
import com.ApiGL.entity.SmsOtpLog;
import com.ApiGL.entity.request.ConditionsRequest;

public interface SmsOtpLogService {
	
	public ResponseEntity<?> querySmsOtpLog(ConditionsRequest conditionsRequest) throws ParseException;

	public SmsOtpLog findBySolId(Long solId);
}
