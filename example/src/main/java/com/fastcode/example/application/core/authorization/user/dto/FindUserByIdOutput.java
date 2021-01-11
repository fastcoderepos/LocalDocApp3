package com.fastcode.example.application.core.authorization.user.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindUserByIdOutput {

    private String emailAddress;
    private String firstName;
    private Long id;
    private Boolean isActive;
    private Boolean isEmailConfirmed;
    private String lastName;
    private String phoneNumber;
    private String userName;
    private Long versiono;
    private String theme;
    private String language;
}
