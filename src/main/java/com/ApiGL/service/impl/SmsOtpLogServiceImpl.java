package com.ApiGL.service.impl;

import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ApiGL.entity.SmsOtpLog;
import com.ApiGL.entity.request.ConditionsRequest;
import com.ApiGL.entity.response.JSGridResponse;
import com.ApiGL.entity.response.JSGridReturnData;
import com.ApiGL.exception.QueryNoDataException;
import com.ApiGL.repositories.SmsOtpLogRepository;
import com.ApiGL.service.ServiceUtil;
import com.ApiGL.service.SmsOtpLogService;
import com.ApiGL.util.DateTimtUtil;
import com.baomidou.dynamic.datasource.annotation.DS;

@DS("DBTOAPI")
@Service
public class SmsOtpLogServiceImpl implements SmsOtpLogService {

	@Autowired
	SmsOtpLogRepository smsOtpLogRepository;
	
	@Autowired
	DateTimtUtil dateTimtUtil;
	
	@Autowired
	ServiceUtil serviceUtil;
	
	/**
	 * LpmApiLog Table依查詢條件查詢,依分頁及排序Response
	 * @param lpmApiLogRequest
	 * @return ResponseEntity
	 * @throws ParseException 
	 */
    @Override
	public ResponseEntity<?> querySmsOtpLog(ConditionsRequest conditionsRequest) throws ParseException
	{
		serviceUtil.requestDateCheck(conditionsRequest);
		System.out.println(conditionsRequest);
		List<SmsOtpLog> smsOtpLogs = smsOtpLogRepository.querySmsOtpLog(conditionsRequest.getQueryUserId(), conditionsRequest.getStartDate(), 
									 conditionsRequest.getEndDate(), conditionsRequest.getQueryData(), conditionsRequest.getQueryType(), 
									 conditionsRequest.getQueryUrl(), conditionsRequest.getPageIndex(), conditionsRequest.getPageSize());
		//排序
		if(smsOtpLogs.size() > 0) {
			List<SmsOtpLog> result = smsOtpLogs.stream()
				.peek(lpm -> {
						lpm.setShowDate(dateTimtUtil.formatDateToStr(lpm.getCreatetime(), "yyyy-MM-dd"));
						lpm.setShowTime(dateTimtUtil.formatDateToStr(lpm.getCreatetime(), "HH:mm:ss"));
					})
				.sorted(smsOtpLogSort(conditionsRequest.getSortField(), conditionsRequest.getSortOrder()))
				.collect(Collectors.toList());
			
			return new ResponseEntity<JSGridReturnData<SmsOtpLog>>(JSGridResponse.getResponseData(result, smsOtpLogRepository.count()), HttpStatus.OK);
		} else {
			throw new QueryNoDataException("查無資料!!!", 404);
		} 
	}

	/**
	 * 依solId查詢
	 * @param solId
	 * @return SmsOtpLog
	 */
    @Override
	public SmsOtpLog findBySolId(Long solId) {
		return smsOtpLogRepository.findById(solId).get();
	}
	
	/**
	 * 排序欄位設置
	 * @param sortField
	 * @param sortOrder
	 * @return Comparator<SmsOtpLog>
	 */
	private Comparator<SmsOtpLog> smsOtpLogSort(String sortField, String sortOrder) {
		switch(sortField) {
		case "USERID":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(SmsOtpLog::getUserId) : 
						Comparator.comparing(SmsOtpLog::getUserId).reversed();
		case "PHONE":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(SmsOtpLog::getPhone) : 
						Comparator.comparing(SmsOtpLog::getPhone).reversed();
		case "SYSFLAG":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(SmsOtpLog::getSysFlag) : 
						Comparator.comparing(SmsOtpLog::getSysFlag).reversed();	
		}
		
		return sortOrder.equals("asc") ? 
				Comparator.comparing(SmsOtpLog::getSolId) : 
					Comparator.comparing(SmsOtpLog::getSolId).reversed();
	}
}
