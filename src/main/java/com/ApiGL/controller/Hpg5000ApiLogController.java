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
import com.ApiGL.service.Hpg5000ApiLogService;

@RestController
@RequestMapping(value = "/hpg5000ApiLog")
public class Hpg5000ApiLogController {
	
	@Autowired
	Hpg5000ApiLogService hpg5000ApiLogService;
	
	@GetMapping("/")
    public ModelAndView index() {
    	return new ModelAndView("hpg5000ApiLog");
    }
	
	/**
	 * 依查詢條件查詢Hpg5000ApiLog Table資料
	 * @param session
	 * @param ConditionsRequest
	 * @return 排序及分頁後的Hpg5000ApiLog List
	 * @throws ParseException 
	 */
	@PostMapping(path = "/queryHpg5000ApiLog", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> queryHpg5000ApiLog(HttpSession session, @RequestBody ConditionsRequest conditionsRequest) throws ParseException {
		return hpg5000ApiLogService.queryHpg5000ApiLog(conditionsRequest);
    }
}
