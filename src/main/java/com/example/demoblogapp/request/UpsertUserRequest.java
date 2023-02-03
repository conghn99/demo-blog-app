package com.example.demoblogapp.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpsertUserRequest {
    private String name;
    private String email;
    private String avatar;
    private String password;
}
