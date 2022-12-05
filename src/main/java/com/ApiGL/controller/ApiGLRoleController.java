package com.ApiGL.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ApiGL.entity.ApiGLFunction;
import com.ApiGL.entity.ApiGLRole;
import com.ApiGL.entity.response.ErrorMsg;
import com.ApiGL.repositories.ApiglRoleRepository;
import com.ApiGL.service.ApiglRoleService;
import com.ApiGL.util.ExcelUtil;
import com.ApiGL.util.ValidateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping(value = "/apiGLRole")
public class ApiGLRoleController {
	
	@Autowired
	ApiglRoleService apiglRoleService;
	
	@Autowired
	ValidateUtil validateUtil;
	
    @GetMapping("/")
    public String index(Model model) {
    	return "apiGLRole";
    }
    
    /**
	 * 依查詢條件查詢TWPayLineBindLog Table資料
	 * @param session
	 * @param ConditionsRequest
	 * @return 排序及分頁後的ListTWPayLineBindLog
	 * @throws ParseException 
	 */
	@PostMapping(path = "/queryapiGLRole", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> queryApiGLRole(@RequestBody ApiGLRole apiglRole) throws ParseException {
		return apiglRoleService.queryApiGLRole(apiglRole);
    }
   
	/**
	 * 依apiglRoleId查詢ApiGLRole Table資料
	 * @param apiglRoleId
	 * @return ApiGLRole
	 */
	@GetMapping(path = "/findByApiglRoleId/{apiglRoleId}")
	@ResponseBody
	public ApiGLRole findByApiglRoleId(@PathVariable("apiglRoleId") Long apiglRoleId) {
		return apiglRoleService.findByApiglRoleId(apiglRoleId);
    }
	
    /**
	 * 新增
	 * @param apiGLFunction
	 * @return
	 * @throws JsonProcessingException
	 */
	@PostMapping(path = "/", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<?>  add(@RequestBody ApiGLRole apiglRole) throws JsonProcessingException {
    	String respMsg = apiglRoleService.save(apiglRole, "新增");
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
	public ResponseEntity<?>  update(@RequestBody ApiGLRole apiglRole) throws JsonProcessingException {
    	String respMsg = apiglRoleService.save(apiglRole, "修改");
    	return new ResponseEntity<>("{\"message\": \"" + respMsg + "\"}",HttpStatus.OK);
    }
    
    /**
     * 刪除
     * @param id
     * @return
     */
    @DeleteMapping(path = "/delete")
    @ResponseBody
	public ResponseEntity<?> delete(Long id){
    	apiglRoleService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
    }
}
