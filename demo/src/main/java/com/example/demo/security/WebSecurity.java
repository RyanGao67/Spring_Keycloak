package com.example.demo.security;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;


@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //------> provide this annotation @Secured   -------> method level security configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception{
//  It will create the token authentication filter that will intercept the requests and it will extract
//  the authorization bearer token and it will expect that token to be jwt rather than a packet token.
//  And that will use that jwt token to see if it's valid.

//  For example, let's provide a security configuration that will make sure that http get request sent to
//  users end point will be allowed only if user has granted a profile scope of access when client
//  application requested an access token.
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
        http
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/users/status/check")
                .hasAnyRole("Developer")
                // this is equal to hasAuthority("ROLE_Developer")    ---------------------> check role
                // .hasAuthority("SCOPE_profile")                     ---------------------> check authority
            .anyRequest().authenticated()
            .and()
            .oauth2ResourceServer()
            .jwt()
                // So this(above two lines) will make the framework to do a lot of work behind the scenes for us.
                // It will create the bearer token authentication filter that will intercept the requests
                // and it will extract the authorization bearer token and it will expect that token to be jwt
                // rather than a packet token. And that will use that jwt token to see if it's valid.
            .jwtAuthenticationConverter(jwtAuthenticationConverter);
    }
}
