package com.ApiGL.service.impl;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ApiGL.entity.RoleFunction;
import com.ApiGL.entity.User;
import com.ApiGL.entity.UserFunction;
import com.ApiGL.entity.PermissionResponse;
import com.ApiGL.exception.PermissionNullException;
import com.ApiGL.repositories.RoleFunctionRepository;
import com.ApiGL.repositories.UserFunctionRepository;
import com.ApiGL.repositories.UserRepository;
import com.ApiGL.service.ApiGLPermissionService;
import com.ApiGL.util.SessionUser;
import com.ApiGL.util.ValidateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;

@DS("DBAPIGL")
@Service
public class ApiGLPermissionServiceImpl implements ApiGLPermissionService {

	@Autowired
	RoleFunctionRepository roleFunctionRepository;
	
	@Autowired
	UserFunctionRepository userFunctionRepository;
	
	@Autowired
	SessionUser sessionUser;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ValidateUtil validateUtil;
	
	
	@Override
	public Set<PermissionResponse> queryRolePermission(Long apiglRoleId) {
    	return roleFunctionRepository.queryRolePermission(apiglRoleId);
	}
    
	@Override
	@Transactional
	public String saveRoleFunctions(Set<RoleFunction> roleFunctions) {
		roleFunctions.forEach(role -> {
			role.setCreateTime(new Date());
			role.setCreateUser(sessionUser.getUser().getAccount());
		});
		deleteRoleFunctions(roleFunctions.iterator().next().getApiglRoleId());
		Iterator<RoleFunction> rolefunctions = roleFunctionRepository.saveAll(roleFunctions).iterator();
		return "修改成功";
	}
	
	@Override
	public void deleteRoleFunctions(Long apiglRoleId) {
		roleFunctionRepository.deleteByapiglRoleId(apiglRoleId);
	}

	@Override
	public Set<PermissionResponse> queryUserPermission(Long userId) {
    	return userFunctionRepository.queryUserPermission(userId);
	}

	@Override
	@Transactional
	public String saveUserFunctions(Set<UserFunction> userIds) {
		userIds.forEach(user -> {
			user.setCreateTime(new Date());
			user.setCreateUser(sessionUser.getUser().getAccount());
		});
		deleteUserFunctions(userIds.iterator().next().getUserId());
		Iterator<UserFunction> userfunctions = userFunctionRepository.saveAll(userIds).iterator();
		return "修改成功";
	}

	@Override
	public void deleteUserFunctions(Long userId) {
		userFunctionRepository.deleteByUserId(userId);
	}
	
