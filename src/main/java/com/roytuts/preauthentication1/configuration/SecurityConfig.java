package com.roytuts.preauthentication1.configuration;

import com.roytuts.preauthentication1.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterAfter(customHeaderAuthenticationFilter(), RequestHeaderAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/user").access("hasRole('ADMIN')")
                .antMatchers("/blogs").access("hasRole('ADMIN')")
                .anyRequest().authenticated();

        return http.build();
    }

    @Bean(name = "customHeaderAuthenticationFilter")
    public RequestHeaderAuthenticationFilter customHeaderAuthenticationFilter() throws Exception {
        RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter = new RequestHeaderAuthenticationFilter();
        requestHeaderAuthenticationFilter.setPrincipalRequestHeader("employeetype");
        requestHeaderAuthenticationFilter.setPrincipalRequestHeader("name");

        requestHeaderAuthenticationFilter.setAuthenticationManager(authenticationManager());
        return requestHeaderAuthenticationFilter;
    }

    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        final List<AuthenticationProvider> providers = new ArrayList<>(1);
        providers.add(preauthAuthProvider());
        return new ProviderManager(providers);
    }

    @Bean(name = "preAuthProvider")
    PreAuthenticatedAuthenticationProvider preauthAuthProvider() throws Exception {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(userDetailsServiceWrapper());
        return provider;
    }

    @Bean
    UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> userDetailsServiceWrapper() throws Exception {
        UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> wrapper = new UserDetailsByNameServiceWrapper<>();
        wrapper.setUserDetailsService(customUserDetailsService);
        return wrapper;
    }

}

/*
{
    "uid":"p.sunthar@iitb.ac.in",
    "employeenumber":"i06011",
    "employeetype":"fac",
    "cn":"P Sunthar"
}
 */