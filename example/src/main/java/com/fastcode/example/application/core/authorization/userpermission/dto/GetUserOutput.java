package com.fastcode.example.application.core.authorization.userpermission.dto;

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
    private Long userpermissionPermissionId;
    private Long userpermissionUserId;
}
