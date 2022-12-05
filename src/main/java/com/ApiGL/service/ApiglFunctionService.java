package com.ApiGL.service;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;

import com.ApiGL.entity.ApiGLFunction;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ApiglFunctionService {

	ResponseEntity<?> queryApiglFunction(ApiGLFunction apiGLFunction) throws ParseException;

	ApiGLFunction findByApiglFunctionId(Long apiglFunctionId);
	
	String save(ApiGLFunction apiGLFunction, String func, HttpSession session, HttpServletRequest request);
	
	void delete(Long id);
	
	List<ApiGLFunction> findAll();
	
	List<ApiGLFunction> findByIsEnable();
}
