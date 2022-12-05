package com.ApiGL.service;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;

import com.ApiGL.entity.ApiGuiLog;
import com.ApiGL.entity.UserTrackLog;
import com.ApiGL.entity.request.ConditionsRequest;

public interface UserTrackLogService {
	
	public ResponseEntity<?> queryUserTrackLog(ConditionsRequest conditionsRequest) throws ParseException;

	public UserTrackLog findByUserTrackId(Long userTrackId);

	public void save(UserTrackLog userTrackLog);
}
