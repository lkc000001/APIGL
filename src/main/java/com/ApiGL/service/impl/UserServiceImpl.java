package com.ApiGL.service.impl;

import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ApiGL.config.EPortalServer;
import com.ApiGL.entity.User;
import com.ApiGL.entity.response.JSGridResponse;
import com.ApiGL.entity.response.JSGridReturnData;
import com.ApiGL.exception.QueryNoDataException;
import com.ApiGL.repositories.UserRepository;
import com.ApiGL.service.UserService;
import com.ApiGL.util.SessionUser;
import com.ApiGL.util.ValidateUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	EPortalServer ePortalServer;

	@Autowired
	ValidateUtil validateUtil;
	
	@Autowired
	SessionUser sessionUser;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public User getUser(String account) {
    	User user = new User();
    	if(account.equals("admin")) {
    		user.setBranch("A01");
    		user.setGroupName("DC");
    		user.setAccountId(99999);
    		user.setAccount("admin");
    		user.setName("管理者");
    		user.setPwd("48FNeRZ1gQ1UKG3ZWxsThQ==");
    	}
    	
    	if(account.equals("user01")) {
    		user.setBranch("H15");
    		user.setGroupName("DC");
    		user.setAccountId(12345);
    		user.setAccount("user01");
    		user.setName("USER01");
    		user.setPwd("48FNeS9PhwtSLmvfXR0Vgw==");
    	}
    	
    	if(account.equals("user02")) {
    		user.setBranch("H16");
    		user.setGroupName("DB");
    		user.setAccountId(654321);
    		user.setAccount("user02");
    		user.setName("USER02");
    		user.setPwd("48FNeS9PhwtSLmvfXR0Vgw==");
    	}
    	
    	if(account.equals("user03")) {
    		user.setBranch("H88");
    		user.setGroupName("DB");
    		user.setAccountId(88888);
    		user.setAccount("user03");
    		user.setName("USER03");
    		user.setPwd("48FNeS9PhwtSLmvfXR0Vgw==");
    	}
		return user;
	}
	
	/**
	 * User Table依查詢條件查詢,依分頁及排序Response
	 * @param ConditionsRequest
	 * @return ResponseEntity
	 * @throws ParseException 
	 */
    @Override
	public ResponseEntity<?> queryUserPermission(User user) throws ParseException {
    	
    	checkData(user);

		List<User> Users = userRepository.queryUserPermission(user.getBranch(), user.getGroupName(), 
				user.getAccountId(), user.getAccount(), user.getName(), user.getEnabled());
		if(Users.size() > 0 ) {
			//分頁,排序
			int pageSize = user.getPageSize();
			List<User> result = Users.stream()
				.sorted(userPermissionSort(user.getSortField(), user.getSortOrder()))
				.skip(pageSize * (user.getPageIndex() - 1))
				.limit(pageSize)
				.collect(Collectors.toList());

			return new ResponseEntity<JSGridReturnData<User>>(JSGridResponse.getResponseData(result, Users.size()), HttpStatus.OK);
		} else {
			throw new QueryNoDataException("查無資料!!!", 404);
		}
    	//return new ResponseEntity(HttpStatus.OK);
	}
    
    @Override
    public User findByUserId(Long userId) {
    	return userRepository.findById(userId).get();
    }
   
    @Override
	public String save(User user, String func) {
    	boolean isEnabled = true;
    	if(func.equals("新增")) {
    		System.out.println(user);
    		user.setCreateTime(new Date());
    		user.setCreateUser(sessionUser.getUser().getAccount());
    		User oldUser =  userRepository.findByAccount(user.getAccount());
    		System.out.println(oldUser);
    		if(validateUtil.isNotEmpty(oldUser)) {
    			isEnabled = false;
    		}
    	} else {
    		User oldUser = findByUserId(user.getUserId());
    		user.setCreateTime(oldUser.getCreateTime());
    		user.setCreateUser(oldUser.getCreateUser());
    		user.setUpdateTime(new Date());
    		user.setUpdateUser(sessionUser.getUser().getAccount());
    	}

    	if(validateUtil.isBlank(user.getEnabled())) {
    		user.setEnabled("0");
    	}
    	User userResp = null;
    	if(isEnabled) {
    		userResp = userRepository.save(user);
    	} else {
    		return func + "使用者資料失敗，帳號已存在";
    	}
    	
    	if(validateUtil.isEmpty(userResp)) {
    		return func + "使用者資料失敗";
    	}
    	
    	return func +"使用者資料成功";
	}
    
    @Override
    public Boolean findByisEnable(String account) {
    	User user = userRepository.findByisEnable(account);
    	return user == null ? false : true;
	}
	
    /**
	 * 排序欄位設置
	 * @param sortField
	 * @param sortOrder
	 * @return Comparator<ApiGLRole>
	 */
	private Comparator<User> userPermissionSort(String sortField, String sortOrder) {
		switch(sortField) {
		case "branch":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(User::getBranch) : 
						Comparator.comparing(User::getBranch).reversed();
		case "accountid":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(User::getAccountId) : 
						Comparator.comparing(User::getAccountId).reversed();
		case "account":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(User::getAccount) : 
						Comparator.comparing(User::getAccount).reversed();
		case "name":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(User::getName) : 
						Comparator.comparing(User::getName).reversed();	
		case "groupname":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(User::getGroupName) : 
						Comparator.comparing(User::getGroupName).reversed();		
		default:
			return sortOrder.equals("asc") ? 
				Comparator.comparing(User::getUserId) : 
					Comparator.comparing(User::getUserId).reversed();
		}
	}
	
	/**
	 * 判斷是否有無資料,另外處理
	 * @param apiGLRole
	 */
	private void checkData(User user) {
		if(validateUtil.isBlank(user.getSortField())) {
			user.setSortField("sort");
    	}
    	if(validateUtil.isBlank(user.getSortOrder())) {
    		user.setSortOrder("asc");
    	}
    	if(validateUtil.isNotBlank(user.getName())) {
    		user.setName("%" + user.getName() + "%");
    	}
	}
	
	private String getIpAdrress(HttpServletRequest request) {
		String Xip = request.getHeader("X-Real-IP");
		String XFor = request.getHeader("X-Forwarded-For");

		if(validateUtil.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
			int index = XFor.indexOf(",");
			if(index != -1){
				return XFor.substring(0,index);
			}else{
				return XFor;
			}
		}
		XFor = Xip;
		if(validateUtil.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
			return XFor;
		}
		if (validateUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("Proxy-Client-IP");
		}
		if (validateUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("WL-Proxy-Client-IP");
		}
		if (validateUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("HTTP_CLIENT_IP");
		}
		if (validateUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (validateUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getRemoteAddr();
		}
		if (XFor.equals("0:0:0:0:0:0:0:1")) {
			XFor = "127.0.0.1";
		}
		return XFor;
	}
		
}
