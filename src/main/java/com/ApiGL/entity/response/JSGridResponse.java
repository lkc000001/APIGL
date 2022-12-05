package com.ApiGL.entity.response;

import java.util.List;

import org.springframework.data.domain.Page;

public class JSGridResponse {
    public static <T> JSGridReturnData<T> getResponseData(Page<T> pagedata)
    {
    	JSGridReturnData<T> r=new JSGridReturnData<T>();
    	r.data=pagedata.getContent();
    	r.itemsCount=pagedata.getTotalElements();
    	return r;
    };
    
    public static <T> JSGridReturnData<T> getResponseData(List<T> list, long count)
    {
    	JSGridReturnData<T> r=new JSGridReturnData<T>();
    	r.data=list;
    	r.itemsCount=count;
    	return r;
    };
    
    
	
}


