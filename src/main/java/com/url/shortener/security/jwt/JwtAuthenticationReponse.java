package com.url.shortener.security.jwt;


public class JwtAuthenticationReponse {
	

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public JwtAuthenticationReponse(String token) {
		super();
		this.token = token;
	}
	

}
