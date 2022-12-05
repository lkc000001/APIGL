package com.ApiGL.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ApiGL.entity.ApiGLFunction;
import com.ApiGL.entity.ApiGLRole;
import com.ApiGL.entity.User;
import com.ApiGL.repositories.ApiglFunctionRepository;
import com.ApiGL.repositories.ApiglRoleRepository;
import com.ApiGL.repositories.UserRepository;
import com.ApiGL.service.SystemSetupService;
import com.ApiGL.util.ExcelUtil;
import com.ApiGL.util.SessionUser;
import com.ApiGL.util.ValidateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;

@DS("DBAPIGL")
@Service
public class SystemSetupServiceImpl implements SystemSetupService {

	@Autowired
	ApiglRoleRepository apiglRoleRepository;
	
	@Autowired
	ApiglFunctionRepository apiglFunctionRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ValidateUtil validateUtil;
	
	@Autowired
	SessionUser sessionUser;
	
	@Autowired
	ExcelUtil excelUtil;
	
    @Override
    public String apiGLRoleUpload(MultipartFile file) throws EncryptedDocumentException, IOException {
		Workbook wb = WorkbookFactory.create(file.getInputStream());
		Object obj = excelUtil.parseExcel(wb, 1);
		List<ApiGLRole> apiGLRoles = (List<ApiGLRole>)obj;
		List<ApiGLRole> newApiGLRoles = apiGLRoles.stream().peek(role -> {
			
			role.setCreateUser(sessionUser.getUser().getName());
			if (validateUtil.isBlank(role.getApiglRoleNumber())) {
				role.setApiglRoleNumber("");
			}
			
			ApiGLRole apiGLRole = apiglRoleRepository.findByApiglRoleNameAndApiglRoleNumberAndApiglRoleType(role.getApiglRoleName(), role.getApiglRoleNumber(), role.getApiglRoleType());
			if(apiGLRole != null) {
				role.setApiglRoleId(apiGLRole.getApiglRoleId());
				role.setCreateTime(apiGLRole.getCreateTime());
				role.setCreateUser(apiGLRole.getCreateUser());
				role.setUpdateTime(new Date());
				role.setUpdateUser(sessionUser.getUser().getName());
			}
			if(validateUtil.isNotBlank(role.getApiglRoleName())) {
				apiglRoleRepository.save(role);
			}
		}).collect(Collectors.toList());
    	return "上傳" + newApiGLRoles.size() + "筆資料成功";
    }
    
    @Override
    public String apiglFunctionUpload(MultipartFile file) throws EncryptedDocumentException, IOException {
		Workbook wb = WorkbookFactory.create(file.getInputStream());
		Object obj = excelUtil.parseExcel(wb, 2);
		List<ApiGLFunction> apiGLFunctions = (List<ApiGLFunction>)obj;
		List<ApiGLFunction> newApiGLFunctions = apiGLFunctions.stream().peek(function -> {
			
			function.setCreateUser(sessionUser.getUser().getName());
			
			ApiGLFunction apiGLFunction = apiglFunctionRepository.findByApiglFunctionName(function.getApiglFunctionName());
				if(apiGLFunction != null) {
					function.setApiglFunctionId(apiGLFunction.getApiglFunctionId());
					function.setCreateTime(apiGLFunction.getCreateTime());
					function.setCreateUser(apiGLFunction.getCreateUser());
					function.setUpdateTime(new Date());
					function.setUpdateUser(sessionUser.getUser().getName());
				}
				
				if(validateUtil.isNotBlank(function.getApiglFunctionName())) {
					apiglFunctionRepository.save(function);
				}
			}).collect(Collectors.toList());
    	return "上傳" + newApiGLFunctions.size() + "筆資料成功";
    }
    
    @Override
    public String UserUpload(MultipartFile file) throws EncryptedDocumentException, IOException {
		Workbook wb = WorkbookFactory.create(file.getInputStream());
		Object obj = excelUtil.parseExcel(wb, 3);
		List<User> users = (List<User>)obj;
		List<User> newUsers = users.stream().peek(user -> {
			
			user.setCreateUser(sessionUser.getUser().getName());
			
			User oldUser = userRepository.findByAccount(user.getAccount());
			if(oldUser != null) {
				user.setUserId(oldUser.getUserId());
				user.setPwd(oldUser.getPwd());
				user.setCreateTime(oldUser.getCreateTime());
				user.setCreateUser(oldUser.getCreateUser());
				user.setUpdateTime(new Date());
				user.setUpdateUser(sessionUser.getUser().getName());
			}
			
			if(validateUtil.isNotBlank(user.getAccount())) {
				userRepository.save(user);
			}
		}).collect(Collectors.toList());
    	return "上傳" + newUsers.size() + "筆資料成功";
    }
}
