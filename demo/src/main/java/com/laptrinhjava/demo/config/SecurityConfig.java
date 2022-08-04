package com.laptrinhjava.demo.config;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.laptrinhjava.demo.DTO.User;
import com.laptrinhjava.demo.exception.ErrorException;
import com.laptrinhjava.demo.service.CustomerDetailService;
import com.laptrinhjava.demo.service.UserService;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	CustomerDetailService customer;
	
	
	
	// cấu hình bean cách thức mã hóa ở đây là Bcrypt
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
			.authorizeHttpRequests()
			//cho phép các link trong ".." được xác thực 
			.antMatchers("/css/**").permitAll()
			.antMatchers("/fonts/**").permitAll()
			.antMatchers("/images/**").permitAll()
			.antMatchers("/vendor/**").permitAll()
			.antMatchers("/products/**").permitAll()
			.antMatchers("/productDetail**").permitAll()
			.antMatchers("/search**").permitAll()
			.antMatchers("/productDetail/**").permitAll()
			.antMatchers("/js/**").permitAll()
			.antMatchers("/api/**").permitAll()
			.antMatchers("/home").permitAll()
			.antMatchers("/login*").permitAll()
			.antMatchers("/register/**").permitAll()
			// còn lại phải xác thực bằng cách login
			.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
//				.defaultSuccessUrl("/home")
				.successHandler(new AuthenticationSuccessHandler() {
					
					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) throws IOException, ServletException {
						// TODO Auto-generated method stub
						HttpSession httpSession=request.getSession();
						response.sendRedirect(String.valueOf(httpSession.getAttribute("url_pre")));
					}
				})
				.failureHandler(new AuthenticationFailureHandler() {
					 @Override
		                public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		                        AuthenticationException exception) throws IOException, ServletException {
		                    String error = exception.getMessage();
		                    System.out.println("A failed login attempt with email: "
		                                        + ". Reason: " + error);
		 
		                    String redirectUrl =  "/login?error=false";
		                    System.out.println(redirectUrl);
		                    
		                    
		                    response.sendRedirect(redirectUrl);
		                }
				})
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessHandler(new LogoutSuccessHandler() {
				
				@Override
				public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
						throws IOException, ServletException {
					// TODO Auto-generated method stub
					System.out.println(request.getHeader("referer"));
					response.sendRedirect(request.getHeader("referer"));
				}
			})
			.invalidateHttpSession(true)
			.permitAll()
			.and()
			.httpBasic()
			;
	}
	// Xác thực tài khoản đăng nhập bằng dùng userdetail từ customerdetailservice
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth
	    .userDetailsService(customer).passwordEncoder(passwordEncoder());
//	      .inMemoryAuthentication()
//	        .withUser("user").password(passwordEncoder().encode("123")).roles("USER");
	  }
	
}
	