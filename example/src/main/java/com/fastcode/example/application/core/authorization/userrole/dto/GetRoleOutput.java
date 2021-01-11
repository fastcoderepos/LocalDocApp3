package com.fastcode.example.application.core.authorization.userrole.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetRoleOutput {

    private Long id;
    private String name;
    private String displayName;
    private Long userroleRoleId;
    private Long userroleUserId;
}
