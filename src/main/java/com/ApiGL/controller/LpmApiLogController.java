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
import com.ApiGL.service.LpmApiLogService;

@RestController
@RequestMapping(value = "/lpmApiLog")
public class LpmApiLogController {
	
	@Autowired
	LpmApiLogService lpmApiLogService;
	
	@GetMapping("/")
    public ModelAndView index() {
    	return new ModelAndView("lpmApiLog");
    }
	
	/**
	 * 依查詢條件查詢LpmApiLog Table資料
	 * @param session
	 * @param conditionsRequest
	 * @return 排序及分頁後的List<LpmApiLog>
	 * @throws ParseException 
	 */
	@PostMapping(path = "/queryLpmApiLog", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> queryLpmApiLog(HttpSession session, @RequestBody ConditionsRequest conditionsRequest) throws ParseException {
		return lpmApiLogService.queryLpmApiLog(conditionsRequest);
    }	
}
