package com.example.StudentManagementHonors.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/students/register", "/api/students/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/students/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/students/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/students/**").permitAll()// Allow registration and login without authentication
                                .anyRequest().authenticated() // Secure all other endpoints
                )
                .formLogin(AbstractHttpConfigurer::disable) // Disable form-based login
                .httpBasic(AbstractHttpConfigurer::disable) // Disable basic authentication
                .csrf(AbstractHttpConfigurer::disable); // Disable CSRF for testing purposes (enable in production)

        return http.build();
    }
}
