package com.fastcode.example.domain.core.filmcategory;

import com.fastcode.example.domain.core.abstractentity.AbstractEntity;
import com.fastcode.example.domain.core.category.CategoryEntity;
import com.fastcode.example.domain.core.film.FilmEntity;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "FILM_CATEGORY")
@IdClass(FilmCategoryId.class)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class FilmCategoryEntity extends AbstractEntity {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "CATEGORY_ID", nullable = false)
    private Integer categoryId;

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "FILM_ID", nullable = false)
    private Integer filmId;

    @Basic
    @Column(name = "LAST_UPDATE", nullable = false)
    private LocalDateTime lastUpdate;

    @ManyToOne
    @JoinColumn(name = "FILM_ID", insertable = false, updatable = false)
    private FilmEntity film;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", insertable = false, updatable = false)
    private CategoryEntity category;
}
