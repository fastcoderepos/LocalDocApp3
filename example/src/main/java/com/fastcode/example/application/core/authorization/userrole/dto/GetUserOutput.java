package com.fastcode.example.application.core.authorization.userrole.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserOutput {

    private String lastName;
    private String userName;
    private Boolean isActive;
    private String firstName;
    private String password;
    private String emailAddress;
    private Long id;
    private Long userroleRoleId;
    private Long userroleUserId;
}
