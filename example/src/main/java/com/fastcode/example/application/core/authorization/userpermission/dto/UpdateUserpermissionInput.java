package com.fastcode.example.application.core.authorization.userpermission.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserpermissionInput {

    @NotNull(message = "permissionId Should not be null")
    private Long permissionId;

    private Boolean revoked;

    @NotNull(message = "userId Should not be null")
    private Long userId;

    private Long versiono;
}
