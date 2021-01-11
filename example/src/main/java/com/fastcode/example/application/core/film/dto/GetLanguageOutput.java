package com.fastcode.example.application.core.film.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetLanguageOutput {

    private Integer languageId;
    private LocalDateTime lastUpdate;
    private String name;
    private Integer filmFilmId;
}
