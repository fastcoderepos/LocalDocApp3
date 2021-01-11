package com.fastcode.example.application.core.authorization.userrole.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindUserroleByIdOutput {

    private Long roleId;
    private Long userId;
    private String roleDescriptiveField;
    private String userDescriptiveField;
    private Long versiono;
}
