package com.ApiGL.service;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;

import com.ApiGL.entity.User;

public interface UserService {
	
	User getUser(String account);
	
	ResponseEntity<?> queryUserPermission(User user) throws ParseException;
	
	User findByUserId(Long userId);
	
	String save(User user, String func);
	
	Boolean findByisEnable(String account);
}
