package com.ApiGL.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ApiGL.entity.User;
import com.ApiGL.service.UserService;
import com.ApiGL.util.ValidateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping(value = "/userPermission")
public class UserPermissionController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ValidateUtil validateUtil;
	
	@GetMapping("/")
    public String index(Model model) {
    	return "userPermission";
    }
	
	/**
	 * 依查詢條件查詢ApiGLFunction Table資料
	 * @param session
	 * @param ApiGLFunction
	 * @return 排序及分頁後的ApiGLFunction
	 * @throws ParseException 
	 */
	@PostMapping(path = "/queryUserPermission", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> queryUserPermission(@RequestBody User user) throws ParseException {
		return userService.queryUserPermission(user);
    }

	@GetMapping(path = "/getUser/{userId}")
	@ResponseBody
	public User getUser(@PathVariable("userId") Long userId) {
		return userService.findByUserId(userId);
	}
	
	/**
	 * 新增
	 * @param apiGLFunction
	 * @return
	 * @throws JsonProcessingException
	 */
	@PostMapping(path = "/", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> add(@RequestBody User user) {
		String respMsg = userService.save(user, "新增");
		return new ResponseEntity<>("{\"message\": \"" + respMsg + "\"}",HttpStatus.OK);
    }
    
    /**
     * 修改
     * @param apiGLFunction
     * @return
     * @throws JsonProcessingException
     */
	@PutMapping(path = "/", consumes = "application/json", produces = "application/json")
    @ResponseBody
	public ResponseEntity<?> update(@RequestBody User user) {
    	String respMsg = userService.save(user, "修改");
    	return new ResponseEntity<>("{\"message\": \"" + respMsg + "\"}",HttpStatus.OK);
    }
    
	
}
