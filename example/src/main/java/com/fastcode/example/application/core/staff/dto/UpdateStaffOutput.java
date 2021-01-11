package com.fastcode.example.application.core.staff.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStaffOutput {

    private Boolean active;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime lastUpdate;
    private String password;
    private Integer staffId;
    private Short storeId;
    private String username;
    private Integer addressId;
    private Integer addressDescriptiveField;
}
