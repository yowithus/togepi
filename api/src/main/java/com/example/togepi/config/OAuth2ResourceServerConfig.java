package com.example.togepi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources
			.resourceId("resource_id")
			.stateless(false);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			//                .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')")
			//                .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write')")
			//                .antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('write')")
			//                .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write')")
			//                .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write')")
//			.antMatchers("/hello").hasRole("ADMIN");
		.antMatchers(HttpMethod.GET, "/token").access("#oauth2.hasScope('read')");
	}
}
