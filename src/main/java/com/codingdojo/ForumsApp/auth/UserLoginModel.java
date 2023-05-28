package com.codingdojo.ForumsApp.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserLoginModel {
	
	@Email(message = "Please use a valid email")
	@NotEmpty(message="This field should not be blank")
	private String loginEmail;
	
	@NotEmpty(message="This field should not be blank")
	@Size(min = 3,max = 100 , message = "password should be 3 or more characters")
	private String loginPassword;
	
	
	//super constructor
	public UserLoginModel() {
		// TODO Auto-generated constructor stub	
	}

	//getters and setters
	public String getLoginEmail() {
		return loginEmail;
	}


	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}


	public String getLoginPassword() {
		return loginPassword;
	}


	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	
	
}
