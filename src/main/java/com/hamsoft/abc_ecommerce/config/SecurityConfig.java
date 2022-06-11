package com.hamsoft.abc_ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @project onewa-user-service
 * @Author kabiruahmed on 04/04/2021
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
        return http.build();
    }
}

