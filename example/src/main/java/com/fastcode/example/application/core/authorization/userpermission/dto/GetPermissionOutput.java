package com.fastcode.example.application.core.authorization.userpermission.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPermissionOutput {

    private Long id;
    private String name;
    private String displayName;
    private Long userpermissionPermissionId;
    private Long userpermissionUserId;
}
