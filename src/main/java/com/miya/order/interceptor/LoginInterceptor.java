package com.miya.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.golden.pojo.RespCode;
import com.golden.pojo.RespObject;
import com.golden.util.CookieUtils;
import com.miya.order.dto.UserOutputDto;
import com.miya.order.feign.SsoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * 判断用户是否登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
	
	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;
	@Value("${SSO_URL}")
	private String SSO_URL;

	@Autowired
	private SsoService ssoService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//执行handler之前先执行此方法
		//1.从cookie中取token信息
		String token = CookieUtils.getCookieValue(request, TOKEN_KEY);
		//2.如果取不到token，跳转到sso的登录页面，需要把当前请求的url做为参数传递给sso，sso登录成功之后跳转回请求的页面。
		if (StringUtils.isEmpty(token)) {
			//取当前请求的url
			String requestURL = request.getRequestURL().toString();
			String queryString = request.getQueryString();
			//跳转到登录页面
			response.sendRedirect(SSO_URL + "/user/showLogin?url=" + requestURL + "?" + queryString);
			//拦截
			return false;
		}
		//3.取到token，调用sso系统的服务判断用户是否登录
		RespObject<UserOutputDto> result = ssoService.getUserByToken(token);
		//4.如果用户未登录，即没取到用户信息。跳转到sso的登录页面
		if (!result.getResponseModal().getCode().equals(RespCode.OK.getKey())) {
			//取当前请求的url
			String requestURL = request.getRequestURL().toString();
			String queryString = request.getQueryString();
			//跳转到登录页面
			response.sendRedirect(SSO_URL + "/user/showLogin?url=" + requestURL + "?" + queryString);
 			//拦截
			return false;
		}
		//5.如果取到用户信息。放行。
		// 把用户信息放到request中
		UserOutputDto user = result.getData();
		request.setAttribute("userInfo", user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//handler执行之后，modelAndView返回之前

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 在ModelAndView返回之后，异常处理

	}

}
