package com.ApiGL.service;

import java.io.IOException;
import java.text.ParseException;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.ApiGL.entity.ApiGLRole;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ApiglRoleService {

	ResponseEntity<?> queryApiGLRole(ApiGLRole apiGLRole) throws ParseException;

	ApiGLRole findByApiglRoleId(Long apiglRoleId);
	
	String save(ApiGLRole apiGLRole, String func) throws JsonProcessingException;
	
	void delete(Long id);
}
