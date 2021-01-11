package com.fastcode.example.domain.core.filmactor;

import java.io.Serializable;
import java.time.*;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilmActorId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer actorId;
    private Integer filmId;

    public FilmActorId(Integer actorId, Integer filmId) {
        this.actorId = actorId;
        this.filmId = filmId;
    }
}
