package com.laptrinhjava.demo.DTO;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.Getter;

@Data
public class UserRegistration {
	@NotEmpty(message = "Missing username")
    private String userName;
	
	@NotEmpty(message = "Missing password")
    private String password;
	@NotEmpty(message = "Missing Confirmpassword")
    private String confirmPassword;

	@NotEmpty(message = "Missing email")
    private String email;



    @Override
    public String toString() {
    	String s="username: "+this.userName+",password:"+this.password+",confirm-pass: "+this.confirmPassword+",email: "+this.email;
    	return s;
    }
   
}