	@Override
	public boolean getPermissionAndNavbarStr(HttpSession session, HttpServletRequest request) {
		
		boolean subconf = false;
		
		StringBuilder navBarStr = new StringBuilder();
		
		if(validateUtil.isNotEmpty(sessionUser) && validateUtil.isNotEmpty(sessionUser.getUser())) {
			String account = sessionUser.getUser().getAccount();
			String groupName = sessionUser.getUser().getGroupName();

			Map<String,List<PermissionResponse>> PermissionMap = new HashMap<>();
			User user = new User();
			
			if(account.equals("root")) {
				user = new User("A01", "DC", 999999, "root", "管理者", "1");
				if (validateUtil.isNotEmpty(user)) {
					PermissionMap = getPermissionMap(user);
				}
				if(PermissionMap == null || PermissionMap.size() <= 0) {
					PermissionMap.put("3", Arrays.asList(
							new PermissionResponse("SystemSetup", "設定", "/systemSetup/", 
									1, "1", "3", "1")));
				} 
			} else {
				if(groupName.equals("DC")) {
					user = sessionUser.getUser();
				} else {
					user = userRepository.findByAccount(account);
				}
				PermissionMap = getPermissionMap(user);
			}

			if(validateUtil.isNotEmpty(PermissionMap)) {
	    		for(String key : PermissionMap.keySet()) {
					List<PermissionResponse> functions = PermissionMap.get(key);
					
					//主選單
					switch(key) {
					case "1":
						navBarStr.append("<li class=\"nav-item menu-is-opening menu-open\">");
						navBarStr.append("<a href=\"#\" class=\"nav-link main\">");
						navBarStr.append("<i class=\"nav-icon fa-solid fa-magnifying-glass\"></i>");
						navBarStr.append("<p>業務<i class=\"right fa-solid fa-plus\"></i></p></a>");
						subconf = true;
						break;
					case "2":
						if(user.getGroupName().equals("DC")) {
							navBarStr.append("<li class=\"nav-item menu-is-opening menu-open\">");
							navBarStr.append("<a href=\"#\" class=\"nav-link main\">");
							navBarStr.append("<i class=\"nav-icon fa-solid fa-user-large\"></i>");
							navBarStr.append("<p>管理<i class=\"right fa-solid fa-plus\"></i></p></a>");
							subconf = true;
						}
						break;
					case "3":
						if(user.getGroupName().equals("DC")) {
							navBarStr.append("<li class=\"nav-item menu-is-opening menu-open\">");
							navBarStr.append("<a href=\"#\" class=\"nav-link main\">");
							navBarStr.append("<i class=\"nav-icon fas fa-cog\"></i>");
							navBarStr.append("<p>設定<i class=\"right fa-solid fa-plus\"></i></p></a>");
							subconf = true;
						}
						break;
					}
					//子選單
					if(subconf) {
						for(PermissionResponse func : functions) {
							navBarStr.append("<ul class=\"nav nav-treeview\">");
							navBarStr.append("<li class=\"nav-item\">");
							String functionurl = validateUtil.isNotBlank(request.getContextPath()) ? 
													request.getContextPath() + func.getApiglfunctionurl() : 
														func.getApiglfunctionurl();
							navBarStr.append("<a href=\"");
							navBarStr.append(functionurl);
							navBarStr.append("\" class=\"nav-link\">");

							navBarStr.append("<span class=\"navlisticon\" id=\"select");
							navBarStr.append(func.getApiglfunctionname());
							navBarStr.append("\"></span>");
							
							navBarStr.append("<p id=\"show");
							navBarStr.append(func.getApiglfunctionname());
							navBarStr.append("\">");
							navBarStr.append(func.getApiglfunctionshowname() );
							navBarStr.append("</p></a></li></ul>");
						}
						navBarStr.append("</li>");
					}
				}
	    	}
		}

    	if(subconf) {
    		session.setAttribute("navbarFunction", navBarStr.toString());
    	} else {
    		session.removeAttribute("user");
    		throw new PermissionNullException("您尚未開通權限，請洽管理人員開通權限", 404);
    	}
    	return subconf;
    }
	/**
	 * 取得第1個Function URL
	 */
	@Override
	public String getFirstFunction(String account) {
    	User user = userRepository.findByAccount(account);
		Map<String,List<PermissionResponse>> PermissionMap = getPermissionMap(user);
		String functionurl = PermissionMap.entrySet().stream()
				.findFirst().get()
				.getValue()
				.get(0).getApiglfunctionurl();
		return functionurl;
	}
	
	public Map<String,List<PermissionResponse>> getPermissionMap(User user) {

		if (validateUtil.isEmpty(user)) {
			return null;
		}
		
    	Map<String,List<PermissionResponse>> PermissionList = null;
    	
    	Set<PermissionResponse> UserPermissions = queryUserPermission(user.getUserId());
    	if (validateUtil.isNotEmpty(UserPermissions)) {
    		if(user.getGroupName().equals("DC")) {
    			//"DC"全功能都可以使用,不過濾UserFunction Enabled
    			UserPermissions = UserPermissions.stream()
								  .filter(u -> u.getEnabled() != null) 
								  .filter(u -> u.getEnabled().equals("1"))
								  .collect(Collectors.toSet());
    		} else {
    			UserPermissions = UserPermissions.stream()
							  	  .filter(u -> u.getEnabled() != null) //過濾Function Enabled的null
							 	  .filter(u -> u.getEnabled().equals("1")) //過濾Function 不啟用的功能
				    			  .filter(u -> u.getPermissionenabled() != null) //過濾UserFunction Enabled的null
								  .filter(u -> u.getPermissionenabled().equals("1")) //過濾UserFunction 沒有權限的功能
			    				  .collect(Collectors.toSet());
    		}
    		//分群處理不同類別
    		PermissionList = UserPermissions.stream()
    						.sorted(comparing(PermissionResponse::getType)
    								.thenComparing(PermissionResponse::getApiglfunctionsort))
    						//.sorted(comparing(PermissionResponse::getApiglfunctionsort))
							.collect(groupingBy(PermissionResponse::getType, LinkedHashMap::new, Collectors.toList()));
			
    	}
    	return PermissionList;
	}
}
