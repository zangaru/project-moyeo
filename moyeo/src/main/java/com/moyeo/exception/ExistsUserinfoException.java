package com.moyeo.exception;

import com.moyeo.dto.Userinfo;


import lombok.Getter;

@Getter
public class ExistsUserinfoException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private Userinfo userinfo; //사용자로부터 입력받은 회원정보를 저장

	public ExistsUserinfoException() {
		// TODO Auto-generated constructor stub
	}
	
	public ExistsUserinfoException(String message, Userinfo userinfo) {
		super(message);
		this.userinfo=userinfo;
	}
}
