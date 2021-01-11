package com.fastcode.example.application.core.authorization.user.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserpreferenceOutput {

    private Long id;
    private String userName;
    private Boolean isActive;
    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private Boolean isEmailConfirmed;
    private Long userId;
}
