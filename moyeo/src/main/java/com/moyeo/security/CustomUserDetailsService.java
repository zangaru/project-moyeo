package com.moyeo.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.moyeo.dao.UserinfoDAO;
import com.moyeo.dto.Userinfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final UserinfoDAO userinfoDAO;
	
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		Userinfo userinfo=userinfoDAO.selectUserinfoById(id);
		
		if(userinfo == null) {
			throw new UsernameNotFoundException(id);
		}
		
		return new CustomUserDetails(userinfo);
	}
}
