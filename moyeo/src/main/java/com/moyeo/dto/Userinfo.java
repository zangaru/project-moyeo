package com.moyeo.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull; 
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
이름              널?       유형             
--------------- -------- -------------- 
ID              NOT NULL VARCHAR2(50)   
PW                       VARCHAR2(3000) 
NAME                     VARCHAR2(50)   
GENDER                   CHAR(1)        
EMAIL                    VARCHAR2(100)  
BIRTH                    DATE           
ADDRESS                  VARCHAR2(4000) 
PHONE                    VARCHAR2(50)   
REGDATE                  DATE           
STARTDATEPICKER          DATE           
ENDDATEPICKER            DATE           
LOGDATE                  DATE           
STATUS                   NUMBER         
ENABLED                  VARCHAR2(1)  

 * */

@Data
public class Userinfo {
   
   @NotBlank(message = "아이디를 입력해주세요.")
   @Pattern(regexp = "^[A-Za-z]{1}[A-Za-z0-9]{3,19}$",
   message = "아이디는 영문, 숫자만 가능하며 4 ~ 20자리까지 가능합니다.")
   private String id;
   
   @NotNull(message = "비밀번호를 입력해주세요.")
   @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$",
   message = "비밀번호는 8~16자의 영문 대소문자 숫자 및 특수문자를 최소 하나씩 입력해주세요.")
   private String pw;
   
   @NotNull(message = "이름을 입력해주세요.")
   @Pattern(regexp = "^[가-힣]{2,4}$",
   message = "2~4 글자의 한글만 가능합니다.") 
   private String name;
    
   @NotNull(message = "성별을 선택해주세요.")
   private String gender;
   
   @NotBlank(message = "이메일을 반드시 입력해주세요.")
   @Email(message = "이메일을 형식에 맞게 입력해주세요.")
   private String email;
   
   @NotNull(message = "생년월일을 입력해주세요.")
   @Pattern(regexp = "^(19[0-9]{2}|2000|20[0-2][0-9])-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$",
   message = "생년월일을 형식에 맞게 입력해주세요.")
   private String birth;
   
   @NotBlank(message = "우편번호 찾기를 선택해주세요.")
   private String address;
   
   @NotNull(message = "휴대폰번호를 입력해주세요.")
   @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$",
    message = "번호를 형식에 맞게 입력해주세요.")
   private String phone;
   
   private String regdate;
   private String logdate;
   private int status;
   
   private String enabled;
   private List<SecurityAuth> securityAuthList;
  
}