package com.ApiGL.util;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ApiGL.entity.User;

@Component
public class SessionUser {
	
	@Autowired
	HttpSession session;
	
	public User getUser() {
		return (User)session.getAttribute("user");
	}
}
