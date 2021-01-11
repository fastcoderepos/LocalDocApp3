package com.fastcode.example.domain.core.country;

import com.fastcode.example.domain.core.abstractentity.AbstractEntity;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "COUNTRY")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class CountryEntity extends AbstractEntity {

    @Basic
    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "COUNTRY_ID", nullable = false)
    private Integer countryId;

    @Basic
    @Column(name = "LAST_UPDATE", nullable = false)
    private LocalDateTime lastUpdate;
}
