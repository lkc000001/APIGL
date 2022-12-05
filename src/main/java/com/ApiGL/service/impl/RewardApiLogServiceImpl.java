package com.ApiGL.service.impl;

import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ApiGL.entity.RewardAPILog;
import com.ApiGL.entity.request.ConditionsRequest;
import com.ApiGL.entity.response.JSGridResponse;
import com.ApiGL.entity.response.JSGridReturnData;
import com.ApiGL.exception.QueryNoDataException;
import com.ApiGL.repositories.RewardApiLogRepository;
import com.ApiGL.service.RewardApiLogService;
import com.ApiGL.service.ServiceUtil;
import com.ApiGL.util.DateTimtUtil;
import com.baomidou.dynamic.datasource.annotation.DS;

@DS("DBTREWARD")
@Service
public class RewardApiLogServiceImpl implements RewardApiLogService {

	@Autowired
	RewardApiLogRepository rewardAPILogRepository;
	
	@Autowired
	DateTimtUtil dateTimtUtil;
	
	@Autowired
	ServiceUtil serviceUtil;
	
	/**
	 * RewardAPILog Table依查詢條件查詢,依分頁及排序Response
	 * @param ConditionsRequest
	 * @return ResponseEntity
	 * @throws ParseException 
	 */
    @Override
	public ResponseEntity<?> queryRewardAPILog(ConditionsRequest conditionsRequest) throws ParseException
	{
		serviceUtil.requestDateCheck(conditionsRequest);
		
		List<RewardAPILog> rewardAPILogs = rewardAPILogRepository.queryRewardAPILog(conditionsRequest.getQueryUserId(), conditionsRequest.getStartDate(), 
										conditionsRequest.getEndDate(), conditionsRequest.getQueryData(), conditionsRequest.getQueryType(), 
										conditionsRequest.getQueryUrl(), conditionsRequest.getPageIndex(), conditionsRequest.getPageSize());
		if(rewardAPILogs.size() > 0 ) {
			//排序
			List<RewardAPILog> result = rewardAPILogs.stream()
				.peek(lpm -> {
						lpm.setShowDate(dateTimtUtil.formatDateToStr(lpm.getCreatetime(), "yyyy-MM-dd"));
						lpm.setShowTime(dateTimtUtil.formatDateToStr(lpm.getCreatetime(), "HH:mm:ss"));
					})
				.sorted(rewardAPILogSort(conditionsRequest.getSortField(), conditionsRequest.getSortOrder()))
				.collect(Collectors.toList());
			
			return new ResponseEntity<JSGridReturnData<RewardAPILog>>(JSGridResponse.getResponseData(result, rewardAPILogRepository.count()), HttpStatus.OK);
		} else {
			throw new QueryNoDataException("查無資料!!!", 404);
		}
	}

	/**
	 * 依rewardId查詢
	 * @param rewardId
	 * @return RewardAPILog
	 */
    @Override
	public RewardAPILog findByrewardId(Long rewardId) {
		return rewardAPILogRepository.findById(rewardId).get();
	}
	
	/**
	 * 排序欄位設置
	 * @param sortField
	 * @param sortOrder
	 * @return Comparator<RewardAPILog>
	 */
	private Comparator<RewardAPILog> rewardAPILogSort(String sortField, String sortOrder) {
		switch(sortField) {
		case "RewardIdnTpan":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(RewardAPILog::getRewardIdnTpan) : 
						Comparator.comparing(RewardAPILog::getRewardIdnTpan).reversed();
		case "RewardSeqNo":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(RewardAPILog::getRewardSeqNo) : 
						Comparator.comparing(RewardAPILog::getRewardSeqNo).reversed();
		case "RewardTxnTime":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(RewardAPILog::getRewardTxnTime) : 
						Comparator.comparing(RewardAPILog::getRewardTxnTime).reversed();
		}
		
		return sortOrder.equals("asc") ? 
				Comparator.comparing(RewardAPILog::getRewardId) : 
					Comparator.comparing(RewardAPILog::getRewardId).reversed();
	}
}
