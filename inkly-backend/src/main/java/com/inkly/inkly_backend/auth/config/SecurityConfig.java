package com.inkly.inkly_backend.auth.config;

import com.inkly.inkly_backend.auth.filter.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public SecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {

        httpSecurity.csrf(csrf->csrf.disable())
                .exceptionHandling(
                        exception->
                                exception.authenticationEntryPoint(customAuthenticationEntryPoint)
                )
                .authorizeHttpRequests(
                        auth->auth.requestMatchers(
                                "/v1/auth/user/register",
                                "/v1/auth/user/refresh",
                                "/v1/auth/user/login"
                        ).permitAll()
                                .anyRequest().authenticated()
                ).sessionManagement(
                        session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return httpSecurity.build();
    }
}
