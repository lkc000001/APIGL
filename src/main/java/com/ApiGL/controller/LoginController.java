package com.ApiGL.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ApiGL.config.EPortalServer;
import com.ApiGL.config.EportalHomePage;
import com.ApiGL.config.RootLogin;
import com.ApiGL.entity.ApiGLFunction;
import com.ApiGL.entity.User;
import com.ApiGL.exception.AuthCodeException;
import com.ApiGL.exception.EPortalErrorException;
import com.ApiGL.exception.LoginErrorException;
import com.ApiGL.exception.PermissionNullException;
import com.ApiGL.service.ApiGLPermissionService;
import com.ApiGL.service.ApiglFunctionService;
import com.ApiGL.service.ServiceUtil;
import com.ApiGL.service.UserService;
import com.ApiGL.util.AESUtil;
import com.ApiGL.util.ValidateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.formosoft.sso.tbb.Dept;
import com.formosoft.sso.tbb.Group;
import com.formosoft.sso.tbb.GroupCollection;
import com.formosoft.sso.tbb.Principal;
import com.formosoft.sso.tbb.SSOMgr;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ValidateUtil validateUtil;
	
	@Autowired
	AESUtil aesUtil;
	
	@Autowired
	EPortalServer ePortalServer;

	@Autowired
	EportalHomePage eportalHomePage;
	
	@Autowired
	RootLogin rootLogin;
	
	@Autowired
	ServiceUtil serviceUtil;
	
	@Autowired
	ApiGLPermissionService apiGLPermissionService;
	
	@Autowired
	ApiglFunctionService apiglFunctionService;

	@GetMapping("/")
    public String index(HttpSession session, HttpServletRequest request, Model model) {
		User user = (User) session.getAttribute("user");
		String eportalHomepage = eportalHomePage.getHomepage();
		session.setAttribute("eportalHomepage", eportalHomepage);
    	if(user == null) {
    		String ePortalAuthCode = request.getParameter("authCode");
			model.addAttribute("ePortalAuthCode", ePortalAuthCode);
			model.addAttribute("eportalHomepage", eportalHomepage);
			return "login";
    	} else {
    		String functionurl = "";
    		if(user.getGroupName().equals("DC")) {
        		List<ApiGLFunction> functions = apiglFunctionService.findByIsEnable();
        		if(functions.size() > 0) {
	    			functionurl = functions.get(0).getApiglFunctionUrl();
	    		}
        	} else {
        		functionurl = apiGLPermissionService.getFirstFunction(user.getAccount());
        	}
    		return "redirect:" + functionurl;
    	}
    }

    /**
     * 登入
     * @param req
     * @param user
     * @return
     * @throws JsonProcessingException 
     */
    @PostMapping(path = "/login")
    public ResponseEntity<?> login(HttpSession session, HttpServletRequest request, HttpServletResponse response,
    			@RequestBody User loginUser) {
    		ResponseEntity<?>  resp = null;
	    	int authErrorNum = request.getSession().getAttribute("authErrorNum") == null ? 0 : (Integer)request.getSession().getAttribute("authErrorNum");
			//判斷驗證碼
	    	String loginAuthCode = (String)session.getAttribute("authCode") == null ? "" : (String)session.getAttribute("authCode");
			if(!loginAuthCode.equals(loginUser.getUsercode())) {
				throw new AuthCodeException(" <BR>驗證碼錯誤", 400);
	    	}
			//登入認證
			//root登入
			String rootUser = rootLogin.getUser();
			String rootPwd = rootLogin.getMima();
			if(loginUser.getAccount().equals(rootUser)) {
				if(loginUser.getShapwd().equals(rootPwd)) {
		    		session.setAttribute("user", new User("A01", "DC", 999999, "root", "管理者", "1"));
		    		apiGLPermissionService.getPermissionAndNavbarStr(session, request);
		    		List<ApiGLFunction> functions = apiglFunctionService.findByIsEnable();
		    		String functionurl = "/systemSetup/";
		    		if(functions.size() > 0) {
		    			functionurl = functions.get(0).getApiglFunctionUrl();
		    		}
					return new ResponseEntity<Object>("{\"functionurl\": \"" + functionurl + "\" }", HttpStatus.OK);
		    	} else {
					session.setAttribute("authErrorNum", authErrorNum + 1);
		        	throw new LoginErrorException("登入失敗" + (authErrorNum + 1) + "次<BR>請確認帳號及密碼是否正確", 404);
			    }
			} else {
				/*User ssoUser = new User("B01", "DB", 66666, "user03", "USER03", "48FNeS9PhwtSLmvfXR0Vgw==");
				session.setAttribute("user", new User("B01", "DB", 66666, "user03", "USER03", "1"));
				apiGLPermissionService.getPermissionAndNavbarStr(session, request);
	    		List<ApiGLFunction> functions = apiglFunctionService.findByIsEnable();
	    		String functionurl = "/systemSetup/";
	    		if(functions.size() > 0) {
	    			functionurl = functions.get(0).getApiglFunctionUrl();
	    		}
				return new ResponseEntity<Object>("{\"functionurl\": \"" + functionurl + "\" }", HttpStatus.OK);
				*/
				session.setAttribute("authErrorNum", authErrorNum + 1);
	        	throw new LoginErrorException("登入失敗" + (authErrorNum + 1) + "次<BR>請確認帳號及密碼是否正確", 404);
			}
    }
    
    @PostMapping(path = "/ePortalLogin")
    public ResponseEntity<?> ePortalLogin(HttpSession session, HttpServletRequest request, HttpServletResponse response,
    			@RequestBody User loginUser) {
    	ResponseEntity<?>  resp = null;
    	//ePortal登入
		//檢查ePortal是否已是登入狀態
		SSOMgr ssoMgr = (SSOMgr) session.getAttribute("PortalObject");
		User ssoUser = new User();
		
		if(ssoMgr == null) {
			ssoUser = ePortalLogin(session, request, loginUser.getAuthcode());
		} else {
			ssoUser = getSsoUser(ssoMgr);
		}
    	//User ssoUser = new User("H16", "DB", 654321, "user02", "USER02", "48FNeS9PhwtSLmvfXR0Vgw==");
        if(validateUtil.isNotEmpty(ssoUser)) {
        	if(userService.findByisEnable(ssoUser.getAccount())) {
	        	session.setAttribute("user", ssoUser);
	        	apiGLPermissionService.getPermissionAndNavbarStr(session, request);
	        	
	        	String functionurl = "/systemSetup/";
	        	if(ssoUser.getGroupName().equals("DC")) {
	        		List<ApiGLFunction> functions = apiglFunctionService.findByIsEnable();
	        		functions.forEach(System.out::println);
	        		if(functions.size() > 0) {
		    			functionurl = functions.get(0).getApiglFunctionUrl();
		    		}
	        	} else {
	        		functionurl = apiGLPermissionService.getFirstFunction(ssoUser.getAccount());
	        	}
	        	resp = new ResponseEntity<Object>("{\"functionurl\": \"" + functionurl + "\" }", HttpStatus.OK);
        	} else {
        		throw new PermissionNullException("您尚未開通權限，請洽管理人員開通權限", 404);
        	}
        } else {
        	throw new LoginErrorException("登入失敗，無法取得EPortal使用者資訊", 404);
        }
        return resp;
    }
    /**
     * 取得AES KEY
     * @return String
     */
    @GetMapping(value = "/getKey")
    @ResponseBody
    public ResponseEntity<String> getKey() {
    	return new ResponseEntity<String>("{\"keystr\": \"ApiGuiLog2022Aes\", \"ivstr\": \"A3b97!azg3DB83En\"}", HttpStatus.OK);
    }
    
    /**
     * 取得驗證碼圖片
     * @param req
     * @param resp
     */
    @GetMapping("/createAuthCodeImage")
    public void createAuthCodeImage(HttpServletRequest req, HttpServletResponse resp) {

		String authCode = String.format("%04d", new Random().nextInt(10000));
		req.getSession().setAttribute("authCode", authCode);
		
		// 產生驗證碼圖片
		try {
			ImageIO.write(getAuthCodeImage(authCode), "JPEG", resp.getOutputStream());
		} catch (Exception e) {
			throw new AuthCodeException("產生驗證圖片錯誤!!!", 500);
		}
    }
    
    /**
     * 產生圖片
     * @param authCode
     * @return
     */
    private BufferedImage getAuthCodeImage(String authCode) {
		// 建立圖像暫存區
		BufferedImage img = new BufferedImage(80, 30, BufferedImage.TYPE_INT_RGB);
		// 建立畫布
		Graphics graphics = img.getGraphics();
		// 設定顏色
		graphics.setColor(Color.YELLOW);
		// 塗滿背景
		graphics.fillRect(0, 0, 80, 30);
		// 設定顏色
		graphics.setColor(Color.BLACK);
		// 設定字型(字體, 字樣, 尺寸)
		graphics.setFont(new Font("新細明體", Font.PLAIN, 30));
		// 繪文字
		graphics.drawString(authCode, 10, 23);
		// 放入干擾線
		Random random = new Random();
		graphics.setColor(Color.RED);
		for(int i=0;i<10;i++) {
			int x1 = random.nextInt(80);
			int y1 = random.nextInt(30);
			int x2 = random.nextInt(80);
			int y2 = random.nextInt(30);
			graphics.drawLine(x1, y1, x2, y2);
		}
		return img;
	}
    
    /**
     * ePortal 登入
     * @param session
     * @param request
     * @param AuthCode
     * @return
     */
    private User ePortalLogin(HttpSession session, HttpServletRequest request, String AuthCode) {
    	try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
    	String ePortal_URL = "http://10.16.22.109/TBBEPortalServlet/eportal"; 
    	String sAPIUser = "APIGL";
    	String sAPIPwd = "!QAZ2wsx";
    	String sAPName = "APIGL";

    	User ssoUser = null;
    	String sUserIP = request.getRemoteAddr();
    	SSOMgr ssomgr = new SSOMgr(ePortal_URL);
    	Principal prn = null;
    	int iRtn = ssomgr.init(sAPIUser, sAPIPwd, sAPName);
    	String sAuthCode = AuthCode;
    	
    	if (iRtn == 0) {
    		
    		if (ssomgr.checkLogon(sAuthCode, sUserIP)) {
    			ssoUser = new User();
    			prn = ssomgr.getPrincipal();
    			if (prn == null) {
    				//System.out.println(ssomgr.getLastError() + ",getPrincipal failed<br>");
    				throw new EPortalErrorException("getPrincipal failed。 <BR> " + ssomgr.getLastError() , 500);
    				//return ssoUser;
    			}
    			
    			// 將Portal API物件存於session中，
    			// 往後如有需要使用，直接由session中取用，不需再做checkLogon。
    			// 此步驟務必實作，才能持續原來Portal的session。
    			session.setAttribute("PortalObject", ssomgr);
    			ssoUser = getSsoUser(ssomgr);

    			// 如果想讓使用者用本系統後，還可以回到SSO系統，就不要call free，讓SSO系統的session可以一直keep
    			// ssomgr.free(); 
    		} else {
    			throw new EPortalErrorException("ePortal驗證失敗或SSO Session過期，請重新登入SSO。 <BR> ePortal錯誤代碼：: " + ssomgr.getLastError() , 500);
    		}
    		
    	} else {
    		throw new EPortalErrorException("AP初始化失敗。", 500);
    	}
    	return ssoUser;
    }
    
    /**
     * 取得行員資料
     * @param ssomgr
     * @return
     */
    private User getSsoUser(SSOMgr ssomgr) {
    	Principal prn = ssomgr.getPrincipal();
    	User ssoUser = new User();
    	
    	Dept dept = prn.getDept();
		ssoUser.setBranch(dept.getCode());

		// 取得行員所屬的群組
		GroupCollection gCol = prn.getGroups();
		if (gCol != null) {
			Iterator it = gCol.iterator();
			Group grp = null;
			while (it.hasNext()) {
				grp = (Group) it.next();
				ssoUser.setGroupName(grp.getName());
			}
		}
		ssoUser.setAccount(prn.getAccount());
		ssoUser.setAccountId(prn.getId());
		ssoUser.setName(prn.getName());
    	return ssoUser;
    }
}
