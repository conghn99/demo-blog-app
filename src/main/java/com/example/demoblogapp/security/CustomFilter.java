package com.example.demoblogapp.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CustomFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        // Lay username tu trong session
//         String username = (String) request.getSession().getAttribute("TECHMASTER_SESSION");
//
//
//
//        // Kiem tra username
//        if (username == null) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // Lay thong tin user
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//        // Tao doi tuong xac thuc
//        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//        // Xac thuc thanh cong, luu object authentication vao securitycontextholder
//        SecurityContextHolder.getContext().setAuthentication(user);
//        filterChain.doFilter(request, response);

        // Lay token tu header
        String authorizationToken = request.getHeader("Authorization");
        if (authorizationToken == null || !authorizationToken.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationToken.substring(7); // cat chuoi tu sau chu "Bearer"

        // Lay thong tin tu trong token
        Claims claims = jwtTokenUtil.getClaimsFromToken(token);

        // Lay username tu trong token
        String email = claims.getSubject();

        // Lay thong tin user
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // Tao doi tuong xac thuc
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // Xac thuc thanh cong, luu object authentication vao securitycontextholder
        SecurityContextHolder.getContext().setAuthentication(user);
        filterChain.doFilter(request, response);
    }
}
