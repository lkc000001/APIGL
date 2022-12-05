package com.ApiGL.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import com.ApiGL.entity.PermissionResponse;
import com.ApiGL.entity.User;
import com.ApiGL.entity.response.ErrorMsg;
import com.ApiGL.repositories.UserFunctionRepository;
import com.ApiGL.service.ApiGLPermissionService;
import com.ApiGL.service.impl.ApiGLPermissionServiceImpl;

@WebFilter(filterName = "sessionFilter", 
		   urlPatterns = {"/lpmApiLog/*", "/twPayLineBindLog/*", "/rewardApiLog/*", "/icpApiLog/*", 
				   		  "/mtpApiLog/*", "/hpg5000ApiLog/*", "/smsOtpLog/*", "/apiGuiLog/*",
				   		  "/userTrackLog/*", "/showDetailed/", "/apiGLFunction/*", "/apiGLRole/*", 
				   		  "/systemSetup/*", "/apiGLPermission/*", "/userPermission/*"})
public class sessionFilter implements Filter{
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		String uri = request.getRequestURI();
		
		User user = (User) session.getAttribute("user");
		if(user == null) {
			session.setAttribute("loginMessage", "您尚未登入，或者閒置超過30分鐘。<BR>請按確認鍵或3秒後自動轉至登入頁面，重新登入!!");
			response.sendRedirect(request.getContextPath() + "/erroe");
		} else {
			if(!user.getGroupName().equals("DC")) {
				String function = session.getAttribute("navbarFunction").toString();
				if(!function.contains(uri.split("/")[1])) {
					session.setAttribute("loginMessage", "您尚未有此功能之權限。<BR>如需開通權限，請洽管理人員。<BR>請按確認鍵或3秒後自動轉至起始頁面。");
					response.sendRedirect(request.getContextPath() + "/erroe");
				}
			} 
			filterChain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {

	}	
}
