package com.inkly.inkly_backend.auth.filter;

import com.inkly.inkly_backend.auth.exception.JwtTokenExpiredException;
import com.inkly.inkly_backend.auth.exception.JwtTokenInvalidException;
import com.inkly.inkly_backend.auth.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public JwtAuthenticationFilter(JwtService jwtService, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.jwtService = jwtService;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {


        String token = request.getHeader("Authorization");

        if(token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }

        try {
            token = token.substring(7);

            Claims claims = jwtService.validateToken(token);

            String username = claims.getSubject();

            List<String> roles = claims.get("authorities", List.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    roles.stream().map(SimpleGrantedAuthority::new).toList()
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }catch (ExpiredJwtException exception){
            customAuthenticationEntryPoint.commence(request,response, new JwtTokenExpiredException("Authentication token is expired"));
            return;
        }catch (JwtException | IllegalArgumentException ex) {
            customAuthenticationEntryPoint.commence(request,response, new JwtTokenInvalidException("Authentication token is invalid"));
            return;
        }

       filterChain.doFilter(request,response);

    }
}
