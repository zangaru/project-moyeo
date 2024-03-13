package com.moyeo.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
	        AuthenticationException exception) throws IOException, ServletException {

	    String errorMessage = "ID와 PW가 올바르지 않습니다."; 
	    
	    if (exception instanceof BadCredentialsException) {
	        errorMessage = "ID와 PW가 올바르지 않습니다.";
	    } else if (exception instanceof DisabledException) {
	        errorMessage = "계정이 사용 불가합니다.";
	    } 

	    request.setAttribute("loginError", errorMessage);
	    
	    // 사용자 ID를 세션에 저장
	    request.getSession().setAttribute("userid", request.getParameter("userid"));
	    
	    // 실패 URL로 포워딩
	    request.getRequestDispatcher("/user/login").forward(request, response);
	}

}