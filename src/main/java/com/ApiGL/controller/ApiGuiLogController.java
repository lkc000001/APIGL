package com.ApiGL.controller;

import java.text.ParseException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ApiGL.entity.request.ConditionsRequest;
import com.ApiGL.service.ApiGuiLogService;
import com.ApiGL.service.SmsOtpLogService;

@Controller
@RequestMapping(value = "/apiGuiLog")
public class ApiGuiLogController {
	
	@Autowired
	ApiGuiLogService apiGuiLogService;
	
	@GetMapping("/")
    public String index() {
    	return "apiGuiLog";
    }
	
	/**
	 * 依查詢條件查詢ApiGuiLog Table資料
	 * @param session
	 * @param ConditionsRequest
	 * @return 排序及分頁後的ApiGuiLog List
	 * @throws ParseException 
	 */
	@PostMapping(path = "/queryApiGuiLog", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> queryApiGuiLog(HttpSession session, @RequestBody ConditionsRequest conditionsRequest) throws ParseException {
		return apiGuiLogService.queryApiGuiLog(conditionsRequest);
    }
}
