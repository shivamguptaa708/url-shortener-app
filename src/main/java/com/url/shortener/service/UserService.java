package com.url.shortener.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.url.shortener.dtos.LoginRequest;
import com.url.shortener.models.User;
import com.url.shortener.repository.UserRepository;
import com.url.shortener.security.jwt.JwtAuthenticationReponse;
import com.url.shortener.security.jwt.JwtUtils;
import com.url.shortener.service.UserDetailsImp;

//import lombok.AllArgsConstructor;


@Service
//@AllArgsConstructor
public class UserService {
	
//	private PasswordEncoder passwordEncoder;
//	private UserRepository userRepository;
	
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    // Manual constructor injection
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }
	
	public User registerUser(User user)
	{
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public JwtAuthenticationReponse loginUser(LoginRequest loginRequest)
	{
		Authentication authentication = authenticationManager.authenticate(
				
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
		String jwt = jwtUtils.generateToken(userDetails);
		return new JwtAuthenticationReponse(jwt);
		
		
	}

	public User findByUsername(String name) {
	
		return userRepository.findByUsername(name).orElseThrow(() -> new UsernameNotFoundException("User not found with username : " + name));
	}
	

}
