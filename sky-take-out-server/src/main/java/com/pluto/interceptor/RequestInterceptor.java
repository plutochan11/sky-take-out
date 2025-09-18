package com.pluto.interceptor;

import com.pluto.context.RequestContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class RequestInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Integer requestUserId = null;
		try {
			requestUserId = Integer.valueOf(request.getHeader("request-user-id"));
		} catch (NumberFormatException e) {
			// Continue execution without setting requestUserId
			return true;
		}
		RequestContext.setRequestUserId(requestUserId);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		RequestContext.clear();
	}
}
