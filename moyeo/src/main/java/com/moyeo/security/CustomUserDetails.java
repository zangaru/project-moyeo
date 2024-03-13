package com.moyeo.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.moyeo.dto.SecurityAuth;
import com.moyeo.dto.Userinfo;

import lombok.Data;


@Data
public class CustomUserDetails implements UserDetails {
   private static final long serialVersionUID = 1L;
   
   // 인증된 사용자의 정보를 저장할 필드를 선언
   private String id;
   private String pw;
   private String name;
   private String gender;
   private String email;
   private String birth;
   private String address;
   private String phone;
   private String regdate;
   private String logdate;
   private int status;

   
   private String enabled;

   private List<GrantedAuthority> userinfoAuthList;

   public CustomUserDetails(Userinfo userinfo) {
      this.id = userinfo.getId();
      this.pw = userinfo.getPw();
      this.name = userinfo.getName();
      this.gender = userinfo.getGender();
      this.email = userinfo.getEmail();
      this.birth = userinfo.getBirth();
      this.address = userinfo.getAddress();
      this.phone = userinfo.getPhone();
      this.regdate = userinfo.getRegdate();
      this.logdate = userinfo.getLogdate();
      this.status = userinfo.getStatus();
      this.enabled = userinfo.getEnabled();
     
      this.userinfoAuthList= new ArrayList<GrantedAuthority>();
      for (SecurityAuth auth : userinfo.getSecurityAuthList()) {
    	  userinfoAuthList.add(new SimpleGrantedAuthority(auth.getAuth()));
      }
     
   }

   // 인증된 사용자의 권한 정보를 반환
   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return userinfoAuthList;
   }

   // 인증된 사용자의 비밀번호를 반환
   @Override
   public String getPassword() {
      return pw;
   }

   // 인증된 사용자의 식별자(아이디)를 반환
   @Override
   public String getUsername() {
      return id;
   }

   // 인증 사용자의 계정 유효 기간 관련 상태 정보를 반환
   // ▶ false: 계정 유효 기간 만료 / true: 계정 유효 기간 안만료
   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   // 인증 사용자의 계정 잠금 관련 상태 정보를 반환
   // ▶ false: 계정 잠금 상태 / true: 계정 해제 상태
   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   // 인증 사용자의 비밀번호 유효 기간 관련 상태 정보를 반환
   // ▶ false: 비밀번호 유효 기간 만료 / true: 비밀번호 유효 기간 안만료
   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   // 인증 사용자의 활성화 상태 정보를 반환
   // ▶ false: 사용자 비활성화 상태 / true: 사용자 활성화 상태
   @Override
   public boolean isEnabled() {
     return enabled.equals("0");
   }
}