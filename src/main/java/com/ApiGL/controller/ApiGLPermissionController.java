package com.ApiGL.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ApiGL.entity.ApiGLRole;
import com.ApiGL.entity.RoleFunction;
import com.ApiGL.entity.UserFunction;
import com.ApiGL.entity.PermissionResponse;
import com.ApiGL.service.ApiglRoleService;
import com.ApiGL.service.UserService;
import com.ApiGL.service.ApiGLPermissionService;
import com.ApiGL.util.SessionUser;
import com.ApiGL.util.ValidateUtil;

@Controller
@RequestMapping(value = "/apiGLPermission")
public class ApiGLPermissionController {
	
	@Autowired
	ApiglRoleService apiglRoleService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ApiGLPermissionService apiGLPermissionService;
	
	@Autowired
	SessionUser sessionUser;
	
	@Autowired
	ValidateUtil validateUtil;
	
	@GetMapping("/")
    public String index() {
    	return "apiGLPermission";
    }
	
    @GetMapping("/role/{apiglRoleId}")
    public String role(Model model, @PathVariable("apiglRoleId") Long apiglRoleId) {

    	Set<PermissionResponse> RolePermission = apiGLPermissionService.queryRolePermission(apiglRoleId);
    	System.out.println(RolePermission);
    	model.addAttribute("apiglrole", apiglRoleService.findByApiglRoleId(apiglRoleId));
    	model.addAttribute("functionsTable", createFunctionsTableStr(RolePermission));
    	model.addAttribute("path", "role");
    	
    	return "apiGLPermission";
    }

    @PostMapping(path = "/role/update", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> roleupdate(Model model, @RequestBody Set<RoleFunction> roleFunctions) {
    	apiGLPermissionService.saveRoleFunctions(roleFunctions);
    	return new ResponseEntity<>("{\"message\": \"修改成功\"}",HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}")
    public String user(Model model, @PathVariable("userId") Long userId) {

    	Set<PermissionResponse> UserPermission = apiGLPermissionService.queryUserPermission(userId);

    	model.addAttribute("user", userService.findByUserId(userId));
    	model.addAttribute("functionsTable", createFunctionsTableStr(UserPermission));
    	model.addAttribute("path", "user");
    	
    	ApiGLRole apiglrole = new ApiGLRole();
    	apiglrole.setApiglRoleId(-1L);
    	model.addAttribute("apiglrole", apiglrole);
    	return "apiGLPermission";
    }
    
    @PostMapping(path = "/user/update", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> userupdate(Model model, @RequestBody Set<UserFunction> userFunctions) {
    	apiGLPermissionService.saveUserFunctions(userFunctions);
    	return new ResponseEntity<>("{\"message\": \"修改成功\"}",HttpStatus.OK);
    }
    
    private String isEnabled(String permissionEnabled) {
    	String respStr = "";
    	if(validateUtil.isBlank(permissionEnabled) || permissionEnabled.equals("0")) {
    		respStr += "<td><input type=\"checkbox\" value=\"1\"></td>";
		} else {
			respStr += "<td><input type=\"checkbox\" value=\"1\" checked=\"checked\"></td>";
		}
    	return respStr;
    } 
    
    private String createFunctionsTableStr(Set<PermissionResponse> RolePermission) {
    	String functionsTable = "<tr>";
    	int index = 0;
    	for(PermissionResponse perm: RolePermission) {
    		if(index == 2) {
    			functionsTable += "</tr>";
    			functionsTable += "<tr>";
    			functionsTable += "<td>" + perm.getApiglfunctionid() + "</td>";
    			functionsTable += "<td>" + perm.getApiglfunctionshowname() + "</td>";
    			functionsTable += isEnabled(perm.getPermissionenabled());
    			index = 0;
    		} else {
    			functionsTable += "<td>" + perm.getApiglfunctionid() + "</td>";
    			functionsTable += "<td>" + perm.getApiglfunctionshowname() + "</td>";
    			functionsTable += isEnabled(perm.getPermissionenabled());
    		}
    		index +=1;
    	}
    	functionsTable += "</tr>";
    	return functionsTable;
    }
}
