package com.fastcode.example.domain.core.film;

import com.fastcode.example.domain.core.abstractentity.AbstractEntity;
import com.fastcode.example.domain.core.language.LanguageEntity;
import java.math.BigDecimal;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "FILM")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class FilmEntity extends AbstractEntity {

    @Basic
    @Column(name = "RENTAL_RATE", nullable = false)
    private BigDecimal rentalRate;

    @Basic
    @Column(name = "RENTAL_DURATION", nullable = true)
    private Short rentalDuration;

    @Basic
    @Column(name = "length", nullable = true)
    private Short length;

    @Basic
    @Column(name = "rating", nullable = true)
    private String rating;

    @Basic
    @Column(name = "description", nullable = true)
    private String description;

    @Basic
    @Column(name = "REPLACEMENT_COST", nullable = false)
    private BigDecimal replacementCost;

    @Basic
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "FILM_ID", nullable = false)
    private Integer filmId;

    @Basic
    @Column(name = "LAST_UPDATE", nullable = false)
    private LocalDateTime lastUpdate;

    @Basic
    @Column(name = "RELEASE_YEAR", nullable = true)
    private Integer releaseYear;

    @ManyToOne
    @JoinColumn(name = "LANGUAGE_ID")
    private LanguageEntity language;
}
