package com.example.JWT.config;

import jakarta.validation.constraints.NotBlank;

public class AuthRequest {

    @NotBlank(message = "Username must not be blank")
    private String username;
    @NotBlank(message = "Password must not be blank")
    private String password;

    public AuthRequest() {
    }

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthRequest{" +
                "username='" + username + '\'' +
                '}';
    }
}
