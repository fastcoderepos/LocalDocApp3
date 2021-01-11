package com.fastcode.example.application.core.filmactor.dto;

import java.math.BigDecimal;
import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetFilmOutput {

    private String description;
    private Integer filmId;
    private LocalDateTime lastUpdate;
    private Short length;
    private String rating;
    private Integer releaseYear;
    private Short rentalDuration;
    private BigDecimal rentalRate;
    private BigDecimal replacementCost;
    private String title;
    private Integer filmActorActorId;
    private Integer filmActorFilmId;
}
