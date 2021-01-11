package com.fastcode.example.application.core.authorization.user.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CreateUserInput {

    @Length(max = 256, message = "emailAddress must be less than 256 characters")
    private String emailAddress;

    @Length(max = 32, message = "firstName must be less than 32 characters")
    private String firstName;

    private Boolean isActive;

    private Boolean isEmailConfirmed;

    @Length(max = 32, message = "lastName must be less than 32 characters")
    private String lastName;

    @Length(max = 128, message = "password must be less than 128 characters")
    private String password;

    @Length(max = 32, message = "phoneNumber must be less than 32 characters")
    private String phoneNumber;

    @Length(max = 32, message = "userName must be less than 32 characters")
    private String userName;
}
