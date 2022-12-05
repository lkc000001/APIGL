package com.ApiGL.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ApiGL.entity.RewardAPILog;
import com.ApiGL.entity.TwPayLineBindLog;
import com.ApiGL.entity.request.ConditionsRequest;

public interface RewardApiLogService {
	
	public ResponseEntity<?> queryRewardAPILog(ConditionsRequest conditionsRequest) throws ParseException;

	public RewardAPILog findByrewardId(Long rewardId);
}
