package com.baza.mamanevdomabackend.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AppUserService userService) throws Exception {
        return http.authorizeHttpRequests(authorization ->
                                authorization
//                                .requestMatchers("/").permitAll()
                                        .anyRequest().authenticated()
                ).formLogin(Customizer.withDefaults())
                .oauth2Login(oc -> oc.userInfoEndpoint(ui ->
                        ui.userService(userService.oauth2LoginHandler())
                                .oidcUserService(userService.oidcLoginHandler())
                ))
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successLogger() {
        return event -> log.info("success: {}", event.getAuthentication());
    }

}
