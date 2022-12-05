package com.ApiGL.controller;

import java.util.Iterator;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ApiGL.entity.request.ConditionsRequest;
import com.ApiGL.service.ApiGuiLogService;
import com.ApiGL.service.Hpg5000ApiLogService;
import com.ApiGL.service.IcpApiLogService;
import com.ApiGL.service.LpmApiLogService;
import com.ApiGL.service.MtpApiLogService;
import com.ApiGL.service.RewardApiLogService;
import com.ApiGL.service.SmsOtpLogService;
import com.ApiGL.service.TwPayLineBindLogService;
import com.ApiGL.service.UserTrackLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/showDetailed")
public class ShowDetailedController {
	
	@Autowired
	LpmApiLogService lpmApiLogService;
	
	@Autowired
	TwPayLineBindLogService twPayLineBindLogService;
	
	@Autowired
	RewardApiLogService rewardAPILogService;
	
	@Autowired
	IcpApiLogService icpAPILogService;
	
	@Autowired
	MtpApiLogService mtpAPILogService;
	
	@Autowired
	Hpg5000ApiLogService hpg5000ApiLogService;
	
	@Autowired
	SmsOtpLogService smsOtpLogService;
	
	@Autowired
	ApiGuiLogService apiGuiLogService;
	
	@Autowired
	UserTrackLogService userTrackLogService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	/**
	 * 依table查詢祥細資料
	 * @param ConditionsRequest
	 * @param model
	 * @return ModelAndView(回傳查詢資料及轉導到showDetailed.jsp)
	 */
	@PostMapping(path = "/", produces = "application/json")
	public ModelAndView doShowDetailed(@RequestParam(name = "queryId") Long queryId, @RequestParam(name = "queryTable") String queryTable) {
		
		ModelAndView mv = new ModelAndView();
		//Long queryId = conditionsRequest.getQueryId();
		//String queryTable = conditionsRequest.getQueryTable();
		Object respTable = null;
		try {
			
			switch(queryTable) {
			case "LpmApiLog":
				respTable = lpmApiLogService.findByLpmid(queryId);
				break;
			case "TwPayLineBindLog":
				respTable = twPayLineBindLogService.findBytwPayLineBindLogId(queryId);
				break;
			case "RewardApiLog":
				respTable = rewardAPILogService.findByrewardId(queryId);
				break;
			case "IcpApiLog":
				respTable = icpAPILogService.findByicpId(queryId);
				break;
			case "MtpApiLog":
				respTable = mtpAPILogService.findBymtpLId(queryId);
				break;
			case "Hpg5000ApiLog":
				respTable = hpg5000ApiLogService.findByhpgLogId(queryId);
				break;
			case "SmsOtpLog":
				respTable = smsOtpLogService.findBySolId(queryId);
				break;
			case "ApiGuiLog":
				respTable = apiGuiLogService.findByApiGuiId(queryId);
				break;
			case "UserTrackLog":
				respTable = userTrackLogService.findByUserTrackId(queryId);
				break;	
			}
			
			mv.setStatus(HttpStatus.OK);
			mv.setViewName("showDetailed");
			mv.addObject("queryTable", queryTable);
			mv.addObject("detailedresp", objectMapper.writeValueAsString(respTable).replace("\\", "\\\\"));
			//throw new Exception();
		} catch (JsonProcessingException e) {
			mv.setStatus(HttpStatus.BAD_REQUEST);
			mv.setViewName("error");
			mv.addObject("errorMsg", "Json格式轉換錯誤!!! <BR>");
		} catch (Exception e) {
			e.printStackTrace();
			mv.setStatus(HttpStatus.BAD_REQUEST);
			mv.setViewName("error");
			mv.addObject("errorMsg", "查詢失敗!!! <BR>");
		}
		return mv;
    }
}
