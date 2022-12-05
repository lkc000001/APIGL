package com.ApiGL.aspect;

import java.io.IOException;
import java.net.ConnectException;
import java.text.ParseException;
import java.util.Date;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;

import org.apache.poi.EncryptedDocumentException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.ApiGL.entity.ApiGuiLog;
import com.ApiGL.entity.User;
import com.ApiGL.entity.UserTrackLog;
import com.ApiGL.entity.response.ErrorMsg;
import com.ApiGL.exception.AesException;
import com.ApiGL.exception.AuthCodeException;
import com.ApiGL.exception.EPortalErrorException;
import com.ApiGL.exception.LoginErrorException;
import com.ApiGL.exception.PermissionNullException;
import com.ApiGL.exception.QueryNoDataException;
import com.ApiGL.exception.TimeFormatException;
import com.ApiGL.service.ApiGuiLogService;
import com.ApiGL.service.UserTrackLogService;
import com.ApiGL.util.ValidateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
@Aspect
@Order(1)
public class ApiGuiLogAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	HttpSession session;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	ValidateUtil validateUtil;
	
	@Autowired
	ApiGuiLogService apiGuiLogService;
	
	@Autowired
	UserTrackLogService userTrackLogService;

	@Pointcut(value = "execution(* com.ApiGL.controller.*.*(..)) && " +
					  "!execution(* com.ApiGL.controller.LoginController.createAuthCodeImage(..)) && " + 
					  "!execution(* com.ApiGL.controller.LoginController.getKey(..)) && " + 
					  "!execution(* com.ApiGL.controller.LoginController.index(..)) && " + 
					  "!execution(* com.ApiGL.controller.ApiGLController.*(..)) && " + 
					  "!execution(* com.ApiGL.controller.ShowDetailedController.*(..))")
	public void apiGuiLogpt() {}
	
	@Pointcut(value = "execution(* com.ApiGL.controller.ShowDetailedController.*(..))")
	public void showDetailedpt() {}
	
	@Pointcut(value = "execution(* com.ApiGL.controller.*.*(..)) && " +  
			  "!execution(* com.ApiGL.controller.ApiGLController.*(..))")
	public void controllerpt() {}
	
	@Pointcut(value = "execution(* org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate.*(..))")  
	//@Pointcut(value = "execution(* org.springframework.jdbc.core.JdbcOperations.*(..))")
	public void sqlLogpt() {}
	
    /**
     * 抓取request 記錄log
     * @param joinPoint
     */
	@Before(value = "apiGuiLogpt() || showDetailedpt()")
	public void requestLog(JoinPoint joinPoint) {
		int lastPointIndex = joinPoint.getSignature().getDeclaringTypeName().lastIndexOf(".") + 1; 
        String className = joinPoint.getSignature().getDeclaringTypeName().substring(lastPointIndex).replace("Controller", "");
		String methName = joinPoint.getSignature().getName();
		String data = "";
		//System.out.println(joinPoint.getTarget().getClass().getName());
		Object[] joinPointArgs = joinPoint.getArgs();
		if(joinPointArgs.length > 0) {
			try {
				System.out.println(methName);
				//判斷method取得request boby的方式
				if(methName.equals("doShowDetailed")) {
					//data = objectMapper.writeValueAsString(joinPointArgs[0]);
					data = "{\"queryId\":" + joinPointArgs[0] + 
							",\"queryTable\":\"" + joinPointArgs[1] + "\"}";
				} else if(methName.equals("ePortalLogin")) {
					data = "ePortal登入";
				} else if(methName.equals("roleUpload") || methName.equals("functionUpload") || methName.equals("userUpload")) {
					data = "上傳檔案";	
				} else {
					//判斷是否為登入,如果是登入把PWD隱藏
					if(methName.equals("login")) {
						User requestuser = (User) ((User) joinPointArgs[joinPointArgs.length-1]).clone();
						requestuser.setPwd("XXXXX");
						data = objectMapper.writeValueAsString(requestuser);
					} else if (methName.equals("logout")) {
						data = "登出";
					} else {
						data = objectMapper.writeValueAsString(joinPointArgs[joinPointArgs.length-1]);
					}
				}
			} catch (IOException | CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		createLog(data, "", className + " / " + methName, "1", "3");	
	}
	
	/**
	 * 抓取sql語法 記錄log
	 * @param joinPoint
	 */
	@Before(value = "sqlLogpt()")
	public void sqlLog(JoinPoint joinPoint) {
		Object[] methodArgs = joinPoint.getArgs();
		if(methodArgs.length > 0) {
			
			String data = methodArgs[0].toString().replace("\"", "");
			if(methodArgs.length>1) {
				//System.out.println("SQL data: " + data);
				if(!data.contains("APIGUILog") && !data.contains("USERTRACKLog")) {
					//createLog(data, "", "SQL", "1", "4");
				}
			}
			
				
		}
	}
	
	/**
	 * Exception處理
	 * @param proceedingJoinPoint
	 * @return
	 */
	@Around(value = "controllerpt()")
	public Object sessionAndExceptionCont(ProceedingJoinPoint proceedingJoinPoint) {
		String methodName = proceedingJoinPoint.getSignature().getName();
		Object result = null;
		try {
			result = proceedingJoinPoint.proceed();
		} catch (LoginErrorException e) {
			createLog(e.getMessage(), e.getCode().toString(), methodName, "2", "2");
			result = new ResponseEntity<ErrorMsg>(new ErrorMsg(e.getCode(),  e.getMessage() + "!!! <BR>"), HttpStatus.NOT_FOUND);
		} catch (AuthCodeException e) {
			String logType = "2";
			if(e.getCode() == 500) {
				logType = "1";
			}
			createLog(e.getMessage(), e.getCode().toString(), methodName, "2", logType);
			result = new ResponseEntity<ErrorMsg>(new ErrorMsg(e.getCode(),  e.getMessage() + "!!! <BR>"), HttpStatus.BAD_REQUEST);
		} catch (TimeFormatException e) {
			createLog(e.getMessage(), e.getCode().toString(), methodName, "2", "2");
			result = new ResponseEntity<ErrorMsg>(new ErrorMsg(e.getCode(),  e.getMessage() + "!!! <BR>"), HttpStatus.BAD_REQUEST);
		} catch (QueryNoDataException e) {
			createLog(e.getMessage(), e.getCode().toString(), methodName, "2", "2");
			result = new ResponseEntity<ErrorMsg>(new ErrorMsg(e.getCode(), e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (PermissionNullException e) {
			createLog(e.getMessage(), "400", methodName, "2", "2");
			result = new ResponseEntity<ErrorMsg>(new ErrorMsg(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (ParseException e) {
			createLog(e.getMessage(), "400", methodName, "2", "1");
			result = new ResponseEntity<ErrorMsg>(new ErrorMsg(400, "日期格式轉換錯誤!!! <BR>"), HttpStatus.BAD_REQUEST);
		} catch (NumberFormatException e) {
			createLog(e.getMessage(), "500", methodName, "2", "2");
			result = new ResponseEntity<ErrorMsg>(new ErrorMsg(400, "會員帳號必須為整數!!! <BR>"), HttpStatus.BAD_REQUEST);
		} catch (ConnectException e) {
			createLog(e.getMessage(), "500", methodName, "2", "1");
			result = new ResponseEntity<ErrorMsg>(new ErrorMsg(500, "Connection timed out <BR> "), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (EPortalErrorException | AesException e) {
			createLog(e.getMessage(), "500", methodName, "2", "1");
			result = new ResponseEntity<ErrorMsg>(new ErrorMsg(500, e.getMessage() + " <BR> "), HttpStatus.BAD_REQUEST);
		} catch (NoSuchElementException e) {
			createLog(e.getMessage(), "500", methodName, "2", "1");
			result = new ResponseEntity<ErrorMsg>(new ErrorMsg(500, e.getMessage() + " <BR> "), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (EncryptedDocumentException e) {
			createLog(e.getMessage(), "500", methodName, "2", "1");
			result = new ResponseEntity<ErrorMsg>(new ErrorMsg(500, "上傳格式錯誤!!! <BR>"), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			createLog(e.getMessage(), "500", methodName, "2", "1");
			result = new ResponseEntity<ErrorMsg>(new ErrorMsg(500, "上傳失敗!!! <BR>"), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbActionExecutionException e) {
			createLog(e.getMessage(), "500", methodName, "2", "1");
			result = new ResponseEntity<ErrorMsg>(new ErrorMsg(500, "上傳格式錯誤!!! <BR>"), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Throwable e) {
			e.printStackTrace();
			createLog(e.getMessage(), "500", methodName, "2", "1");
			result = new ResponseEntity<ErrorMsg>(new ErrorMsg(500,  "系統錯誤!!! <BR>" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}
	
	
	/**
	 * 抓取Response 記錄log
	 * @param joinPoint
	 * @param retVal
	 */
	@AfterReturning(value = "apiGuiLogpt()", returning="retVal")
	public void logResponse(JoinPoint joinPoint, ResponseEntity<?> retVal) {
		int lastPointIndex = joinPoint.getSignature().getDeclaringTypeName().lastIndexOf(".") + 1; 
        String className = joinPoint.getSignature().getDeclaringTypeName().substring(lastPointIndex).replace("Controller", "");
		String methName = joinPoint.getSignature().getName();
		String returnCode = "200";
		String data = "";
		try {
			if (retVal != null) {
				returnCode = String.valueOf(retVal.getStatusCodeValue());
				data = objectMapper.writeValueAsString(retVal.getBody());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		createLog(data, returnCode, className + " / " + methName, "2", "3");
	}
	
	/**
	 * 抓取Detailed Response 記錄log
	 * @param joinPoint
	 * @param retVal
	 */
	@AfterReturning(value = "showDetailedpt()", returning="retVal")
	public void detailedResponseLog(JoinPoint joinPoint, ModelAndView retVal) {
		String className = "";
		String data = "";
		String methName = joinPoint.getSignature().getName();
		String returnCode = String.valueOf(retVal.getStatus().value());
		if(retVal.getModel().get("errorMsg") == null) {
			className = retVal.getModel().get("queryTable").toString();
			data = retVal.getModel().get("detailedresp").toString().replace("\\", "").replace("\"{", "{").replace("}\"", "}");

			//移除非必要顯示欄位
			try {
				ObjectNode dataJsonNode = (ObjectNode) objectMapper.readTree(data);
				dataJsonNode.remove("pageIndex");
				dataJsonNode.remove("pageSize");
				dataJsonNode.remove("sortField");
				dataJsonNode.remove("sortOrder");
				dataJsonNode.remove("showDate");
				dataJsonNode.remove("showTime");
				data = dataJsonNode.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		createLog(data, returnCode, className + " / " + methName, "2", "3");
	}
	
	public void createLog(String data, String returnCode, String methodName, 
			   String sendStatus, String logType) {
		User user = (User) session.getAttribute("user");
		String userid = user == null ? "" : user.getAccountId().toString();
		
		ApiGuiLog apiGuiLog = new ApiGuiLog(userid, data, sendStatus, returnCode,
			methodName, logType, new Date());
		
		String outLog = "";
		if(sendStatus.equals("1")) {
			outLog ="request: " + apiGuiLog;
			if(!logType.equals("4")) {
				Integer userAccountId = user == null ? null : user.getAccountId();
				String userAccount = user == null ? "" : user.getAccount().toString();
				String userName = user == null ? "" : user.getName().toString();
				
				if(validateUtil.isNotNumNone(userAccountId)) {
					UserTrackLog userTrackLog = new UserTrackLog(userAccountId, userAccount, 
									userName, data, methodName, new Date());
					userTrackLogService.save(userTrackLog);
				}
			}
		} else {
			outLog = "response: " + apiGuiLog;
		}
		switch(logType) {
		case "1":
		if(logger.isErrorEnabled()) {
			logger.error(outLog);
		}
		break;
		case "2":
		if(logger.isWarnEnabled()) {
			logger.warn(outLog);
		}
		break;
		case "3":
		if(logger.isInfoEnabled()) {
			logger.info(outLog);
		}
		break;
		case "4":
		if(logger.isDebugEnabled()) {
			logger.debug(outLog);
		}
		break;
		}
		apiGuiLogService.save(apiGuiLog);
	}
	
}
