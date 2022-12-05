package com.ApiGL.service;

import java.text.ParseException;

import javax.servlet.http.HttpSession;

import com.ApiGL.entity.request.ConditionsRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ServiceUtil {
	
	void requestDateCheck(ConditionsRequest conditionsRequest) throws ParseException;
	
}
