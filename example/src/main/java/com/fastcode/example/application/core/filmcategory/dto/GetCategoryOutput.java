package com.fastcode.example.application.core.filmcategory.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCategoryOutput {

    private Integer categoryId;
    private LocalDateTime lastUpdate;
    private String name;
    private Integer filmCategoryCategoryId;
    private Integer filmCategoryFilmId;
}
