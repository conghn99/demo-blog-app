package com.example.demoblogapp.controller;

import com.example.demoblogapp.request.LoginRequest;
import com.example.demoblogapp.response.LoginResponse;
import com.example.demoblogapp.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Tao doi tuong
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );
        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(token);

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        session.setAttribute("TECHMASTER_SESSION", authentication.getName());

        // Tao token va tra ve cho client
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String tokenJwt = jwtTokenUtil.generateToken(userDetails);

        LoginResponse loginResponse = new LoginResponse(userDetails, tokenJwt, true);
        return ResponseEntity.ok(loginResponse);
    }
}
