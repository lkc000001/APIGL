package com.ApiGL.service.impl;

import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ApiGL.entity.ApiGuiLog;
import com.ApiGL.entity.request.ConditionsRequest;
import com.ApiGL.entity.response.JSGridResponse;
import com.ApiGL.entity.response.JSGridReturnData;
import com.ApiGL.exception.QueryNoDataException;
import com.ApiGL.repositories.ApiGuiLogRepository;
import com.ApiGL.service.ApiGuiLogService;
import com.ApiGL.service.ServiceUtil;
import com.ApiGL.util.DateTimtUtil;
import com.baomidou.dynamic.datasource.annotation.DS;

@DS("DBAPIGL")
@Service
public class ApiGuiLogServiceImpl implements ApiGuiLogService {

	@Autowired
	ApiGuiLogRepository apiGuiLogRepository;
	
	@Autowired
	DateTimtUtil dateTimtUtil;
	
	@Autowired
	ServiceUtil serviceUtil;
	
	/**
	 * ApiGuiLog Table依查詢條件查詢,依分頁及排序Response
	 * @param ConditionsRequest
	 * @return ResponseEntity
	 * @throws ParseException 
	 */
    @Override
	public ResponseEntity<?> queryApiGuiLog(ConditionsRequest conditionsRequest) throws ParseException
	{
		serviceUtil.requestDateCheck(conditionsRequest);
		
		List<ApiGuiLog> apiGuiLogs = apiGuiLogRepository.queryApiGuiLog(conditionsRequest.getQueryUserId(), conditionsRequest.getStartDate(), 
									 conditionsRequest.getEndDate(), conditionsRequest.getQueryData(), conditionsRequest.getQueryType(), 
									 conditionsRequest.getQueryUrl(), conditionsRequest.getPageIndex(), conditionsRequest.getPageSize());
		
		//排序
		if(apiGuiLogs.size() > 0 ) {
			List<ApiGuiLog> result = apiGuiLogs.stream()
				.peek(lpm -> {
						lpm.setShowDate(dateTimtUtil.formatDateToStr(lpm.getCreatetime(), "yyyy-MM-dd"));
						lpm.setShowTime(dateTimtUtil.formatDateToStr(lpm.getCreatetime(), "HH:mm:ss"));
				})
				.sorted(apiGuiLogSort(conditionsRequest.getSortField(), conditionsRequest.getSortOrder()))
				.collect(Collectors.toList());
			
			return new ResponseEntity<JSGridReturnData<ApiGuiLog>>(JSGridResponse.getResponseData(result, apiGuiLogRepository.count()), HttpStatus.OK);
		} else {
			throw new QueryNoDataException("查無資料!!!", 404);
		}
	}

	/**
	 * 依Lpmid查詢
	 * @param lpmid
	 * @return LpmApiLog
	 */
    @Override
	public ApiGuiLog findByApiGuiId(Long apiGuiId) {
		return apiGuiLogRepository.findById(apiGuiId).get();
	}
	
    /**
     * 儲存LOG
     * @param ApiGuiLog
     */
	@Override
	public void save(ApiGuiLog apiGuiLog) {
		apiGuiLogRepository.save(apiGuiLog);
	}
	
	/**
	 * 排序欄位設置
	 * @param sortField
	 * @param sortOrder
	 * @return Comparator<ApiGuiLog>
	 */
	private Comparator<ApiGuiLog> apiGuiLogSort(String sortField, String sortOrder) {
		switch(sortField) {
		case "ApiGuiUserId":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(ApiGuiLog::getApiGuiUserId) : 
						Comparator.comparing(ApiGuiLog::getApiGuiUserId).reversed();
		case "ApiGuiUrl":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(ApiGuiLog::getApiGuiUrl) : 
						Comparator.comparing(ApiGuiLog::getApiGuiUrl).reversed();
		case "ApiGuiSendStatus":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(ApiGuiLog::getApiGuiSendStatus) : 
						Comparator.comparing(ApiGuiLog::getApiGuiSendStatus).reversed();
		}
		
		return sortOrder.equals("asc") ? 
				Comparator.comparing(ApiGuiLog::getApiGuiId) : 
					Comparator.comparing(ApiGuiLog::getApiGuiId).reversed();
	}
	
}
