package com.ApiGL.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ApiGL.config.EportalHomePage;
import com.ApiGL.entity.ApiGuiLog;
import com.ApiGL.entity.User;
import com.ApiGL.entity.response.JSGridResponse;
import com.ApiGL.entity.response.JSGridReturnData;
import com.ApiGL.repositories.UserFunctionRepository;
import com.ApiGL.service.ApiGLPermissionService;

@Controller
public class ApiGLController {
	
	@Autowired
	ApiGLPermissionService apiGLPermissionService;

	@Autowired
	EportalHomePage eportalHomePage;
	
    @GetMapping("/")
    public String index(HttpSession session, HttpServletRequest request) {
    	User user = (User) session.getAttribute("user");
    	if(user == null) {
    		//return "redirect:/" + request.getContextPath()  + "/login/";
    		return "redirect:/login/";
    	} else {
    		String functionurl = apiGLPermissionService.getFirstFunction(user.getAccount());
    		return "redirect:" + functionurl;
    	}
    }
    
    @GetMapping("/error")
    public String error() {
    	return "error";
    }
    
    @GetMapping("/eportalHomepage")
    @ResponseBody
    public String eportalHomepage() {
    	return eportalHomePage.getHomepage();
    }
    
}
