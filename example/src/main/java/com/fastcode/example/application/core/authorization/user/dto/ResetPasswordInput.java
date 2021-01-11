package com.fastcode.example.application.core.authorization.user.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordInput {

    @NotNull
    String token;

    @NotNull
    String password;
}
