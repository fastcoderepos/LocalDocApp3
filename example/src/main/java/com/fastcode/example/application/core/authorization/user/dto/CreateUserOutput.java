package com.fastcode.example.application.core.authorization.user.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserOutput {

    private String emailAddress;
    private String firstName;
    private Long id;
    private Boolean isActive;
    private Boolean isEmailConfirmed;
    private String lastName;
    private String phoneNumber;
    private String userName;
    private String theme;
    private String language;
}
