package com.example.demoblogapp.response;

import com.example.demoblogapp.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private UserDetails auth;
    private String token;
    @JsonProperty("isAuthenticated")
    private boolean isAuthenticated;
}
