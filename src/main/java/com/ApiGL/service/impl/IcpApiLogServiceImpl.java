package com.ApiGL.service.impl;

import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ApiGL.entity.IcpApiLog;
import com.ApiGL.entity.request.ConditionsRequest;
import com.ApiGL.entity.response.JSGridResponse;
import com.ApiGL.entity.response.JSGridReturnData;
import com.ApiGL.exception.QueryNoDataException;
import com.ApiGL.repositories.IcpApiLogRepository;
import com.ApiGL.service.IcpApiLogService;
import com.ApiGL.service.ServiceUtil;
import com.ApiGL.util.DateTimtUtil;
import com.baomidou.dynamic.datasource.annotation.DS;

@DS("DBTICP")
@Service
public class IcpApiLogServiceImpl implements IcpApiLogService {

	@Autowired
	IcpApiLogRepository icpAPILogRepository;
	
	@Autowired
	DateTimtUtil dateTimtUtil;
	
	@Autowired
	ServiceUtil serviceUtil;
	
	/**
	 * IcpApiLog Table依查詢條件查詢,依分頁及排序Response
	 * @param ConditionsRequest
	 * @return ResponseEntity
	 * @throws ParseException 
	 */
    @Override
	public ResponseEntity<?> queryIcpApiLog(ConditionsRequest conditionsRequest) throws ParseException
	{
		serviceUtil.requestDateCheck(conditionsRequest);
		
		List<IcpApiLog> icpApiLogs = icpAPILogRepository.queryIcpApiLog(conditionsRequest.getQueryUserId(), conditionsRequest.getStartDate(), 
									 conditionsRequest.getEndDate(), conditionsRequest.getQueryData(), conditionsRequest.getQueryType(), 
									 conditionsRequest.getQueryUrl(), conditionsRequest.getPageIndex(), conditionsRequest.getPageSize());
		if(icpApiLogs.size() > 0 ) {
			//排序
			List<IcpApiLog> result = icpApiLogs.stream()
				.peek(lpm -> {
						lpm.setShowDate(dateTimtUtil.formatDateToStr(lpm.getCreatetime(), "yyyy-MM-dd"));
						lpm.setShowTime(dateTimtUtil.formatDateToStr(lpm.getCreatetime(), "HH:mm:ss"));
					})
				.sorted(icpApiLogSort(conditionsRequest.getSortField(), conditionsRequest.getSortOrder()))
				.collect(Collectors.toList());
			
			return new ResponseEntity<JSGridReturnData<IcpApiLog>>(JSGridResponse.getResponseData(result, icpAPILogRepository.count()), HttpStatus.OK);
		} else {
			throw new QueryNoDataException("查無資料!!!", 404);
		}
	}

	/**
	 * 依icpId查詢
	 * @param icpId
	 * @return IcpApiLog
	 */
    @Override
	public IcpApiLog findByicpId(Long icpId) {
		return icpAPILogRepository.findById(icpId).get();
	}
	
	/**
	 * 排序欄位設置
	 * @param sortField
	 * @param sortOrder
	 * @return Comparator<IcpApiLog>
	 */
	private Comparator<IcpApiLog> icpApiLogSort(String sortField, String sortOrder) {
		switch(sortField) {
		case "ICPCustId":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(IcpApiLog::getIcpCustId) : 
						Comparator.comparing(IcpApiLog::getIcpCustId).reversed();
		case "ICPType":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(IcpApiLog::getIcpType) : 
						Comparator.comparing(IcpApiLog::getIcpType).reversed();
		}
		
		return sortOrder.equals("asc") ? 
				Comparator.comparing(IcpApiLog::getIcpId) : 
					Comparator.comparing(IcpApiLog::getIcpId).reversed();
	}
}
