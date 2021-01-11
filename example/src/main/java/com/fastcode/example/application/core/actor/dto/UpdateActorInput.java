package com.fastcode.example.application.core.actor.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UpdateActorInput {

    @NotNull(message = "actorId Should not be null")
    private Integer actorId;

    @NotNull(message = "firstName Should not be null")
    @Length(max = 45, message = "firstName must be less than 45 characters")
    private String firstName;

    @NotNull(message = "lastName Should not be null")
    @Length(max = 45, message = "lastName must be less than 45 characters")
    private String lastName;

    @NotNull(message = "lastUpdate Should not be null")
    private LocalDateTime lastUpdate;

    private Long versiono;
}
