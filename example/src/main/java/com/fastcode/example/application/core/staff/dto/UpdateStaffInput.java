package com.fastcode.example.application.core.staff.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UpdateStaffInput {

    @NotNull(message = "active Should not be null")
    private Boolean active;

    @Length(max = 50, message = "email must be less than 50 characters")
    private String email;

    @NotNull(message = "firstName Should not be null")
    @Length(max = 45, message = "firstName must be less than 45 characters")
    private String firstName;

    @NotNull(message = "lastName Should not be null")
    @Length(max = 45, message = "lastName must be less than 45 characters")
    private String lastName;

    @NotNull(message = "lastUpdate Should not be null")
    private LocalDateTime lastUpdate;

    @Length(max = 40, message = "password must be less than 40 characters")
    private String password;

    @NotNull(message = "staffId Should not be null")
    private Integer staffId;

    private Short storeId;

    @NotNull(message = "username Should not be null")
    @Length(max = 16, message = "username must be less than 16 characters")
    private String username;

    private Integer addressId;
    private Long versiono;
}
