package com.ApiGL.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ApiGL.config.EportalHomePage;

@Controller
@RequestMapping(value = "/logout")
public class LogoutController {

	@Autowired
	EportalHomePage eportalHomePage;
	
    @GetMapping("/")
    public String logout(HttpServletRequest req) {
    	req.getSession().invalidate();
    	return "redirect:" + eportalHomePage.getHomepage();
    }
}
