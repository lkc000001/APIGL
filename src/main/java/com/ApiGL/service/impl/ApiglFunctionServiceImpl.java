package com.ApiGL.service.impl;

import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ApiGL.entity.ApiGLFunction;
import com.ApiGL.entity.response.JSGridResponse;
import com.ApiGL.entity.response.JSGridReturnData;
import com.ApiGL.exception.QueryNoDataException;
import com.ApiGL.repositories.ApiglFunctionRepository;
import com.ApiGL.service.ApiGLPermissionService;
import com.ApiGL.service.ApiglFunctionService;
import com.ApiGL.service.ServiceUtil;
import com.ApiGL.util.DateTimtUtil;
import com.ApiGL.util.SessionUser;
import com.ApiGL.util.ValidateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;

@DS("DBAPIGL")
@Service
public class ApiglFunctionServiceImpl implements ApiglFunctionService {

	@Autowired
	ApiglFunctionRepository apiglFunctionRepository;
	
	@Autowired
	DateTimtUtil dateTimtUtil;
	
	@Autowired
	ServiceUtil serviceUtil;
	
	@Autowired
	ValidateUtil validateUtil;
	
	@Autowired
	SessionUser sessionUser;
	
	@Autowired
	ApiGLPermissionService apiGLPermissionService;
	
	/**
	 * ApiGLFunction Table依查詢條件查詢,依分頁及排序Response
	 * @param ConditionsRequest
	 * @return ResponseEntity
	 * @throws ParseException 
	 */
    @Override
	public ResponseEntity<?> queryApiglFunction(ApiGLFunction apiGLFunction) throws ParseException
	{
    	checkData(apiGLFunction);

		List<ApiGLFunction> apiGLFunctions = apiglFunctionRepository.queryApiglFunction(apiGLFunction.getApiglFunctionName(), apiGLFunction.getApiglFunctionShowName(), 
				apiGLFunction.getApiglFunctionUrl(), apiGLFunction.getEnabled(), apiGLFunction.getType(), 
				apiGLFunction.getPageIndex(), apiGLFunction.getPageSize());
		if(apiGLFunctions.size() > 0 ) {
		//排序
		List<ApiGLFunction> result = apiGLFunctions.stream()
			.sorted(apiGLFunctionSort(apiGLFunction.getSortField(), apiGLFunction.getSortOrder()))
			.collect(Collectors.toList());
		
			return new ResponseEntity<JSGridReturnData<ApiGLFunction>>(JSGridResponse.getResponseData(result, apiglFunctionRepository.count()), HttpStatus.OK);
		} else {
			throw new QueryNoDataException("查無資料!!!", 404);
		}
	}

    public List<ApiGLFunction> findAll() {
    	return apiglFunctionRepository.findAll();
    }
    
	/**
	 * 依apiglFunctionId查詢功能資料
	 * @param apiglFunctionId
	 * @return ApiGLFunction
	 */
    @Override
	public ApiGLFunction findByApiglFunctionId(Long apiglFunctionId) {
		return apiglFunctionRepository.findById(apiglFunctionId).get();
	}
	
    /**
     * 新增/修改
     * @param apiglFunctionId, func, session, request
	 * @return msg
     */
    @Override
	public String save(ApiGLFunction apiGLFunction, String func, HttpSession session, HttpServletRequest request) {
    	if(func.equals("新增")) {
    		apiGLFunction.setCreateTime(new Date());
    		apiGLFunction.setCreateUser(sessionUser.getUser().getAccount());
    	} else {
    		ApiGLFunction oldApiGLFunction = findByApiglFunctionId(apiGLFunction.getApiglFunctionId());
    		apiGLFunction.setCreateTime(oldApiGLFunction.getCreateTime());
    		apiGLFunction.setCreateUser(oldApiGLFunction.getCreateUser());
    		apiGLFunction.setUpdateTime(new Date());
    		apiGLFunction.setUpdateUser(sessionUser.getUser().getAccount());
    	}

    	if(validateUtil.isBlank(apiGLFunction.getEnabled())) {
    		apiGLFunction.setEnabled("0");
    	}

    	if(validateUtil.isIntegerNull(apiGLFunction.getApiglFunctionSort())) {
    		apiGLFunction.setApiglFunctionSort(0);
    	}
    	
    	ApiGLFunction apiGLFunctionResp = apiglFunctionRepository.save(apiGLFunction);
    	
    	if(validateUtil.isEmpty(apiGLFunctionResp)) {
    		return func + "功能資料失敗";
    	}
    	
    	apiGLPermissionService.getPermissionAndNavbarStr(session, request);
    	return func +"功能資料成功";
	}
    
    @Override
	public void delete(Long id) {
    	apiglFunctionRepository.deleteById(id);
	}
    
    @Override
    public List<ApiGLFunction> findByIsEnable() {
    	return apiglFunctionRepository.findByIsEnable();
    }
	/**
	 * 排序欄位設置
	 * @param sortField
	 * @param sortOrder
	 * @return Comparator<ApiGLFunction>
	 */
	private Comparator<ApiGLFunction> apiGLFunctionSort(String sortField, String sortOrder) {
		switch(sortField) {
		case "apiglFunctionId":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(ApiGLFunction::getApiglFunctionId) : 
						Comparator.comparing(ApiGLFunction::getApiglFunctionId).reversed();
		case "apiglFunctionName":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(ApiGLFunction::getApiglFunctionName) : 
						Comparator.comparing(ApiGLFunction::getApiglFunctionName).reversed();
		case "apiglFunctionShowName":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(ApiGLFunction::getApiglFunctionShowName) : 
						Comparator.comparing(ApiGLFunction::getApiglFunctionShowName).reversed();
		case "apiglFunctionUrl":
			return sortOrder.equals("asc") ? 
					Comparator.comparing(ApiGLFunction::getApiglFunctionUrl) : 
						Comparator.comparing(ApiGLFunction::getApiglFunctionUrl).reversed();
		default:
			return sortOrder.equals("asc") ? 
				Comparator.comparing(ApiGLFunction::getApiglFunctionSort) : 
					Comparator.comparing(ApiGLFunction::getApiglFunctionSort).reversed();
		}
	}
	
	/**
	 * 確認欄位資料
	 * @param apiGLFunction
	 */
	private void checkData(ApiGLFunction apiGLFunction) {
		if(validateUtil.isBlank(apiGLFunction.getSortField())) {
    		apiGLFunction.setSortField("sort");
    	}
    	if(validateUtil.isBlank(apiGLFunction.getSortOrder())) {
    		apiGLFunction.setSortOrder("asc");
    	}
    	if(validateUtil.isNotBlank(apiGLFunction.getApiglFunctionName())) {
    		apiGLFunction.setApiglFunctionName("%" + apiGLFunction.getApiglFunctionName() + "%");
    	}
    	if(validateUtil.isNotBlank(apiGLFunction.getApiglFunctionShowName())) {
    		apiGLFunction.setApiglFunctionShowName("%" + apiGLFunction.getApiglFunctionShowName() + "%");
    	}
    	if(validateUtil.isNotBlank(apiGLFunction.getApiglFunctionUrl())) {
    		apiGLFunction.setApiglFunctionUrl("%" + apiGLFunction.getApiglFunctionUrl() + "%");
    	}
	}
}
