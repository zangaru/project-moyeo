package com.moyeo.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.moyeo.dto.Userinfo;
import com.moyeo.exception.BadRequestException;


public class LoginAuthInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session=request.getSession();
		
		Userinfo loginUserinfo=(Userinfo)session.getAttribute("loginUserinfo");
		
		//비로그인 사용자인 경우
		if(loginUserinfo == null) {
			throw new BadRequestException("비정상적인 요청입니다.");
		}
		
		return true;//요청 처리 메소드 호출
	}
}