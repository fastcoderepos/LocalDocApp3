package com.fastcode.example.application.core.language.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindLanguageByIdOutput {

    private Integer languageId;
    private LocalDateTime lastUpdate;
    private String name;
    private Long versiono;
}
