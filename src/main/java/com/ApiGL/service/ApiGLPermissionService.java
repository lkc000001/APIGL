package com.ApiGL.service;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ApiGL.entity.RoleFunction;
import com.ApiGL.entity.User;
import com.ApiGL.entity.UserFunction;
import com.ApiGL.entity.PermissionResponse;

public interface ApiGLPermissionService {
	
	Set<PermissionResponse> queryRolePermission(Long apiglRoleId);
	
	String saveRoleFunctions(Set<RoleFunction> roleFunctions);
	
	void deleteRoleFunctions(Long apiglRoleId);
	
	Set<PermissionResponse> queryUserPermission(Long userId);
	
	String saveUserFunctions(Set<UserFunction> userIds);
	
	void deleteUserFunctions(Long userId);
	
	boolean getPermissionAndNavbarStr(HttpSession session, HttpServletRequest request);
	
	String getFirstFunction(String account);
}
