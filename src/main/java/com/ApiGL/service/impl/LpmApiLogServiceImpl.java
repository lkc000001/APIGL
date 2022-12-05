package com.ApiGL.service.impl;

import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ApiGL.entity.LpmApiLog;
import com.ApiGL.entity.request.ConditionsRequest;
import com.ApiGL.entity.response.JSGridResponse;
import com.ApiGL.entity.response.JSGridReturnData;
import com.ApiGL.exception.QueryNoDataException;
import com.ApiGL.repositories.LpmApiLogRepository;
import com.ApiGL.service.LpmApiLogService;
import com.ApiGL.service.ServiceUtil;
import com.ApiGL.util.DateTimtUtil;
import com.baomidou.dynamic.datasource.annotation.DS;

@DS("DBTLPM")
@Service
public class LpmApiLogServiceImpl implements LpmApiLogService {

	@Autowired
	LpmApiLogRepository lpmApiLogRepository;
	
	@Autowired
	DateTimtUtil dateTimtUtil;
	
	@Autowired
	ServiceUtil serviceUtil;
	
	/**
	 * LpmApiLog Table依查詢條件查詢,依分頁及排序Response
	 * @param ConditionsRequest
	 * @return ResponseEntity
	 * @throws ParseException 
	 */
    @Override
	public ResponseEntity<?> queryLpmApiLog(ConditionsRequest conditionsRequest) throws ParseException
	{
		serviceUtil.requestDateCheck(conditionsRequest);
		
		List<LpmApiLog> lpmApiLogs = lpmApiLogRepository.getLpmApiLog(conditionsRequest.getQueryUserId(), conditionsRequest.getStartDate(), 
									 conditionsRequest.getEndDate(), conditionsRequest.getQueryData(), conditionsRequest.getQueryType(), 
									 conditionsRequest.getQueryUrl(), conditionsRequest.getPageIndex(), conditionsRequest.getPageSize());
		
		//排序
		if(lpmApiLogs.size() > 0 ) {
			List<LpmApiLog> result = lpmApiLogs.stream()
				.peek(lpm -> {
						lpm.setShowDate(dateTimtUtil.formatDateToStr(lpm.getCreatetime(), "yyyy-MM-dd"));
						lpm.setShowTime(dateTimtUtil.formatDateToStr(lpm.getCreatetime(), "HH:mm:ss"));
					})
				.sorted(lpmApiLogSort(conditionsRequest.getSortField(), conditionsRequest.getSortOrder()))
				.collect(Collectors.toList());
			
			return new ResponseEntity<JSGridReturnData<LpmApiLog>>(JSGridResponse.getResponseData(result, lpmApiLogRepository.count()), HttpStatus.OK);
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
	public LpmApiLog findByLpmid(Long lpmId) {
    	return lpmApiLogRepository.findById(lpmId).get();
	}
	
	/**
	 * 排序欄位設置
	 * @param sortField
	 * @param sortOrder
	 * @return Comparator<LpmApiLog>
	 */
	private Comparator<LpmApiLog> lpmApiLogSort(String sortField, String sortOrder) {
		switch(sortField) {
		case "lpmtxseq":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(LpmApiLog::getLpmTxSeq) : 
						Comparator.comparing(LpmApiLog::getLpmTxSeq).reversed();
		case "lpmuserid":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(LpmApiLog::getLpmUserId) : 
						Comparator.comparing(LpmApiLog::getLpmUserId).reversed();
		case "lpmusercode":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(LpmApiLog::getLpmUserCode) : 
						Comparator.comparing(LpmApiLog::getLpmUserCode).reversed();
		}
		
		return sortOrder.equals("asc") ? 
				Comparator.comparing(LpmApiLog::getLpmId) : 
					Comparator.comparing(LpmApiLog::getLpmId).reversed();
	}
}
