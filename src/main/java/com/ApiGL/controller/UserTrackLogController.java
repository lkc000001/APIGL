package com.ApiGL.controller;

import java.text.ParseException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ApiGL.entity.request.ConditionsRequest;
import com.ApiGL.service.TwPayLineBindLogService;
import com.ApiGL.service.UserTrackLogService;

@RestController
@RequestMapping(value = "/userTrackLog")
public class UserTrackLogController {
	
	@Autowired
	UserTrackLogService userTrackLogService;
	
	@GetMapping("/")
    public ModelAndView index() {
    	return new ModelAndView("userTrackLog");
    }
	
	/**
	 * 依查詢條件查詢UserTrackLog Table資料
	 * @param ConditionsRequest
	 * @return 排序及分頁後的UserTrackLog
	 * @throws ParseException 
	 */
	@PostMapping(path = "/queryUserTrackLog", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> queryUserTrackLog(@RequestBody ConditionsRequest conditionsRequest) throws ParseException {
		return userTrackLogService.queryUserTrackLog(conditionsRequest);
    }
	

}
