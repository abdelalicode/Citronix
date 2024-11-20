package com.youfarm.citronix.authentication;

import com.youfarm.citronix.dto.UserReturnDTO;
import lombok.*;

@Setter
@Getter
@Builder

public class LoginResponse {

    private String token;
    private UserReturnDTO user;

    public LoginResponse(String token, UserReturnDTO user) {
        this.token = token;
        this.user = user;
    }
}
