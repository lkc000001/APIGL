package com.ApiGL.service.impl;

import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ApiGL.entity.TwPayLineBindLog;
import com.ApiGL.entity.request.ConditionsRequest;
import com.ApiGL.entity.response.JSGridResponse;
import com.ApiGL.entity.response.JSGridReturnData;
import com.ApiGL.exception.QueryNoDataException;
import com.ApiGL.repositories.TwPayLineBindLogRepository;
import com.ApiGL.service.ServiceUtil;
import com.ApiGL.service.TwPayLineBindLogService;
import com.ApiGL.util.DateTimtUtil;
import com.baomidou.dynamic.datasource.annotation.DS;

@DS("DBTOAPI")
@Service
public class TwPayLineBindLogServiceImpl implements TwPayLineBindLogService {

	@Autowired
	TwPayLineBindLogRepository twPayLineBindLogRepository;
	
	@Autowired
	DateTimtUtil dateTimtUtil;
	
	@Autowired
	ServiceUtil serviceUtil;
	
	/**
	 * LpmApiLog Table依查詢條件查詢,依分頁及排序Response
	 * @param conditionsRequest
	 * @return ResponseEntity
	 * @throws ParseException 
	 */
    @Override
	public ResponseEntity<?> querytwPayLineBindLog(ConditionsRequest conditionsRequest) throws ParseException
	{
		serviceUtil.requestDateCheck(conditionsRequest);
		
		List<TwPayLineBindLog> twPayLineBindLogs = twPayLineBindLogRepository.querytwPayLineBindLog(conditionsRequest.getQueryId(), conditionsRequest.getStartDate(), 
				 							conditionsRequest.getEndDate(), conditionsRequest.getQueryData(), conditionsRequest.getQueryType(), 
				 							conditionsRequest.getQueryUrl(), conditionsRequest.getPageIndex(), conditionsRequest.getPageSize());
		//排序
		if(twPayLineBindLogs.size() > 0 ) {
			List<TwPayLineBindLog> result = twPayLineBindLogs.stream()
				.peek(lpm -> {
						lpm.setShowDate(dateTimtUtil.formatDateToStr(lpm.getCreatetime(), "yyyy-MM-dd"));
						lpm.setShowTime(dateTimtUtil.formatDateToStr(lpm.getCreatetime(), "HH:mm:ss"));
					})
				.sorted(twPayLineBindLogSort(conditionsRequest.getSortField(), conditionsRequest.getSortOrder()))
				.collect(Collectors.toList());
		
			return new ResponseEntity<JSGridReturnData<TwPayLineBindLog>>(JSGridResponse.getResponseData(result, twPayLineBindLogRepository.count()), HttpStatus.OK);
		} else {
			throw new QueryNoDataException("查無資料!!!", 404);
		}
	}

	/**
	 * 依twPayLineBindLogid查詢
	 * @param twPayLineBindLogid
	 * @return TwPayLineBindLog
	 */
    @Override
	public TwPayLineBindLog findBytwPayLineBindLogId(Long twPayLineBindLogid) {
		return twPayLineBindLogRepository.findById(twPayLineBindLogid).get();
	}
	
	/**
	 * 排序欄位設置
	 * @param sortField
	 * @param sortOrder
	 * @return Comparator<TwPayLineBindLog>
	 */
	private Comparator<TwPayLineBindLog> twPayLineBindLogSort(String sortField, String sortOrder) {
		switch(sortField) {
		case "TWPayLineBindLogLineUid":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(TwPayLineBindLog::getTwPayLineBindLogLineUid) : 
						Comparator.comparing(TwPayLineBindLog::getTwPayLineBindLogLineUid).reversed();
		case "TWPayLineBindLogTxnNo":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(TwPayLineBindLog::getTwPayLineBindLogTxnNo) : 
						Comparator.comparing(TwPayLineBindLog::getTwPayLineBindLogTxnNo).reversed();
		case "TWPayLineBindLogTxnDateTime":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(TwPayLineBindLog::getTwPayLineBindLogTxnDateTime) : 
						Comparator.comparing(TwPayLineBindLog::getTwPayLineBindLogTxnDateTime).reversed();
		}
		
		return sortOrder.equals("asc") ? 
				Comparator.comparing(TwPayLineBindLog::getTwPayLineBindLogId) : 
					Comparator.comparing(TwPayLineBindLog::getTwPayLineBindLogId).reversed();
	}
}
