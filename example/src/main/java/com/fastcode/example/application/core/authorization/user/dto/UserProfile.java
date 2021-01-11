package com.fastcode.example.application.core.authorization.user.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UserProfile {

    @NotNull(message = "emailAddress Should not be null")
    @Length(max = 256, message = "emailAddress must be less than 256 characters")
    @Pattern(
        regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
        message = "Email Address should be valid"
    )
    private String emailAddress;

    @NotNull(message = "firstName Should not be null")
    @Length(max = 32, message = "firstName must be less than 32 characters")
    private String firstName;

    @NotNull(message = "lastName Should not be null")
    @Length(max = 32, message = "lastName must be less than 32 characters")
    private String lastName;

    @Length(max = 32, message = "phoneNumber must be less than 32 characters")
    private String phoneNumber;

    @NotNull(message = "userName Should not be null")
    @Length(max = 32, message = "userName must be less than 32 characters")
    private String userName;

    private String theme;
    private String language;
}
