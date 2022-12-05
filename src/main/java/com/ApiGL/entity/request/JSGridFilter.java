package com.ApiGL.entity.request;

import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Data;

public  @Data class JSGridFilter {
	@Transient
	private int pageIndex;
	
	@Transient
	private int pageSize;
	
	@Transient
	private String sortField;
	
	@Transient
	private String sortOrder;
	
	@Transient
	private String showDate;
	
	@Transient
	private String showTime;
	
    public Pageable convertToSpringPagingObject()
    {
    	if (pageSize==0) pageSize=100;
    	if (sortField==null || sortField.equals("")) return PageRequest.of(pageIndex-1,  // 查詢的頁數，從0起算
                pageSize, // 查詢的每頁筆數
                Sort.by("lpmid").descending()); // 依欄位降冪排序    	
		
		if (sortOrder !=null && sortOrder.equalsIgnoreCase("desc")) 
			return PageRequest.of(pageIndex-1,  // 查詢的頁數，從0起算
	                pageSize, // 查詢的每頁筆數
	                Sort.by(sortField).descending()); // 依欄位降冪排序

		return PageRequest.of(pageIndex-1,  // 查詢的頁數，從0起算
                pageSize, // 查詢的每頁筆數
                Sort.by(sortField)); // 依欄位排序
		
    }
}
