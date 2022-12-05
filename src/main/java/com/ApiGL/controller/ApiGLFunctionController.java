package com.ApiGL.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ApiGL.entity.ApiGLFunction;
import com.ApiGL.service.ApiglFunctionService;
import com.ApiGL.util.ValidateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping(value = "/apiGLFunction")
public class ApiGLFunctionController {
	
	@Autowired
	ApiglFunctionService apiglFunctionService;
	
	@Autowired
	ValidateUtil validateUtil;
	
    @GetMapping("/")
    public String index(Model model) {
    	return "apiGLFunction";
    }
    
    /**
	 * 依查詢條件查詢ApiGLFunction Table資料
	 * @param session
	 * @param ApiGLFunction
	 * @return 排序及分頁後的ApiGLFunction
	 * @throws ParseException 
	 */
	@PostMapping(path = "/queryApiglFunction", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> queryApiglFunction(@RequestBody ApiGLFunction apiGLFunction) throws ParseException {
		return apiglFunctionService.queryApiglFunction(apiGLFunction);
    }
	
	/**
	 * 依apiglRoleId查詢ApiGLRole Table資料
	 * @param apiglRoleId
	 * @return ApiGLRole
	 */
	@GetMapping(path = "/getApiGLFunction/{apiglFunctionId}")
	@ResponseBody
	public ApiGLFunction getApiGLFunction(@PathVariable("apiglFunctionId") Long apiglFunctionId) {
		return apiglFunctionService.findByApiglFunctionId(apiglFunctionId);
    }
	
	/**
	 * 新增
	 * @param apiGLFunction
	 * @return
	 * @throws JsonProcessingException
	 */
    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    @ResponseBody
	public ResponseEntity<?> add(HttpSession session, HttpServletRequest request, @RequestBody ApiGLFunction apiGLFunction) {
    	String respMsg = apiglFunctionService.save(apiGLFunction, "新增", session, request);
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
	public ResponseEntity<?> update(HttpSession session, HttpServletRequest request, @RequestBody ApiGLFunction apiGLFunction) throws JsonProcessingException {
    	String respMsg = apiglFunctionService.save(apiGLFunction, "修改", session, request);
    	return new ResponseEntity<>("{\"message\": \"" + respMsg + "\"}",HttpStatus.OK);
    }
    
    /**
     * 刪除
     * @param id
     * @return
     */
    @DeleteMapping(path = "/delete")
	public ResponseEntity<?> delete(Long id){
    	apiglFunctionService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
    }
}
