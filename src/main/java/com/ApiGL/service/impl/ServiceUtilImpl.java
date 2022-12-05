package com.ApiGL.service.impl;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ApiGL.entity.request.ConditionsRequest;
import com.ApiGL.exception.TimeFormatException;
import com.ApiGL.service.ServiceUtil;
import com.ApiGL.util.DateTimtUtil;
import com.ApiGL.util.ValidateUtil;

@Service
public class ServiceUtilImpl implements ServiceUtil {
	
	@Autowired
	DateTimtUtil dateTimtUtil;
	
	@Autowired
	ValidateUtil validateUtil;
	
	@Override
	public void requestDateCheck(ConditionsRequest conditionsRequest) throws ParseException {

		if(conditionsRequest.getQueryDate().equals("____/__/__")) {
			conditionsRequest.setQueryDate("");
		}
		if (validateUtil.isBlank(conditionsRequest.getQueryDate()) &&
				(validateUtil.isNotBlank(conditionsRequest.getTimeStart()) ||
				validateUtil.isNotBlank(conditionsRequest.getTimeEnd())) ) {
			throw new TimeFormatException("日期不可為空白!!!", 400);
		}
		
		
		System.out.println(conditionsRequest.getQueryDate());
		if (validateUtil.isNotBlank(conditionsRequest.getQueryDate())) {
			conditionsRequest.setQueryDate(conditionsRequest.getQueryDate().replace("/","-"));
			//如果沒有時間加上時間
			String startTime = validateUtil.isBlank(conditionsRequest.getTimeStart()) ? "00:00:00.000" : conditionsRequest.getTimeStart() + ":00.000";
			String endTime = validateUtil.isBlank(conditionsRequest.getTimeEnd()) ? "23:59:59.997" : conditionsRequest.getTimeEnd() + ":59.997";
			//檢查起始時間是否小於結束時間
			if(Integer.parseInt(startTime.substring(0, 8).replace(":", "")) > Integer.parseInt(endTime.substring(0, 8).replace(":", ""))) {
				throw new TimeFormatException("時間設置錯誤，起始時間大於結束時間!!!", 400);
			}
			conditionsRequest.setStartDate(dateTimtUtil.formatStrToDate(conditionsRequest.getQueryDate() + " " + startTime));
			conditionsRequest.setEndDate(dateTimtUtil.formatStrToDate(conditionsRequest.getQueryDate() + " " + endTime));
		}
		
		String sortField = validateUtil.isBlank(conditionsRequest.getSortField()) ? "lpmid" : conditionsRequest.getSortField();
		conditionsRequest.setSortField(sortField);
		String sortOrder = validateUtil.isBlank(conditionsRequest.getSortOrder()) ? "desc" : conditionsRequest.getSortOrder();
		conditionsRequest.setSortOrder(sortOrder);
		String queryData =  validateUtil.isBlank(conditionsRequest.getQueryData()) ? "" : "%" + conditionsRequest.getQueryData() + "%";
		conditionsRequest.setQueryData(queryData);
		String queryUrl =  validateUtil.isBlank(conditionsRequest.getQueryUrl()) ? "" : "%" + conditionsRequest.getQueryUrl() + "%";
		conditionsRequest.setQueryUrl(queryUrl);
		String queryUserId =  validateUtil.isBlank(conditionsRequest.getQueryUserId()) ? "" : "%" + conditionsRequest.getQueryUserId() + "%";
		conditionsRequest.setQueryUserId(queryUserId);
	}
}
