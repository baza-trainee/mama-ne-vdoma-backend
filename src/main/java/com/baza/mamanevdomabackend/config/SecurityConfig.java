package com.baza.mamanevdomabackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorization ->
                        authorization
                                .requestMatchers("/").permitAll()
                                .anyRequest().authenticated()
                ).oauth2Login(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .logout(l ->
                        l.logoutSuccessUrl("/logout"))
                .build();
    }
}
