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
import com.ApiGL.service.MtpApiLogService;

@RestController
@RequestMapping(value = "/mtpApiLog")
public class MtpApiLogController {
	
	@Autowired
	MtpApiLogService mtpApiLogService;

	@GetMapping("/")
    public ModelAndView index() {
    	return new ModelAndView("mtpApiLog");
    }
	
	/**
	 * 依查詢條件查詢MtpApiLog Table資料
	 * @param session
	 * @param ConditionsRequest
	 * @return 排序及分頁後的MtpApiLog List
	 * @throws ParseException 
	 */
	@PostMapping(path = "/queryMtpApiLog", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> queryMtpApiLog(HttpSession session, @RequestBody ConditionsRequest conditionsRequest) throws ParseException {
		return mtpApiLogService.queryMtpApiLog(conditionsRequest);
    }
}
