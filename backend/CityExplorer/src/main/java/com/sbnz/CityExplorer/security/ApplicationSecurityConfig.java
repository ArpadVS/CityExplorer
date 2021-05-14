package com.sbnz.CityExplorer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private JwtUserDetailsService jwtUserDetailsService;
	private VerifyTokenFilter verifyTokenFilter;

	@Autowired
	public ApplicationSecurityConfig(JwtUserDetailsService service, VerifyTokenFilter filter) {
		this.jwtUserDetailsService = service;
		this.verifyTokenFilter = filter;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// configure AuthenticationManager so that it knows from where to load
	// user for matching credentials
	// Use BCryptPasswordEncoder
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	// Defining access rights to specific URLs
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()

			// comunication between client and server is stateless
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

			// don't authenticate this particular request
			.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/api/**")
			.permitAll()
			.antMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/registration","/api/activities/search" )
			.permitAll()
			.antMatchers(HttpMethod.GET, "/api/activities/", "/api/**").permitAll()

			// all other requests need to be authenticated
			.anyRequest().authenticated().and()
			
			// intercept every request and add filter
			.addFilterBefore(verifyTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) {
		// TokenAuthenticationFilter will ignore the following
		web.ignoring().antMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/registration");
		web.ignoring().antMatchers(HttpMethod.GET,"/api/**");
	}
}
