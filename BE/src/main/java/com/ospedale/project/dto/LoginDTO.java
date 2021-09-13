package com.ospedale.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDTO {
    public String username;
    public String password;

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
