package com.fastcode.example.domain.core.filmcategory;

import java.io.Serializable;
import java.time.*;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilmCategoryId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer categoryId;
    private Integer filmId;

    public FilmCategoryId(Integer categoryId, Integer filmId) {
        this.categoryId = categoryId;
        this.filmId = filmId;
    }
}
