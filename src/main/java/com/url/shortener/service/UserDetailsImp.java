package com.url.shortener.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.url.shortener.models.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetailsImp implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String username;
	private String email;
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;
	 
	
	public UserDetailsImp(long id, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}
	
	public static UserDetailsImp build(User user)
	{
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
		
		return new UserDetailsImp(
				user.getId(),
				user.getUsername(),
				user.getEmail(),
				user.getPassword(),
				Collections.singletonList(authority)
			);
		
	}

	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

}
