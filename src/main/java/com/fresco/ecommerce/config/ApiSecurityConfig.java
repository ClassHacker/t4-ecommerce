package com.fresco.ecommerce.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fresco.ecommerce.repo.UserRepo;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Autowired
    ApiAuthenticationEntryPoint apiAuthenticationEntryPoint;
    
	@Override
	public void configure(WebSecurity web) throws Exception {
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(
                username -> userRepo.findByUsername(username)
                    .orElseThrow(
                        () -> new UsernameNotFoundException("User " + username + " not found.")));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .authorizeRequests().antMatchers("/api/public/**").permitAll()
        .antMatchers("/h2-console/**").permitAll()
        .anyRequest().authenticated()
        .and().exceptionHandling().authenticationEntryPoint(apiAuthenticationEntryPoint)
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().disable();
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public RegistrationBean jwtAuthFilterRegister(JwtAuthenticationFilter filter) {
	    final FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
	    registrationBean.setFilter((Filter) filter);
//	    registrationBean.addUrlPatterns("/rest/*");

	    return registrationBean;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
