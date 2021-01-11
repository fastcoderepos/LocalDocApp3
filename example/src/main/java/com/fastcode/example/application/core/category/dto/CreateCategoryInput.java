package com.fastcode.example.application.core.category.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CreateCategoryInput {

    @NotNull(message = "categoryId Should not be null")
    private Integer categoryId;

    @NotNull(message = "lastUpdate Should not be null")
    private LocalDateTime lastUpdate;

    @NotNull(message = "name Should not be null")
    @Length(max = 25, message = "name must be less than 25 characters")
    private String name;
}
